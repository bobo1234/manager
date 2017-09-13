package com.java.back.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.java.back.constant.PageConstant;
import com.java.back.dao.support.AbstractDao;
import com.java.back.dto.RoleListDto;
import com.java.back.model.TeAccountRole;
import com.java.back.model.TeRole;

@Repository
public class RoleDao extends AbstractDao<TeRole> {

	@Override
	public Class<TeRole> getEntityClass() {
		// TODO Auto-generated method stub
		return TeRole.class;
	}

	@SuppressWarnings("unchecked")
	public List<RoleListDto> findRoleList(int page, String searchVal) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.java.back.dto.RoleListDto (roleId, roleName, roleDescription, createTime, creator) from TeRole ");
		query.append(StringUtils.isNotBlank(searchVal) ? "where roleName like '%" + searchVal + "%'" : "");
		query.append("order by roleId desc ");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int findRoleCount(String searchVal) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select count(roleId) from TeRole ");
		query.append(StringUtils.isNotBlank(searchVal) ? "where roleName like '%" + searchVal + "%'" : "");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<TeAccountRole> findMyCharacter(String acctName, String roleLabel) {
		StringBuffer query = new StringBuffer();
		query.append("from TeAccountRole where acctName = ? and roleLabel = ?");
		return findSession().createQuery(query.toString()).setString(0, acctName).setString(1, roleLabel).list();
	}

}