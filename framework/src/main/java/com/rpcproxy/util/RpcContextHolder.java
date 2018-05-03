package com.rpcproxy.util;

import com.rpcproxy.consumer.ConsumerBeanFactory;
import com.rpcproxy.consumer.ConsumerBeanRegistry;
import com.rpcproxy.consumer.support.DefaultConsumerBeanFactory;
import com.rpcproxy.provider.ProviderBeanFactory;
import com.rpcproxy.provider.ProviderBeanRegistry;
import com.rpcproxy.provider.support.DefaultProviderBeanFactory;
import com.rpcproxy.serialize.Serializer;
import com.rpcproxy.serialize.support.JacksonSerializer;

public class RpcContextHolder {

	private static final JacksonSerializer JACKSON_SERIALIZER = new JacksonSerializer();
	private static final DefaultProviderBeanFactory DEFAULT_PROVIDER_BEAN_FACTORY = new DefaultProviderBeanFactory();
	private static final DefaultConsumerBeanFactory DEFAULT_CONSUMER_BEAN_FACTORY = new DefaultConsumerBeanFactory();

	public static Serializer getSerializer() {
		return JACKSON_SERIALIZER;
	}

	public static ProviderBeanFactory getProviderBeanFactory() {
		return DEFAULT_PROVIDER_BEAN_FACTORY;
	}

	public static ProviderBeanRegistry getProviderBeanRegistry() {
		return DEFAULT_PROVIDER_BEAN_FACTORY;
	}

	public static ConsumerBeanFactory getConsumerBeanFactory() {
		return DEFAULT_CONSUMER_BEAN_FACTORY;
	}

	public static ConsumerBeanRegistry getConsumerBeanRegistry() {
		return DEFAULT_CONSUMER_BEAN_FACTORY;
	}

}
