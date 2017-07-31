package com.sample.spring.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private int userId;

	private String bumon;

	@Column(name="INS_DT")
	private Timestamp insDt;

	private String sex;

	@Column(name="USER_NAME")
	private String userName;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBumon() {
		return this.bumon;
	}

	public void setBumon(String bumon) {
		this.bumon = bumon;
	}

	public Timestamp getInsDt() {
		return this.insDt;
	}

	public void setInsDt(Timestamp insDt) {
		this.insDt = insDt;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}