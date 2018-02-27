/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.rule.entity.RuleCondition;
import com.scnjwh.intellectreport.modules.rule.dao.RuleConditionDao;

/**
 * 规则条件Service
 * @author pjh
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true)
public class RuleConditionService extends CrudService<RuleConditionDao, RuleCondition> {

	public RuleCondition get(String id) {
		return super.get(id);
	}
	
	public List<RuleCondition> findList(RuleCondition ruleCondition) {
		return super.findList(ruleCondition);
	}
	
	public Page<RuleCondition> findPage(Page<RuleCondition> page, RuleCondition ruleCondition) {
		return super.findPage(page, ruleCondition);
	}
	
	@Transactional(readOnly = false)
	public String saveOfCheck(RuleCondition ruleCondition) {
		super.save(ruleCondition);
		return "保存规则条件成功";
	}
	
	@Transactional(readOnly = false)
	public void delete(RuleCondition ruleCondition) {
		super.delete(ruleCondition);
	}
	
	@Transactional(readOnly = false)
	public String batchDeleteOfCheck(String[] ids) {
        super.batchDelete(ids);
        return "批量删除规则条件成功";
    }
	
	public void batchDeleteByDid(String dId){
	    dao.batchDeleteByDid(dId);
	}
	
}