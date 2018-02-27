/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.validation.constraints.NotNull;

import com.scnjwh.intellectreport.modules.sys.entity.Area;
import com.scnjwh.intellectreport.common.persistence.TreeEntity;
import com.scnjwh.intellectreport.common.utils.excel.annotation.ExcelField;

/**
 * 单位管理Entity
 * @author timo
 * @version 2017-06-16
 */
public class SysOrgs extends TreeEntity<SysOrgs> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 机构编码
	private SysOrgs parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	private String fullName;		// 全称
	private SysOrgsFiscal fiscalType;		// 财政机构类型
	private SysOrgs putunder;		// 业务归口处室
	private Area area;		// 归属区域
	private String type;		// 机构类型
	private String primaryPerson;		// 主负责人
	private String deputyPerson;		// 副负责人
	private String address;		// 联系地址
	private String zipCode;		// 邮政编码
	private String phone;		// 电话
	private String fax;		// 传真
	private String email;		// 邮箱
	private String useable;		// 是否启用
	
	public SysOrgs() {
		super();
	}

	public SysOrgs(String id){
		super(id);
	}

	@Length(min=1, max=200, message="机构编码长度必须介于 1 和 200 之间")
	@ExcelField(title="单位编码", align=2, sort=10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	@ExcelField(title="父级编码", align=2, sort=11,value="parent.code")
	public SysOrgs getParent() {
		return parent;
	}

	public void setParent(SysOrgs parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
//	@ExcelField(title="", align=2, sort=12)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=200, message="名称长度必须介于 1 和 200 之间")
	@ExcelField(title="单位简称", align=2, sort=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=200, message="全称长度必须介于 1 和 200 之间")
	@ExcelField(title="单位全称", align=2, sort=30)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@NotNull(message="财政机构类型不能为空")
	@ExcelField(title="财政机构类型", align=2, sort=50,value="fiscalType.name")
	public SysOrgsFiscal getFiscalType() {
		return fiscalType;
	}

	public void setFiscalType(SysOrgsFiscal fiscalType) {
		this.fiscalType = fiscalType;
	}
	
//	@JsonBackReference
//	@NotNull(message="业务归口处室不能为空")
	@ExcelField(title="业务归口处室", align=2, sort=60,value="putunder.name")
	public SysOrgs getPutunder() {
		return putunder;
	}

	public void setPutunder(SysOrgs putunder) {
		this.putunder = putunder;
	}
	@ExcelField(title="归属区域", align=2, sort=80,value="area.name")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=1, max=1, message="机构类型长度必须介于 1 和 1 之间")
	@ExcelField(title="单位类型", align=2, sort=70,dictType="sys_Agency_Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="主负责人长度必须介于 0 和 64 之间")
	@ExcelField(title="主负责人", align=2, sort=90)
	public String getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(String primaryPerson) {
		this.primaryPerson = primaryPerson;
	}
	
	@Length(min=0, max=64, message="副负责人长度必须介于 0 和 64 之间")
	@ExcelField(title="副负责人", align=2, sort=101)
	public String getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(String deputyPerson) {
		this.deputyPerson = deputyPerson;
	}
	
	@Length(min=0, max=255, message="联系地址长度必须介于 0 和 255 之间")
	@ExcelField(title="联系地址", align=2, sort=110)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=200, message="邮政编码长度必须介于 0 和 200 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	@Length(min=0, max=200, message="电话长度必须介于 0 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=200, message="传真长度必须介于 0 和 200 之间")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Length(min=0, max=200, message="邮箱长度必须介于 0 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=1, message="是否启用长度必须介于 0 和 1 之间")
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}