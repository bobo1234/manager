package com.java.back.controller.forum;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.java.back.controller.support.AbstractController;
import com.java.back.support.JSONReturn;
import com.java.back.utils.fileUtils.FileOperate;

@Scope
@Controller
public class HeadpicController extends AbstractController {

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("HeadUpload")
	@ResponseBody
	public JSONReturn HeadUpload(HttpServletRequest request,
			HttpServletResponse response,String img) {
		System.out.println("img=============="+img);
		FileOperate.saveCardImage(img.substring(img.indexOf(";")+1, img.length()), "222.jpg",request);
		FileOperate.base64ToImage(img.substring(img.indexOf(";")+1, img.length()), "E:\\bbb.jpg");
		return JSONReturn.buildSuccess("ok");
	}

	@RequestMapping("filesUpload")
	public String filesUpload(@RequestParam("fileselect") MultipartFile[] files) {
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				saveFile(file);
			}
		}
		// 重定向
		return "redirect:/list.html";
	}

	/***
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 */
	private boolean saveFile(MultipartFile file) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = request.getSession().getServletContext()
						.getRealPath("/")
						+ "upload/" + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
