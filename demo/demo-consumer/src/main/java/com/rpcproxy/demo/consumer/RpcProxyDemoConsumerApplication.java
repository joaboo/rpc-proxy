package com.rpcproxy.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class RpcProxyDemoConsumerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RpcProxyDemoConsumerApplication.class, args);
	}
}
