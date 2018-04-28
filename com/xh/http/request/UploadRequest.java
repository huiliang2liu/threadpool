package com.xh.http.request;

import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;

import com.xh.http.util.Method;

/**
 * threadPool com.xh.http.request 2018 2018-4-28 上午10:58:19 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class UploadRequest extends Request {
	public static final String PREFIX = "--";
	public static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	public static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
	private String fileKey;
	private String filename;
	private InputStream inputStream;

	public UploadRequest() {
		// TODO Auto-generated constructor stub
		super();
		head.put("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		head.put("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		method = Method.POST;
	}

	@Override
	public UploadRequest setMethod(Method method) {
		// TODO Auto-generated method stub
		return this;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public UploadRequest setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}

	public UploadRequest setFileKey(String fileKey) {
		this.fileKey = fileKey;
		return this;
	}

	public UploadRequest setFilename(String filename) {
		this.filename = filename;
		return this;
	}

	@Override
	public UploadRequest setAlive(boolean isAlive) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String params2string() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		// String params = "";

		/***
		 * 以下是用于上传参数
		 */
		if (params != null && params.size() > 0) {
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = params.get(key).toString();
				sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"")
						.append(key).append("\"").append(LINE_END)
						.append(LINE_END);
				sb.append(value).append(LINE_END);
			}
		}
		/**
		 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 filename是文件的名字，包含后缀名的
		 * 比如:abc.png
		 */
		sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
		sb.append("Content-Disposition:form-data; name=\"" + fileKey
				+ "\"; filename=\"" + filename + "\"" + LINE_END);
		sb.append("Content-Type:image/pjpeg,image/png,image/bmp,image/bmp,image/x-png"
				+ LINE_END); // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
		sb.append(LINE_END);
		return super.params2string();
	}
}
