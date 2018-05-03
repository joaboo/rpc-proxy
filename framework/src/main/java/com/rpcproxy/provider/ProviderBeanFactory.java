package com.rpcproxy.provider;

public interface ProviderBeanFactory {

	public Object getProvider(String interfaceName);

	public ProviderBean getProviderBean(String interfaceName);
}
