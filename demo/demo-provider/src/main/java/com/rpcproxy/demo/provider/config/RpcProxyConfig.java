package com.rpcproxy.demo.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rpcproxy.spring.EnableRpcProxy;
import com.rpcproxy.spring.SpringProviderConfBean;

@Configuration
@EnableRpcProxy
public class RpcProxyConfig {

	@Bean
	public SpringProviderConfBean serviceProvider() {
		SpringProviderConfBean providerConfBean = new SpringProviderConfBean();
		providerConfBean.addInterfaceName(com.rpcproxy.demo.api.service.DemoService.class.getName());
		return providerConfBean;
	}

}
