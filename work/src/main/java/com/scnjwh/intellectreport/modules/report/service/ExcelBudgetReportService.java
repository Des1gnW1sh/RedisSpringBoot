/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.MailUtil;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudget;
import com.scnjwh.intellectreport.modules.report.utils.ExcelUtil;
import com.scnjwh.intellectreport.modules.sys.dao.OfficeDao;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportCollectDao;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportDao;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportLogDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportCollect;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportLog;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

import javax.mail.MessagingException;

/**
 * 网络数据报表预算Service
 * @author timo
 * @version 2017-06-06
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudgetReportService extends CrudService<ExcelBudgetReportDao, ExcelBudgetReport> {
	
	@Autowired
	private ExcelBudgetReportCollectDao budgetReportCollectDao;
	@Autowired
	private ExcelBudgetReportLogDao budgetReportLogDao;
	@Autowired
	private OfficeDao officeDao;
	
	public ExcelBudgetReport get(String id) {
		return super.get(id);
	}
	
	public List<ExcelBudgetReport> findList(ExcelBudgetReport excelBudgetReport) {
		return super.findList(excelBudgetReport);
	}
	
	public Page<ExcelBudgetReport> findPage(Page<ExcelBudgetReport> page, ExcelBudgetReport excelBudgetReport) {
		return super.findPage(page, excelBudgetReport);
	}
	//我创建的
	public Page<ExcelBudgetReport> findPageOfOwn(Page<ExcelBudgetReport> page, ExcelBudgetReport excelBudgetReport,String userId) {
		excelBudgetReport.setPage(page);
		excelBudgetReport.setUserId(userId);
		page.setList(dao.findListOfOwn(excelBudgetReport));
		return page;
	}
	//我参与的
	public Page<ExcelBudgetReport> findPageOfMy(Page<ExcelBudgetReport> page, ExcelBudgetReport excelBudgetReport,List<Office> offices) {
		excelBudgetReport.setPage(page);
		excelBudgetReport.setOffices(offices);
		page.setList(dao.findListOfMy(excelBudgetReport));
		return page;
		//return super.findPage(page, excelBudgetReport);
	}
	
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = false)
	public String saveOfCheck(ExcelBudgetReport excelBudgetReport,String doActive) {
		excelBudgetReport.setIsOver("0");
		if(StringUtils.isBlank(excelBudgetReport.getExcle())){
			return "报表不能为空";
		}
		if(excelBudgetReport.getInformUrge().equals(DictUtils.getDictValue("否","yes_no","0"))){
			excelBudgetReport.setUrgeTime(0L);
		}
		String id = excelBudgetReport.getId();
		if(StringUtils.isNoneBlank(id)){
			ExcelBudgetReportCollect budgetReportCollect = new 	ExcelBudgetReportCollect();
			budgetReportCollect.setStatus(DictUtils.getDictValue("已提交","excel_status", "1"));
			budgetReportCollect.setExcelBudgetReport(excelBudgetReport);
			List<ExcelBudgetReportCollect> li = budgetReportCollectDao.findList(budgetReportCollect);
			if (li != null && li.size() > 0 && !StringUtils.isNoneBlank(doActive)) {
				return "修改失败：已有汇总信息";
			}else{
				ExcelBudgetReportLog a = new ExcelBudgetReportLog();
				a.preInsert();
				a.setContent("修改信息-" + excelBudgetReport.getName());
				a.setBudget(excelBudgetReport);
				budgetReportLogDao.insert(a);
				super.save(excelBudgetReport);
			}
		}else{
			ExcelBudgetReportLog a = new ExcelBudgetReportLog();
			a.preInsert();
			a.setContent("新增信息-" + excelBudgetReport.getName());
			a.setBudget(excelBudgetReport);
			budgetReportLogDao.insert(a);
			super.save(excelBudgetReport);
		}
		String reportName = excelBudgetReport.getName();
		String overTime = DateUtils.formatDate(excelBudgetReport.getOverTimo(),"yyyy年MM月dd日 HH时mm分ss秒");
		String orgids = excelBudgetReport.getOfficeLab();
		budgetReportCollectDao.deleteByBugetId(excelBudgetReport.getId());
		for (String orgid : orgids.split(",")) {
			ExcelBudgetReportCollect collect = new ExcelBudgetReportCollect();
			collect.preInsert();
			collect.setOffice(new Office(orgid));
			collect.setExcelBudgetReport(excelBudgetReport);
			collect.setXml(excelBudgetReport.getExcle());
			collect.setStatus(DictUtils.getDictValue("未提交", "excel_status", "0"));
			budgetReportCollectDao.insert(collect);
			sendTaskEmail(id,orgid,reportName,overTime);
		}
		return "保存成功";
	}

	/**
	 * 新增或修改填报任务时给各任务部门下发通知邮件
	 * @param reportId 任务id
	 * @param officeId 部门id
	 * @param reportName 任务名
	 * @param finishDate 任务完成时间
	 */
	private void sendTaskEmail(String reportId, String officeId, String reportName, String finishDate){
		try {
			Office office = officeDao.get(officeId);
			String officeName = office.getName();
			User primaryPerson = office.getPrimaryPerson();//部门主负责人
			User deputyPerson = office.getDeputyPerson();//部门副负责人
			String officeEmail = office.getEmail();//部门邮箱
			String primaryEmail = primaryPerson != null ? primaryPerson.getEmail() : "";//主负责人邮箱
			String deputyEmail = deputyPerson != null ? deputyPerson.getEmail() : "";//副负责人邮箱
			String receiver = "";
			//判断是否有合法邮箱地址，有则加入发送邮件
			if (primaryEmail != null && !"".equals(primaryEmail.trim()) && MailUtil.checkEmail(primaryEmail)) {
				receiver = primaryEmail;
			} else if (deputyEmail != null && !"".equals(deputyEmail.trim()) && MailUtil.checkEmail(deputyEmail)) {
				receiver = deputyEmail;
			} else if (officeEmail != null && !"".equals(officeEmail.trim()) && MailUtil.checkEmail(officeEmail)) {
				receiver = officeEmail;
			}
			StringBuilder content = new StringBuilder("<span style='color:red'>").append(officeName).append("</span>，");
			if(StringUtils.isNoneBlank(reportId)){
				content.append("您的报表填报任务“<span style='color:red'>").append(reportName).append("</span>”模板有修改，填报截止于<span style='color:red'>").append(finishDate).append("</span>，请及时确认并修改。");
			}else{
				content.append("您有新的报表填报任务“<span style='color:red'>").append(reportName).append("</span>”，填报截止于<span style='color:red'>").append(finishDate).append("</span>，请及时关注并填报。");
			}
			if(!"".equals(receiver.trim())){
				MailUtil.sendEmail(receiver,"填报任务提示",content.toString());
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(readOnly = false)
	public String deleteOfCheck(String[] ids) {
		if (budgetReportCollectDao.findListOfCheckStatusByBugetIds(ids) > 0) {
			return "修改失败：已有汇总信息";
		}else{
			budgetReportCollectDao.batchDeleteByBugetIds(ids);
			super.batchDelete(ids);
			return "删除成功";
		}
	}
	
	public String getXmlById(String id) {
		return super.dao.getXmlById(id);
	}
	public String getBudget(String parentId,String xmlId,String status) { return super.dao.getBudget(parentId,xmlId,status);}
	public Map<String, String> getBudgetChild(String parentId,String xmlId) { return super.dao.getBudgetChild(parentId,xmlId);}

	public Map<String,String> getBudgetMB(String parentId) { return super.dao.getBudgetMB(parentId);}

	/**
	 * 设置报表任务为已完成状态
	 * @param id 任务id
	 */
	@Transactional(readOnly = false)
	public void updateIsOver(String id){
		ExcelBudgetReport report = new ExcelBudgetReport();
		report.setId(id);
		String isOver = DictUtils.getDictValue("已完成","excel_buIsOver","1");
		report.setIsOver(isOver);
		super.dao.updateIsOver(report);
	}

	public String getSubmitById(String id) {
		return super.dao.getSubmitById(id);
	}

	public List<ExcelBudgetReport> getNeedEmailReport(){
		return super.dao.getNeedEmailReport();
	};

	public List<Map<String,Object>> getOfficeLeader(String[] officeIds){
		return super.dao.getOfficeLeader(officeIds);
	};

    /**
     * 汇总
     * @param reportId 汇总任务id
     * @return
     */
	@Transactional(readOnly = false)
	public String summary(String reportId){
	    String sumXml;
		ExcelBudgetReport report = new ExcelBudgetReport();
		report.setId(reportId);
		List<ExcelBudgetReportCollect> collects = budgetReportCollectDao.getSummaryData(reportId);
		int len = collects.size();
		List<String> departNames = new ArrayList<>();
		List<String> xmls = new ArrayList<>();
		for(ExcelBudgetReportCollect collect : collects){
		    String officeName = collect.getOffice().getName();
		    String xml = collect.getXml();
		    departNames.add(officeName);
		    xmls.add(xml);
        }
        Map<String,String> result = ExcelUtil.summary(departNames,xmls);
		String error = result.get("error");
		sumXml = result.get("sumXML");
		report.setSubmit(sumXml);
		super.dao.saveSubmit(report);
		return error;
	}

	/**
	 * 获得已结束任务
	 * @return
	 */
	public List<ExcelBudgetReport> getFinishedTask(){return super.dao.getFinishedTask();}


	/**
	 * 设置子任务为已发送结束邮件
	 * @param id 子任务id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int setFinishEmail(String id){return super.dao.setFinishEmail(id);}
}