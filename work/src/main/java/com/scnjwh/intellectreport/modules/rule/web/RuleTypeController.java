/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.scnjwh.intellectreport.common.beanvalidator.BeanValidators;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.utils.excel.ExportExcel;
import com.scnjwh.intellectreport.common.utils.excel.ImportExcel;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.rule.entity.RuleType;
import com.scnjwh.intellectreport.modules.rule.service.RuleTypeService;

/**
 * 规则类型Controller
 * @author pjh
 * @version 2017-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rule/ruleType")
public class RuleTypeController extends BaseController {
	@Autowired
	private RuleTypeService ruleTypeService;

	@ModelAttribute
	public RuleType get(@RequestParam(required=false) String id) {
		RuleType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ruleTypeService.get(id);
		}
		if (entity == null){
			entity = new RuleType();
		}
		return entity;
	}
	
	@RequiresPermissions("rule:ruleType:view")
	@RequestMapping(value = {"list", ""})
	public String list(RuleType ruleType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RuleType> page = ruleTypeService.findPage(new Page<RuleType>(request, response), ruleType); 
		model.addAttribute("page", page);
		return "modules/rule/ruleTypeList";
	}
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<RuleType>  listData(RuleType ruleType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RuleType> page = ruleTypeService.findPage(new Page<RuleType>(request, response), ruleType); 
		return page;
	}

	@RequiresPermissions("rule:ruleType:view")
	@RequestMapping(value = "form")
	public String form(RuleType ruleType, Model model) {
		model.addAttribute("ruleType", ruleType);
		return "modules/rule/ruleTypeForm";
	}
	@RequiresPermissions("rule:ruleType:view")
	@RequestMapping(value = "formView")
	public String formView(RuleType ruleType, Model model) {
		model.addAttribute("ruleType", ruleType);
		return "modules/rule/ruleTypeView";
	}

	@RequiresPermissions("rule:ruleType:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(RuleType ruleType, Model model, HttpServletResponse response) {
		if (!beanValidator(model, ruleType)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		return  renderString(response, new AjaxJson().setMsg(ruleTypeService.saveOfCheck(ruleType)));
	}
	
	@RequiresPermissions("rule:ruleType:edit")
	@RequestMapping(value = "delete")
	public String delete(RuleType ruleType, RedirectAttributes redirectAttributes) {
		ruleTypeService.delete(ruleType);
		addMessage(redirectAttributes, "删除规则类型成功");
		return "redirect:"+Global.getAdminPath()+"/rule/ruleType/?repage";
	}
	
	@RequiresPermissions("rule:ruleType:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(ruleTypeService.batchDeleteOfCheck(ids)));
    }
	
	/**
     * table树
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(HttpServletResponse response){
        List<RuleType> ruleTypes = ruleTypeService.findList(new RuleType());
        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (RuleType ruleType : ruleTypes) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", ruleType.getId());
            map.put("pId", "0");
            map.put("pIds", "0,");
            map.put("name", ruleType.getName());
            mapList.add(map);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", "0");
        map.put("pId", "0");
        map.put("pIds", "0,");
        map.put("name", "规则类型");
        mapList.add(map);
        return mapList;
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
	public String exportFile(RuleType ruleType, HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "规则类型数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			Page<RuleType> page = ruleTypeService.findPage(new Page<RuleType>(request, response, -1), ruleType);
			new ExportExcel("规则类型数据", RuleType.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出规则类型失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/rule/ruleType/?repage";
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
			String fileName = "规则类型数据导入模板.xlsx";
			List<RuleType> list = Lists.newArrayList();
			//插入演示数据
			new ExportExcel("规则类型数据模板", RuleType.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/rule/ruleType/?repage";
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
			List<RuleType> list = ei.getDataList(RuleType.class);
			for (RuleType ruleType : list) {
				try {
//					BeanValidators.validateWithException(validator, ruleType);
//					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
//	orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
					ruleTypeService.save(ruleType);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>" + ruleType.getName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>" + ruleType.getName() + " 导入失败："+ ex.getMessage());
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
		return "redirect:" + adminPath + "/rule/ruleType/?repage";
	}

}