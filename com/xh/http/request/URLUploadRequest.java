package com.xh.http.request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * threadPool com.xh.http.request 2018 2018-4-28 ÉÏÎç11:32:35 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class URLUploadRequest extends UploadRequest {
	public URLUploadRequest setURL(URL url) {
		if (url != null)
			try {
				setInputStream(url.openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return this;
	}

	public URLUploadRequest setURL(String url) {
		try {
			return setURL(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this;
		}
	}
}
