package com.java.back.controller.forum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.back.controller.support.AbstractController;
import com.java.back.support.JSONReturn;
import com.java.back.utils.fileUtils.FileOperate;

@Scope
@Controller
public class HeadpicController extends AbstractController {

	@Autowired
	private HttpServletRequest request;

	/**
	 * 首页图片上传
	 * @param request
	 * @param response
	 * @param img
	 * @return
	 */
	@RequestMapping("HeadPicUpload")
	@ResponseBody
	public JSONReturn HeadPicUpload(HttpServletRequest request,
			HttpServletResponse response,String img) {
		System.out.println("img=============="+img);
		FileOperate.saveCardImage(img.substring(img.indexOf(";")+1, img.length()), "222.jpg",request);
		
		return JSONReturn.buildSuccess("ok");
	}


}
