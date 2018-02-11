package io.springboot.samples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import feign.Feign;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import io.springboot.samples.api.DataApi;

@Configuration
public class RestClientConfig {

	@Value("${rest.api.host:http://localhost:8080}")
	String restApiHost;

	@Bean
	ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}

	@Bean
	Feign.Builder createFeignBuilder(ObjectMapper objectMapper) {
		return Feign.builder().encoder(new FormEncoder(new JacksonEncoder(objectMapper)))
				.decoder(new JacksonDecoder(objectMapper)).logger(new Slf4jLogger());
	}

	@Bean
	DataApi createDataApi(Feign.Builder feign) {
		return feign.target(DataApi.class, restApiHost);
	}

}
