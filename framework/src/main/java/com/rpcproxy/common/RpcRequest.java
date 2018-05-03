package com.rpcproxy.common;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcRequest implements Serializable {
	private static final long serialVersionUID = 4617463490568848070L;

	private String interfaceName;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;
}
