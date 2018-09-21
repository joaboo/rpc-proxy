package com.rpcproxy.demo.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
public class FeignClientConfig {

	@Bean
	public Request.Options requestOptions() {
		return new Request.Options(5000, 5000);
	}

}
