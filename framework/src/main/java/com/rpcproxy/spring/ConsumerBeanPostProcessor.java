package com.rpcproxy.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;

import com.rpcproxy.consumer.ConsumerBeanFactory;
import com.rpcproxy.util.RpcContextHolder;

public class ConsumerBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {

	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		try {
			ConsumerBeanFactory consumerBeanFactory = RpcContextHolder.getConsumerBeanFactory();
			Object consumer = consumerBeanFactory.getConsumer(beanName);
			if (consumer != null) {
				return consumer;
			}
		} catch (Exception e) {
		}
		return super.postProcessBeforeInstantiation(beanClass, beanName);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

}
