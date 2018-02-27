/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelCollect;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

/**
 * excel汇总状态DAO接口
 * 
 * @author timo
 * @version 2017-05-09
 */
@MyBatisDao
public interface ExcelCollectDao extends CrudDao<ExcelCollect> {

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public int deleteByBugetId(String id);

	public List<ExcelCollect> findListOfMy(
			@Param(value = "collect") ExcelCollect collect,
			@Param(value = "offices") List<Office> offices);

	public void saveXml(ExcelCollect excelCollect);

	public String getXmlById(String id);

	public void updateStatus(ExcelCollect excelCollect);

	int findOverByModel(@Param(value = "id") String id,
			@Param(value = "status") String status);
}