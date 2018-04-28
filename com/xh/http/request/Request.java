package com.xh.http.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.xh.http.util.Method;

/**
 * threadPool com.xh.http.util 2018 2018-4-27 下午6:12:21 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class Request {
	protected Method method;
	protected Map<String, String> head;
	private int connectTime = 5 * 1000;// 超时设置
	private int readTimeOut = 5 * 1000;// 读取超时
	protected Map<String, Object> params;
	private String path;
	private boolean isRedirect = false;
	private boolean isCaches = false;
	private String charset = "UTF-8";// 设置编码
	private SSLSocketFactory factory;
	private HostnameVerifier verifier;
	static {
		defaultHostnameVerifier();
		defaultSSLSocketFactory();
	}

	/**
	 * 
	 * lhl 2017-11-25 下午3:53:13 说明： 设置默认套接工厂 void
	 */
	private static void defaultSSLSocketFactory() {
		try {
			TrustManager myX509TrustManager = new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) {
				}

			};
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
			kmf.init(trustStore, "password".toCharArray());
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("SSL",
					"AndroidOpenSSL");
			sslcontext.init(kmf.getKeyManagers(),
					new TrustManager[] { myX509TrustManager },
					new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext
					.getSocketFactory());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * lhl 2017-11-25 下午3:38:48 说明：设置默认是否忽略证书
	 * 
	 * @param hostnameVerifier
	 *            void
	 */
	private static void defaultHostnameVerifier() {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	public Request() {
		// TODO Auto-generated constructor stub
		head = new HashMap<String, String>();
		head.put("Content-Type", "application/x-www-from-urlencoded");
		head.put("Charset", charset);
		head.put("Accept-Language", "zh-CN");
		head.put("connection", "keep-alive");
		params = new HashMap<String, Object>();
	}

	public Request setLanguage() {
		head.put("Accept-Language", "zh-CN");
		return this;
	}

	public Method getMethod() {
		return method;
	}

	public Request setAlive(boolean isAlive) {
		if (!isAlive)
			head.remove("connection");
		return this;
	}

	public Request setMethod(Method method) {
		this.method = method;
		return this;
	}

	public Map<String, String> getHead() {
		return head;
	}

	public Request addHead(Map<String, String> head) {
		this.head.putAll(head);
		return this;
	}

	public Request addHead(String key, String value) {
		head.put(key, value);
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public Request addParams(Map<String, Object> params) {
		this.params.putAll(params);
		return this;
	}

	public Request addParams(String key, Object value) {
		params.put(key, value);
		return this;
	}

	public String getPath() {
		if (method == Method.GET) {
			String params = params2string();
			if (params != null) {
				StringBuffer stringBuffer = new StringBuffer(path);
				if (path.indexOf("?") > 1)
					stringBuffer.append("&");
				else
					stringBuffer.append("?");
				stringBuffer.append(params);
				return stringBuffer.toString();
			}
		}
		return path;
	}

	public String params2string() {
		if (params != null && params.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			Iterator<Entry<String, Object>> iterator = params.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				Object object = entry.getValue();
				String value = object == null ? "" : object.toString();
				stringBuffer.append(entry.getKey()).append("=");
				try {
					stringBuffer.append(URLEncoder.encode(value, charset));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					stringBuffer.append(value);
				}
				stringBuffer.append("&");
			}
			return stringBuffer.substring(0, stringBuffer.length() - 1);
		}
		return null;
	}

	public SSLSocketFactory getFactory() {
		return factory;
	}

	public void setFactory(SSLSocketFactory factory) {
		this.factory = factory;
	}

	public HostnameVerifier getVerifier() {
		return verifier;
	}

	public void setVerifier(HostnameVerifier verifier) {
		this.verifier = verifier;
	}

	public Request setPath(String path) {
		this.path = path;
		return this;
	}

	public int getConnectTime() {
		return connectTime;
	}

	public Request setConnectTime(int connectTime) {
		this.connectTime = connectTime;
		return this;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public Request setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
		return this;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public Request setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
		return this;
	}

	public String getCharset() {
		return charset;
	}

	public Request setCharset(String charset) {
		head.put("Charset", charset);
		return this;
	}

	public boolean isCaches() {
		return isCaches;
	}

	public void setCaches(boolean isCaches) {
		this.isCaches = isCaches;
	}

}
