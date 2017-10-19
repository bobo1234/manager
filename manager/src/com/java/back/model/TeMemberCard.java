package com.java.back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 会员,会员卡关系表
 */
@Entity
@Table(name = "te_member_card")
public class TeMemberCard extends BaseBean {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String id;
	/**
	 * 会员卡id
	 */
	private long cardid;
	/**
	 * 会员id
	 */
	private long memberid;
	/**
	 * 剩余次数
	 */
	private int residuetimes;
	/**
	 * 是否作废
	 */
	private int ifuseless;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCardid() {
		return cardid;
	}
	public void setCardid(long cardid) {
		this.cardid = cardid;
	}
	public long getMemberid() {
		return memberid;
	}
	public void setMemberid(long memberid) {
		this.memberid = memberid;
	}
	public int getResiduetimes() {
		return residuetimes;
	}
	public void setResiduetimes(int residuetimes) {
		this.residuetimes = residuetimes;
	}
	public int getIfuseless() {
		return ifuseless;
	}
	public void setIfuseless(int ifuseless) {
		this.ifuseless = ifuseless;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public TeMemberCard() {
		super();
	}
	
}