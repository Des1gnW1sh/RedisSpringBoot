/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 预算日志Entity
 * @author timo
 * @version 2017-05-22
 */
public class ExcelBudgetLog extends DataEntity<ExcelBudgetLog> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// content
	private ExcelBudget budget;		// budget_id
	private Date beginCreateDate;		// 开始 操作时间
	private Date endCreateDate;		// 结束 操作时间
	
	public ExcelBudgetLog() {
		super();
	}

	public ExcelBudgetLog(String id){
		super(id);
	}

	@Length(min=0, max=255, message="content长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public ExcelBudget getBudget() {
		return budget;
	}

	public void setBudget(ExcelBudget budget) {
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