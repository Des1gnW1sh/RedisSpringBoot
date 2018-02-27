/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelationDetail;
import com.scnjwh.intellectreport.modules.func.dao.FunctionDataRelationDetailDao;

/**
 * 功能数据表关联从表Service
 * @author pjh
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true)
public class FunctionDataRelationDetailService extends CrudService<FunctionDataRelationDetailDao, FunctionDataRelationDetail> {

	public FunctionDataRelationDetail get(String id) {
		return super.get(id);
	}
	
	public List<FunctionDataRelationDetail> findList(FunctionDataRelationDetail functionDataRelationDetail) {
		return super.findList(functionDataRelationDetail);
	}
	
	public Page<FunctionDataRelationDetail> findPage(Page<FunctionDataRelationDetail> page, FunctionDataRelationDetail functionDataRelationDetail) {
		return super.findPage(page, functionDataRelationDetail);
	}
	
	@Transactional(readOnly = false)
	public String saveOfCheck(FunctionDataRelationDetail functionDataRelationDetail) {
		super.save(functionDataRelationDetail);
		return "保存功能数据表关联从表成功";
	}
	
	@Transactional(readOnly = false)
	public void delete(FunctionDataRelationDetail functionDataRelationDetail) {
		super.delete(functionDataRelationDetail);
	}
	
	@Transactional(readOnly = false)
	public String batchDeleteOfCheck(String[] ids) {
        super.batchDelete(ids);
        return "批量删除功能数据表关联从表成功";
    }
	
}