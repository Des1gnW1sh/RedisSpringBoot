/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.dao;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportCollect;

import java.util.List;

/**
 * 网络报表提交状态DAO接口
 * 
 * @author timo
 * @version 2017-06-06
 */
@MyBatisDao
public interface ExcelBudgetReportCollectDao extends
		CrudDao<ExcelBudgetReportCollect> {
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int deleteByBugetId(String id);
	  /**
     * 批量删除-根据预算
     * @param ids id集合
     * @Author : pengjunhao. create at 2017年6月7日 下午5:08:59
     */
    public void batchDeleteByBugetIds(@Param(value = "ids")String[] ids);
    
    /**
     * 批量检查状态--根据预算
     * @param ids id集合
     * @Author : pengjunhao. create at 2017年6月7日 下午5:08:59
     */
    public int findListOfCheckStatusByBugetIds(@Param(value = "ids")String[] ids);
    
    public String getXmlById(String id);
	public void saveXml(ExcelBudgetReportCollect budgetReportCollect);
	
	public void updateStatus(ExcelBudgetReportCollect excelCollect);

	/**
	 * 获取报表任务下发部门已提交汇总的数据
	 * @param excelId 报表任务ID
	 */
	List<ExcelBudgetReportCollect> getSummaryData(String excelId);

	/**
	 * 获取任务完成进度
	 * @param id 报表任务id
	 * @return
	 */
	String getTaskProgress(String id);

	/**
	 * 获取主任务报表
	 * @param id 子任务id
	 * @return
	 */
	String getParentExcel(String id);

	/**
	 * 获得任务下未上报的子任务
	 * @param id 任务id
	 * @return
	 */
	List<ExcelBudgetReportCollect> getNotUploadTask(String id);

	/**
	 * 根据部门id查询填报任务
	 * @param officeId 部门id
	 * @return
	 */
	List<ExcelBudgetReportCollect> getCollectByOffice(String officeId);
}