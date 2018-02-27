/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsFiscalDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsFiscal;

/**
 * 财政机构类型Service
 * @author timo
 * @version 2017-06-16
 */
@Service
@Transactional(readOnly = true)
public class SysOrgsFiscalService extends CrudService<SysOrgsFiscalDao, SysOrgsFiscal> {
	
	public SysOrgsFiscal getByName(String name) {
		return super.dao.getByName(name);
	}
	public SysOrgsFiscal get(String id) {
		return super.get(id);
	}
	
	public List<SysOrgsFiscal> findList(SysOrgsFiscal sysOrgsFiscal) {
		return super.findList(sysOrgsFiscal);
	}
	
	public Page<SysOrgsFiscal> findPage(Page<SysOrgsFiscal> page, SysOrgsFiscal sysOrgsFiscal) {
		return super.findPage(page, sysOrgsFiscal);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOrgsFiscal sysOrgsFiscal) {
		super.save(sysOrgsFiscal);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOrgsFiscal sysOrgsFiscal) {
		super.delete(sysOrgsFiscal);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
        super.batchDelete(ids);
    }
	public List<Map<String, String>> select(SysOrgsFiscal sysOrgsFiscal){
		 List<Map<String, String>> obj = super.dao.select(sysOrgsFiscal);
		return obj;
	}
	
}