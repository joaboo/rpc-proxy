package com.rpcproxy.exception;

public class ProviderNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5949657228970160963L;

	public ProviderNotFoundException(String msg) {
		super(msg);
	}

	public ProviderNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
