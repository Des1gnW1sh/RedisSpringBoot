/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.service.TreeService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.dao.UserDao;
import com.scnjwh.intellectreport.modules.sys.orgs.dao.SysOrgsDao;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgs;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsFiscal;

/**
 * 单位管理Service
 * 
 * @author timo
 * @version 2017-06-16
 */
@Service
@Transactional(readOnly = true)
public class SysOrgsService extends TreeService<SysOrgsDao, SysOrgs> {

	@Autowired
	private UserDao userDao;

	public SysOrgs get(String id) {
		return super.get(id);
	}

	public SysOrgs getByName(String name) {
		return super.dao.getByName(name);
	}

	public SysOrgs getByCode(String code) {
		return super.dao.getByCode(code);
	}

	public List<SysOrgs> findList(SysOrgs sysOrgs) {
		if (StringUtils.isNotBlank(sysOrgs.getParentIds())) {
			sysOrgs.setParentIds("," + sysOrgs.getParentIds() + ",");
		}
		return super.findList(sysOrgs);
	}

	@Transactional(readOnly = false)
	public String saveOfCheck(SysOrgs sysOrgs) {
		if (StringUtils.isBlank(sysOrgs.getId())) {
			// 添加
			super.save(sysOrgs);
			return "保存单位成功";
		}
		// 修改
		super.save(sysOrgs);
		return "保存单位成功";
	}

	@Transactional(readOnly = false)
	public void delete(SysOrgs sysOrgs) {
		super.delete(sysOrgs);
	}

	public List<SysOrgs> getOneList() {
		SysOrgsFiscal fiscal = new SysOrgsFiscal();
		fiscal.setName("业务处室");
		SysOrgs sysOrgs = new SysOrgs();
		sysOrgs.setFiscalType(fiscal);
		return super.findList(sysOrgs);
	}

}