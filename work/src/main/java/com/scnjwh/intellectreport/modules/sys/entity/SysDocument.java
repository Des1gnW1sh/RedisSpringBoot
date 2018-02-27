/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.common.utils.DateUtils;

/**
 * 公文管理Entity
 * @author 彭俊豪
 * @version 2017-06-20
 */
public class SysDocument extends DataEntity<SysDocument> {
	
	private static final long serialVersionUID = 1L;
	private Integer status;		// 状态
	private String code;		// 公文编号
	private String title;		// 公文标题
	private Integer type;		// 公文类型
	private Integer dense;		// 公文密级
	private Integer degree;		// 公文紧急程度
	private Date timeLimit;		// 公文办理时限
	private String digest;		// 公文摘要
	private String content;		// 正文信息
	private String fattaid;		// 附件id
	private Office office;		// 单位
	private String dateValue;
	private Date beginTimeLimit;		// 开始 公文办理时限
	private Date endTimeLimit;		// 结束 公文办理时限
	public String getDateValue() {
        if (this.getCreateDate() != null) {
            this.dateValue = DateUtils.parseTsDate(this.getCreateDate());
        }
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }
	public SysDocument() {
		super();
	}

	public SysDocument(String id){
		super(id);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=1, max=255, message="公文编号长度必须介于 1 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=2000, message="公文标题长度必须介于 1 和 2000 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull(message="公文类型不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotNull(message="公文密级不能为空")
	public Integer getDense() {
		return dense;
	}

	public void setDense(Integer dense) {
		this.dense = dense;
	}
	
	@NotNull(message="公文紧急程度不能为空")
	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="公文办理时限不能为空")
	public Date getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	@Length(min=0, max=255, message="公文摘要长度必须介于 0 和 255 之间")
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="附件id长度必须介于 0 和 255 之间")
	public String getFattaid() {
		return fattaid;
	}

	public void setFattaid(String fattaid) {
		this.fattaid = fattaid;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public Date getBeginTimeLimit() {
		return beginTimeLimit;
	}

	public void setBeginTimeLimit(Date beginTimeLimit) {
		this.beginTimeLimit = beginTimeLimit;
	}
	
	public Date getEndTimeLimit() {
		return endTimeLimit;
	}

	public void setEndTimeLimit(Date endTimeLimit) {
		this.endTimeLimit = endTimeLimit;
	}
		
}