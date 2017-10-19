package com.java.back.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String validtime;
	/**
	 * 会员卡价钱
	 */
	private Long price;
	/**
	 * 创建时间
	 */
	private String createtime;
	/**
	 * 类型下会员卡数量
	 */
	private String cardamount;
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
	public String getValidtime() {
		return validtime;
	}
	public void setValidtime(String validtime) {
		this.validtime = validtime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCardamount() {
		return cardamount;
	}
	public void setCardamount(String cardamount) {
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
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public TeCard() {
		super();
	}
}