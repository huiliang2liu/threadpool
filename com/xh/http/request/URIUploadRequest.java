package com.xh.http.request;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * threadPool com.xh.http.request 2018 2018-4-28 ÉÏÎç11:36:35 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class URIUploadRequest extends URLUploadRequest {
	public URIUploadRequest setUri(URI uri) {
		try {
			setURL(uri.toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	public URIUploadRequest setUri(String uri) {
		try {
			setUri(URI.create(uri));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return this;
	}
}
