package com.java.back.controller.support;

import javax.servlet.http.HttpSession;

import com.java.back.constant.SessionKey;

public class AbstractController {
	/**
	 * 获取session里用户名称
	 * @param httpSession
	 * @return
	 */
	public String acctName(HttpSession httpSession) {
		return (String) httpSession.getAttribute(SessionKey.MODULEACCTNAME);
	}

}
