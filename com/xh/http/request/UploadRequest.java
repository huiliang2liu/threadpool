package com.xh.http.request;

import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;

import com.xh.http.util.Method;

/**
 * threadPool com.xh.http.request 2018 2018-4-28 ����10:58:19 instructions��
 * author:liuhuiliang email:825378291@qq.com
 **/

public class UploadRequest extends Request {
	public static final String PREFIX = "--";
	public static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // ��������
	public static final String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ
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
		 * �����������ϴ�����
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
		 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ� filename���ļ������֣�������׺����
		 * ����:abc.png
		 */
		sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
		sb.append("Content-Disposition:form-data; name=\"" + fileKey
				+ "\"; filename=\"" + filename + "\"" + LINE_END);
		sb.append("Content-Type:image/pjpeg,image/png,image/bmp,image/bmp,image/x-png"
				+ LINE_END); // �������õ�Content-type����Ҫ�� �����ڷ������˱���ļ������͵�
		sb.append(LINE_END);
		return super.params2string();
	}
}
