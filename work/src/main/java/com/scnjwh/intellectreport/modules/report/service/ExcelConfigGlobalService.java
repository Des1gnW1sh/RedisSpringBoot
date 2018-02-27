/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.service;

import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportCollectDao;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportLogDao;
import com.scnjwh.intellectreport.modules.report.dao.ExcelConfigGlobalDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelConfigGlobal;
import com.scnjwh.intellectreport.modules.sys.dao.OfficeDao;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
/**
 * 网络数据报表预算Service
 * @author timo
 * @version 2017-06-06
 */
@Service
@Transactional(readOnly = true)
public class ExcelConfigGlobalService extends CrudService<ExcelConfigGlobalDao, ExcelConfigGlobal> {
	
	@Autowired
	private ExcelConfigGlobalDao excelConfigGlobalDao;
	@Autowired
	private OfficeDao officeDao;

	@Transactional(readOnly = false)
	public String insert(ExcelConfigGlobal excelConfigGlobal){
		excelConfigGlobal.preInsert();
		excelConfigGlobal.setCreateBy(UserUtils.getUser());
		excelConfigGlobal.setCreateDate(new Date());
		excelConfigGlobal.setUpdateBy(UserUtils.getUser());
		excelConfigGlobal.setUpdateDate(new Date());
		excelConfigGlobal.setUserId(UserUtils.getUser().getId());
		Integer result = excelConfigGlobalDao.insert(excelConfigGlobal);
		if(result > 0){
			return  "保存成功!";
		}else{
			return  "保存失败!";
		}
	}


	@Transactional(readOnly = false)
	public String update(ExcelConfigGlobal excelConfigGlobal){
		excelConfigGlobal.setUpdateBy(UserUtils.getUser());
		excelConfigGlobal.setUpdateDate(new Date());
		excelConfigGlobal.setUserId(UserUtils.getUser().getId());
		Integer result = excelConfigGlobalDao.update(excelConfigGlobal);
		if(result > 0){
			return  "修改成功!";
		}else{
			return  "修改失败!";
		}
	}

	public ExcelConfigGlobal getConfig(String userId){
		if(!StringUtils.isBlank(userId)){
			return excelConfigGlobalDao.getByUserId(userId);
		}else{
			return  new ExcelConfigGlobal();
		}
	}
}