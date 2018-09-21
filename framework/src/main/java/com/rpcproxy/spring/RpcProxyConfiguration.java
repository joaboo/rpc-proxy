package com.rpcproxy.spring;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rpcproxy.common.RpcConstants;
import com.rpcproxy.provider.servlet.ProviderDispatcherServlet;
import com.rpcproxy.util.SpringContextHolder;

@Configuration
public class RpcProxyConfiguration {

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

	@Bean
	public ConsumerBeanFactoryPostProcessor consumerBeanFactoryPostProcessor() {
		return new ConsumerBeanFactoryPostProcessor();
	}

	@Bean
	public ConsumerBeanPostProcessor consumerBeanPostProcessor() {
		return new ConsumerBeanPostProcessor();
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProviderDispatcherServlet(), RpcConstants.DISPATCH_REQUEST_MAPPING);
		servletRegistrationBean.setAsyncSupported(true);
		return servletRegistrationBean;
	}
}
