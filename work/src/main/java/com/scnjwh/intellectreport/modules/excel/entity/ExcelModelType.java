/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.excel.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.scnjwh.intellectreport.common.persistence.TreeEntity;

/**
 * excel模板类型Entity
 * 
 * @author timo
 * @version 2017-05-09 注意此处由于继承了treeentity 以下字段手动注释 parent||"parentIds" ||"name"
 *          ||"sort"
 */
public class ExcelModelType extends TreeEntity<ExcelModelType> {

	private static final long serialVersionUID = 1L;
	private String name; // 类型名称
	private ExcelModelType parent; // 父级
	private String parentIds; // 所有父级编号
	private String code; // code

	public ExcelModelType() {
		super();
	}

	public ExcelModelType(String id) {
		super(id);
	}

	@Length(min = 1, max = 255, message = "类型名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonBackReference
	public ExcelModelType getParent() {
		return parent;
	}

	public void setParent(ExcelModelType parent) {
		this.parent = parent;
	}

	@Length(min = 0, max = 255, message = "所有父级编号长度必须介于 0 和 255 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Length(min = 1, max = 255, message = "code长度必须介于 1 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

}