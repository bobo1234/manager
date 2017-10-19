package com.java.back.constant;

/**
 * 俱乐部相关常量
 * 
 * @author gyb
 */
public class ClubConst {

	/**
	 * 黄铜会员
	 */
	public static final int TONG = 1;

	/**
	 * 白银会员
	 */
	public static final int YIN = 2;

	/**
	 * 黄金会员
	 */
	public static final int JIN = 3;

	/**
	 * 钻石会员
	 */
	public static final int ZUAN = 4;

	public static String findStatus(int key) {
		switch (key) {
		case 1:
			return "黄铜会员";
		case 2:
			return "白银会员";
		case 3:
			return "黄金会员";
		case 4:
			return "钻石会员";
		}
		return "";
	}

	/**
	 * 女
	 */
	public static final int GIRL = 0;
	/**
	 * 男
	 */
	public static final int BOY = 1;

	/**
	 * 直板
	 */
	public static final int ZHI = 1;
	/**
	 * 横板
	 */
	public static final int HENG = 2;
	
	/**
	 * 会员卡未使用
	 */
	public static final int STATE_UNUSED=1;
	
	/**
	 * 会员卡已使用
	 */
	public static final int STATE_USED=2;
	
	/**
	 * 会员卡已过期
	 */
	public static final int STATE_OVERDUE=3;
	
	
	/**
	 * 根据 消费情况判断会员等级
	 * @param money
	 * @return
	 */
	public static int getGrade(int money) {
		if (money>5000) {
			return ZUAN;
		}else if (money>2000) {
			return JIN;
		}else if (money>500) {
			return YIN;
		}
		return TONG;
	}
}
