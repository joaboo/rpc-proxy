package com.rpcproxy.demo.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rpcproxy.spring.EnableRpcProxy;
import com.rpcproxy.spring.SpringConsumerConfBean;

@Configuration
@EnableRpcProxy
public class RpcProxyConfig {

	@Bean
	public SpringConsumerConfBean productServiceConsumer() {
		SpringConsumerConfBean consumerConfBean = new SpringConsumerConfBean();
		consumerConfBean.setFeignClientInterfaceName(com.rpcproxy.demo.consumer.client.DemoClient.class.getName());
		consumerConfBean.addRefererInterfaceName(com.rpcproxy.demo.api.service.DemoService.class.getName());
		return consumerConfBean;
	}

}
