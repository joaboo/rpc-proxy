package com.rpcproxy.common;

import java.nio.charset.Charset;

public class RpcConstants {

	public static final Charset UTF8 = Charset.forName("UTF-8");
	public static final String CONSUMER_DISPATCH_MOTHED_NAME = "dispatch";
	public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

	public static final String DISPATCH_REQUEST_MAPPING = "/rpc-proxy/dispatch";
}
