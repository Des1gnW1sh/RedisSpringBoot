/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleType;

/**
 * 规则类型DAO接口
 * @author pjh
 * @version 2017-07-10
 */
@MyBatisDao
public interface RuleTypeDao extends CrudDao<RuleType> {
	public RuleType getByName(String name);
	
}