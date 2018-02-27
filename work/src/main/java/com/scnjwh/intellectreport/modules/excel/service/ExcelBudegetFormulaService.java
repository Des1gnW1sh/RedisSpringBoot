/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudegetFormula;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudegetFormulaDao;

/**
 * 预算公式Service
 * @author timo
 * @version 2017-05-31
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudegetFormulaService extends CrudService<ExcelBudegetFormulaDao, ExcelBudegetFormula> {

	public ExcelBudegetFormula get(String id) {
		return super.get(id);
	}
	
	public List<ExcelBudegetFormula> findList(ExcelBudegetFormula excelBudegetFormula) {
		return super.findList(excelBudegetFormula);
	}
	
	public Page<ExcelBudegetFormula> findPage(Page<ExcelBudegetFormula> page, ExcelBudegetFormula excelBudegetFormula) {
		return super.findPage(page, excelBudegetFormula);
	}
	
	@Transactional(readOnly = false)
	public void save(ExcelBudegetFormula excelBudegetFormula) {
		super.save(excelBudegetFormula);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExcelBudegetFormula excelBudegetFormula) {
		super.delete(excelBudegetFormula);
	}
	
}