/**
 * Copyright (c) 2015 - 2016 elabcare Inc.
 * All rights reserved.
 */
package com.scnjwh.intellectreport.modules.act.entity;

/**
 * 线上的判断条件
 * 
 * @create pengjunhao
 * @createDate 2017年4月28日 下午5:31:40
 * @update
 * @updateDate
 */
public class LineQuery {
	/** id */
	private String id;
	/** 显示内容 */
	private String text;
	/** 图片 */
	private String icon;
	/** 是否被选择 */
	private Boolean selected;

	public String getId() {
		return id;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
