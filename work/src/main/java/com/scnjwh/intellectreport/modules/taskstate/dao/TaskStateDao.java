/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.taskstate.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.taskstate.entity.TaskState;

/**
 * 用户报表填报任务情况表DAO接口
 * @author zhou
 * @version 2018-02-24
 */
@MyBatisDao
public interface TaskStateDao extends CrudDao<TaskState> {
	public TaskState getByName(String name);

	/**
	 * 根据用户查询任务统计
	 * @param userId 用户id
	 * @return
	 */
	TaskState getByUserId(String userId);

	/**
	 * 查询未查看用户任务统计
	 * @param userId 用户id
	 * @return
	 */
	TaskState getNoCheckByUser(String userId);
}