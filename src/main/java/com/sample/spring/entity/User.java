package com.sample.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the user database table.
 *
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private int userId;

	@Column(name="BUMON")
	private String bumon;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="SEX")
	private String sex;

	@Column(name="INS_DT")
	private Timestamp insDt;

	public User() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBumon() {
		return bumon;
	}

	public void setBumon(String bumon) {
		this.bumon = bumon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getInsDt() {
		return insDt;
	}

	public void setInsDt(Timestamp insDt) {
		this.insDt = insDt;
	}

}