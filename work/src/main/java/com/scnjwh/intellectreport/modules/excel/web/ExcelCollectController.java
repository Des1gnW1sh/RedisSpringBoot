/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelCollect;
import com.scnjwh.intellectreport.modules.excel.service.ExcelCollectService;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * excel汇总状态Controller
 * 
 * @author timo
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelCollect")
public class ExcelCollectController extends BaseController {

	@Autowired
	private ExcelCollectService excelCollectService;

	@ModelAttribute
	public ExcelCollect get(@RequestParam(required = false) String id) {
		ExcelCollect entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = excelCollectService.get(id);
		}
		if (entity == null) {
			entity = new ExcelCollect();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(ExcelCollect excelCollect, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ExcelCollect> page = excelCollectService.findPage(
				new Page<ExcelCollect>(request, response), excelCollect);
		model.addAttribute("page", page);
		return "modules/excel/excelCollectList";
	}

	@RequestMapping(value = "listView")
	public String listView(ExcelCollect excelCollect,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ExcelCollect> page = excelCollectService.findPageOfMy(
				new Page<ExcelCollect>(request, response), excelCollect,
				UserUtils.getOfficeList());
		model.addAttribute("page", page);
		return "modules/excel/excelCollectListOfMy";
	}

	@RequestMapping(value = "form")
	public String form(ExcelCollect excelCollect, Model model) {
		model.addAttribute("excelCollect", excelCollect);
		return "modules/excel/excelCollectForm";
	}

	@RequestMapping(value = "formMy")
	public String formMy(ExcelCollect excelCollect, Model model) {
		model.addAttribute("excelCollect", excelCollect);
		return "modules/excel/excelCollectFormOfMy";
	}

	@RequestMapping(value = "submitExcel")
	public String submitExcel(String collectId, Model model,
			RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes,
				excelCollectService.submitExcel(collectId));
		return "redirect:" + Global.getAdminPath()
				+ "/excel/excelCollect/listView?repage";
	}

	@RequestMapping(value = "rejectExcel")
	public String rejectExcel(String collectId, String buId, Model model,
			RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes,
				excelCollectService.rejectExcel(collectId));
		return "redirect:" + Global.getAdminPath()
				+ "/excel/excelCollect/list?budget.id=" + buId;
	}

	@RequestMapping(value = "save")
	public String save(ExcelCollect excelCollect, Model model,
			HttpServletResponse response) {
		/*
		 * if (!beanValidator(model, excelCollect)){ return form(excelCollect,
		 * model); }
		 */
		// 此处只保存xml了
		excelCollectService.saveXml(excelCollect);
		return renderString(response, new AjaxJson().setMsg("保存文件成功"));
	}

	@RequestMapping(value = "delete")
	public String delete(ExcelCollect excelCollect,
			RedirectAttributes redirectAttributes) {
		excelCollectService.delete(excelCollect);
		addMessage(redirectAttributes, "删除汇总状态成功");
		return "redirect:" + Global.getAdminPath()
				+ "/excel/excelCollect/?repage";
	}

	@RequestMapping(value = "getXmlById")
	@ResponseBody
	public String getXmlById(String id, HttpServletResponse response) {
		return renderString(response, excelCollectService.getXmlById(id));
	}

}