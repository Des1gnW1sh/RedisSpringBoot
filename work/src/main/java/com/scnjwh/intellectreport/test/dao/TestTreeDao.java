/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.test.dao;

import com.scnjwh.intellectreport.common.persistence.TreeDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author ThinkGem
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}