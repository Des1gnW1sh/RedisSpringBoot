/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.utils.excel.fieldtype;

import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelation;
import com.scnjwh.intellectreport.modules.func.dao.FunctionDataRelationDao;

/**
 * 功能数据表关联Type
 * 导入导出时 若此类为子类时使用
 * @author 彭俊豪
 * @version 2017-07-13
 */
 public class FunctionDataRelationType {
	
	private static FunctionDataRelationDao functionDataRelationDao = SpringContextHolder.getBean(FunctionDataRelationDao.class);
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(StringUtils.isBlank(val)){
			return null;
		}
		return functionDataRelationDao.getByName(val);
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((FunctionDataRelation)val).getFunctionName() != null){
			return ((FunctionDataRelation)val).getFunctionName();
		}
		return "";
	}
}