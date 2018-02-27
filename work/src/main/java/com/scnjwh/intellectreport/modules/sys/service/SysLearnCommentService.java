/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.sys.entity.SysLearnComment;
import com.scnjwh.intellectreport.modules.sys.dao.SysLearnCommentDao;

/**
 * 评论Service
 * @author 彭俊豪
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true)
public class SysLearnCommentService extends CrudService<SysLearnCommentDao, SysLearnComment> {

	public SysLearnComment get(String id) {
		return super.get(id);
	}
	
	public List<SysLearnComment> findList(SysLearnComment sysLearnComment) {
		return super.findList(sysLearnComment);
	}
	
	public Page<SysLearnComment> findPage(Page<SysLearnComment> page, SysLearnComment sysLearnComment) {
		return super.findPage(page, sysLearnComment);
	}
	
	@Transactional(readOnly = false)
	public void save(SysLearnComment sysLearnComment) {
		super.save(sysLearnComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysLearnComment sysLearnComment) {
		super.delete(sysLearnComment);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
        super.batchDelete(ids);
    }
	
}