/**
 * 
 */
package com.redbus.assignment.logserrorfinder.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * It is an executor class, which provides thread pool implementation to
 * efficiently manage threads.
 * 
 * @author user_53by97
 *
 */
public final class JobExecutor {

	private final ThreadPoolExecutor threadPoolExecutor;

	public JobExecutor() {
		BlockingQueue<Runnable> jobQueue = new LinkedBlockingQueue<Runnable>();
		threadPoolExecutor = new ThreadPoolExecutor(20, 30, 1, TimeUnit.MINUTES, jobQueue);
		threadPoolExecutor.allowCoreThreadTimeOut(true);
	}

	public void executor(Runnable runnable) {
		threadPoolExecutor.execute(runnable);
	}

	public boolean awaitTermination(int time, TimeUnit timeUnit) throws InterruptedException {
		return threadPoolExecutor.awaitTermination(time, timeUnit);
	}

	public void shutdown() {
		threadPoolExecutor.shutdown();
	}

}
