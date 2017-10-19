package com.java.back.model.config;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.back.model.BaseBean;

/**
 * 首页图片实体类
 * @author JYD
 *
 */
@Entity
@Table(name = "te_headpic")
public class TeHeadpic extends BaseBean {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	private String title;//标题
	private String picurl;//url
	private long porder;//顺序
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public long getPorder() {
		return porder;
	}
	public void setPorder(long porder) {
		this.porder = porder;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public TeHeadpic() {
		super();
	}

}