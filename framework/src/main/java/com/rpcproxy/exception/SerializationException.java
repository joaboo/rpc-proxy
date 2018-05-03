package com.rpcproxy.exception;

public class SerializationException extends RuntimeException {
	private static final long serialVersionUID = -3289912795700841986L;

	public SerializationException(String msg) {
		super(msg);
	}

	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
