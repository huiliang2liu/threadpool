package com.xh.http;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.xh.http.listen.ProgressListen;
import com.xh.http.request.DownRequest;
import com.xh.http.response.ResponseInputStream;
import com.xh.http.util.MD5;

/**
 * threadPool com.xh.http 2018 2018-4-28 上午11:39:46 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class Down {
	private long fileLen;
	private long downLen = 0;
	private int downCode = 0;// 0下载中，1下载成功，-1下载失败
	private File saveFile;
	private File temporaryFile;
	private float progress;
	private ProgressListen listen;
	private int threadSize;
	private String path;

	public ProgressListen getListen() {
		return listen;
	}

	public void setListen(ProgressListen listen) {
		this.listen = listen;
	}

	private synchronized void setDownLen(long down) {
		downLen += down;
		if (downLen >= fileLen) {
			progress = 100.00f;
			downCode = 1;
			if (temporaryFile.renameTo(saveFile))
				if (listen != null) {
					listen.end(saveFile);
				}
		} else {
			long p = downLen * 10000 / fileLen;
			if (p == 10000)
				p -= 1;
			progress = p * 1.0f / 100;
			if (listen != null)
				listen.progress(progress);
		}
	}

	public int getDownCode() {
		return downCode;
	}

	public float getProgress() {
		return progress;
	}

	public Down(String path, int threadSize, File saveFile) {
		// TODO Auto-generated constructor stub
		File parent = saveFile.getParentFile();
		if (saveFile.exists())
			saveFile.delete();
		synchronized (Down.class) {
			if (!parent.exists())
				parent.exists();
		}
		this.saveFile = saveFile;
		this.threadSize = threadSize;
		this.path = path;
		temporaryFile = new File(parent, MD5.String2MD5Method(path));

	}

	public void connection() {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(path)
					.openConnection();
			fileLen = connection.getContentLengthLong();
			long len = fileLen / threadSize;
			for (int i = 0; i < threadSize; i++) {
				long start = len * i;
				long end = start + len;
				if (i == threadSize - 1) {
					end = fileLen;
				}
				new Thread(new DownRun(start, end)).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private class DownRun implements Runnable {
		private long startPos;
		private long endPos;

		public DownRun(long startPos, long endPos) {
			// TODO Auto-generated constructor stub
			this.startPos = startPos;
			this.endPos = endPos;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				RandomAccessFile randAF = new RandomAccessFile(temporaryFile,
						"rwd");
				randAF.seek(startPos);
				DownRequest downRequest = new DownRequest();
				downRequest.setPath(path);
				downRequest.setStartAndEnd(startPos, endPos);
				ResponseInputStream ris = HttpManage.inputStream(downRequest);
				if (ris.getCode() == 206 || ris.getCode() == 200) {
					InputStream is = ris.getInputStream();
					if (is != null) {
						byte[] buff = new byte[1024 * 1024];
						int len = is.read(buff);
						while (len != -1) {
							randAF.write(buff, 0, len);
							setDownLen(len);
							len = is.read(buff);
						}
						is.close();
					}
				} else {
					synchronized (Down.this) {
						downCode = -1;
					}
				}
				randAF.close();
				setDownLen(0);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				downCode = -1;
			}
		}
	}
}
