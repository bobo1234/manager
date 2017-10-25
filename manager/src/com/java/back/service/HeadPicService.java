package com.java.back.service;

import javax.servlet.http.HttpServletRequest;

import com.java.back.model.forum.TeHeadpic;
import com.java.back.support.JSONReturn;

public abstract interface HeadPicService {

	public abstract JSONReturn findPicList(int page, String title);

	public abstract JSONReturn findPicCount(int page, String title);

	public abstract JSONReturn findPicById(long picid);
	
	public abstract JSONReturn modefyPicById(TeHeadpic headpic);

	public abstract JSONReturn addPic(TeHeadpic headpic);

	public abstract JSONReturn deletePic(long picid,HttpServletRequest request);

	public abstract JSONReturn findAllPic();


}
