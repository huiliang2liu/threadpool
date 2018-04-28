package com.xh.http.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * threadPool com.xh.http.util 2018 2018-4-28 上午11:57:36 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class MD5 {
	/**
	 * 将字节数组MD5加密
	 * 
	 * @param b
	 * @return
	 */
	public static String Bytes2MD5Method(byte[] b) {
		try {
			StringBuilder sb = new StringBuilder();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(b);// 执行MD5算法
			for (byte by : md5.digest()) {
				sb.append(String.format("%02X", by));// 将生成的字节MD５值转换成字符串
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 生成MD5类的实例
		return null;
	}

	/**
	 * 将字符串MD5加密
	 * 
	 * @param s
	 * @return
	 */
	public static String String2MD5Method(String s) {
		return Bytes2MD5Method(s.getBytes());
	}
}
