package com.scnjwh.intellectreport.common.utils.excel.fieldtype;

import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgs;
import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgs;

public class SysOrgsType {
	
	private static SysOrgsDao orgsDao = SpringContextHolder.getBean(SysOrgsDao.class);
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		if(StringUtils.isBlank(val)){
			return null;
		}
		return orgsDao.getByName(val);
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((SysOrgs)val).getName() != null){
			return ((SysOrgs)val).getName();
		}
		return "";
	}
}
