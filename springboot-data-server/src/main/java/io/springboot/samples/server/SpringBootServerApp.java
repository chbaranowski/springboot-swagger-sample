package io.springboot.samples.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class SpringBootServerApp {

	@Configuration
	@EnableSwagger2
	public class SwaggerConfig {
		@Bean
		public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build();
		}
	}

	@Configuration
	public static class WebConfig extends WebMvcConfigurationSupport {

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
		
		@Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("swagger-ui.html")
	                .addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
	}

	public static void main(String[] args) {
		new SpringApplication(SpringBootServerApp.class).run(args);
	}

}
