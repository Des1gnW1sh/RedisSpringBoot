/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.TreeDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	public List<Office> findListByParent(Office office);

	public List<Office> findListByParentId(Office office);

	public List<Office> findListByType(Office office);

	public Integer findListByParentCount(Office office);
	
	public List<Office> findListByfTypeCode(Office office);
	
	public List<Office> findListByFOfficeType(@Param(value="fOfficeType")String fOfficeType);
}
