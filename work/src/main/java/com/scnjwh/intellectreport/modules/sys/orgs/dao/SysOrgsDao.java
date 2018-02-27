/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.dao;

import com.scnjwh.intellectreport.common.persistence.TreeDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgs;

/**
 * 单位管理DAO接口
 * @author timo
 * @version 2017-06-16
 */
@MyBatisDao
public interface SysOrgsDao extends TreeDao<SysOrgs> {
	public SysOrgs getByName(String name);
	public SysOrgs getByCode(String code);
}