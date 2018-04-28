package com.xh.http.response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.xh.http.request.Request;

/**
 * threadPool com.xh.http.response 2018 2018-4-27 ÏÂÎç7:20:37 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class Response {
	private int code;
	private String error;
	protected Request request;
	private long len;
	

	public long getLen() {
		return len;
	}

	public void setLen(long len) {
		this.len = len;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String inputStream2string(InputStream is, String charset) {
		if (is == null)
			return "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			byte[] arr = baos.toByteArray();
			baos.close();
			is.close();
			return new String(arr, charset);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}
}
