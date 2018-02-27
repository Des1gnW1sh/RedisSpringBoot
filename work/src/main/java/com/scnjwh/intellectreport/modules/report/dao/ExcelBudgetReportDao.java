/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

/**
 * 网络数据报表预算DAO接口
 *
 * @author timo
 * @version 2017-06-06
 */
@MyBatisDao
public interface ExcelBudgetReportDao extends CrudDao<ExcelBudgetReport> {

    //我参与的
    public List<ExcelBudgetReport> findListOfMy(ExcelBudgetReport budgetReport);

    public String getXmlById(String id);

    public List<ExcelBudgetReport> findListOfOwn(ExcelBudgetReport budgetReport);

    public String  getBudget( @Param(value = "parentId") String parentId,@Param(value = "xmlId") String xmlId,@Param(value = "status") String status);

    public Map<String,String>  getBudgetChild( @Param(value = "parentId") String parentId,@Param(value = "xmlId") String xmlId);

    public Map<String,String>  getBudgetMB( @Param(value = "id") String id);

    public String getSubmitById(String id);

    public void saveSubmit(ExcelBudgetReport budget);

    public void updateIsOver(ExcelBudgetReport budget);

    List<ExcelBudgetReport> getNeedEmailReport();

    List<Map<String,Object>> getOfficeLeader(String[] officeIds);
    /**
     * 获得已结束任务
     * @return
     */
    List<ExcelBudgetReport> getFinishedTask();


    /**
     * 设置子任务为已发送结束邮件
     * @param id 子任务id
     * @return
     */
    int setFinishEmail(String id);
}