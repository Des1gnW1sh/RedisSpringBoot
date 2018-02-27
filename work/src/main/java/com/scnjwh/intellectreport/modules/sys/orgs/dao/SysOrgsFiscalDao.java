/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.dao;

import java.util.List;
import java.util.Map;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsFiscal;

/**
 * 财政机构类型DAO接口
 * @author timo
 * @version 2017-06-16
 */
@MyBatisDao
public interface SysOrgsFiscalDao extends CrudDao<SysOrgsFiscal> {
	public List<Map<String, String>> select(SysOrgsFiscal fiscal);
	public SysOrgsFiscal getByName(String name);
}