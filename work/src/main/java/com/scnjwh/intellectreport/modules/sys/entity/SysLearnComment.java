/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 评论Entity
 * @author 彭俊豪
 * @version 2017-06-15
 */
public class SysLearnComment extends DataEntity<SysLearnComment> {
	
	private static final long serialVersionUID = 1L;
	private String lId;		// 学习交流主键
	private String content;		// 内容
	private String commentBy;		// 评论人
	private Date commentDate;		// 评论时间
	private String  commentByName; //名称
	
	
	public String getCommentByName() {
        return commentByName;
    }

    public void setCommentByName(String commentByName) {
        this.commentByName = commentByName;
    }

    public SysLearnComment() {
		super();
	}

	public SysLearnComment(String id){
		super(id);
	}

	public String getLId() {
		return lId;
	}

	public void setLId(String lId) {
		this.lId = lId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCommentBy() {
		return commentBy;
	}

	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
}