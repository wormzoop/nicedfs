package com.zoop.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取文件
 * 端口以及文件路径在配置文件配制
 * @author Administrator
 *
 */
public class FetchFileHandle {

	public static int port = 8899;
	
	public static String dir = "F:/upload";
	
	//监听
	public void accept() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("fetch server is started");
			ExecutorService executor = Executors.newCachedThreadPool();
			while(true) {
				Socket socket = serverSocket.accept();
				executor.execute(new FileFetchHandler(socket));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				serverSocket.close();
			}catch(Exception e) {
				
			}
		}
	}
	
	class FileFetchHandler implements Runnable {
		
		private Socket socket;
		
		public FileFetchHandler(Socket socket) {
			this.socket = socket;
		}
		
		@SuppressWarnings("resource")
		public void run() {
			InputStream in = null;
			try {
				in = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = reader.readLine();
				String fileName;
				OutputStream out = socket.getOutputStream();
				PrintStream writer = new  PrintStream(out);
				if(line != null) {
					int index = line.indexOf(" ");
					fileName = line.substring(index+1,line.indexOf(" ",index+1));
					fileName = URLDecoder.decode(fileName,"utf-8");
					File file = new File(dir+fileName);
					if(file.exists()) {
						writer.println("HTTP/1.0 200 OK");
						writer.println("Content-Type: application/octet-stream");
						writer.println("Content-Length: "+file.length());
						writer.println();
						FileInputStream iin = new FileInputStream(file);
						byte[] buf = new byte[iin.available()];
						iin.read(buf);
						writer.write(buf);
						writer.close();
						out.close();
					}else {
						writer.println("HTTP/1.1 404 File Not Found");
						writer.println("Content-Type: text/html");
						writer.println("Content-Length: 23");
						writer.println();
						writer.println("<h1>File Not Found</h1>");
						writer.close();
						out.close();
					}
				}else {
					writer.println("HTTP/1.1 404 File Not Found");
					writer.println("Content-Type: text/html");
					writer.println("Content-Length: 23");
					writer.println();
					writer.println("<h1>File Not Found</h1>");
					writer.close();
					out.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					socket.close();
					in.close();
				}catch(Exception e) {
					
				}
			}
		}
	}
	
}
