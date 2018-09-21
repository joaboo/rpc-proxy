package com.rpcproxy.provider.support;

import java.lang.reflect.Method;

import com.rpcproxy.common.RpcRequest;
import com.rpcproxy.common.RpcResponse;
import com.rpcproxy.provider.ProviderBeanFactory;
import com.rpcproxy.provider.ProviderExecutor;
import com.rpcproxy.util.RpcContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultProviderExecutor implements ProviderExecutor {

	private final ProviderBeanFactory providerBeanFactory;

	public DefaultProviderExecutor() {
		this.providerBeanFactory = RpcContextHolder.getProviderBeanFactory();
	}

	@Override
	public RpcResponse execute(RpcRequest rpcRequest) {
		try {
			Object target = providerBeanFactory.getProvider(rpcRequest.getInterfaceName());
			Method method = target.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
			Object result = method.invoke(target, rpcRequest.getParameters());
			return RpcResponse.success(result);
		} catch (Throwable e) {
			log.error(String.format("Provider execute error -> request:%s", rpcRequest), e);
			return RpcResponse.fail(e);
		}
	}

}
