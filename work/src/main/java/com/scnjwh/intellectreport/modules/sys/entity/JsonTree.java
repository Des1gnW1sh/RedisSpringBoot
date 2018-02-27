/**
 * Copyright (c) 2015 - 2016 elabcare Inc.
 * All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.entity;

import java.util.List;

/**
 * 选择树
 * 
 * @create pengjunhao
 * @createDate 2017年4月21日 上午11:15:36
 * @update
 * @updateDate
 */
public class JsonTree {
	/** 主键 */
	private String id;
	/** 显示名称 */
	private String text;
	/** 是否被选择 */
	private Boolean isChecked;
	/** 是否打开 */
	private String state;
	/** 类型 1：公司 2:部门 3：用户4：角色 */
	private String type;
	/** 子节点数据 */
	private List<JsonTree> children;

	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<JsonTree> getChildren() {
		return children;
	}

	public void setChildren(List<JsonTree> children) {
		this.children = children;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
