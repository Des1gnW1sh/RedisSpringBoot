/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.utils.excel.fieldtype;

import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.rule.entity.RuleType;
import com.scnjwh.intellectreport.modules.rule.dao.RuleTypeDao;

/**
 * 规则类型Type
 * 导入导出时 若此类为子类时使用
 * @author pjh
 * @version 2017-07-10
 */
 public class RuleTypeType {
	
	private static RuleTypeDao ruleTypeDao = SpringContextHolder.getBean(RuleTypeDao.class);
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(StringUtils.isBlank(val)){
			return null;
		}
		return ruleTypeDao.getByName(val);
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((RuleType)val).getName() != null){
			return ((RuleType)val).getName();
		}
		return "";
	}
}