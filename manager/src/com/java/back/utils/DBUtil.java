package com.java.back.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

	private static String username = null;
	private static Connection con;
	private static String password = null;
	private static String driver = null;
	private static String url = null;
	static {
		driver = "dm.jdbc.driver.DmDriver";
		url = "jdbc:dm://192.168.1.110:5236/L001";
		username = "L001-1";
		password = "123456789";
	}

	// public static Connection getConnection() {
	// if (conn==null) {
	// try {
	// Class.forName(driver);
	// conn = DriverManager.getConnection(url, username, password);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// return conn;
	// }
	public static synchronized Connection getConnection() {
		try {
			if (con == null) {
				synchronized (Connection.class) {
					if (con == null) {
						Class.forName(driver);
						con = DriverManager.getConnection(url, username,
								password);
					}
				}
			}
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	public static void close(ResultSet rs, Statement stat, Connection conn)
			throws Exception {
		if (rs != null) {
			rs.close();
		}
		if (stat != null) {
			stat.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public static void close(Statement stat, Connection conn) throws Exception {
		if (stat != null) {
			stat.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 增删修改
	 * 
	 * @param sql
	 * @param str
	 * @return
	 */
	public static int execute(String sql, String str[]) {
		int a = 0;
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			if (str != null) {
				for (int i = 0; i < str.length; i++) {
					pst.setString(i + 1, str[i]);
				}
			}
			a = pst.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * 执行sql语句查询
	 * 
	 * @param sql
	 * @return
	 */
	public static ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return rs;
	}

	/**
	 * 执行sql查询语句，带有参数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet query(String sql, Object[] params) {
		ResultSet rs = null;
		try {
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}

	// 开始事物：取消默认提交
	public static void setAutoCommit(Connection connection) {
		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	// 都成功提交事物
	public static void commit(Connection connection) {
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 回滚事物
	public static void rollbank(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询所有
	 * 
	 * @throws SQLException
	 */
	public static void getList() {
		String sql = "SELECT * FROM  TEST ";
		ResultSet resultSet = query(sql);
		List mapList;
		try {
			mapList = handleResultSetToMapList(resultSet);
			for (int i = 0; i < mapList.size(); i++) {
				System.out.println(mapList.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getOne(String id) {
		String sql = "SELECT * FROM  TEST WHERE ID=?";
		Object[] params = { id };
		ResultSet resultSet = query(sql, params);
		List mapList;
		try {
			mapList = handleResultSetToMapList(resultSet);
			for (int i = 0; i < mapList.size(); i++) {
				System.out.println(mapList.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void deleteByid(String id) {
		String sql = "DELETE FROM  TEST WHERE ID=?";
		String[] params = { id };
		int i = execute(sql, params);// 大于0 删除成功
		System.out.println(i);
	}

	public static void add(String id, String name, int age) {
		String sql = "INSERT INTO TEST VALUES (?,?,?,?) ";
		PreparedStatement pst;
		try {
			pst = getConnection().prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, name);
			pst.setInt(3, age);
			java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
			pst.setDate(4, date);
			int a = pst.executeUpdate();
			System.out.println(a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将查询出的结果封装成map
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> handleResultSetToMapList(
			ResultSet resultSet) throws SQLException {

		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		// 获取列名
		List<String> columnLabels = getColumnLabels(resultSet);
		Map<String, Object> map = null;
		while (resultSet.next()) {
			map = new HashMap<String, Object>();
			for (String columnLabel : columnLabels) {
				Object value = resultSet.getObject(columnLabel);
				map.put(columnLabel, value);
			}
			values.add(map);
		}
		return values;
	}

	private static List<String> getColumnLabels(ResultSet rs) {
		List<String> labels = new ArrayList<String>();
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				labels.add(rsmd.getColumnLabel(i + 1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return labels;
	}

	public static void main(String[] args) throws Exception {
		getList();
		// getOne("1111111111111111111");
		// deleteByid("123");
		// add("1111111111111111111", "张三", 28);
		getConnection().close();
	}
}
