package com.rpcproxy.provider;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderBean implements Serializable {
	private static final long serialVersionUID = 4690477260921686008L;

	private String interfaceName;
	private Class<?> interfaceClass;
	private Object target;

}
