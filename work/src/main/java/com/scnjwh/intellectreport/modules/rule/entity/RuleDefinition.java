/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

/**
 * 规则定义Entity
 * @author 彭俊豪
 * @version 2017-07-10
 */
public class RuleDefinition extends DataEntity<RuleDefinition> {
	
	private static final long serialVersionUID = 1L;
	private String typeId;		// 规则类型
	private String name;		// 规则名称
	private Office office;		// 所属公司
	private String typeName;
	private String functionName;       // 功能名称
    private String functionId;      // 功能主键
    private List<RuleCondition> ruleConditions; //条件

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
	
	public List<RuleCondition> getRuleConditions() {
        return ruleConditions;
    }

    public void setRuleConditions(List<RuleCondition> ruleConditions) {
        this.ruleConditions = ruleConditions;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public RuleDefinition() {
		super();
	}

	public RuleDefinition(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规则类型长度必须介于 1 和 64 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=255, message="规则名称长度必须介于 1 和 255 之间")
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