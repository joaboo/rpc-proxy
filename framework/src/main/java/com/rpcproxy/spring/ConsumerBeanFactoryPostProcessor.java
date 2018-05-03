package com.rpcproxy.spring;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;

public class ConsumerBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;

		Map<String, SpringConsumerConfBean> consumerConfBeanMap = beanFactory.getBeansOfType(SpringConsumerConfBean.class);
		for (SpringConsumerConfBean consumerConfBean : consumerConfBeanMap.values()) {
			List<String> refererInterfaceNames = consumerConfBean.getRefererInterfaceNames();
			for (String refererInterfaceName : refererInterfaceNames) {
				AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(refererInterfaceName).getBeanDefinition();
				defaultListableBeanFactory.registerBeanDefinition(refererInterfaceName, beanDefinition);
			}
		}
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

}
