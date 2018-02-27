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
import com.scnjwh.intellectreport.modules.excel.entity.ExcelCollect;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * excel财政预算Service
 * 
 * @author timo
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudgetService extends
		CrudService<ExcelBudgetDao, ExcelBudget> {

	@Autowired
	private ExcelCollectDao excelCollectDao;
	@Autowired
	private ExcelModelDao excelModelDao;
	@Autowired
	private ExcelBudgetLogDao budgetLogDao;

	public ExcelBudget get(String id) {
		return super.get(id);
	}

	public List<ExcelBudget> findList(ExcelBudget excelBudget) {
		return super.findList(excelBudget);
	}

	public Page<ExcelBudget> findPage(Page<ExcelBudget> page,
			ExcelBudget excelBudget) {
		return super.findPage(page, excelBudget);
	}

	/**
	 * 保存财政预算 同时自动保存两个状态
	 */
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = false)
	public String saveOfcheck(ExcelBudget excelBudget) {
		/*
		 * String xml =
		 * excelModelDao.getXmlById(excelBudget.getExcleModel().getId());
		 * excelBudget.setExcleXml(xml);
		 */
		if (StringUtils.isNoneBlank(excelBudget.getId())) {
			ExcelCollect excelCollect = new ExcelCollect();
			excelCollect.setStatus(DictUtils.getDictValue("已提交",
					"excel_status", "1"));
			excelCollect.setBudget(excelBudget);
			List<ExcelCollect> li = excelCollectDao.findList(excelCollect);
			if (li != null && li.size() > 0) {
				return "修改财政预算失败：已有汇总信息";
			} else {
				ExcelBudgetLog a = new ExcelBudgetLog();
				a.preInsert();
				a.setContent("修改预算信息-" + excelBudget.getName());
				a.setBudget(excelBudget);
				budgetLogDao.insert(a);
				super.save(excelBudget);
			}
		} else {
			super.save(excelBudget);
			excelBudget = super.dao.getByName(excelBudget);
			ExcelBudgetLog a = new ExcelBudgetLog();
			a.preInsert();
			a.setContent("新增预算信息-" + excelBudget.getName());
			a.setBudget(excelBudget);
			budgetLogDao.insert(a);
		}
		String orgids = excelBudget.getOfficeLab();
		excelCollectDao.deleteByBugetId(excelBudget.getId());
		for (String orgid : orgids.split(",")) {
			ExcelCollect collect = new ExcelCollect();
			collect.preInsert();
			collect.setOffice(new Office(orgid));
			collect.setBudget(excelBudget);
			collect.setStatus(DictUtils
					.getDictValue("未提交", "excel_status", "0"));
			excelCollectDao.insert(collect);
		}
		return "保存财政预算成功";
	}

	@SuppressWarnings("deprecation")
	@Transactional(readOnly = false)
	public String deleteOfcheck(ExcelBudget excelBudget) {
		ExcelCollect excelCollect = new ExcelCollect();
		excelCollect.setStatus(DictUtils.getDictValue("已提交", "excel_status","1"));
		excelCollect.setBudget(excelBudget);
		List<ExcelCollect> li = excelCollectDao.findList(excelCollect);
		if (li != null && li.size() > 0) {
			return "删除财政预算失败：已有汇总信息";
		} else {
			excelCollectDao.deleteByBugetId(excelBudget.getId());
			ExcelBudgetLog a = new ExcelBudgetLog();
			a.preInsert();
			a.setContent("删除预算信息-" + excelBudget.getName());
			a.setBudget(excelBudget);
			budgetLogDao.insert(a);
			super.delete(excelBudget);
			return "删除财政预算成功";
		}
	}

	public ExcelBudget getByName(String name) {
		ExcelBudget a = new ExcelBudget();
		a.setName(name);
		return super.dao.getByName(a);
	}

	public String getXmlById(String id) {
		return super.dao.getXmlById(id);
	}

	public String getModelXmlById(String id) {
		return super.dao.getModelXmlById(id);
	}
}