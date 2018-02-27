/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.basesys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.basesys.entity.BaseSysPost;
import com.scnjwh.intellectreport.modules.basesys.dao.BaseSysPostDao;

/**
 * 用户职位Service
 * @author timo
 * @version 2017-04-20
 */
@Service
@Transactional(readOnly = true)
public class BaseSysPostService extends CrudService<BaseSysPostDao, BaseSysPost> {

	public BaseSysPost get(String id) {
		return super.get(id);
	}
	
	public List<BaseSysPost> findList(BaseSysPost baseSysPost) {
		return super.findList(baseSysPost);
	}
	
	public Page<BaseSysPost> findPage(Page<BaseSysPost> page, BaseSysPost baseSysPost) {
		return super.findPage(page, baseSysPost);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseSysPost baseSysPost) {
		super.save(baseSysPost);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseSysPost baseSysPost) {
		super.delete(baseSysPost);
	}
	
}