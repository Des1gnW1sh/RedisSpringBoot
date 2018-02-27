/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.excel.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.scnjwh.intellectreport.common.persistence.TreeEntity;

/**
 * 预算公式类型Entity
 * @author timo
 * @version 2017-05-31
 * 注意此处由于继承了treeentity 以下字段手动注释
 *	 parent||"parentIds" ||"name" ||"sort"
 */
public class ExcelBudegetFormulaType extends TreeEntity<ExcelBudegetFormulaType> {

	private static final long serialVersionUID = 1L;
			private String name;		// 名称			
			private ExcelBudegetFormulaType parent;		// 父级			
			private String parentIds;		// 所有父级编号			
			private String code;		// code			
	
	public ExcelBudegetFormulaType() {
		super();
	}

	public ExcelBudegetFormulaType(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	@JsonBackReference
		public ExcelBudegetFormulaType getParent() {
			return parent;
		}

		public void setParent(ExcelBudegetFormulaType parent) {
			this.parent = parent;
		}
	@Length(min=1, max=255, message="所有父级编号长度必须介于 1 和 255 之间")
		public String getParentIds() {
			return parentIds;
		}

		public void setParentIds(String parentIds) {
			this.parentIds = parentIds;
		}
	@Length(min=1, max=255, message="code长度必须介于 1 和 255 之间")
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