/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

/**
 * 网络报表提交状态Entity
 * 
 * @author timo
 * @version 2017-06-06
 */
public class ExcelBudgetReportCollect extends
		DataEntity<ExcelBudgetReportCollect> {

	private static final long serialVersionUID = 1L;
	private Office office; // 预算机构
	private ExcelBudgetReport excelBudgetReport; // 预算ID
	private String sumitUser; // 填报人
	private String status; // 提交状态
	private String xml; // 数据报表
	private Date beginUpdateDate; // 开始 更新时间
	private Date endUpdateDate; // 结束 更新时间
	private String officeid; // 查询条件

	public ExcelBudgetReportCollect() {
		super();
	}

	public ExcelBudgetReportCollect(String id) {
		super(id);
	}

	@NotNull(message = "预算机构不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@NotNull(message = "预算不能为空")
	public ExcelBudgetReport getExcelBudgetReport() {
		return excelBudgetReport;
	}

	public void setExcelBudgetReport(ExcelBudgetReport excelBudgetReport) {
		this.excelBudgetReport = excelBudgetReport;
	}

	@Length(min = 0, max = 50, message = "填报人长度必须介于 0 和 50 之间")
	public String getSumitUser() {
		return sumitUser;
	}

	public void setSumitUser(String sumitUser) {
		this.sumitUser = sumitUser;
	}

	@Length(min = 1, max = 1, message = "提交状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}

	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}

	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
}