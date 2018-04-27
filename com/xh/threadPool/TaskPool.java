package com.xh.threadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * threadPool com.xh.threadPool 2018 2018-4-27 ÏÂÎç3:27:05 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class TaskPool {
	private ThreadPool mThreadPool;
	private TimeThreadPool mTimeThreadPool;

	public TaskPool() {
		// TODO Auto-generated constructor stub
		mThreadPool = new ThreadPool();
		mTimeThreadPool = new TimeThreadPool();
	}

	public void submit(Task task) {
		if (task == null)
			return;
		if (task.mStartDate != null) {
			if (task.mPeriod > 0)
				mTimeThreadPool.submit(task.build(), task.mStartDate,
						task.mPeriod);
			else
				mTimeThreadPool.submit(task.build(), task.mStartDate);
			return;
		}
		if (task.mDelayTime > 0) {
			if (task.mPeriod > 0)
				mTimeThreadPool.submit(task.build(), task.mDelayTime,
						task.mPeriod);
			else
				mTimeThreadPool.submit(task.build(), task.mDelayTime);
			return;
		}
		if (task.mPeriod > 0) {
			mTimeThreadPool.submit(task.build(), new Date(), task.mPeriod);
			return;
		}
		mThreadPool.submit(task.build());
	}

	public void submit(Collection<Task> tasks) {
		if (tasks == null || tasks.size() <= 0)
			return;
		int size = tasks.size();
		Task[] mTasks = tasks.toArray(new Task[size]);
		for (int i = 0; i < size; i++) {
			submit(mTasks[i]);
		}
	}

	public boolean remove(Task task) {
		if (task == null)
			return false;
		Runnable runnable = task.build();
		if (mThreadPool.remove(runnable))
			return true;
		return mTimeThreadPool.remove(runnable);
	}

	public Collection<Task> remove(Collection<Task> tasks) {
		if (tasks == null || tasks.size() <= 0)
			return null;
		int size = tasks.size();
		Task[] mTasks = tasks.toArray(new Task[size]);
		List<Task> mList = new ArrayList<Task>(size);
		for (int i = 0; i < size; i++) {
			Task task = mTasks[i];
			if (remove(task))
				continue;
			mList.add(task);
		}
		return mList;
	}

	public void shutdown() {
		mTimeThreadPool.shutdown();
		mThreadPool.shutdown();
	}

	public Collection<Runnable> shutdownNow() {
		return mThreadPool.shutdownNow();
	}
}
