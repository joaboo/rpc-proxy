package com.rpcproxy.consumer.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.rpcproxy.consumer.ConsumerBean;
import com.rpcproxy.consumer.ConsumerBeanFactory;
import com.rpcproxy.consumer.ConsumerBeanRegistry;
import com.rpcproxy.exception.ConsumerNotFoundException;

public class DefaultConsumerBeanFactory implements ConsumerBeanRegistry, ConsumerBeanFactory {

	private static final ConcurrentMap<String, ConsumerBean> consumerCache = new ConcurrentHashMap<>();

	@Override
	public Object getConsumer(String refererInterfaceName) {
		ConsumerBean consumerBean = consumerCache.get(refererInterfaceName);
		if (consumerBean == null) {
			throw new ConsumerNotFoundException("Consumer not found.[" + refererInterfaceName + "]");
		}
		return consumerBean.getRefererTarget();
	}

	@Override
	public ConsumerBean getConsumerBean(String refererInterfaceName) {
		ConsumerBean consumerBean = consumerCache.get(refererInterfaceName);
		if (consumerBean == null) {
			throw new ConsumerNotFoundException("Consumer not found.[" + refererInterfaceName + "]");
		}
		return consumerBean;
	}

	@Override
	public void register(ConsumerBean consumerBean) {
		consumerCache.put(consumerBean.getRefererInterfaceName(), consumerBean);
	}
}
