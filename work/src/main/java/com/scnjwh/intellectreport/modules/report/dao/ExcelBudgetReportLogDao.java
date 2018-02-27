/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportLog;

/**
 * 网络数据报表日志DAO接口
 * @author timo
 * @version 2017-06-06
 */
@MyBatisDao
public interface ExcelBudgetReportLogDao extends CrudDao<ExcelBudgetReportLog> {
	
}