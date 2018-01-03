package com.zoop.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static int uploadPort = 8889;
	
	public static int fetchPort = 8899;
	
	public static String dir = "F:/upload";
	
	public static String http = "";
	
	public void config() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File file = new File(path);
		String config = file.getParentFile().getParentFile().getAbsolutePath()+File.separator+"conf"+File.separator+"nicedfs.conf";
		File configFile = new File(config);
		if(configFile.exists()) {
			//文件存在使用配置
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(configFile));
				Properties pro = new Properties();
				pro.load(in);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//文件不存在使用默认配置
		}
	}
	
}
