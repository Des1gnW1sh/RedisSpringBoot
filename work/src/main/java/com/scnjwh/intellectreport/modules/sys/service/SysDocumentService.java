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
import com.scnjwh.intellectreport.modules.sys.entity.SysDocument;
import com.scnjwh.intellectreport.modules.sys.dao.SysDocumentDao;

/**
 * 公文管理Service
 * @author 彭俊豪
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true)
public class SysDocumentService extends CrudService<SysDocumentDao, SysDocument> {

	public SysDocument get(String id) {
		return super.get(id);
	}
	
	public List<SysDocument> findList(SysDocument sysDocument) {
		return super.findList(sysDocument);
	}
	
	public Page<SysDocument> findPage(Page<SysDocument> page, SysDocument sysDocument) {
		return super.findPage(page, sysDocument);
	}
	
	@Transactional(readOnly = false)
	public void save(SysDocument sysDocument) {
	    if (sysDocument.getContent() != null) {
	        sysDocument
                .setContent(StringEscapeUtils.unescapeHtml4(sysDocument.getContent()));
        }
		super.save(sysDocument);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysDocument sysDocument) {
		super.delete(sysDocument);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
        super.batchDelete(ids);
    }
	
}