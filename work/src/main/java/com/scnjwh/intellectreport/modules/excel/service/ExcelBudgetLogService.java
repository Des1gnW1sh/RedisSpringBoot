/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudgetLog;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudgetLogDao;

/**
 * 预算日志Service
 * @author timo
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudgetLogService extends CrudService<ExcelBudgetLogDao, ExcelBudgetLog> {

	public ExcelBudgetLog get(String id) {
		return super.get(id);
	}
	
	public List<ExcelBudgetLog> findList(ExcelBudgetLog excelBudgetLog) {
		return super.findList(excelBudgetLog);
	}
	
	public Page<ExcelBudgetLog> findPage(Page<ExcelBudgetLog> page, ExcelBudgetLog excelBudgetLog) {
		return super.findPage(page, excelBudgetLog);
	}
	
	@Transactional(readOnly = false)
	public void save(ExcelBudgetLog excelBudgetLog) {
		super.save(excelBudgetLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExcelBudgetLog excelBudgetLog) {
		super.delete(excelBudgetLog);
	}
	
}