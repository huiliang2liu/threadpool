package com.xh.http.task;

import com.xh.http.response.Response;
import com.xh.http.response.ResponseInputStream;
import com.xh.http.response.ResponseString;

/**
 * threadPool com.xh.http.task 2018 2018-4-28 ÉÏÎç10:04:03 instructions£º
 * author:liuhuiliang email:825378291@qq.com
 **/

public class StringTask extends ATask {

	@Override
	public Response connection() {
		// TODO Auto-generated method stub
		InputStreamTask ist = new InputStreamTask();
		ist.setRequest(mRequest);
		ResponseInputStream ris = (ResponseInputStream) ist.connection();
		ResponseString rs = new ResponseString();
		int code = ris.getCode();
		rs.setCode(code);
		if (code == 200) {
			rs.setRequest(mRequest);
			rs.setMsg(rs.inputStream2string(ris.getInputStream(),
					mRequest.getCharset()));
		} else {
			rs.setError(ris.getError());
		}
		return rs;
	}

}
