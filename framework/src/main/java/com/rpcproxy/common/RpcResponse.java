package com.rpcproxy.common;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcResponse implements Serializable {
	private static final long serialVersionUID = 4617463490568848070L;

	private Object result;
	private Throwable exception;

	public RpcResponse() {
	}

	public RpcResponse(Object result) {
		this.result = result;
	}

	public RpcResponse(Throwable exception) {
		this.exception = exception;
	}

	public static RpcResponse success(Object result) {
		return new RpcResponse(result);
	}

	public static RpcResponse fail(Throwable exception) {
		return new RpcResponse(exception);
	}
}
