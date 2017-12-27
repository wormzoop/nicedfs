package com.zoop.web;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
			ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
			while(true) {
				Socket socket = serverSocket.accept();
				cacheThreadPool.execute(new SocketHandler(socket));
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
	
	//新线程处理请求
	class SocketHandler implements Runnable {

		private Socket socket;
		
		public SocketHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			InputStream in = null;
			try {
				in = socket.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(in != null) {
						in.close();
					}
				}catch(Exception e) {
					
				}
			}
		}
		
	}
	
}
