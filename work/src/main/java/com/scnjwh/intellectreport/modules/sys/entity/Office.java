/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scnjwh.intellectreport.common.persistence.TreeEntity;
import com.scnjwh.intellectreport.common.utils.excel.annotation.ExcelField;

/**
 * 机构Entity
 * 
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;
	private Integer parent_Id; // 父级编号
	// private String parentIds; // 所有父级编号
	private Area area; // 归属区域
	private String code; // 机构编码
	// private String name; // 机构名称
	// private Integer sort; // 排序

	private String type; // 机构类型（0：机构；1：行政；2：事业；3其他）
	private String grade; // 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; // 负责人
	private String phone; // 电话
	private String fax; // 传真
	private String email; // 邮箱
	private String useable;// 是否可用
	private User primaryPerson;// 主负责人
	private User deputyPerson;// 副负责人
	private List<String> childDeptList;// 快速添加子部门
	// 新增的列
	private String bigamtDays;// 大额现金规则（金额/天数）
	private String bigAmtPer;// 大额现金规则
	private String codeAlias;// 代码别名
	private String relaGency;// 单位
	private String agentLevel;// 单位级次
	private String levelNo;// 该项级次
	private String industryType;// 行业类型
	private String bugetType;// 经费供给方式
	private String fromItemId;// 来源流水号
	private String toItemId;// 目的流水号
	private String superItemId;// 上级流水号
	private String sysTempretag; // 是否系统预设
	private String isDeliverPay;// 是否转拨
	private String isLeaf;// 是否最末级
	private String elementCode;// 数据项编码
	private String status;// 状态
	private String isBnCode;// 组织机构码

	// DOTOzeke添加字段 2017-05-15
	private String fTypeCode; // 单位类型
	private String fCode; // 机构编码
	private String fSmallName; // 简称
	private String fFullName; // 全称
	private String fOfficeType; // 财政机构类型（1-业务处室 2-本级预算单位
								// 6-一次性单位5-财政专户3-下级财政(下级市州)8-下级财政(试点县)）
	private String fPutunder; // 业务归口处室
	private String fIsWages; // 是否为工资直发单位
	private Date fStartDate;// 启用日期
	private Date fEndDate;// 结束日期

	public Office() {
		super();
		// this.sort = 30;
		this.type = "2";
	}

	public Office(String id) {
		super(id);
	}

	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	@ExcelField(title = "是否启用", align = 2, sort = 26)
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	@JsonIgnore
	@ExcelField(title = "副负责人", align = 2, sort = 20)
	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

	// @JsonBackReference
	// @NotNull
	@JsonIgnore
	@ExcelField(title = "父级编号", align = 1, sort = 1)
	public Office getParent() {
		return this.parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}

	//
	// @Length(min=1, max=2000)
	// public String getParentIds() {
	// return parentIds;
	// }
	//
	// public void setParentIds(String parentIds) {
	// this.parentIds = parentIds;
	// }

	//@NotNull
	@ExcelField(title = "归属区域", align = 2, sort = 16)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	/*
	 * @Length(min=1, max=100)
	 * 
	 * @ExcelField(title="名称", align=2, sort=1) public String getName() { return
	 * name; }
	 * 
	 * 
	 * public void setName(String name) { this.name = name; }
	 * 
	 * @ExcelField(title="排序", align=2, sort=30) public Integer getSort() {
	 * return sort; }
	 * 
	 * public void setSort(Integer sort) { this.sort = sort; }
	 */
	@Length(min = 1, max = 1)
	@ExcelField(title = "类型", align = 2, sort = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title = "等级", align = 2, sort = 14)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min = 0, max = 255)
	@ExcelField(title = "地址", align = 2, sort = 27)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min = 0, max = 100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min = 0, max = 100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min = 0, max = 200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min = 0, max = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 100)
	@ExcelField(title = "编号", align = 2, sort = 0)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// public String getParentId() {
	// return parent != null && parent.getId() != null ? parent.getId() : "0";
	// }

	@Override
	public String toString() {
		return name;
	}

	@Length(min = 0, max = 64)
	@ExcelField(title = "单位类型", align = 2, sort = 6)
	public String getFTypeCode() {
		return fTypeCode;
	}

	/**
	 * @param fTypeCode
	 *            要设置的 fTypeCode
	 */
	public void setFTypeCode(String fTypeCode) {
		this.fTypeCode = fTypeCode;
	}

	/**
	 * @return fCode
	 */
	@Length(min = 0, max = 200)
	@ExcelField(title = "编码", align = 2, sort = 0)
	public String getFCode() {
		return fCode;
	}

	/**
	 * @param fCode
	 *            要设置的 fCode
	 */
	public void setFCode(String fCode) {
		this.fCode = fCode;
	}

	/**
	 * @return fSmallName
	 */
	@Length(min = 0, max = 100)
	@ExcelField(title = "简称", align = 2, sort = 2)
	public String getFSmallName() {
		return fSmallName;
	}

	/**
	 * @param fSmallName
	 *            要设置的 fSmallName
	 */
	public void setFSmallName(String fSmallName) {
		this.fSmallName = fSmallName;
	}

	/**
	 * @return fFullName
	 */
	@Length(min = 0, max = 200)
	@ExcelField(title = "全称", align = 2, sort = 3)
	public String getFFullName() {
		return fFullName;
	}

	/**
	 * @param fFullName
	 *            要设置的 fFullName
	 */
	public void setFFullName(String fFullName) {
		this.fFullName = fFullName;
	}

	/**
	 * @return fOfficeType
	 */
	@JsonIgnore
	@Length(min = 0, max = 64)
	@ExcelField(title = "机构类型", align = 2, sort = 4)
	public String getFOfficeType() {
		return fOfficeType;
	}

	/**
	 * @param fOfficeType
	 *            要设置的 fOfficeType
	 */
	public void setFOfficeType(String fOfficeType) {
		this.fOfficeType = fOfficeType;
	}

	/**
	 * @return fPutunder
	 */
	@Length(min = 0, max = 64)
	@ExcelField(title = "业务归口处室", align = 2, sort = 5)
	public String getFPutunder() {
		return fPutunder;
	}

	/**
	 * @param fPutunder
	 *            要设置的 fPutunder
	 */
	public void setFPutunder(String fPutunder) {
		this.fPutunder = fPutunder;
	}

	/**
	 * @return fIsWages
	 */
	@Length(min = 0, max = 200)
	public String getFIsWages() {
		return fIsWages;
	}

	/**
	 * @param fIsWages
	 *            要设置的 fIsWages
	 */
	public void setFIsWages(String fIsWages) {
		this.fIsWages = fIsWages;
	}

	/**
	 * @return fStartDate
	 */
	@ExcelField(title = "开始时间", align = 2, sort = 15)
	public Date getFStartDate() {
		return fStartDate;
	}

	/**
	 * @param fStartDate
	 *            要设置的 fStartDate
	 */
	public void setFStartDate(Date fStartDate) {
		this.fStartDate = fStartDate;
	}

	/**
	 * @return fEndDate
	 */
	public Date getFEndDate() {
		return fEndDate;
	}

	/**
	 * @param fEndDate
	 *            要设置的 fEndDate
	 */
	public void setFEndDate(Date fEndDate) {
		this.fEndDate = fEndDate;
	}

	public String getBigamtDays() {
		return bigamtDays;
	}

	public void setBigamtDays(String bigamtDays) {
		this.bigamtDays = bigamtDays;
	}

	public String getBigAmtPer() {
		return bigAmtPer;
	}

	public void setBigAmtPer(String bigAmtPer) {
		this.bigAmtPer = bigAmtPer;
	}

	public String getCodeAlias() {
		return codeAlias;
	}

	public void setCodeAlias(String codeAlias) {
		this.codeAlias = codeAlias;
	}

	public String getRelaGency() {
		return relaGency;
	}

	public void setRelaGency(String relaGency) {
		this.relaGency = relaGency;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}


	public String getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(String levelNo) {
		this.levelNo = levelNo;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getBugetType() {
		return bugetType;
	}

	public void setBugetType(String bugetType) {
		this.bugetType = bugetType;
	}

	public String getFromItemId() {
		return fromItemId;
	}

	public void setFromItemId(String fromItemId) {
		this.fromItemId = fromItemId;
	}

	public String getToItemId() {
		return toItemId;
	}

	public void setToItemId(String toItemId) {
		this.toItemId = toItemId;
	}


	public String getSuperItemId() {
		return superItemId;
	}

	public void setSuperItemId(String superItemId) {
		this.superItemId = superItemId;
	}


	public String getSysTempretag() {
		return sysTempretag;
	}

	public void setSysTempretag(String sysTempretag) {
		this.sysTempretag = sysTempretag;
	}

	public String getIsDeliverPay() {
		return isDeliverPay;
	}

	public void setIsDeliverPay(String isDeliverPay) {
		this.isDeliverPay = isDeliverPay;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getElementCode() {
		return elementCode;
	}

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsBnCode() {
		return isBnCode;
	}

	public void setIsBnCode(String isBnCode) {
		this.isBnCode = isBnCode;
	}

	public String getfTypeCode() {
		return fTypeCode;
	}

	public void setfTypeCode(String fTypeCode) {
		this.fTypeCode = fTypeCode;
	}

	public String getfCode() {
		return fCode;
	}

	public void setfCode(String fCode) {
		this.fCode = fCode;
	}

	public String getfSmallName() {
		return fSmallName;
	}

	public void setfSmallName(String fSmallName) {
		this.fSmallName = fSmallName;
	}

	public String getfFullName() {
		return fFullName;
	}

	public void setfFullName(String fFullName) {
		this.fFullName = fFullName;
	}

	public String getfPutunder() {
		return fPutunder;
	}

	public void setfPutunder(String fPutunder) {
		this.fPutunder = fPutunder;
	}

	public String getfIsWages() {
		return fIsWages;
	}

	public void setfIsWages(String fIsWages) {
		this.fIsWages = fIsWages;
	}

	public Integer getParent_Id() {
		return parent_Id;
	}

	public void setParent_Id(Integer parent_Id) {
		this.parent_Id = parent_Id;
	}


	// end

}