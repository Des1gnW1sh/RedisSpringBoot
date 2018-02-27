/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsAdjust;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsAdjustDao;

/**
 * 单位调整Service
 * @author timo
 * @version 2017-06-19
 */
@Service
@Transactional(readOnly = true)
public class SysOrgsAdjustService extends CrudService<SysOrgsAdjustDao, SysOrgsAdjust> {

	public SysOrgsAdjust get(String id) {
		return super.get(id);
	}
	
	public List<SysOrgsAdjust> findList(SysOrgsAdjust sysOrgsAdjust) {
		return super.findList(sysOrgsAdjust);
	}
	
	public Page<SysOrgsAdjust> findPage(Page<SysOrgsAdjust> page, SysOrgsAdjust sysOrgsAdjust) {
		return super.findPage(page, sysOrgsAdjust);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOrgsAdjust sysOrgsAdjust) {
		super.save(sysOrgsAdjust);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOrgsAdjust sysOrgsAdjust) {
		super.delete(sysOrgsAdjust);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
        super.batchDelete(ids);
    }
	
}