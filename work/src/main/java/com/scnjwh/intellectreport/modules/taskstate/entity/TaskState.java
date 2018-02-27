/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.taskstate.entity;

import com.scnjwh.intellectreport.modules.sys.entity.User;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

import java.util.Date;

/**
 * 用户报表填报任务情况表Entity
 * @author zhou
 * @version 2018-02-24
 */
public class TaskState extends DataEntity<TaskState> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// user_id
	private String taskIds;//进行中任务id
	private Integer runningNum;		// 进行中任务数
	private Integer aboutEndNum;	//就配置时间而言即将结束任务数
	private Integer overtimeNum;		// 超时任务数
	private Integer isCheck;		// 是否查看消息：是(1)否(0)
	private Date checkTime;			//查看时间

	public TaskState() {
		super();
	}

	public TaskState(String id){
		super(id);
	}

	public String getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getRunningNum() {
		return runningNum;
	}

	public void setRunningNum(Integer runningNum) {
		this.runningNum = runningNum;
	}

	public Integer getAboutEndNum() {
		return aboutEndNum;
	}

	public void setAboutEndNum(Integer aboutEndNum) {
		this.aboutEndNum = aboutEndNum;
	}

	public Integer getOvertimeNum() {
		return overtimeNum;
	}

	public void setOvertimeNum(Integer overtimeNum) {
		this.overtimeNum = overtimeNum;
	}
	
	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
}