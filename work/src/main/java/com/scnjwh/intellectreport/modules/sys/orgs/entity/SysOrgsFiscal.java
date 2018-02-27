/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.entity;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 财政机构类型Entity
 * @author timo
 * @version 2017-06-16
 */
public class SysOrgsFiscal extends DataEntity<SysOrgsFiscal> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	
	public SysOrgsFiscal() {
		super();
	}

	public SysOrgsFiscal(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}