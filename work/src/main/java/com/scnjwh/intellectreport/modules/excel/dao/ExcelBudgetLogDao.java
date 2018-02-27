/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudgetLog;

/**
 * 预算日志DAO接口
 * @author timo
 * @version 2017-05-22
 */
@MyBatisDao
public interface ExcelBudgetLogDao extends CrudDao<ExcelBudgetLog> {
	
}