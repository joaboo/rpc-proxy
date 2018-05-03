package com.rpcproxy.consumer;

public interface ConsumerBeanFactory {

	public Object getConsumer(String refererInterfaceName);

	public ConsumerBean getConsumerBean(String refererInterfaceName);
}
