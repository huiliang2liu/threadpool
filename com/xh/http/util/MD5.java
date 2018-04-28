package com.xh.http.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * threadPool com.xh.http.util 2018 2018-4-28 ����11:57:36 instructions��
 * author:liuhuiliang email:825378291@qq.com
 **/

public class MD5 {
	/**
	 * ���ֽ�����MD5����
	 * 
	 * @param b
	 * @return
	 */
	public static String Bytes2MD5Method(byte[] b) {
		try {
			StringBuilder sb = new StringBuilder();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(b);// ִ��MD5�㷨
			for (byte by : md5.digest()) {
				sb.append(String.format("%02X", by));// �����ɵ��ֽ�MD��ֵת�����ַ���
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ����MD5���ʵ��
		return null;
	}

	/**
	 * ���ַ���MD5����
	 * 
	 * @param s
	 * @return
	 */
	public static String String2MD5Method(String s) {
		return Bytes2MD5Method(s.getBytes());
	}
}
