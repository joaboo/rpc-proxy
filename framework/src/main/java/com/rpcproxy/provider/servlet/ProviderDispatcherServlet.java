package com.rpcproxy.provider.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rpcproxy.common.RpcConstants;
import com.rpcproxy.common.RpcRequest;
import com.rpcproxy.common.RpcResponse;
import com.rpcproxy.provider.ProviderExecutor;
import com.rpcproxy.serialize.Serializer;
import com.rpcproxy.util.IOUtils;
import com.rpcproxy.util.RpcContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProviderDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = -6331986607054806669L;

	private ProviderServletConfig providerServletConfig;
	private ProviderExecutor providerExecutor;
	private Serializer serializer;

	@Override
	public void init() throws ServletException {
		this.providerServletConfig = RpcContextHolder.getProviderServletConfig();
		this.providerExecutor = RpcContextHolder.getProviderExecutor();
		this.serializer = RpcContextHolder.getSerializer();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (providerServletConfig.isAsyncMode()) {
			AsyncContext asyncContext = request.startAsync();
			asyncContext.addListener(new DispatcherListener());
			asyncContext.setTimeout(providerServletConfig.getTimeoutInMillis());
			// asyncContext.start(new RequestProcessor(asyncContext));
			providerServletConfig.getExecutor().execute(new RequestProcessor(asyncContext));
		} else {
			doBusiness(request, response);
		}
	}

	@Override
	public void destroy() {
		ExecutorService executor = providerServletConfig.getExecutor();
		if (executor != null) {
			executor.shutdown();
		}
	}

	class DispatcherListener implements AsyncListener {

		@Override
		public void onComplete(AsyncEvent event) throws IOException {
		}

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			log.error("Request timeout -> request:{}", event.getSuppliedRequest());
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			log.error("Request error -> request:{}, error:{}", event.getSuppliedRequest(), event.getThrowable());
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
		}
	}

	class RequestProcessor implements Runnable {
		final AsyncContext asyncContext;

		RequestProcessor(final AsyncContext asyncContext) {
			this.asyncContext = asyncContext;
		}

		@Override
		public void run() {
			HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
			HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
			try {
				doBusiness(request, response);
			} catch (Exception e) {
				log.error("process error", e);
			} finally {
				asyncContext.complete();
			}
		}
	}

	private void doBusiness(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RpcResponse rpcResponse = null;
		byte[] outBytes = null;

		try {
			byte[] inBytes = IOUtils.toByteArray(request.getInputStream());
			RpcRequest rpcRequest = serializer.deserialize(inBytes, RpcRequest.class);
			log.info("rpcRequest=" + rpcRequest);
			rpcResponse = providerExecutor.execute(rpcRequest);
			outBytes = serializer.serialize(rpcResponse);
		} catch (Throwable e) {
			log.error("process error", e);
			rpcResponse = RpcResponse.fail(e);
			outBytes = serializer.serialize(rpcResponse);
		}

		log.info("rpcResponse=" + rpcResponse);
		response.setStatus(rpcResponse.getException() == null ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
