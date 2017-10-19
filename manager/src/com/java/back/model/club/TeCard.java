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
 * 会员卡类型
 * @author gyb
 *
 */
@Entity
@Table(name = "te_card")
public class TeCard extends BaseBean {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;
	
	/**
	 * 类型名称
	 */
	private String typename;
	/**
	 * 有效期
	 */
	private int validtime;
	/**
	 * 会员卡价钱
	 */
	private int price;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 类型下会员卡数量
	 */
	private Long cardamount;
	/**
	 * 总次数
	 */
	private int usertimes;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public int getValidtime() {
		return validtime;
	}
	public void setValidtime(int validtime) {
		this.validtime = validtime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getCardamount() {
		return cardamount;
	}
	public void setCardamount(Long cardamount) {
		this.cardamount = cardamount;
	}
	public int getUsertimes() {
		return usertimes;
	}
	public void setUsertimes(int usertimes) {
		this.usertimes = usertimes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public TeCard() {
		super();
	}
}