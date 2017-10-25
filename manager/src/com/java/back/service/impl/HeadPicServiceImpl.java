package com.java.back.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.back.constant.ClubConst;
import com.java.back.constant.PageConstant;
import com.java.back.dao.support.AbstractDao;
import com.java.back.model.forum.TeHeadpic;
import com.java.back.service.HeadPicService;
import com.java.back.support.JSONReturn;
import com.java.back.utils.DateTimeUtil;
import com.java.back.utils.PageUtils;
import com.java.back.utils.StringUtil;
import com.java.back.utils.fileUtils.FileOperations;

@Service
@Transactional
public class HeadPicServiceImpl extends AbstractDao<TeHeadpic> implements
		HeadPicService {

	@Override
	public JSONReturn findPicList(int page, String title) {
		// TODO Auto-generated method stub
		String hql = getHql + " and title" + StringUtil.formatLike(title);
		List<TeHeadpic> queryHqlForList = this.queryHqlForList(hql, null, page);
		if (CollectionUtils.isEmpty(queryHqlForList))
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(queryHqlForList);
	}

	@Override
	public JSONReturn findPicCount(int page, String title) {
		// TODO Auto-generated method stub
		String hql = getHql + " and title" + StringUtil.formatLike(title);
		int count = this.countHqlAll(hql);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count,
				PageConstant.PAGE_LIST));
	}

	@Override
	public JSONReturn findPicById(long picid) {
		// TODO Auto-generated method stub
		TeHeadpic teHeadpic = get(picid);
		if (teHeadpic==null)
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(teHeadpic);
	}

	@Override
	public JSONReturn modefyPicById(TeHeadpic headpic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONReturn addPic(TeHeadpic headpic) {
		// TODO Auto-generated method stub
		headpic.setIfuseless(ClubConst.valid);
		headpic.setCreatetime(DateTimeUtil.getCurrentTime());
		boolean save = this.save(headpic);
		if (save) {
			return JSONReturn.buildSuccess("新增成功");
		}
		return JSONReturn.buildFailure("新增失败");
	}

	@Override
	public JSONReturn deletePic(long picid, HttpServletRequest request) {
		// TODO Auto-generated method stub
		int deleteByProperty = deleteByProperty("id", picid);
		TeHeadpic teHeadpic = get(picid);
		String rootPath = request.getSession().getServletContext()
				.getRealPath("");
		FileOperations.delefile(rootPath, teHeadpic.getPicurl());
		return deleteByProperty > 0 ? JSONReturn.buildSuccess("删除成功")
				: JSONReturn.buildFailure("删除失败");
	}

	@Override
	public JSONReturn findAllPic() {
		// TODO Auto-generated method stub
		List<TeHeadpic> findAll = findAll();
		return JSONReturn.buildSuccess(findAll);
	}

	@Override
	public Class<TeHeadpic> getEntityClass() {
		// TODO Auto-generated method stub
		return TeHeadpic.class;
	}

}
