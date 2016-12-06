package com.xxx.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareDatas {
	private int num = 0;
	private Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	public void product() {
		lock.lock();
		try {
			while (num != 0) {
				condition.await();
			}
			++num;
			System.out.println(Thread.currentThread().getName() + "\t" + num);
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void sale() {
		lock.lock();
		try {
			while (num == 0) {
				condition.await();
			}
			--num;
			System.out.println(Thread.currentThread().getName() + "\t" + num);
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}

public class HelloWorld {

	public static void main(String[] args) {

		final ShareDatas sd = new ShareDatas();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					sd.product();
				}
			}
		}, "AA").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					sd.sale();
				}
			}
		}, "BB").start();
	}
}
