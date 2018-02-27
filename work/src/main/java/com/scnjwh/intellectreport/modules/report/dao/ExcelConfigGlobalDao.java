/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelConfigGlobal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 网络数据报表预算DAO接口
 *
 * @author timo
 * @version 2017-06-06
 */
@MyBatisDao
public interface ExcelConfigGlobalDao extends CrudDao<ExcelConfigGlobal> {
    public ExcelConfigGlobal getByUserId(String userId);

    /**
     * 获取配置过的用户的部门id
     * @return
     */
    List<ExcelConfigGlobal> getAllConfig();
}