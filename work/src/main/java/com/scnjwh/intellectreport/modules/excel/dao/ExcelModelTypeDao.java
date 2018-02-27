/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.excel.dao;

import com.scnjwh.intellectreport.common.persistence.TreeDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModelType;

/**
 * excel模板类型DAO接口
 * 
 * @author timo
 * @version 2017-05-09
 */
@MyBatisDao
public interface ExcelModelTypeDao extends TreeDao<ExcelModelType> {

}