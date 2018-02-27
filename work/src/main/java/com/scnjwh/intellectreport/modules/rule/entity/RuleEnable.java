/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.entity;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

/**
 * 规则定义启动Entity
 * @author 彭俊豪
 * @version 2017-07-10
 */
public class RuleEnable extends DataEntity<RuleEnable> {
	
	private static final long serialVersionUID = 1L;
	private String tableId;		// 表ID
	private Integer status;		// 是否启用
	private Office office;		// 所属公司
	private String tableName;
	private String columnIds;
	private String columnNames;

    public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getColumnIds() {
		return columnIds;
	}

	public void setColumnIds(String columnIds) {
		this.columnIds = columnIds;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public RuleEnable() {
		super();
	}

	public RuleEnable(String id){
		super(id);
	}

	@Length(min=1, max=64, message="表ID长度必须介于 1 和 64 之间")
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}