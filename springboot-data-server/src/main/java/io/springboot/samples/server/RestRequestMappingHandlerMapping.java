package io.springboot.samples.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.swagger.annotations.Api;

/**
 * Support for spring annotation in interface see 
 * https://stackoverflow.com/questions/8002514/spring-mvc-annotated-controller-interface-with-pathvariable 
 */
class RestRequestMappingHandlerMapping extends RequestMappingHandlerMapping{

    public HandlerMethod testCreateHandlerMethod(Object handler, Method method){
        return createHandlerMethod(handler, method);
    }

    @Override
    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
        HandlerMethod handlerMethod;
        if (handler instanceof String) {
            String beanName = (String) handler;
            handlerMethod = new RestServiceHandlerMethod(beanName,getApplicationContext().getAutowireCapableBeanFactory(), method);
        }
        else {
            handlerMethod = new RestServiceHandlerMethod(handler, method);
        }
        return handlerMethod;
    }


    public static class RestServiceHandlerMethod extends HandlerMethod{

        private Method interfaceMethod;


        public RestServiceHandlerMethod(Object bean, Method method) {
            super(bean,method);
            changeType();
        }

        public RestServiceHandlerMethod(Object bean, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
            super(bean,methodName,parameterTypes);
            changeType();
        }

        public RestServiceHandlerMethod(String beanName, BeanFactory beanFactory, Method method) {
            super(beanName,beanFactory,method);
            changeType();
        }


        private void changeType(){
            for(Class<?> clazz : getMethod().getDeclaringClass().getInterfaces()){
                if(clazz.isAnnotationPresent(Api.class)){
                    try{
                        interfaceMethod = clazz.getMethod(getMethod().getName(), getMethod().getParameterTypes());
                        break;      
                    }catch(NoSuchMethodException e){

                    }
                }
            }
            MethodParameter[] params = super.getMethodParameters();
            for(int i=0;i<params.length;i++){
                params[i] = new RestServiceMethodParameter(params[i]);
            }
        }

        private class RestServiceMethodParameter extends MethodParameter{

            private volatile Annotation[] parameterAnnotations;

            public RestServiceMethodParameter(MethodParameter methodParameter){
                super(methodParameter);
            }


            @Override
            public Annotation[] getParameterAnnotations() {
                if (this.parameterAnnotations == null){
                        if(RestServiceHandlerMethod.this.interfaceMethod!=null) {
                            Annotation[][] annotationArray = RestServiceHandlerMethod.this.interfaceMethod.getParameterAnnotations();
                            if (this.getParameterIndex() >= 0 && this.getParameterIndex() < annotationArray.length) {
                                this.parameterAnnotations = annotationArray[this.getParameterIndex()];
                            }
                            else {
                                this.parameterAnnotations = new Annotation[0];
                            }
                        }else{
                            this.parameterAnnotations = super.getParameterAnnotations();
                        }
                }
                return this.parameterAnnotations;
            }

        }

    }

}
