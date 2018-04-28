package com.xh.http.response;

import java.io.InputStream;

/**
 * threadPool com.xh.http.response 2018 2018-4-27 ÏÂÎç7:15:50 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public final class ResponseInputStream extends Response {
	private InputStream is;

	public InputStream getInputStream() {
		return is;
	}

	public void setInputStream(InputStream is) {
		this.is = is;
	}

	public ResponseInputStream() {
		// TODO Auto-generated constructor stub
	}

}
