package com.xh.http.task;

import com.xh.http.request.Request;
import com.xh.http.response.Response;

/**
 * threadPool com.xh.http 2018 2018-4-28 ÉÏÎç9:57:36 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public abstract class ATask {
	protected Request mRequest;

	public void setRequest(Request mRequest) {
		this.mRequest = mRequest;
	}

	public abstract Response connection();
}
