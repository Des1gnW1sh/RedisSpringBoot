/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportLog;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportLogService;

/**
 * 网络数据报表日志Controller
 * @author timo
 * @version 2017-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/report/excelBudgetReportLog")
public class ExcelBudgetReportLogController extends BaseController {

	@Autowired
	private ExcelBudgetReportLogService excelBudgetReportLogService;
	
	@ModelAttribute
	public ExcelBudgetReportLog get(@RequestParam(required=false) String id) {
		ExcelBudgetReportLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = excelBudgetReportLogService.get(id);
		}
		if (entity == null){
			entity = new ExcelBudgetReportLog();
		}
		return entity;
	}
	
	@RequiresPermissions("report:excelBudgetReportLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExcelBudgetReportLog excelBudgetReportLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExcelBudgetReportLog> page = excelBudgetReportLogService.findPage(new Page<ExcelBudgetReportLog>(request, response), excelBudgetReportLog); 
		model.addAttribute("page", page);
		return "modules/report/excelBudgetReportLogList";
	}

	@RequiresPermissions("report:excelBudgetReportLog:view")
	@RequestMapping(value = "form")
	public String form(ExcelBudgetReportLog excelBudgetReportLog, Model model) {
		model.addAttribute("excelBudgetReportLog", excelBudgetReportLog);
		return "modules/report/excelBudgetReportLogForm";
	}

	@RequiresPermissions("report:excelBudgetReportLog:edit")
	@RequestMapping(value = "save")
	public String save(ExcelBudgetReportLog excelBudgetReportLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, excelBudgetReportLog)){
			return form(excelBudgetReportLog, model);
		}
		excelBudgetReportLogService.save(excelBudgetReportLog);
		addMessage(redirectAttributes, "保存报表日志成功");
		return "redirect:"+Global.getAdminPath()+"/report/excelBudgetReportLog/?repage";
	}
	
	@RequiresPermissions("report:excelBudgetReportLog:edit")
	@RequestMapping(value = "delete")
	public String delete(ExcelBudgetReportLog excelBudgetReportLog, RedirectAttributes redirectAttributes) {
		excelBudgetReportLogService.delete(excelBudgetReportLog);
		addMessage(redirectAttributes, "删除报表日志成功");
		return "redirect:"+Global.getAdminPath()+"/report/excelBudgetReportLog/?repage";
	}

}