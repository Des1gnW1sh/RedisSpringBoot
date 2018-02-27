/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudgetDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudgetLogDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelCollectDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelModelDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudget;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudgetLog;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModel;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * excel模板Service
 * @author timo
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class ExcelModelService extends CrudService<ExcelModelDao, ExcelModel> {
	
	@Autowired
	private ExcelBudgetDao budgetDao;
	@Autowired
	private ExcelBudgetLogDao budgetLogDao;
	@Autowired
	private ExcelCollectDao collectDao;
	
	public ExcelModel get(String id) {
		return super.get(id);
	}
	
	public List<ExcelModel> findList(ExcelModel excelModel) {
		return super.findList(excelModel);
	}
	
	public Page<ExcelModel> findPage(Page<ExcelModel> page, ExcelModel excelModel) {
		return super.findPage(page, excelModel);
	}
	
	@Transactional(readOnly = false)
	public String saveOfCheck(ExcelModel excelModel) {
		if(StringUtils.isNoneBlank(excelModel.getId())){
			int nu = collectDao.findOverByModel(excelModel.getId(), DictUtils.getDictValue("已提交","excel_status","1"));
			if(nu>0){
				return "使用预算已有提交，不能进行修改";
			}
			ExcelBudgetLog a = new ExcelBudgetLog();
			a.preInsert();
			a.setContent("修改模板-"+excelModel.getName());
			budgetLogDao.insert(a);
		}else{
			ExcelBudgetLog a = new ExcelBudgetLog();
			a.preInsert();
			a.setContent("新增模板-"+excelModel.getName());
			budgetLogDao.insert(a);
		}
		super.save(excelModel);
		return "保存excel模板类型成功";
	}
	
	@Transactional(readOnly = false)
	public String deleteOfCheck(ExcelModel excelModel) {
		ExcelBudget budget = new ExcelBudget();
		budget.setExcleModel(excelModel);
		List<ExcelBudget> li = budgetDao.findList(budget);
		if(li!=null && li.size()>0){
			return  "删除excel模板失败：模板正在被预算使用";
		}else{
			super.delete(excelModel);
			ExcelBudgetLog a = new ExcelBudgetLog();
			a.preInsert();
			a.setContent("删除模板-"+excelModel.getName());
			budgetLogDao.insert(a);
			return  "删除excel模板成功";
		}
	}

	public String getXmlById(String id) {
		return super.dao.getXmlById(id);
	}
	
}