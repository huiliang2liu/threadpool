package com.xh.http.task;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import com.xh.http.request.DownRequest;
import com.xh.http.request.UploadRequest;
import com.xh.http.response.Response;
import com.xh.http.response.ResponseInputStream;
import com.xh.http.util.Method;

/**
 * threadPool com.xh.http 2018 2018-4-27 ÏÂÎç5:58:56 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class InputStreamTask extends ATask {

	@Override
	public Response connection() {
		// TODO Auto-generated method stub
		ResponseInputStream response = new ResponseInputStream();
		if (mRequest == null) {
			response.setCode(500);
			response.setError("request is null");
		}
		response.setRequest(mRequest);
		try {
			Method method = mRequest.getMethod();
			String path = mRequest.getPath();
			HttpURLConnection connection = (HttpURLConnection) new URL(path)
					.openConnection();
			if (path.startsWith("https")) {
				HttpsURLConnection https = (HttpsURLConnection) connection;
				HostnameVerifier verifier = mRequest.getVerifier();
				SSLSocketFactory factory = mRequest.getFactory();
				if (verifier != null)
					https.setHostnameVerifier(verifier);
				if (factory != null)
					https.setSSLSocketFactory(factory);
			}
			connection.setConnectTimeout(mRequest.getConnectTime());
			connection.setReadTimeout(mRequest.getReadTimeOut());
			connection.setInstanceFollowRedirects(mRequest.isRedirect());
			connection.setRequestMethod(method.getMethod());
			connection.setUseCaches(mRequest.isCaches());
			connection.setDoInput(true);
			String params = null;
			if (method == Method.GET)
				connection.setDoOutput(false);
			else {
				connection.setDoOutput(true);
				if (method == Method.POST) {
					params = mRequest.params2string();
				}
			}
			Map<String, String> head = mRequest.getHead();
			if (head != null && head.size() > 0) {
				Iterator<Entry<String, String>> iterator = head.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					connection.setRequestProperty(entry.getKey(),
							entry.getValue());
				}
			}
			connection.connect();
			response.setLen(connection.getContentLengthLong());
			if (method == Method.POST) {
				if (params != null || mRequest instanceof UploadRequest) {
					DataOutputStream dos = new DataOutputStream(
							connection.getOutputStream());
					if (params != null)
						dos.write(params.getBytes());
					if (mRequest instanceof UploadRequest) {
						UploadRequest uploadRequest = (UploadRequest) mRequest;
						InputStream is = uploadRequest.getInputStream();
						if (is != null) {
							byte[] buff = new byte[1024 * 1024];
							int len = is.read(buff);
							while (len != -1) {
								dos.write(buff, 0, len);
								len = is.read(buff);
							}
							is.close();
						}
						dos.write(UploadRequest.LINE_END.getBytes());
						byte[] end_data = (UploadRequest.PREFIX
								+ UploadRequest.BOUNDARY + UploadRequest.PREFIX + UploadRequest.LINE_END)
								.getBytes();
						dos.write(end_data);
					}
					dos.flush();
					dos.close();
				}
			}
			int code = connection.getResponseCode();
			response.setCode(code);
			if (code == 200) {
				response.setInputStream(connection.getInputStream());
			} else {
				if (mRequest instanceof DownRequest && code == 206)
					response.setInputStream(connection.getInputStream());
				else
					response.setError(response.inputStream2string(
							connection.getErrorStream(), mRequest.getCharset()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.setCode(500);
			response.setError(e == null ? "network request failed" : e
					.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	protected void putHead() {

	}
}
