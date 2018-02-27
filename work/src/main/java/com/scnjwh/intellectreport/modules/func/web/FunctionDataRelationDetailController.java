/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.scnjwh.intellectreport.common.beanvalidator.BeanValidators;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.excel.ExportExcel;
import com.scnjwh.intellectreport.common.utils.excel.ImportExcel;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelationDetail;
import com.scnjwh.intellectreport.modules.func.service.FunctionDataRelationDetailService;

/**
 * 功能数据表关联从表Controller
 * @author pjh
 * @version 2017-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/func/functionDataRelationDetail")
public class FunctionDataRelationDetailController extends BaseController {
	@Autowired
	private FunctionDataRelationDetailService functionDataRelationDetailService;

	@ModelAttribute
	public FunctionDataRelationDetail get(@RequestParam(required=false) String id) {
		FunctionDataRelationDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = functionDataRelationDetailService.get(id);
		}
		if (entity == null){
			entity = new FunctionDataRelationDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("func:functionDataRelationDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(FunctionDataRelationDetail functionDataRelationDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FunctionDataRelationDetail> page = functionDataRelationDetailService.findPage(new Page<FunctionDataRelationDetail>(request, response), functionDataRelationDetail); 
		model.addAttribute("page", page);
		return "modules/func/functionDataRelationDetailList";
	}
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FunctionDataRelationDetail>  listData(FunctionDataRelationDetail functionDataRelationDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FunctionDataRelationDetail> page = functionDataRelationDetailService.findPage(new Page<FunctionDataRelationDetail>(request, response), functionDataRelationDetail); 
		return page;
	}

	@RequiresPermissions("func:functionDataRelationDetail:view")
	@RequestMapping(value = "form")
	public String form(FunctionDataRelationDetail functionDataRelationDetail, Model model) {
		model.addAttribute("functionDataRelationDetail", functionDataRelationDetail);
		return "modules/func/functionDataRelationDetailForm";
	}
	@RequiresPermissions("func:functionDataRelationDetail:view")
	@RequestMapping(value = "formView")
	public String formView(FunctionDataRelationDetail functionDataRelationDetail, Model model) {
		model.addAttribute("functionDataRelationDetail", functionDataRelationDetail);
		return "modules/func/functionDataRelationDetailView";
	}

	@RequiresPermissions("func:functionDataRelationDetail:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(FunctionDataRelationDetail functionDataRelationDetail, Model model, HttpServletResponse response) {
		if (!beanValidator(model, functionDataRelationDetail)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		return  renderString(response, new AjaxJson().setMsg(functionDataRelationDetailService.saveOfCheck(functionDataRelationDetail)));
	}
	
	@RequiresPermissions("func:functionDataRelationDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(FunctionDataRelationDetail functionDataRelationDetail, RedirectAttributes redirectAttributes) {
		functionDataRelationDetailService.delete(functionDataRelationDetail);
		addMessage(redirectAttributes, "删除功能数据表关联从表成功");
		return "redirect:"+Global.getAdminPath()+"/func/functionDataRelationDetail/?repage";
	}
	
	@RequiresPermissions("func:functionDataRelationDetail:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(functionDataRelationDetailService.batchDeleteOfCheck(ids)));
    }
  	
  	
	/**
	 * 导出数据
	 * 导入导出需要给实体类字段配置注解 
	 * 例：@ExcelField(title="父级编码", align=2, sort=11,value="parent.code")
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(FunctionDataRelationDetail functionDataRelationDetail, HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "功能数据表关联从表数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			Page<FunctionDataRelationDetail> page = functionDataRelationDetailService.findPage(new Page<FunctionDataRelationDetail>(request, response, -1), functionDataRelationDetail);
			new ExportExcel("功能数据表关联从表数据", FunctionDataRelationDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出功能数据表关联从表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/func/functionDataRelationDetail/?repage";
	}
	/**
	 * 下载导入数据模板
	 * 导入导出需要给实体类字段配置注解 
	 * 例：@ExcelField(title="父级编码", align=2, sort=11,value="parent.code")
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			String fileName = "功能数据表关联从表数据导入模板.xlsx";
			List<FunctionDataRelationDetail> list = Lists.newArrayList();
			//插入演示数据
			new ExportExcel("功能数据表关联从表数据模板", FunctionDataRelationDetail.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/func/functionDataRelationDetail/?repage";
	}
	
	/**
	 * 导入单位数据
	 * 导入导出需要给实体类字段配置注解 
	 * 例：@ExcelField(title="父级编码", align=2, sort=11,value="parent.code")
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FunctionDataRelationDetail> list = ei.getDataList(FunctionDataRelationDetail.class);
			for (FunctionDataRelationDetail functionDataRelationDetail : list) {
				try {
//					BeanValidators.validateWithException(validator, functionDataRelationDetail);
//					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
//	orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
					functionDataRelationDetailService.save(functionDataRelationDetail);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>" + functionDataRelationDetail.getRetinueTableName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>" + functionDataRelationDetail.getRetinueTableName() + " 导入失败："+ ex.getMessage());
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				addMessage(redirectAttributes, "导入失败！失败信息：" + failureMsg);
			}else{
				addMessage(redirectAttributes, "已成功导入 " + successNum + " 条数据"+ failureMsg);
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/func/functionDataRelationDetail/?repage";
	}

}