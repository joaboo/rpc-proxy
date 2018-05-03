package com.rpcproxy.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class RpcProxyDemoProviderApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RpcProxyDemoProviderApplication.class, args);
	}
}
