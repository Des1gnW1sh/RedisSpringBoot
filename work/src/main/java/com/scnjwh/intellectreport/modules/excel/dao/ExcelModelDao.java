/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModel;

/**
 * excel模板DAO接口
 * 
 * @author timo
 * @version 2017-05-09
 */
@MyBatisDao
public interface ExcelModelDao extends CrudDao<ExcelModel> {

	String getXmlById(String id);

}