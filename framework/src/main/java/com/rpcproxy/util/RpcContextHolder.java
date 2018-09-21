package com.rpcproxy.util;

import com.rpcproxy.consumer.ConsumerBeanFactory;
import com.rpcproxy.consumer.ConsumerBeanRegistry;
import com.rpcproxy.consumer.support.DefaultConsumerBeanFactory;
import com.rpcproxy.provider.ProviderBeanFactory;
import com.rpcproxy.provider.ProviderBeanRegistry;
import com.rpcproxy.provider.ProviderExecutor;
import com.rpcproxy.provider.servlet.ProviderServletConfig;
import com.rpcproxy.provider.support.DefaultProviderBeanFactory;
import com.rpcproxy.provider.support.DefaultProviderExecutor;
import com.rpcproxy.serialize.Serializer;
import com.rpcproxy.serialize.support.FastJsonSerializer;

public class RpcContextHolder {

	private static final FastJsonSerializer FAST_JSON_SERIALIZER = new FastJsonSerializer();
	private static final DefaultConsumerBeanFactory DEFAULT_CONSUMER_BEAN_FACTORY = new DefaultConsumerBeanFactory();
	private static final DefaultProviderBeanFactory DEFAULT_PROVIDER_BEAN_FACTORY = new DefaultProviderBeanFactory();
	private static final DefaultProviderExecutor DEFAULT_PROVIDER_EXECUTOR = new DefaultProviderExecutor();
	private static final ProviderServletConfig PROVIDER_SERVLET_CONFIG = new ProviderServletConfig();

	public static Serializer getSerializer() {
		return FAST_JSON_SERIALIZER;
	}

	public static ProviderBeanFactory getProviderBeanFactory() {
		return DEFAULT_PROVIDER_BEAN_FACTORY;
	}

	public static ProviderBeanRegistry getProviderBeanRegistry() {
		return DEFAULT_PROVIDER_BEAN_FACTORY;
	}

	public static ProviderExecutor getProviderExecutor() {
		return DEFAULT_PROVIDER_EXECUTOR;
	}

	public static ConsumerBeanFactory getConsumerBeanFactory() {
		return DEFAULT_CONSUMER_BEAN_FACTORY;
	}

	public static ConsumerBeanRegistry getConsumerBeanRegistry() {
		return DEFAULT_CONSUMER_BEAN_FACTORY;
	}

	public static ProviderServletConfig getProviderServletConfig() {
		return PROVIDER_SERVLET_CONFIG;
	}

}
