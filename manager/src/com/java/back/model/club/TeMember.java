package com.java.back.model.club;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.back.model.BaseBean;

/**
 * 俱乐部会员表
 */
@Entity
@Table(name = "te_member")
public class TeMember extends BaseBean {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	/**
	 * 微信账号
	 */
	private String account;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 状态等级
	 */
	private int rank;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 性别
	 */
	private int sex;
	/**
	 * 头像
	 */
	private String headimgurl;
	/**
	 * 握拍方式
	 */
	private int griptype;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getGriptype() {
		return griptype;
	}

	public void setGriptype(int griptype) {
		this.griptype = griptype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TeMember() {
		super();
	}
}