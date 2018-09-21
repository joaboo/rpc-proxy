package com.rpcproxy.provider.servlet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

@Getter
public class ProviderServletConfig {

	private boolean asyncMode = true;
	private int threads;
	private int queues;
	private long timeoutinmillis;
	private volatile ExecutorService executor;

	public void init(boolean asyncMode, int threads, int queues, long timeoutinmillis) {
		this.asyncMode = asyncMode;
		this.threads = threads;
		this.queues = queues;
		this.timeoutinmillis = timeoutinmillis;
	}

	public ExecutorService getExecutor() {
		if (executor != null) {
			return executor;
		}

		synchronized (this) {
			if (executor != null) {
				return executor;
			}
			if (asyncMode) {
				executor = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS,
						queues == 0 ? new SynchronousQueue<Runnable>()
								: (queues < 0 ? new LinkedBlockingQueue<Runnable>()
										: new LinkedBlockingQueue<Runnable>(queues)));
			}
		}
		return executor;
	}
}
