package com.rpcproxy.consumer;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rpcproxy.common.RpcConstants;
import com.rpcproxy.common.RpcRequest;
import com.rpcproxy.common.RpcResponse;
import com.rpcproxy.serialize.Serializer;
import com.rpcproxy.util.RpcContextHolder;
import com.rpcproxy.util.SpringContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerServiceProxyHandler implements InvocationHandler {
	private static final String OBJECT_METHODS_GETCLASS = "getClass";
	private static final String OBJECT_METHODS_HASHCODE = "hashCode";
	private static final String OBJECT_METHODS_TOSTRING = "toString";
	private static final String OBJECT_METHODS_EQUALS = "equals";

	private static final Map<String, SoftReference<Method>> METHOD_CACHE = new ConcurrentHashMap<>();

	private ConsumerBeanFactory consumerBeanFactory;
	private Serializer serializer;

	public ConsumerServiceProxyHandler() {
		this(RpcContextHolder.getConsumerBeanFactory(), RpcContextHolder.getSerializer());
	}

	public ConsumerServiceProxyHandler(ConsumerBeanFactory consumerBeanFactory, Serializer serializer) {
		this.consumerBeanFactory = consumerBeanFactory;
		this.serializer = serializer;
	}

	public Object create(Class<?> interfaceClass) {
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass }, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (OBJECT_METHODS_GETCLASS.equals(method.getName())) {
			return super.getClass();
		} else if (OBJECT_METHODS_HASHCODE.equals(method.getName())) {
			return super.hashCode();
		} else if (OBJECT_METHODS_TOSTRING.equals(method.getName())) {
			return super.toString();
		} else if (OBJECT_METHODS_EQUALS.equals(method.getName())) {
			try {
				Object otherHandler = (args.length > 0 && args[0] != null) ? Proxy.getInvocationHandler(args[0]) : null;
				return super.equals(otherHandler);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}

		ConsumerBean consumerBean = consumerBeanFactory.getConsumerBean(method.getDeclaringClass().getName());
		Object feignClientTarget = SpringContextHolder.getBean(consumerBean.getFeignClientInterfaceClass());

		RpcRequest rpcRequest = new RpcRequest();
		rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
		rpcRequest.setMethodName(method.getName());
		rpcRequest.setParameterTypes(method.getParameterTypes());
		rpcRequest.setParameters(args);

		log.info("rpcRequest=" + rpcRequest);
		byte[] bytes = serializer.serialize(rpcRequest);
		Method dispatchMethod = this.getDispatchMethod(consumerBean.getFeignClientInterfaceName(), feignClientTarget);
		String ret = (String) dispatchMethod.invoke(feignClientTarget, new String(bytes, RpcConstants.UTF8));
		RpcResponse rpcResponse = serializer.deserialize(ret.getBytes(RpcConstants.UTF8), RpcResponse.class);
		log.info("rpcResponse=" + rpcResponse);

		if (rpcResponse.getException() != null) {
			throw rpcResponse.getException();
		} else {
			return rpcResponse.getResult();
		}
	}

	private Method getDispatchMethod(String feignClientInterfaceName, Object feignClientTarget) throws Exception {
		SoftReference<Method> softReference = METHOD_CACHE.get(feignClientInterfaceName);
		if (softReference != null) {
			Method method = softReference.get();
			if (method != null) {
				return method;
			}
		}
		Method method = feignClientTarget.getClass().getMethod(RpcConstants.CONSUMER_DISPATCH_MOTHED_NAME, String.class);
		METHOD_CACHE.put(feignClientInterfaceName, new SoftReference<Method>(method));
		return method;
	}

}
