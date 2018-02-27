/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.web;

import java.util.ArrayList;
import java.util.List;

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
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModel;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModelType;
import com.scnjwh.intellectreport.modules.excel.service.ExcelModelService;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * excel模板Controller
 * 
 * @author timo
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelModel")
public class ExcelModelController extends BaseController {

	@Autowired
	private ExcelModelService excelModelService;

	@ModelAttribute
	public ExcelModel get(@RequestParam(required = false) String id) {
		ExcelModel entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = excelModelService.get(id);
		}
		if (entity == null) {
			entity = new ExcelModel();
		}
		return entity;
	}

	@RequiresPermissions("excel:excelModel:view")
	@RequestMapping(value = { "list", "" })
	public String list(ExcelModel excelModel, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		excelModel.setCreateBy(UserUtils.getUser());
		Page<ExcelModel> page = excelModelService.findPage(
				new Page<ExcelModel>(request, response), excelModel);
		model.addAttribute("page", page);
		return "modules/excel/excelModelList";
	}

	@RequiresPermissions("excel:excelModel:view")
	@RequestMapping(value = "form")
	public String form(ExcelModel excelModel, Model model) {
		model.addAttribute("excelModel", excelModel);
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(excelModel.getId())) {
			excelModel.setIssueFlag(DictUtils.getDictValue("直接下拨",
					"issue_flag", "0"));
			excelModel.setValid(DictUtils.getDictValue("有效", "valid", "0"));
			List<ExcelModel> list = excelModelService.findList(excelModel);
			if (list.size() > 0) {
				excelModel.setSort(list.get(list.size() - 1).getSort());
				if (excelModel.getSort() != null) {
					excelModel.setSort(excelModel.getSort() + 30);
				}
			}
		}
		if (excelModel.getSort() == null) {
			excelModel.setSort("30");
		}
		return "modules/excel/excelModelForm";
	}

	@RequiresPermissions("excel:excelModel:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(ExcelModel excelModel, Model model,
			HttpServletResponse response) {
		if (!beanValidator(model, excelModel)) {
			return renderString(
					response,
					new AjaxJson().setMsg(
							model.asMap().get("message").toString())
							.setSuccess(false));
		}

		return renderString(response, new AjaxJson().setMsg(excelModelService
				.saveOfCheck(excelModel)));
	}

	@RequiresPermissions("excel:excelModel:edit")
	@RequestMapping(value = "delete")
	public String delete(ExcelModel excelModel,
			RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes,
				excelModelService.deleteOfCheck(excelModel));
		return "redirect:" + Global.getAdminPath()
				+ "/excel/excelModel/?repage";
	}

	@RequestMapping(value = "getXmlById")
	@ResponseBody
	public String getXmlById(String id, HttpServletResponse response) {
		return renderString(response, excelModelService.getXmlById(id));
	}

	@RequestMapping(value = "getList")
	@ResponseBody
	public String getbyType(String typeId, HttpServletResponse response) {
		if (StringUtils.isBlank(typeId)) {
			return renderString(response, new ArrayList<ExcelModel>());
		}
		ExcelModel e = new ExcelModel();
		ExcelModelType e1 = new ExcelModelType();
		e1.setId(typeId);
		e.setType(e1);
		e.setCreateBy(UserUtils.getUser());
		e.setValid(DictUtils.getDictValue("有效", "valid", "0"));
		List<ExcelModel> li = excelModelService.findList(e);
		return renderString(response, li);
	}
}