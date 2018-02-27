/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * 规则条件Entity
 * @author pjh
 * @version 2017-07-11
 */
public class RuleCondition extends DataEntity<RuleCondition> {
	
	private static final long serialVersionUID = 1L;
	private String dId;		// 规则表ID
	private String tableId;		// 表ID
	private Integer condition;		// 规则条件（包含/排除）
	private String tableColumn;		// 表字段
	private Integer filtration;		// 等于，包含，排除
	private String content;		// 数据过滤内容
	private String dictName; //字典值
	private Office office;		// 所属公司
	private String tableName;
	private String tableColumnName;
	private String filtrationName;
	
	
    public String getFiltrationName() {
	    if(this.getFiltration()!=null){
	        filtrationName =  DictUtils.getDictLabel(this.getFiltration().toString(), "condition_filtration","");
	    }
        return filtrationName;
    }

    public void setFiltrationName(String filtrationName) {
        this.filtrationName = filtrationName;
    }

    public String getTableColumnName() {
        return tableColumnName;
    }

    public void setTableColumnName(String tableColumnName) {
        this.tableColumnName = tableColumnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public RuleCondition() {
		super();
	}

	public RuleCondition(String id){
		super(id);
	}

	@Length(min=0, max=64, message="规则表ID长度必须介于 0 和 64 之间")
	public String getDId() {
		return dId;
	}

	public void setDId(String dId) {
		this.dId = dId;
	}
	
	@Length(min=1, max=64, message="表ID长度必须介于 1 和 64 之间")
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	@NotNull(message="规则条件（包含/排除）不能为空")
	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	
	@Length(min=1, max=255, message="表字段长度必须介于 1 和 255 之间")
	public String getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	
	public Integer getFiltration() {
		return filtration;
	}

	public void setFiltration(Integer filtration) {
		this.filtration = filtration;
	}
	
	@Length(min=1, max=255, message="数据过滤内容长度必须介于 1 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}