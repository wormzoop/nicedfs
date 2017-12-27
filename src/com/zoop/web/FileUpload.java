package com.zoop.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			serverSocket = new ServerSocket(8889);
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//				String boundary = reader.readLine();
//				String disposition = reader.readLine();
//				int fileIndex = disposition.indexOf("filename=\"");
//				String fileName = disposition.substring(fileIndex,disposition.indexOf("\"",fileIndex));
//				reader.readLine();
//				reader.readLine();
//				//开始读取正文
//				File file = new File("F:/upload/"+fileName);
//				file.createNewFile();
//				FileOutputStream out = new FileOutputStream(file);
//				String line;
//				while((line = reader.readLine()) != null) {
//					if(line.startsWith(boundary) && line.endsWith("--"))
//						break;
//					out.write(line.getBytes());
//					out.flush();
//				}
//				//读取正文结束
//				out.close();
				String line;
				while((line = reader.readLine()) != null) {
					System.out.println(line);
				}
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
