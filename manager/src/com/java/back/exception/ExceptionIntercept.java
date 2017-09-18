package com.java.back.exception;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.java.back.support.JSONReturn;

@ControllerAdvice
public class ExceptionIntercept implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * spring捕捉到的异常 在此进行处理
	 * 
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ Exception.class })
	public JSONReturn exception(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		ex.printStackTrace();
		System.out.println("服务器报错信息:" + ex.getMessage());
		return JSONReturn.buildFailure("服务器错误!");
	}

	/**
	 * 用于处理异常的
	 * 
	 * @return
	 */
//	@ExceptionHandler({ Exception.class })
//	public ModelAndView exception(Exception e) {
//		ModelAndView modelAndView = new ModelAndView();
//		System.out.println("错误信息--------------:" + e.getMessage());
//		modelAndView.addObject("mess", e.getMessage());
//		modelAndView.setViewName("error/error");
//		return modelAndView;
//	}
}
