package com.xh.http.request;

import com.xh.http.util.Method;

/**
 * threadPool com.xh.http.request 2018 2018-4-28 上午11:18:36 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class DownRequest extends Request {
	public DownRequest() {
		// TODO Auto-generated constructor stub
		super();
		method = Method.GET;
		head.put(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		head.put(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
	}

	@Override
	public Request setPath(String path) {
		// TODO Auto-generated method stub
		head.put("Referer", path);
		return super.setPath(path);
	}

	public DownRequest setStartAndEnd(long startPos, long endPos) {
		head.put("Range", "bytes=" + startPos + "-" + endPos);// 设置请求长度
		return this;
	}

	@Override
	public DownRequest setAlive(boolean isAlive) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public DownRequest setMethod(Method method) {
		// TODO Auto-generated method stub
		return this;
	}
}
