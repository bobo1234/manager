package com.java.back.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;

public class FileUtil {
	public static void main(String[] args) throws Exception {
		String inUrl = "http://192.168.1.110:8082/FileService/jsp/index.jsp";
		String outUrl = "E://aaa.jsp";
		FileUtil a = new FileUtil();
		a.saveImageToDisk(inUrl,outUrl);
	}

	public void saveImageToDisk(String inUrl,String outUrl) {
		InputStream inputStream = null;
		inputStream = getInputStream(inUrl);// 调用getInputStream()函数。
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(outUrl);// 初始化一个FileOutputStream对象。
			while ((len = inputStream.read(data)) != -1) {// 循环读取inputStream流中的数据，存入文件流fileOutputStream
				fileOutputStream.write(data, 0, len);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// finally函数，不管有没有异常发生，都要调用这个函数下的代码
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();// 记得及时关闭文件流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();// 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static InputStream getInputStream(String url_path) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(url_path);// 创建的URL
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();// 打开链接
				httpURLConnection.setConnectTimeout(3000);// 设置网络链接超时时间，3秒，链接失败后重新链接
				httpURLConnection.setDoInput(true);// 打开输入流
				httpURLConnection.setRequestMethod("GET");// 表示本次Http请求是GET方式
				int responseCode = httpURLConnection.getResponseCode();// 获取返回码
				if (responseCode == 200) {// 成功为200
					// 从服务器获得一个输入流
					inputStream = httpURLConnection.getInputStream();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;

	}

	// 查找指定目录下的文件
	public static List findFileName(String filepath) {
		File file = new File(filepath);
		List list = new ArrayList();
		File[] files = file.listFiles();
		String name = "";
		if (files == null) {
			return list;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				list.add(files[i].getPath());
			} else {
				list.addAll(findFileName(files[i].getPath()));
			}
		}
		return list;
	}
}