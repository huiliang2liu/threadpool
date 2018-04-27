package com.xh.test;

import com.xh.threadPool.Task;
import com.xh.threadPool.TaskPool;

/**
 * threadPool com.xh.test 2018 2018-4-26 下午2:06:34 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class Test {
	protected static final int CPU_COUNT = Runtime.getRuntime()
			.availableProcessors();// 系统cpu数量
	protected static final int CPRE_POOL_SIZE = CPU_COUNT + 1;// 存活线程默认值
	protected static final int MAX_POOL_SIZE = (CPU_COUNT << 1) + 1;// 最大线程数

	public static void main(String[] args) {
		TaskPool taskPool=new TaskPool();
		Task task=new Task(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("执行了");
			}
		});
		taskPool.submit(task.setDelay(1000).setPeriod(1000).setLatestExecutionTime(System.currentTimeMillis()+10000));
	}
}
