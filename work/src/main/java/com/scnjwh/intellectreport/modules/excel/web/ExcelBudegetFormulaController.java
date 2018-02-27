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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudegetFormula;
import com.scnjwh.intellectreport.modules.excel.service.ExcelBudegetFormulaService;

/**
 * 预算公式Controller
 * @author timo
 * @version 2017-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelBudegetFormula")
public class ExcelBudegetFormulaController extends BaseController {

	@Autowired
	private ExcelBudegetFormulaService excelBudegetFormulaService;
	
	@ModelAttribute
	public ExcelBudegetFormula get(@RequestParam(required=false) String id) {
		ExcelBudegetFormula entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = excelBudegetFormulaService.get(id);
		}
		if (entity == null){
			entity = new ExcelBudegetFormula();
		}
		return entity;
	}
	
	@RequiresPermissions("excel:excelBudegetFormula:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExcelBudegetFormula excelBudegetFormula, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExcelBudegetFormula> page = excelBudegetFormulaService.findPage(new Page<ExcelBudegetFormula>(request, response), excelBudegetFormula); 
		model.addAttribute("page", page);
		return "modules/excel/excelBudegetFormulaList";
	}

	@RequiresPermissions("excel:excelBudegetFormula:view")
	@RequestMapping(value = "form")
	public String form(ExcelBudegetFormula excelBudegetFormula, Model model) {
		model.addAttribute("excelBudegetFormula", excelBudegetFormula);
		return "modules/excel/excelBudegetFormulaForm";
	}

	@RequiresPermissions("excel:excelBudegetFormula:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(ExcelBudegetFormula excelBudegetFormula, Model model, HttpServletResponse response) {
		if (!beanValidator(model, excelBudegetFormula)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		excelBudegetFormulaService.save(excelBudegetFormula);
		return renderString(response, new AjaxJson().setMsg("保存公式成功"));
	}
	
	@RequiresPermissions("excel:excelBudegetFormula:edit")
	@RequestMapping(value = "delete")
	public String delete(ExcelBudegetFormula excelBudegetFormula, RedirectAttributes redirectAttributes) {
		excelBudegetFormulaService.delete(excelBudegetFormula);
		addMessage(redirectAttributes, "删除公式成功");
		return "redirect:"+Global.getAdminPath()+"/excel/excelBudegetFormula/?repage";
	}

}