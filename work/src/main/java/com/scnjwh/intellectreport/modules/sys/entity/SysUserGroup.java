/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.TreeEntity;

/**
 * 组维护Entity
 * @author hzl
 * @version 2017-07-12
 * 注意此处由于继承了treeentity 以下字段手动注释
 *	 parent||"parentIds" ||"name" ||"sort"
 */
public class SysUserGroup extends TreeEntity<SysUserGroup> {

	private static final long serialVersionUID = 1L;
		//	private SysUserGroup parent;		// 父级编号			
		//	private String parentIds;		// 所有父级编号			
		//	private String name;		// 名称			
			private String forder;		// 级次			
			private String ftype;		// 分组类型			
		//	private Integer sort;		// 排序			
	
	public SysUserGroup() {
		super();
	}

	public SysUserGroup(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
		public SysUserGroup getParent() {
			return parent;
		}

		public void setParent(SysUserGroup parent) {
			this.parent = parent;
		}
//	@Length(min=1, max=64, message="所有父级编号长度必须介于 1 和 64 之间")
//		public String getParentIds() {
//			return parentIds;
//		}
//
//		public void setParentIds(String parentIds) {
//			this.parentIds = parentIds;
//		}
//	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
	
	@NotNull(message="级次不能为空")
		public String getForder() {
			return forder;
		}

		public void setForder(String forder) {
			this.forder = forder;
		}
	@Length(min=1, max=10, message="分组类型长度必须介于 1 和 10 之间")
		public String getFtype() {
			return ftype;
		}

		public void setFtype(String ftype) {
			this.ftype = ftype;
		}
//		public Integer getSort() {
//			return sort;
//		}
//
//		public void setSort(Integer sort) {
//			this.sort = sort;
//		}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}