/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.entity;

import org.hibernate.validator.constraints.Length;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 规则类型Entity
 * @author pjh
 * @version 2017-07-10
 */
public class RuleType extends DataEntity<RuleType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Office office;		// 所属公司
	
	public RuleType() {
		super();
	}

	public RuleType(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
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
	
}