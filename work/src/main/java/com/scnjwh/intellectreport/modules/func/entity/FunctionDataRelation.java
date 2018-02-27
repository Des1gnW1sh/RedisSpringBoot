/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.entity;

import java.util.List;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 功能数据表关联Entity
 * @author 彭俊豪
 * @version 2017-07-13
 */
public class FunctionDataRelation extends DataEntity<FunctionDataRelation> {
	
	private static final long serialVersionUID = 1L;
	private String functionName;		// 功能名称
	private String functionId;		// 功能主键
	private String tableName;		// 主表名称
	private String tableId;		// 主表主键
	private String table; //主表描述
	private List<FunctionDataRelationDetail> functionDataRelationDetails;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<FunctionDataRelationDetail> getFunctionDataRelationDetails() {
        return functionDataRelationDetails;
    }

    public void setFunctionDataRelationDetails(List<FunctionDataRelationDetail> functionDataRelationDetails) {
        this.functionDataRelationDetails = functionDataRelationDetails;
    }

    public FunctionDataRelation() {
		super();
	}

	public FunctionDataRelation(String id){
		super(id);
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
}