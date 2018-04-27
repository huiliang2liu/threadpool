package com.xh.threadPool;

import java.util.Date;
import java.util.TimerTask;

/**
 * threadPool com.xh.threadPool 2018 2018-4-27 ����2:41:47 instructions��
 * author:liuhuiliang email:825378291@qq.com
 **/

public final class Task {
	protected Date mStartDate;// ��ʼʱ��
	protected long mDelayTime = -1;// �ӳ�ʱ��
	protected long mPeriod = -1;// ����
	protected long mNum = -1;// ����
	protected long mLatestExecutionTime = -1;// ����ִ��ʱ��
	protected Runnable mRunnable;
	private TaskRunnable mTaskRunnable;

	public Task(Runnable runnable) {
		// TODO Auto-generated constructor stub
		mRunnable = runnable;
	}

	/**
	 * 
	 * 2018 2018-4-27 ����3:08:47 annotation����������ʼʱ�� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param date
	 *            ��ʼʱ��
	 * @return Task
	 */
	public Task setStartDate(Date date) {
		mStartDate = date;
		return this;
	}

	/**
	 * 
	 * 2018 2018-4-27 ����3:10:12 annotation�����������ӳ�ʱ�� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param delay
	 *            �ӳ�ʱ�� ��λ����
	 * @return Task
	 */
	public Task setDelay(long delay) {
		mDelayTime = delay;
		return this;
	}

	/**
	 * 
	 * 2018 2018-4-27 ����3:11:51 annotation���������� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param period
	 *            ���� ��λ����
	 * @return Task
	 */
	public Task setPeriod(long period) {
		mPeriod = period;
		return this;
	}

	/**
	 * 
	 * 2018 2018-4-27 ����3:12:43 annotation������ִ�д��� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param num
	 *            ����
	 * @return Task
	 */
	public Task setNum(long num) {
		mNum = num;
		return this;
	}

	/**
	 * 
	 * 2018 2018-4-27 ����3:13:42 annotation����������ִ��ʱ�� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param latestExecutionTime
	 * @return Task
	 */
	public Task setLatestExecutionTime(long latestExecutionTime) {
		mLatestExecutionTime = latestExecutionTime;
		return this;
	}

	public Task LatestExecutionTime(Date latestExecutionTime) {
		if (latestExecutionTime == null)
			return this;
		mLatestExecutionTime = latestExecutionTime.getTime();
		return this;
	}

	protected Runnable build() {
		if (mTaskRunnable == null)
			mTaskRunnable = new TaskRunnable();
		return mTaskRunnable;
	}

	protected class TaskRunnable implements Runnable {
		private long time = 0;
		private TimerTask mTask;

		protected void setTask(TimerTask task) {
			mTask = task;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mLatestExecutionTime > 0
					&& mLatestExecutionTime < System.currentTimeMillis()) {
				if (mTask != null)
					mTask.cancel();
				return;
			}
			if (mRunnable != null)
				mRunnable.run();
			time++;
			if (mNum > 0) {
				if (time >= mNum && mTask != null)
					mTask.cancel();
			}
		}

	}
}
