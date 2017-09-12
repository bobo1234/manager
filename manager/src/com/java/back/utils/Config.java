package com.java.back.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
  
public class Config {  

	public final static String  ALARM_INFO = "alarmInfo";
	public final static String  WarnNotice_INFO = "warnNoticeInfo";
	public final static String SP = "\\|\\|\\|\\|";//分割符为  ||||
	public final static String TD = "$_$";//替代符
	public static int REDIS_OUT_TIME=60*60*2;//redis超时时间
	public static String LINUX_USERNAME = "user";//linux服务器登陆用户名
	public static String LINUX_PASSWORD = "useruser";//linux服务器登陆密码
	public static String REDIS_IP = "192.168.1.110";//redis服务器
	public static int REDIS_PORT = 6379;//端口
	public static int DMDB_PORT = 5236;//达梦同步端口
	public static int DMHS_PORT = 5345;//达梦同步端口
	public static String REDIS_PASSWORD = "jydadmin123";//密码
	
	static{
		REDIS_IP = getValue("redis","redis.host");//redis服务器
		REDIS_PORT = Integer.parseInt(getValue("redis", "redis.port"));//端口
		REDIS_PASSWORD = getValue("redis", "redis.pass");//密码
		REDIS_OUT_TIME = Integer.parseInt(getValue("redis", "redis.out_time"));//密码
	}
	
    public static String load(String key) {  
        String configFile = Config.class.getResource("/").getPath().toString().replaceAll("file:/", "") + "config.properties";  
        Properties p = new Properties(); 
        String result = "";
        try {
            InputStream inputStream = new FileInputStream(configFile);  
            p.load(inputStream);  
            result = p.getProperty(key);
        } catch (IOException e1) {  
            e1.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
       // System.out.println(configFile); 
        return result;
    }  
    
    public static String getValue(String key){
    	String result = "";
    	try{
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream is = classLoader.getResourceAsStream("config.properties");
    		Properties p = new Properties(); 
    		p.load(is);
    		result = p.getProperty(key);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
    public static String getValue(String name,String key){
    	String result = "";
    	try{
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream is = classLoader.getResourceAsStream(name+".properties");
    		Properties p = new Properties(); 
    		p.load(is);
    		result = p.getProperty(key);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public static void main(String args[]){
    	
    	System.out.println(getValue("redis", "redis.host"));
    }
} 