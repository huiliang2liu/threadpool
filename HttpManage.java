package com.xh.http;

import java.io.File;

import com.xh.http.listen.ProgressListen;
import com.xh.http.request.Request;
import com.xh.http.response.Response;
import com.xh.http.response.ResponseInputStream;
import com.xh.http.response.ResponseString;
import com.xh.http.task.ATask;
import com.xh.http.task.InputStreamTask;
import com.xh.http.task.StringTask;

/**
 * threadPool com.xh.http 2018 2018-4-28 ÉÏÎç10:15:20 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class HttpManage {
	public static ResponseInputStream inputStream(Request request) {
		ATask task = new InputStreamTask();
		return (ResponseInputStream) response(task, request);
	}

	public static ResponseString string(Request request) {
		ATask task = new StringTask();
		return (ResponseString) response(task, request);
	}

	public static void down(String path, int threadSize, File saveFile,
			ProgressListen listen) {
		Down down = new Down(path, threadSize, saveFile);
		down.setListen(listen);
		down.connection();
	}

	private static Response response(ATask task, Request request) {
		task.setRequest(request);
		return task.connection();
	}
}
