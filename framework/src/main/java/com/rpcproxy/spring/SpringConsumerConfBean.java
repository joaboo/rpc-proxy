package com.rpcproxy.spring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.rpcproxy.consumer.ConsumerBean;
import com.rpcproxy.consumer.ConsumerBeanRegistry;
import com.rpcproxy.consumer.ConsumerServiceProxyHandler;
import com.rpcproxy.util.RpcContextHolder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpringConsumerConfBean implements InitializingBean, Serializable {
	private static final long serialVersionUID = 6491200502884878376L;

	private String feignClientInterfaceName;
	private List<String> refererInterfaceNames;

	public void addRefererInterfaceName(String refererInterfaceName) {
		if (refererInterfaceNames == null) {
			refererInterfaceNames = new LinkedList<>();
		}
		refererInterfaceNames.add(refererInterfaceName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (refererInterfaceNames == null) {
			throw new IllegalArgumentException("property 'refererInterfaceNames' is required");
		}
		if (feignClientInterfaceName == null) {
			throw new IllegalArgumentException("property 'feignClientInterfaceName' is required");
		}

		ConsumerBeanRegistry consumerBeanRegistry = RpcContextHolder.getConsumerBeanRegistry();
		List<ConsumerBean> consumerBeans = translate();
		consumerBeans.forEach(consumerBean -> {
			consumerBeanRegistry.register(consumerBean);
		});
	}

	private List<ConsumerBean> translate() throws Exception {
		List<ConsumerBean> consumerBeans = new ArrayList<>(refererInterfaceNames.size());
		for (String refererInterfaceName : refererInterfaceNames) {
			ConsumerBean consumerBean = new ConsumerBean();
			consumerBean.setRefererInterfaceName(refererInterfaceName);
			consumerBean.setFeignClientInterfaceName(feignClientInterfaceName);
			try {
				consumerBean.setRefererInterfaceClass(Class.forName(refererInterfaceName));
				consumerBean.setFeignClientInterfaceClass(Class.forName(feignClientInterfaceName));
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(
						"class not found when 'SpringConsumerConfBean' translate to 'ConsumerBean'", e);
			}
			Object refererTarget = new ConsumerServiceProxyHandler().create(consumerBean.getRefererInterfaceClass());
			consumerBean.setRefererTarget(refererTarget);
			consumerBeans.add(consumerBean);
		}
		return consumerBeans;
	}
}
