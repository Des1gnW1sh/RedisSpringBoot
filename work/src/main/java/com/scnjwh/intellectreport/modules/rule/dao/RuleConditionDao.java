/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.dao;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleCondition;

/**
 * 规则条件DAO接口
 * @author pjh
 * @version 2017-07-11
 */
@MyBatisDao
public interface RuleConditionDao extends CrudDao<RuleCondition> {
	public RuleCondition getByName(String name);
	/**
	 * 通过dId删除
	 * @param dId
	 */
	public void batchDeleteByDid(@Param(value="dId")String dId);
}