package com.zoop;

import com.zoop.web.FetchFileHandle;
import com.zoop.web.FileUpload;

public class Main {

	//启动
	public static void main(String[]args) {
		//上传文件监听启动
		new Thread(new Runnable() {
			public void run() {
				FileUpload upload = new FileUpload();
				upload.accept();
			}
		}).start();
		//web服务器启动
		new Thread(new Runnable() {
			public void run() {
				FetchFileHandle fetch = new FetchFileHandle();
				fetch.accept();
			}
		}).start();
		
	}
	
}
