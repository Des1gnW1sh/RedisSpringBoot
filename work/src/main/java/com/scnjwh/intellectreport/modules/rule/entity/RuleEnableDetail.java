/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.entity;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 规则定义启动明细Entity
 * @author pjh
 * @version 2017-07-10
 */
public class RuleEnableDetail extends DataEntity<RuleEnableDetail> {
	
	private static final long serialVersionUID = 1L;
	private String eId;		// 规则定义启动主键
	private String columnId;		// 启用列
	private String columnName;		// 启动列名称
	private String discName; //字典类型
	
	public String getDiscName() {
        return discName;
    }

    public void setDiscName(String discName) {
        this.discName = discName;
    }

    public RuleEnableDetail() {
		super();
	}

	public RuleEnableDetail(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规则定义启动主键长度必须介于 1 和 64 之间")
	public String getEId() {
		return eId;
	}

	public void setEId(String eId) {
		this.eId = eId;
	}
	
	@Length(min=1, max=255, message="启用列长度必须介于 1 和 255 之间")
	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	
	@Length(min=1, max=255, message="启动列名称长度必须介于 1 和 255 之间")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
}