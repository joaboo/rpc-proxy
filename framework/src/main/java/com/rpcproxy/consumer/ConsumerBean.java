package com.rpcproxy.consumer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerBean implements Serializable {
	private static final long serialVersionUID = -8446341266971012609L;

	private String refererInterfaceName;
	private Class<?> refererInterfaceClass;
	private Object refererTarget;

	private String feignClientInterfaceName;
	private Class<?> feignClientInterfaceClass;
}
