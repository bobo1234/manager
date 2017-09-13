package com.java.back.service;

import java.util.List;

import com.java.back.constant.MethodType;
import com.java.back.model.TeModule;
import com.java.back.support.JSONReturn;

public abstract interface ModuleService {

	public abstract JSONReturn findMenu(String acctName);

	public abstract JSONReturn findModuleParameter(String moduleCode, String acctName);

	public abstract JSONReturn findBreadcrumb(String moduleCode);

	public abstract JSONReturn findAllModule(long roleId);

	public abstract JSONReturn setRoleSecureValid(long rold, String code, int type, boolean add);

	public abstract boolean secureValid(String userName, String[] code, MethodType type);
	/**
	 * 新增菜单
	 * @param module
	 * @return
	 */
	public abstract JSONReturn saveModule(TeModule module);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public abstract JSONReturn deleteModule(String id);
	/**
	 * 修改
	 * @param module
	 * @return
	 */
	public abstract JSONReturn updateModule(TeModule module);
	
	public abstract List<TeModule> getAllMenu();
	
	public abstract JSONReturn findByid(Long id);
	/**
	 * 根据url查询code
	 * @param page
	 * @return
	 */
	public String findByPage(String page);
	
}