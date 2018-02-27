/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 预算公式Entity
 * @author timo
 * @version 2017-05-31
 */
public class ExcelBudegetFormula extends DataEntity<ExcelBudegetFormula> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String content;		// 公式值
	private ExcelBudegetFormulaType type;//公式类型
	
	public ExcelBudegetFormula() {
		super();
	}

	public ExcelBudegetFormula(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=255, message="公式值长度必须介于 1 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@NotNull(message="公式类型不能为空")
	public ExcelBudegetFormulaType getType() {
		return type;
	}

	public void setType(ExcelBudegetFormulaType type) {
		this.type = type;
	}
	
	
	
}