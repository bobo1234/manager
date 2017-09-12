package com.java.back.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public class ClassUtil {

	public static void reflectObject(Object obj) throws Exception {
		System.out.println(obj.getClass().getName());
		// 获取实体类的所有属性，返回Field数组
		Field[] field = obj.getClass().getDeclaredFields();
		// 遍历所有属性
		for (int j = 0; j < field.length; j++) {
			// 获取属性的名字
			String name = field[j].getName();
			System.out.println("属性为：" + name);
			// 将属性的首字符大写，方便构造get，set方法
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			// 获取属性的类型
			String type = field[j].getGenericType().toString();
			// 如果type是类类型，则前面包含"class "，后面跟类名
			Method m = obj.getClass().getMethod("get" + name);
			// 调用getter方法获取属性值
			String value = (String) m.invoke(obj);
		}
	}

	public static void reflectClass(Class M) throws Exception {
		System.out.println(M.getName());
		// 获取实体类的所有属性，返回Field数组
		Field[] field = M.getDeclaredFields();
		// 遍历所有属性
		for (int j = 0; j < field.length; j++) {
			// 获取属性的名字
			String name = field[j].getName();
			// 获取属性的类型
			String type = field[j].getGenericType().toString();
//			System.out.println("属性为：" + name+"    ,类型："+type);
			System.out.println("属性为：" + name);
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			// 如果type是类类型，则前面包含"class "，后面跟类名
		}
	}

	/**
	 * 根据反射组装hql 说明：字段包含id的默认相等查询，其他为模糊查询，不要将时间日期相关字段赋值，请独立拼接sql语句进行查询
	 * 
	 * @param c 实体类
	 * @param tb sql语句里的表别名
	 * @return
	 * @throws Exception
	 */
	public static String readFieldsForHql(Object c,String tb) {
		Field fields[] = c.getClass().getDeclaredFields();// 获得对象所有属性
		Field field = null;
		String hql = " ";
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);// 修改访问权限
			try {
				if ("" != field.get(c) && null != field.get(c)
						&& StringUtils.isNotBlank(field.get(c).toString())) {
					if (field.getName().contains("id")
							|| field.getName().contains("Id")
							|| field.getName().contains("ID")) {
						if (StringUtil.isNotEmpty(tb)) {
							hql += "and " + tb+"."+field.getName() + "  ='" + field.get(c)
									+ "' ";
						}else {
							hql += "and " + field.getName() + "  ='" + field.get(c)
									+ "' ";
						}
						
					} else {
						if (StringUtil.isNotEmpty(tb)) {
							hql += "and " +tb+"."+ field.getName() + " like '%"
									+ field.get(c) + "%' ";
						}else {
							hql += "and " + field.getName() + " like '%"
									+ field.get(c) + "%' ";
						}
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hql;
	}

	/**
	 * 根据反射组装sql,说明：字段包含id的默认相等查询，其他为模糊查询，不要将时间日期相关字段赋值，请独立拼接sql语句进行查询
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static String readFieldsForSql(Object c,String tb) {
		Field fields[] = c.getClass().getDeclaredFields();// 获得对象所有属性
		Field field = null;
		String sql = " ";
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);// 修改访问权限
			try {
				if ("" != field.get(c) && null != field.get(c)
						&& StringUtils.isNotBlank(field.get(c).toString())) {
					// System.out.println("属性类型：");field.getType() instanceof
					// Date
					Column data = field.getAnnotation(Column.class);
					String name = data.name();//注释名称
					System.out.println("获取注释的:"+name);
					String fdname ="";
					String ColumnName=field.getName();//属性名称
					if (StringUtil.isNotEmpty(name)) {
						fdname=name;
					}else {
						fdname=ColumnName.toUpperCase();
					}
					if (ColumnName.contains("id")
							|| ColumnName.contains("Id")
							|| ColumnName.contains("ID")) {
						if (StringUtil.isNotEmpty(tb)) {
							sql += "AND " + tb+"."+fdname + "  ='"
									+ field.get(c) + "' ";
						}else {
							sql += "AND " + fdname + "  ='"
									+ field.get(c) + "' ";
						}
						
					} else {
						if (StringUtil.isNotEmpty(tb)) {
							sql += "AND " + tb+"."+fdname
									+ " like '%" + field.get(c) + "%' ";
						}else {
							sql += "AND " + fdname
									+ " like '%" + field.get(c) + "%' ";
						}
						
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sql;
	}

	public static void main(String[] args) throws Exception {
		
		
		// Field fields[] = c.getClass().getDeclaredFields();// 获得对象所有属性
		// Field field = null;
		// String[] attr = { "name", "address" };
		// for (int i = 0; i < fields.length; i++) {
		// field = fields[i];
		// field.setAccessible(true);// 修改访问权限
		// System.out.println(field.getName() + ":" + field.get(c));// 读取属性值
		// }

		// StringBuffer hql = new StringBuffer("from Tcontraband where 1=1 ");
		// for (int i = 0; i < m.length; i++) {
		// if (StringUtil.isNotEmpty(m)) {
		// hql.append("and NAME" + StringUtil.formatLike(name));
		// }
		// }

	}
}
