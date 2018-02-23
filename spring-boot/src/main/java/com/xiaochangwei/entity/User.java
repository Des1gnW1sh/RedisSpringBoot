package com.xiaochangwei.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
	private static final long serialVersionUID = 4167869185651158701L;

	private Long id;
	private String name;
	private String pwd;
	private String salt;

	// @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date birthDay = new Date();

	private List<Photo> pics = new ArrayList<Photo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public List<Photo> getPics() {
		return pics;
	}

	public void setPics(List<Photo> pics) {
		this.pics = pics;
	}

}
