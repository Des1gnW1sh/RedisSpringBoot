/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.web;

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
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudgetLog;
import com.scnjwh.intellectreport.modules.excel.service.ExcelBudgetLogService;

/**
 * 预算日志Controller
 * 
 * @author timo
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelBudgetLog")
public class ExcelBudgetLogController extends BaseController {

	@Autowired
	private ExcelBudgetLogService excelBudgetLogService;

	@ModelAttribute
	public ExcelBudgetLog get(@RequestParam(required = false) String id) {
		ExcelBudgetLog entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = excelBudgetLogService.get(id);
		}
		if (entity == null) {
			entity = new ExcelBudgetLog();
		}
		return entity;
	}

	@RequiresPermissions("excel:excelBudgetLog:view")
	@RequestMapping(value = { "list", "" })
	public String list(ExcelBudgetLog excelBudgetLog,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ExcelBudgetLog> page = excelBudgetLogService.findPage(
				new Page<ExcelBudgetLog>(request, response), excelBudgetLog);
		model.addAttribute("page", page);
		return "modules/excel/excelBudgetLogList";
	}

	@RequiresPermissions("excel:excelBudgetLog:view")
	@RequestMapping(value = "form")
	public String form(ExcelBudgetLog excelBudgetLog, Model model) {
		model.addAttribute("excelBudgetLog", excelBudgetLog);
		return "modules/excel/excelBudgetLogForm";
	}

	@RequestMapping(value = "save")
	public String save(ExcelBudgetLog excelBudgetLog, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, excelBudgetLog)) {
			return form(excelBudgetLog, model);
		}
		excelBudgetLogService.save(excelBudgetLog);
		addMessage(redirectAttributes, "保存日志成功");
		return "redirect:" + Global.getAdminPath()
				+ "/excel/excelBudgetLog/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(ExcelBudgetLog excelBudgetLog,
			RedirectAttributes redirectAttributes) {
		excelBudgetLogService.delete(excelBudgetLog);
		addMessage(redirectAttributes, "删除日志成功");
		return "redirect:" + Global.getAdminPath()
				+ "/excel/excelBudgetLog/?repage";
	}

}