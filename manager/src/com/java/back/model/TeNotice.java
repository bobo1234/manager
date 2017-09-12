package com.java.back.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公告实体类
 * @author JYD
 *
 */
@Entity
@Table(name = "te_notice")
public class TeNotice extends BaseBean {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	private String title;//标题
	private String content;//内容
	private String file;//附件
	private String createtime;
	private String forwhom;//给谁看(1:管理人员,2:会员)
	private long browsetimes;//浏览次数
	
	public String getForwhom() {
		return forwhom;
	}
	public void setForwhom(String forwhom) {
		this.forwhom = forwhom;
	}
	public long getBrowsetimes() {
		return browsetimes;
	}
	public void setBrowsetimes(long browsetimes) {
		this.browsetimes = browsetimes;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}