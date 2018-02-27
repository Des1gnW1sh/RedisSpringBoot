/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.rule.entity.RuleType;
import com.scnjwh.intellectreport.modules.rule.dao.RuleTypeDao;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 规则类型Service
 * @author pjh
 * @version 2017-07-10
 */
@Service
@Transactional(readOnly = true)
public class RuleTypeService extends CrudService<RuleTypeDao, RuleType> {

	public RuleType get(String id) {
		return super.get(id);
	}
	
	public List<RuleType> findList(RuleType ruleType) {
		return super.findList(ruleType);
	}
	
	public Page<RuleType> findPage(Page<RuleType> page, RuleType ruleType) {
		return super.findPage(page, ruleType);
	}
	
	@Transactional(readOnly = false)
	public String saveOfCheck(RuleType ruleType) {
	    if(StringUtils.isNotBlank(ruleType.getId())){
	        super.save(ruleType);
	        return "保存规则类型成功";
	    }
	    ruleType.setOffice(UserUtils.getUser().getOffice());
		super.save(ruleType);
		return "保存规则类型成功";
	}
	
	@Transactional(readOnly = false)
	public void delete(RuleType ruleType) {
		super.delete(ruleType);
	}
	
	@Transactional(readOnly = false)
	public String batchDeleteOfCheck(String[] ids) {
        super.batchDelete(ids);
        return "批量删除规则类型成功";
    }
	
}