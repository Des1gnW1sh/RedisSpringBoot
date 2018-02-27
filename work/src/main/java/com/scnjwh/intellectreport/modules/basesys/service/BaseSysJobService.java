/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.basesys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.basesys.entity.BaseSysJob;
import com.scnjwh.intellectreport.modules.basesys.dao.BaseSysJobDao;

/**
 * 用户岗位Service
 * 
 * @author timo
 * @version 2017-04-20
 */
@Service
@Transactional(readOnly = true)
public class BaseSysJobService extends CrudService<BaseSysJobDao, BaseSysJob> {

	public BaseSysJob get(String id) {
		BaseSysJob baseSysJob = super.get(id);
		return baseSysJob;
	}

	public List<BaseSysJob> findList(BaseSysJob baseSysJob) {
		return super.findList(baseSysJob);
	}

	public Page<BaseSysJob> findPage(Page<BaseSysJob> page,
			BaseSysJob baseSysJob) {
		return super.findPage(page, baseSysJob);
	}

	@Transactional(readOnly = false)
	public void save(BaseSysJob baseSysJob) {
		super.save(baseSysJob);
	}

	@Transactional(readOnly = false)
	public void delete(BaseSysJob baseSysJob) {
		super.delete(baseSysJob);
	}

}