/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 网络数据报表日志Entity
 * @author timo
 * @version 2017-06-06
 */
public class ExcelBudgetReportLog extends DataEntity<ExcelBudgetReportLog> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private ExcelBudgetReport budget;		// 预算
	private Date beginCreateDate;		// 开始 create_date
	private Date endCreateDate;		// 结束 create_date
	
	public ExcelBudgetReportLog() {
		super();
	}

	public ExcelBudgetReportLog(String id){
		super(id);
	}

	@Length(min=0, max=255, message="内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	public ExcelBudgetReport getBudget() {
		return budget;
	}

	public void setBudget(ExcelBudgetReport budget) {
		this.budget = budget;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}