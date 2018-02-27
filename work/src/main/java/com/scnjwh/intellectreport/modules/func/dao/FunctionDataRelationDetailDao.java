/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.dao;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelationDetail;

/**
 * 功能数据表关联从表DAO接口
 * @author pjh
 * @version 2017-07-13
 */
@MyBatisDao
public interface FunctionDataRelationDetailDao extends CrudDao<FunctionDataRelationDetail> {
	public FunctionDataRelationDetail getByName(String name);
	
	public void batchDeleteByFid(@Param(value="fId") String fId);
}