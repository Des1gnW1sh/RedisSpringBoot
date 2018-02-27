/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.utils.excel.fieldtype;

import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.rule.entity.RuleCondition;
import com.scnjwh.intellectreport.modules.rule.dao.RuleConditionDao;

/**
 * 规则条件Type
 * 导入导出时 若此类为子类时使用
 * @author pjh
 * @version 2017-07-11
 */
 public class RuleConditionType {
	
	private static RuleConditionDao ruleConditionDao = SpringContextHolder.getBean(RuleConditionDao.class);
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(StringUtils.isBlank(val)){
			return null;
		}
		return ruleConditionDao.getByName(val);
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((RuleCondition)val).getContent() != null){
			return ((RuleCondition)val).getContent();
		}
		return "";
	}
}