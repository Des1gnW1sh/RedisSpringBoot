/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * excel财政预算Entity
 * @author timo
 * @version 2017-05-09
 */
public class ExcelBudget extends DataEntity<ExcelBudget> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 预算名称
	private String excleXml;		// 汇总xml  数据文件
	private ExcelModel excleModel;		// 模板
	
	//以下两参数仅是修改是展现使用
	private String officeLab; //下拨机构的id
	private String officeLabName;//下拨机构name
	
	//用户区分完成归集和未完成
	private String isOver;//是否完成

	public ExcelBudget() {
		super();
	}

	public ExcelBudget(String id){
		super(id);
	}

	@Length(min=1, max=255, message="预算名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getExcleXml() {
		return excleXml;
	}

	public void setExcleXml(String excleXml) {
		this.excleXml = excleXml;
	}
	
	@NotNull(message="模板不能为空")
	public ExcelModel getExcleModel() {
		return excleModel;
	}

	public void setExcleModel(ExcelModel excleModel) {
		this.excleModel = excleModel;
	}

	public String getOfficeLab() {
		return officeLab;
	}

	public void setOfficeLab(String officeLab) {
		this.officeLab = officeLab;
	}

	public String getOfficeLabName() {
		return officeLabName;
	}

	public void setOfficeLabName(String officeLabName) {
		this.officeLabName = officeLabName;
	}

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
}