package com.rpcproxy.provider;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.rpcproxy.common.RpcConstants;
import com.rpcproxy.common.RpcRequest;
import com.rpcproxy.common.RpcResponse;
import com.rpcproxy.serialize.Serializer;
import com.rpcproxy.util.RpcContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProviderDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = -6331986607054806669L;

	private ProviderBeanFactory providerBeanFactory;
	private Serializer serializer;

	@Override
	public void init() throws ServletException {
		this.providerBeanFactory = RpcContextHolder.getProviderBeanFactory();
		this.serializer = RpcContextHolder.getSerializer();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] outBytes = null;

		try {
			byte[] inBytes = IOUtils.toByteArray(request.getInputStream());
			RpcRequest rpcRequest = serializer.deserialize(inBytes, RpcRequest.class);
			log.info("rpcRequest=" + rpcRequest);

			Object target = providerBeanFactory.getProvider(rpcRequest.getInterfaceName());
			Method method = target.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
			Object ret = method.invoke(target, rpcRequest.getParameters());
			outBytes = serializer.serialize(RpcResponse.success(ret));
			response.setStatus(200);
		} catch (Throwable e) {
			outBytes = serializer.serialize(RpcResponse.fail(e));
			response.setStatus(500);
		}

		response.setCharacterEncoding(RpcConstants.UTF8.name());
		response.setContentType(RpcConstants.CONTENT_TYPE_JSON);
		ServletOutputStream out = response.getOutputStream();
		try {
			out.write(outBytes);
			out.flush();
		} finally {
			out.close();
		}
	}

}
