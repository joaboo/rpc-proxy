package com.rpcproxy.spring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.rpcproxy.common.RpcConstants;
import com.rpcproxy.provider.ProviderBean;
import com.rpcproxy.provider.ProviderBeanRegistry;
import com.rpcproxy.provider.servlet.ProviderServletConfig;
import com.rpcproxy.util.RpcContextHolder;
import com.rpcproxy.util.SpringContextHolder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpringProviderConfBean implements InitializingBean, Serializable {
	private static final long serialVersionUID = 6424858342891512053L;

	private boolean asyncMode = true;
	private int threads = RpcConstants.DEFAULT_THREADS;
	private int queues = RpcConstants.DEFAULT_QUEUES;
	private long timeoutInMillis = RpcConstants.DEFAULT_TIMEOUTINMILLIS;
	private List<String> interfaceNames;

	public void addInterfaceName(String interfaceName) {
		if (this.interfaceNames == null) {
			this.interfaceNames = new LinkedList<>();
		}
		this.interfaceNames.add(interfaceName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (interfaceNames == null) {
			throw new IllegalArgumentException("property 'interfaceNames' is required");
		}

		ProviderServletConfig providerServletConfig = RpcContextHolder.getProviderServletConfig();
		providerServletConfig.init(asyncMode, threads, queues, timeoutInMillis);

		ProviderBeanRegistry providerBeanRegistry = RpcContextHolder.getProviderBeanRegistry();
		List<ProviderBean> providerBeans = translate();
		providerBeans.forEach(providerBean -> {
			providerBeanRegistry.register(providerBean);
		});
	}

	private List<ProviderBean> translate() throws Exception {
		List<ProviderBean> providerBeans = new ArrayList<>(interfaceNames.size());
		for (String interfaceName : interfaceNames) {
			ProviderBean providerBean = new ProviderBean();
			providerBean.setInterfaceName(interfaceName);

			try {
				providerBean.setInterfaceClass(Class.forName(interfaceName));
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(
						"class not found when 'SpringProviderConfBean' translate to 'ProviderBean'", e);
			}

			Object target = SpringContextHolder.getBean(providerBean.getInterfaceClass());
			if (target == null) {
				throw new IllegalArgumentException("no bean named '" + interfaceNames + "' in spring context");
			}
			providerBean.setTarget(target);

			providerBeans.add(providerBean);
		}
		return providerBeans;
	}
}
