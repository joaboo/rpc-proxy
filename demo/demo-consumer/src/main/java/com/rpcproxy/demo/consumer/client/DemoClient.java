package com.rpcproxy.demo.consumer.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rpcproxy.common.RpcConstants;

@FeignClient("rpc-proxy-demo-provider")
public interface DemoClient {

	@PostMapping(RpcConstants.DISPATCH_REQUEST_MAPPING)
	public String dispatch(@RequestBody String request);

}
