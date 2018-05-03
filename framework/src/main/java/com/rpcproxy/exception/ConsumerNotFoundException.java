package com.rpcproxy.exception;

public class ConsumerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5949657228970160963L;

	public ConsumerNotFoundException(String msg) {
		super(msg);
	}

	public ConsumerNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
