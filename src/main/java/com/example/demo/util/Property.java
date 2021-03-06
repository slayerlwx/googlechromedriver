package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Property {
	private static Logger log = Logger.getLogger(Property.class);
	
	//配置文件路径测试使用
	//private static final String PROPERTYPATH = "src/main/java/config.properties";
	
	public static String propertyPath = "config.properties";
	
	/**
	 * 读取配置文件
	 * @return
	 * @throws FileNotFoundException 
	 */
	private static InputStream readFile() throws FileNotFoundException{
		//return Property.class.getResourceAsStream(propertyPath);
		return new FileInputStream(new File(propertyPath));
	}
	/**
	 * 获取property对象
	 * @param inputStream
	 * @return
	 */
	public static Properties getProperties(InputStream is){
		Properties prop = new Properties();   
		try {
			if(is == null){
				InputStream inputStream = Property.readFile();
				prop.load(inputStream);
				inputStream.close();
			}else{
				prop.load(is);
				is.close();
			}
		} catch (IOException e) {
			log.error("读取:config.properties文件失败" + e.getMessage());
		}
		return prop;
	}
	
	/**
	 * 获取程序使用路径
	 * @return
	 */
	public static String getProjectPath(){
		return System.getProperty("user.dir").replaceAll("\\\\", "/");
	}
	public static String getPropertyPath() {
		return propertyPath;
	}
	public static void setPropertyPath(String propertyPath) {
		Property.propertyPath = propertyPath;
	}
	
}
