/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.excel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.service.TreeService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudegetFormulaDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudegetFormulaTypeDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudegetFormula;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudegetFormulaType;

/**
 * 预算公式类型Service
 * 
 * @author timo
 * @version 2017-05-31
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudegetFormulaTypeService extends
		TreeService<ExcelBudegetFormulaTypeDao, ExcelBudegetFormulaType> {

	@Autowired
	private ExcelBudegetFormulaDao budegetFormulaDao;

	public ExcelBudegetFormulaType get(String id) {
		return super.get(id);
	}

	public List<ExcelBudegetFormulaType> findList(
			ExcelBudegetFormulaType excelBudegetFormulaType) {
		if (excelBudegetFormulaType != null) {
			if (StringUtils.isBlank(excelBudegetFormulaType.getParentIds()))
				excelBudegetFormulaType.setParentIds("%");
			else
				excelBudegetFormulaType.setParentIds(excelBudegetFormulaType
						.getParentIds() + "%");
			return dao.findByParentIdsLike(excelBudegetFormulaType);
		}
		return new ArrayList<ExcelBudegetFormulaType>();
	}

	@Transactional(readOnly = false)
	public void save(ExcelBudegetFormulaType excelBudegetFormulaType) {
		super.save(excelBudegetFormulaType);
	}

	@Transactional(readOnly = false)
	public String deleteOfCheck(ExcelBudegetFormulaType excelBudegetFormulaType) {
		ExcelBudegetFormula a1 = new ExcelBudegetFormula();
		a1.setType(excelBudegetFormulaType);
		List<ExcelBudegetFormula> li1 = budegetFormulaDao.findAllList(a1);
		if (li1 != null && li1.size() > 0) {
			return "删除excel公式类型失败：类型下有公式";
		} else {
			ExcelBudegetFormulaType a = new ExcelBudegetFormulaType();
			a.setParent(excelBudegetFormulaType);
			List<ExcelBudegetFormulaType> li = super.findList(a);
			if (li != null && li.size() > 0) {
				return "删除excel公式类型失败：拥有子类型";
			}
			super.delete(excelBudegetFormulaType);
			return "删除公式类型成功";
		}
	}
}