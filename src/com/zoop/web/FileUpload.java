package com.zoop.web;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 上传存储文件
 * 端口及文件存放路径在配置文件配制
 * @author Administrator
 *
 */
public class FileUpload {

	public void accept() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			System.out.println("socket monitor start");
			while(true) {
				Socket socket = serverSocket.accept();
				new Thread(new Runnable() {
					public void run() {
						
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(serverSocket != null) {
					serverSocket.close();
				}
			}catch(Exception e) {
				
			}
		}
	}
	
}
