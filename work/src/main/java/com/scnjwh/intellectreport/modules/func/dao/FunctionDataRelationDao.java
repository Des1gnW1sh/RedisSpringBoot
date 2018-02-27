/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelation;

/**
 * 功能数据表关联DAO接口
 * @author 彭俊豪
 * @version 2017-07-13
 */
@MyBatisDao
public interface FunctionDataRelationDao extends CrudDao<FunctionDataRelation> {
	public FunctionDataRelation getByName(String name);
	
}