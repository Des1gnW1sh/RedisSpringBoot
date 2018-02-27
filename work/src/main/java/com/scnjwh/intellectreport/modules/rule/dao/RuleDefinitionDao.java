/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleDefinition;

/**
 * 规则定义DAO接口
 * @author 彭俊豪
 * @version 2017-07-10
 */
@MyBatisDao
public interface RuleDefinitionDao extends CrudDao<RuleDefinition> {
	public RuleDefinition getByName(String name);
	
}