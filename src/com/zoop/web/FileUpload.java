package com.zoop.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
				String boundary = "";
				String fileName = "";
				String line_header;
				while((line_header = reader.readLine()) != null) {
					if(line_header.startsWith("content-type"))
						boundary = line_header.substring(line_header.indexOf("boundary=")+9);
					if(line_header.startsWith("Content-Disposition")) {
						fileName = line_header.substring(line_header.indexOf("filename=\"")+10, line_header.length()-1);
						break;
					}
				}
				if(!fileName.equals("")) {
					reader.readLine();
					reader.readLine();
					//开始读取正文
					File file = new File("F:/upload/"+fileName);
					file.createNewFile();
					FileOutputStream out = new FileOutputStream(file);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
					String line;
					while((line = reader.readLine()) != null) {
						if(line.contains(boundary) && line.endsWith("--"))
							break;
						writer.write(line);
						writer.newLine();
						writer.flush();
					}
					//读取正文结束
					writer.close();
					out.close();
					PrintWriter ret = new PrintWriter(socket.getOutputStream());
					String url = "http://localhost:8899/"+fileName;
					ret.println("HTTP/1.0 200 OK");
					ret.println("Content-Type: text/html");
					ret.println("Content-Length: "+url.length());
					ret.println();
					ret.println(url);
					ret.close();
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
