/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.basesys.entity;

import org.hibernate.validator.constraints.Length;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 用户职位Entity
 * 
 * @author timo
 * @version 2017-04-20
 */
public class BaseSysPost extends DataEntity<BaseSysPost> {

	private static final long serialVersionUID = 1L;
	private String name; // 职位名称
	private Office office; // 所在部门
	private String code; // 岗位编号
	private String sort; // 排序字段
	private String valid; // 是否有效

	public BaseSysPost() {
		super();
	}

	public BaseSysPost(String id) {
		super(id);
	}

	@Length(min = 1, max = 255, message = "职位名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 1, max = 255, message = "岗位编号长度必须介于 1 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 1, max = 255, message = "排序字段长度必须介于 1 和 255 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Length(min = 1, max = 1, message = "是否有效长度必须介于 1 和 1 之间")
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}