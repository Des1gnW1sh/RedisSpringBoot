/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportLogDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportLog;

/**
 * 网络数据报表日志Service
 * 
 * @author timo
 * @version 2017-06-06
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudgetReportLogService extends
		CrudService<ExcelBudgetReportLogDao, ExcelBudgetReportLog> {

	public ExcelBudgetReportLog get(String id) {
		return super.get(id);
	}

	public List<ExcelBudgetReportLog> findList(
			ExcelBudgetReportLog excelBudgetReportLog) {
		return super.findList(excelBudgetReportLog);
	}

	public Page<ExcelBudgetReportLog> findPage(Page<ExcelBudgetReportLog> page,
			ExcelBudgetReportLog excelBudgetReportLog) {
		return super.findPage(page, excelBudgetReportLog);
	}

	@Transactional(readOnly = false)
	public void save(ExcelBudgetReportLog excelBudgetReportLog) {
		super.save(excelBudgetReportLog);
	}

	@Transactional(readOnly = false)
	public void delete(ExcelBudgetReportLog excelBudgetReportLog) {
		super.delete(excelBudgetReportLog);
	}

}