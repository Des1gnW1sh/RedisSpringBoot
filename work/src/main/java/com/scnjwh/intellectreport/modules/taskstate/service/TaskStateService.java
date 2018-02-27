/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.taskstate.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.taskstate.entity.TaskState;
import com.scnjwh.intellectreport.modules.taskstate.dao.TaskStateDao;

/**
 * 用户报表填报任务情况表Service
 * @author zhou
 * @version 2018-02-24
 */
@Service
@Transactional(readOnly = true)
public class TaskStateService extends CrudService<TaskStateDao, TaskState> {

	public TaskState get(String id) {
		return super.get(id);
	}
	
	public List<TaskState> findList(TaskState taskState) {
		return super.findList(taskState);
	}
	
	public Page<TaskState> findPage(Page<TaskState> page, TaskState taskState) {
		return super.findPage(page, taskState);
	}
	
	@Transactional(readOnly = false)
	public String saveOfCheck(TaskState taskState) {
		super.save(taskState);
		return "保存taskstate成功";
	}
	
	@Transactional(readOnly = false)
	public void delete(TaskState taskState) {
		super.delete(taskState);
	}
	
	@Transactional(readOnly = false)
	public String batchDeleteOfCheck(String[] ids) {
        super.batchDelete(ids);
        return "批量删除taskstate成功";
    }

    public TaskState getTaskState(String userId){
		return dao.getNoCheckByUser(userId);
	}

	@Transactional(readOnly = false)
	public String updateCheck(String id){
		TaskState state = new TaskState();
		state.setId(id);
		state.setIsCheck(1);
		state.setCheckTime(new Date());
		return "" + dao.update(state);
	}
}