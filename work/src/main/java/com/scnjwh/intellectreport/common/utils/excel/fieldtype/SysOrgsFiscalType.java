package com.scnjwh.intellectreport.common.utils.excel.fieldtype;

import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsFiscalDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsFiscal;
import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsFiscalDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsFiscal;

public class SysOrgsFiscalType {
	private static SysOrgsFiscalDao fiscalDao = SpringContextHolder.getBean(SysOrgsFiscalDao.class);
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(StringUtils.isBlank(val)){
			return null;
		}
		return fiscalDao.getByName(val);
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((SysOrgsFiscal)val).getName() != null){
			return ((SysOrgsFiscal)val).getName();
		}
		return "";
	}
}
