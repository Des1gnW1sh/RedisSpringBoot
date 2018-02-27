/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.entity;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 功能数据表关联从表Entity
 * @author pjh
 * @version 2017-07-13
 */
public class FunctionDataRelationDetail extends DataEntity<FunctionDataRelationDetail> {
	
	private static final long serialVersionUID = 1L;
	private String fId;		// 主表ID
	private String retinueTableName;		// 从表名字
	private String retinueTableId;		// 从表
	private String retinueColumnName;		// 从表字段名称
	private String retinueColumn;		// 从表字段
	private String columnId;		// 主表字段
	private String columnName;		// 主表字段名称
	private String retinueTable;
	
    public String getRetinueTable() {
        return retinueTable;
    }

    public void setRetinueTable(String retinueTable) {
        this.retinueTable = retinueTable;
    }

    public FunctionDataRelationDetail() {
		super();
	}

	public FunctionDataRelationDetail(String id){
		super(id);
	}

	@Length(min=1, max=64, message="主表ID长度必须介于 1 和 64 之间")
	public String getFId() {
		return fId;
	}

	public void setFId(String fId) {
		this.fId = fId;
	}
	
	@Length(min=1, max=255, message="从表名字长度必须介于 1 和 255 之间")
	public String getRetinueTableName() {
		return retinueTableName;
	}

	public void setRetinueTableName(String retinueTableName) {
		this.retinueTableName = retinueTableName;
	}
	
	@Length(min=1, max=64, message="从表长度必须介于 1 和 64 之间")
	public String getRetinueTableId() {
		return retinueTableId;
	}

	public void setRetinueTableId(String retinueTableId) {
		this.retinueTableId = retinueTableId;
	}
	
	@Length(min=1, max=255, message="从表字段名称长度必须介于 1 和 255 之间")
	public String getRetinueColumnName() {
		return retinueColumnName;
	}

	public void setRetinueColumnName(String retinueColumnName) {
		this.retinueColumnName = retinueColumnName;
	}
	
	@Length(min=1, max=255, message="从表字段长度必须介于 1 和 255 之间")
	public String getRetinueColumn() {
		return retinueColumn;
	}

	public void setRetinueColumn(String retinueColumn) {
		this.retinueColumn = retinueColumn;
	}
	
	
	public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Length(min=1, max=255, message="主表字段名称长度必须介于 1 和 255 之间")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
}