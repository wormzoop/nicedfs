package com.zoop.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	//以下是默认配置
	public static int uploadPort = 8889;
	
	public static int fetchPort = 8899;
	
	public static String dir = "F:/upload";
	
	public static String http = "http://localhost:8899/";
	
	public void config() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File file = new File(path);
		String config = file.getParentFile().getParentFile().getAbsolutePath()+File.separator+"conf"+File.separator+"nicedfs.conf";
		File configFile = new File(config);
		if(configFile.exists()) {//文件不存在就使用默认配置
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(configFile));
				Properties pro = new Properties();
				pro.load(in);
				uploadPort = Integer.valueOf(pro.getProperty("upload_port"));
				fetchPort = Integer.valueOf(pro.getProperty("fetch_port"));
				dir = pro.getProperty("dir");
				http = pro.getProperty("http");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
