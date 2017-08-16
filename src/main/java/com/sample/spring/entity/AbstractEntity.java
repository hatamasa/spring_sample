package com.sample.spring.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class AbstractEntity {

	@Column(name="INS_DT")
	private Timestamp insDt;

	@PrePersist
	public void prePersist() {
		Timestamp ts = new Timestamp((new Date()).getTime());
		this.insDt = ts;
	}

	public Timestamp getInsDt() {
		return insDt;
	}

	public void setInsDt(Timestamp insDt) {
		this.insDt = insDt;
	}

}
