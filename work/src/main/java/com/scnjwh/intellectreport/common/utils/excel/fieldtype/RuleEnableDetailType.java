/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.utils.excel.fieldtype;

import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnableDetail;
import com.scnjwh.intellectreport.modules.rule.dao.RuleEnableDetailDao;

/**
 * 规则定义启动明细Type
 * 导入导出时 若此类为子类时使用
 * @author pjh
 * @version 2017-07-10
 */
 public class RuleEnableDetailType {
	
	private static RuleEnableDetailDao ruleEnableDetailDao = SpringContextHolder.getBean(RuleEnableDetailDao.class);
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(StringUtils.isBlank(val)){
			return null;
		}
		return ruleEnableDetailDao.getByName(val);
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((RuleEnableDetail)val).getColumnName() != null){
			return ((RuleEnableDetail)val).getColumnName();
		}
		return "";
	}
}