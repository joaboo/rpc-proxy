package com.rpcproxy.provider.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.rpcproxy.exception.ProviderNotFoundException;
import com.rpcproxy.provider.ProviderBean;
import com.rpcproxy.provider.ProviderBeanFactory;
import com.rpcproxy.provider.ProviderBeanRegistry;

public class DefaultProviderBeanFactory implements ProviderBeanRegistry, ProviderBeanFactory {

	private static final ConcurrentMap<String, ProviderBean> providerCache = new ConcurrentHashMap<>();

	@Override
	public Object getProvider(String interfaceName) {
		ProviderBean providerBean = providerCache.get(interfaceName);
		if (providerBean == null) {
			throw new ProviderNotFoundException("Provider not found.[" + interfaceName + "]");
		}
		return providerBean.getTarget();
	}

	@Override
	public ProviderBean getProviderBean(String interfaceName) {
		ProviderBean providerBean = providerCache.get(interfaceName);
		if (providerBean == null) {
			throw new ProviderNotFoundException("ProviderBean not found.[" + interfaceName + "]");
		}
		return providerBean;
	}

	@Override
	public void register(ProviderBean providerBean) {
		providerCache.put(providerBean.getInterfaceName(), providerBean);
	}

}
