package com.java.back.controller.support;

import javax.servlet.http.HttpSession;

import com.java.back.constant.SessionKey;

public class AbstractController {

	public String acctName(HttpSession httpSession) {
		return (String) httpSession.getAttribute(SessionKey.MODULEACCTNAME);
	}

}
