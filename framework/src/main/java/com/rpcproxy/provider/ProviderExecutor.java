package com.rpcproxy.provider;

import com.rpcproxy.common.RpcRequest;
import com.rpcproxy.common.RpcResponse;

public interface ProviderExecutor {

	RpcResponse execute(RpcRequest rpcRequest);
}
