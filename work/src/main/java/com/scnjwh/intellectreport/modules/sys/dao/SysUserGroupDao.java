/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.sys.dao;

import com.scnjwh.intellectreport.common.persistence.TreeDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.sys.entity.SysUserGroup;

/**
 * 组维护DAO接口
 * @author hzl
 * @version 2017-07-12
 */
@MyBatisDao
public interface SysUserGroupDao extends TreeDao<SysUserGroup> {
	
}