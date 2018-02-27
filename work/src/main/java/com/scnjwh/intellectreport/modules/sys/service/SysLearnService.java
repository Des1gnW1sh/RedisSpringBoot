/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.sys.entity.SysLearn;
import com.scnjwh.intellectreport.modules.sys.dao.SysLearnDao;

/**
 * 学习交流Service
 * @author 彭俊豪
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true)
public class SysLearnService extends CrudService<SysLearnDao, SysLearn> {

	public SysLearn get(String id) {
		return super.get(id);
	}
	
	public List<SysLearn> findList(SysLearn sysLearn) {
		return super.findList(sysLearn);
	}
	
	public Page<SysLearn> findPage(Page<SysLearn> page, SysLearn sysLearn) {
		return super.findPage(page, sysLearn);
	}
	
	@Transactional(readOnly = false)
	public void save(SysLearn sysLearn) {
	    if (sysLearn.getContent() != null) {
	        sysLearn
                .setContent(StringEscapeUtils.unescapeHtml4(sysLearn.getContent()));
        }
		super.save(sysLearn);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysLearn sysLearn) {
		super.delete(sysLearn);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
        super.batchDelete(ids);
    }
	
}