package com.xh.http.util;

/**
 * threadPool com.xh.http.util 2018 2018-4-27 ÏÂÎç6:05:01 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public enum Method {
	GET("GET"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PUT("PUT"), DELETE(
			"DELETE"), TRACE("TRACE");
	private String mMethod;

	private Method(String method) {
		mMethod = method;
	}

	public String getMethod() {
		return mMethod;
	}

}
