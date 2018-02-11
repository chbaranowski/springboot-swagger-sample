package io.springboot.samples.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class SpringBootServerApp {
	
	@Configuration
	public static class WebConfig extends WebMvcConfigurationSupport{

	    @Bean
	    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
	        RestRequestMappingHandlerMapping handlerMapping = new RestRequestMappingHandlerMapping();
	        handlerMapping.setOrder(0);
	        handlerMapping.setInterceptors(getInterceptors());
	        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());
	        PathMatchConfigurer configurer = getPathMatchConfigurer();
	        if (configurer.isUseSuffixPatternMatch() != null) {
	            handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
	        }
	        if (configurer.isUseRegisteredSuffixPatternMatch() != null) {
	            handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
	        }
	        if (configurer.isUseTrailingSlashMatch() != null) {
	            handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
	        }
	        if (configurer.getPathMatcher() != null) {
	            handlerMapping.setPathMatcher(configurer.getPathMatcher());
	        }
	        if (configurer.getUrlPathHelper() != null) {
	            handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
	        }
	        return handlerMapping;
	    }
	}

	public static void main(String[] args) {
		new SpringApplication(SpringBootServerApp.class).run(args);
	}
	
}
