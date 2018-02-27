/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.enc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.enc.entity.TSysEncryption;
import com.scnjwh.intellectreport.modules.enc.dao.TSysEncryptionDao;

/**
 * 系统加密设置Service
 * @author enc
 * @version 2017-06-13
 */
@Service
@Transactional(readOnly = true)
public class TSysEncryptionService extends CrudService<TSysEncryptionDao, TSysEncryption> {

	public TSysEncryption get(String id) {
		return super.get(id);
	}
	
	public List<TSysEncryption> findList(TSysEncryption tSysEncryption) {
		return super.findList(tSysEncryption);
	}
	
	public Page<TSysEncryption> findPage(Page<TSysEncryption> page, TSysEncryption tSysEncryption) {
		return super.findPage(page, tSysEncryption);
	}
	
	@Transactional(readOnly = false)
	public void save(TSysEncryption tSysEncryption) {
		super.save(tSysEncryption);
	}
	
	@Transactional(readOnly = false)
	public void delete(TSysEncryption tSysEncryption) {
		super.delete(tSysEncryption);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
        super.batchDelete(ids);
    }
	
}