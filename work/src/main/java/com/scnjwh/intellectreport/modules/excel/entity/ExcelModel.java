/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * excel模板Entity
 * @author timo
 * @version 2017-05-09
 */
public class ExcelModel extends DataEntity<ExcelModel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 模板名称
	private String xml;		// xml
	private ExcelModelType type;		// 模板类型
	private String issueFlag;		// 下发标识
	private String sort;		// 排序字段
	private String valid;		// 是否有效
	
	public ExcelModel() {
		super();
	}

	public ExcelModel(String id){
		super(id);
	}

	@Length(min=1, max=200, message="模板名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	@NotNull(message="模板类型不能为空")
	public ExcelModelType getType() {
		return type;
	}

	public void setType(ExcelModelType type) {
		this.type = type;
	}
	
	@Length(min=1, max=1, message="下发标识长度必须介于 1 和 1 之间")
	public String getIssueFlag() {
		return issueFlag;
	}

	public void setIssueFlag(String issueFlag) {
		this.issueFlag = issueFlag;
	}
	
	@Length(min=1, max=255, message="排序字段长度必须介于 1 和 255 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=1, max=1, message="是否有效长度必须介于 1 和 1 之间")
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
}