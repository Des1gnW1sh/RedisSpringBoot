/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.web;

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
import com.scnjwh.intellectreport.modules.rule.entity.RuleCondition;
import com.scnjwh.intellectreport.modules.rule.service.RuleConditionService;

/**
 * 规则条件Controller
 * @author pjh
 * @version 2017-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/rule/ruleCondition")
public class RuleConditionController extends BaseController {
	@Autowired
	private RuleConditionService ruleConditionService;

	@ModelAttribute
	public RuleCondition get(@RequestParam(required=false) String id) {
		RuleCondition entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ruleConditionService.get(id);
		}
		if (entity == null){
			entity = new RuleCondition();
		}
		return entity;
	}
	
	@RequiresPermissions("rule:ruleCondition:view")
	@RequestMapping(value = {"list", ""})
	public String list(RuleCondition ruleCondition, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RuleCondition> page = ruleConditionService.findPage(new Page<RuleCondition>(request, response), ruleCondition); 
		model.addAttribute("page", page);
		return "modules/rule/ruleConditionList";
	}
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<RuleCondition>  listData(RuleCondition ruleCondition, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RuleCondition> page = ruleConditionService.findPage(new Page<RuleCondition>(request, response), ruleCondition); 
		return page;
	}

	@RequiresPermissions("rule:ruleCondition:view")
	@RequestMapping(value = "form")
	public String form(RuleCondition ruleCondition, Model model) {
		model.addAttribute("ruleCondition", ruleCondition);
		return "modules/rule/ruleConditionForm";
	}
	@RequiresPermissions("rule:ruleCondition:view")
	@RequestMapping(value = "formView")
	public String formView(RuleCondition ruleCondition, Model model) {
		model.addAttribute("ruleCondition", ruleCondition);
		return "modules/rule/ruleConditionView";
	}

	@RequiresPermissions("rule:ruleCondition:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(RuleCondition ruleCondition, Model model, HttpServletResponse response) {
		if (!beanValidator(model, ruleCondition)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		return  renderString(response, new AjaxJson().setMsg(ruleConditionService.saveOfCheck(ruleCondition)));
	}
	
	@RequiresPermissions("rule:ruleCondition:edit")
	@RequestMapping(value = "delete")
	public String delete(RuleCondition ruleCondition, RedirectAttributes redirectAttributes) {
		ruleConditionService.delete(ruleCondition);
		addMessage(redirectAttributes, "删除规则条件成功");
		return "redirect:"+Global.getAdminPath()+"/rule/ruleCondition/?repage";
	}
	
	@RequiresPermissions("rule:ruleCondition:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(ruleConditionService.batchDeleteOfCheck(ids)));
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
	public String exportFile(RuleCondition ruleCondition, HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "规则条件数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			Page<RuleCondition> page = ruleConditionService.findPage(new Page<RuleCondition>(request, response, -1), ruleCondition);
			new ExportExcel("规则条件数据", RuleCondition.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出规则条件失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/rule/ruleCondition/?repage";
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
			String fileName = "规则条件数据导入模板.xlsx";
			List<RuleCondition> list = Lists.newArrayList();
			//插入演示数据
			new ExportExcel("规则条件数据模板", RuleCondition.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/rule/ruleCondition/?repage";
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
			List<RuleCondition> list = ei.getDataList(RuleCondition.class);
			for (RuleCondition ruleCondition : list) {
				try {
//					BeanValidators.validateWithException(validator, ruleCondition);
//					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
//	orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
					ruleConditionService.save(ruleCondition);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>" + ruleCondition.getContent() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>" + ruleCondition.getContent() + " 导入失败："+ ex.getMessage());
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
		return "redirect:" + adminPath + "/rule/ruleCondition/?repage";
	}

}