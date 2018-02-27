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
import com.scnjwh.intellectreport.modules.excel.dao.ExcelModelDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelModelTypeDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModel;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModelType;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * excel模板类型Service
 * @author timo
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class ExcelModelTypeService extends TreeService<ExcelModelTypeDao, ExcelModelType> {
	
	@Autowired
	private ExcelModelDao excelModelDao;
	public ExcelModelType get(String id) {
		return super.get(id);
	}
	
	public List<ExcelModelType> findList(ExcelModelType excelModelType) {
		if (excelModelType!=null){
			if(StringUtils.isBlank(excelModelType.getParentIds())){
				excelModelType.setParentIds("%");
			}else{
				excelModelType.setParentIds(excelModelType.getParentIds()+"%");
			}
			return dao.findByParentIdsLike(excelModelType);
		}
		return new ArrayList<ExcelModelType>();
	}
	
	@Transactional(readOnly = false)
	public void save(ExcelModelType excelModelType) {
		super.save(excelModelType);
	}
	
	@Transactional(readOnly = false)
	public String deleteOfCheck(ExcelModelType excelModelType) {
		ExcelModel excelModel = new ExcelModel();
		excelModel.setType(excelModelType);
		excelModel.setValid(DictUtils.getDictValue("有效","valid","0"));
		List<ExcelModel> li = excelModelDao.findList(excelModel);
		if(li!=null && li.size()>0){
			return  "删除excel模板类型失败：类型下有启用的模板";
		}else{
			ExcelModelType modelType = new ExcelModelType();
			modelType.setParent(excelModelType);
			List<ExcelModelType> li2 = super.findList(modelType);
			if(li2!=null && li2.size()>0){
				return  "删除excel模板类型失败：拥有子类型";
			}
			super.delete(excelModelType);
			return "删除excel模板类型成功";
		}
	}
	
}