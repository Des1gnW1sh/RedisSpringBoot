/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.dao;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnableDetail;

/**
 * 规则定义启动明细DAO接口
 * @author pjh
 * @version 2017-07-10
 */
@MyBatisDao
public interface RuleEnableDetailDao extends CrudDao<RuleEnableDetail> {
	public RuleEnableDetail getByName(String name);
	
	public void batchDeleteByEId(@Param(value="eId")String eId);
}