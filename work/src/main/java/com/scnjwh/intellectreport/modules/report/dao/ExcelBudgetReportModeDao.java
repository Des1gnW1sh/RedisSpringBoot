package com.scnjwh.intellectreport.modules.report.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportMode;

/**
 * Created by Administrator on 2018-2-5.
 */
@MyBatisDao
public interface ExcelBudgetReportModeDao extends CrudDao<ExcelBudgetReportMode> {

    public String getXmlById(String id);
}
