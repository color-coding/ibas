/**
 * 
 */
/**
 * @author Eric Peng
 *
 */
package org.colorcoding.ibas.bobas.test.messages;

import org.colorcoding.ibas.bobas.messages.MessageLevel;
import org.colorcoding.ibas.bobas.messages.RuntimeLog;

import junit.framework.TestCase;

public class testRuntimeLog extends TestCase {

	public void testMultiLogSave() throws InterruptedException {
		int theadCount = 9999;
		Thread[] threads = new Thread[theadCount];

		for (int i = 0; i < theadCount; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					testRuntimeLogSave();
				}
			}, String.valueOf(i));
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		Thread.sleep(120000);
		System.out.println("主线程结束！");
	}

	public void testRuntimeLogSave() {
		for (int i = 0; i < 100; i++) {
			RuntimeLog.log("message [%s] by [%s]", i, Thread.currentThread().getName());
			RuntimeLog.log(MessageLevel.INFO, "");
		}
	}
}