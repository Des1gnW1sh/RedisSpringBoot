/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.report.utils.ExcelUtil;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportCollect;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportCollectService;

import java.util.List;

/**
 * 网络报表提交状态Controller
 * @author timo
 * @version 2017-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/report/excelBudgetReportCollect")
public class ExcelBudgetReportCollectController extends BaseController {

	@Autowired
	private ExcelBudgetReportCollectService excelBudgetReportCollectService;

	@ModelAttribute
	public ExcelBudgetReportCollect get(@RequestParam(required=false) String id) {
		ExcelBudgetReportCollect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = excelBudgetReportCollectService.get(id);
		}
		if (entity == null){
			entity = new ExcelBudgetReportCollect();
		}
		return entity;
	}
	//预算状态查看
//	@RequiresPermissions("report:excelBudgetReportCollect:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExcelBudgetReportCollect excelBudgetReportCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExcelBudgetReportCollect> page = excelBudgetReportCollectService.findPage(new Page<ExcelBudgetReportCollect>(request, response), excelBudgetReportCollect); 
		model.addAttribute("page", page);
		model.addAttribute("reportId", excelBudgetReportCollect.getExcelBudgetReport().getId());
		return "modules/report/excelBudgetReportCollectList";
	}
	
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ExcelBudgetReportCollect> listData(ExcelBudgetReportCollect excelBudgetReportCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExcelBudgetReportCollect> page = excelBudgetReportCollectService.findPage(new Page<ExcelBudgetReportCollect>(request, response), excelBudgetReportCollect); 
		return page;
	}
	//预算填报
	@RequestMapping(value = "listSubmit")
	public String listSubmit(ExcelBudgetReportCollect excelBudgetReportCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Office> list = UserUtils.getOfficeList();
		if(list.size() != 0 ){
			excelBudgetReportCollect.setOfficeid(list.get(0).getId());
		}
		Page<ExcelBudgetReportCollect> page = excelBudgetReportCollectService.findPage(new Page<ExcelBudgetReportCollect>(request, response), excelBudgetReportCollect); 
		model.addAttribute("page", page);
		model.addAttribute("reportId", excelBudgetReportCollect.getExcelBudgetReport().getId());
		return "modules/report/excelBudgetReportCollectListSubmit";
	}
	@RequestMapping(value = "listSubmitData")
	@ResponseBody
	public Page<ExcelBudgetReportCollect> listSubmitData(ExcelBudgetReportCollect excelBudgetReportCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Office> list = UserUtils.getOfficeList();
		if(list.size() != 0 ){
			excelBudgetReportCollect.setOfficeid(list.get(0).getId());
		}
		Page<ExcelBudgetReportCollect> page = excelBudgetReportCollectService.findPage(new Page<ExcelBudgetReportCollect>(request, response), excelBudgetReportCollect);
		return page;
	}

//	@RequiresPermissions("report:excelBudgetReportCollect:view")
	@RequestMapping(value = "form")
	public String form(ExcelBudgetReportCollect excelBudgetReportCollect, Model model) {
		model.addAttribute("excelBudgetReportCollect", excelBudgetReportCollect);
		return "modules/report/excelBudgetReportCollectForm";
	}
	
	@RequestMapping(value = "formSubmit")
	public String formSubmit(ExcelBudgetReportCollect excelBudgetReportCollect, Model model) {
		model.addAttribute("excelBudgetReportCollect", excelBudgetReportCollect);
		return "modules/report/excelBudgetReportCollectFormSubmit";
	}

//	@RequiresPermissions("report:excelBudgetReportCollect:edit")
	@RequestMapping(value = "save")
	public String save(ExcelBudgetReportCollect excelBudgetReportCollect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, excelBudgetReportCollect)){
			return form(excelBudgetReportCollect, model);
		}
		excelBudgetReportCollectService.save(excelBudgetReportCollect);
		addMessage(redirectAttributes, "保存提交状态成功");
		return "redirect:"+Global.getAdminPath()+"/report/excelBudgetReportCollect/listSubmit?excelBudgetReport.id="+excelBudgetReportCollect.getExcelBudgetReport().getId();
	}
	
//	@RequiresPermissions("report:excelBudgetReportCollect:edit")
	@RequestMapping(value = "delete")
	public String delete(ExcelBudgetReportCollect excelBudgetReportCollect, RedirectAttributes redirectAttributes) {
		excelBudgetReportCollectService.delete(excelBudgetReportCollect);
		addMessage(redirectAttributes, "删除提交状态成功");
		return "redirect:"+Global.getAdminPath()+"/report/excelBudgetReportCollect/?repage";
	}
	
	@RequestMapping(value = "getXmlById")
	@ResponseBody
	public String getXmlById(String id, HttpServletResponse response) {
		return renderString(response, excelBudgetReportCollectService.getXmlById(id));
	}
	
	@RequestMapping(value = "submitExcel")
	public String submitExcel(String collectId, String buId,Model model,
			RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes,excelBudgetReportCollectService.submitExcel(collectId));
		return "redirect:"+Global.getAdminPath()+"/report/excelBudgetReportCollect/listSubmit?excelBudgetReport.id="+buId;
	}

	@RequestMapping(value = "rejectExcel")
	public String rejectExcel(String collectId, String buId, Model model,
			RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes,excelBudgetReportCollectService.rejectExcel(collectId));
		return "redirect:"+Global.getAdminPath()+"/report/excelBudgetReportCollect/list?excelBudgetReport.id="+buId;
	}


	@RequestMapping(value = "getReportExcel")
	@ResponseBody
	public String getReportExcel(String id, HttpServletResponse response) {
		String xml = excelBudgetReportCollectService.getParentExcel(id);
		return renderString(response, xml);
	}
}