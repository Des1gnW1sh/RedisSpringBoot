/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.common.utils.DateUtils;

/**
 * 学习交流Entity
 * @author 彭俊豪
 * @version 2017-06-15
 */
public class SysLearn extends DataEntity<SysLearn> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String title;		// 标题
	private Integer status;		// 状态
	private Office office;		// 单位
	private String dateValue;
	public String getDateValue() {
        if (this.getCreateDate() != null) {
            this.dateValue = DateUtils.parseTsDate(this.getCreateDate());
        }
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }
	
	public SysLearn() {
		super();
	}

	public SysLearn(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=2000, message="标题长度必须介于 1 和 2000 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}