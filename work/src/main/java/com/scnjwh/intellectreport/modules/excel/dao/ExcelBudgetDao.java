/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudget;

/**
 * excel财政预算DAO接口
 * 
 * @author timo
 * @version 2017-05-09
 */
@MyBatisDao
public interface ExcelBudgetDao extends CrudDao<ExcelBudget> {

	public ExcelBudget getByName(ExcelBudget budget);

	public String getXmlById(String id);

	public String getModelXmlById(String id);

	public void saveXml(ExcelBudget budget);

	public void updateIsOver(ExcelBudget budget);
}