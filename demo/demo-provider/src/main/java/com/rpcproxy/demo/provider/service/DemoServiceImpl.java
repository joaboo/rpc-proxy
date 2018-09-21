package com.rpcproxy.demo.provider.service;

import org.springframework.stereotype.Service;

import com.rpcproxy.demo.api.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public String sayHelloTimeout(String name) {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
		}
		return "Hello " + name;
	}

}