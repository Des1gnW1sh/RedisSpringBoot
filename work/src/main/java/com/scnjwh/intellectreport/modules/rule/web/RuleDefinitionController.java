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
import com.scnjwh.intellectreport.modules.rule.entity.RuleDefinition;
import com.scnjwh.intellectreport.modules.rule.entity.RuleType;
import com.scnjwh.intellectreport.modules.rule.service.RuleDefinitionService;
import com.scnjwh.intellectreport.modules.rule.service.RuleEnableService;
import com.scnjwh.intellectreport.modules.rule.service.RuleTypeService;

/**
 * 规则定义Controller
 * @author 彭俊豪
 * @version 2017-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rule/ruleDefinition")
public class RuleDefinitionController extends BaseController {
    @Autowired
    private RuleDefinitionService ruleDefinitionService;
    @Autowired
    private RuleTypeService ruleTypeService;

    @Autowired
    private RuleEnableService ruleEnableService;

    @ModelAttribute
    public RuleDefinition get(@RequestParam(required = false) String id) {
        RuleDefinition entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = ruleDefinitionService.get(id);
        }
        if (entity == null) {
            entity = new RuleDefinition();
        }
        return entity;
    }

    @RequiresPermissions("rule:ruleDefinition:view")
    @RequestMapping(value = { "index" })
    public String index(RuleDefinition ruleDefinition, HttpServletRequest request,
                        HttpServletResponse response, Model model) {
        return "modules/rule/ruleDefinitionIndex";
    }

    @RequiresPermissions("rule:ruleDefinition:view")
    @RequestMapping(value = { "list", "" })
    public String list(RuleDefinition ruleDefinition, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(ruleDefinition.getTypeId())) {
            ruleDefinition.setTypeName(ruleTypeService.get(ruleDefinition.getTypeId()).getName());
        }
        Page<RuleDefinition> page = ruleDefinitionService.findPage(new Page<RuleDefinition>(
            request, response), ruleDefinition);
        model.addAttribute("page", page);
        return "modules/rule/ruleDefinitionList";
    }

    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<RuleDefinition> listData(RuleDefinition ruleDefinition, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(ruleDefinition.getTypeId())) {
            ruleDefinition.setTypeName(ruleTypeService.get(ruleDefinition.getTypeId()).getName());
        }
        Page<RuleDefinition> page = ruleDefinitionService.findPage(new Page<RuleDefinition>(
            request, response), ruleDefinition);
        return page;
    }

    @RequiresPermissions("rule:ruleDefinition:view")
    @RequestMapping(value = "form")
    public String form(RuleDefinition ruleDefinition, Model model) {
        ruleDefinitionService.setSome(ruleDefinition);
        model.addAttribute("ruleDefinition", ruleDefinition);
        return "modules/rule/ruleDefinitionForm";
    }

    @RequiresPermissions("rule:ruleDefinition:view")
    @RequestMapping(value = "formView")
    public String formView(RuleDefinition ruleDefinition, Model model) {
        ruleDefinitionService.setSome(ruleDefinition);
        model.addAttribute("ruleDefinition", ruleDefinition);
        return "modules/rule/ruleDefinitionView";
    }

    @RequiresPermissions("rule:ruleDefinition:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(RuleDefinition ruleDefinition, Model model, HttpServletResponse response) {
        if (!beanValidator(model, ruleDefinition)) {
            return renderString(response,
                new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        if (!ruleDefinitionService.checkDetails(ruleDefinition.getRuleConditions())) {
            return renderString(response, new AjaxJson().setMsg("请填写完整条件规则").setSuccess(false));
        }
        return renderString(response,
            new AjaxJson().setMsg(ruleDefinitionService.saveOfCheck(ruleDefinition)));
    }

    @RequiresPermissions("rule:ruleDefinition:edit")
    @RequestMapping(value = "delete")
    public String delete(RuleDefinition ruleDefinition, RedirectAttributes redirectAttributes) {
        ruleDefinitionService.delete(ruleDefinition);
        addMessage(redirectAttributes, "删除规则定义成功");
        return "redirect:" + Global.getAdminPath() + "/rule/ruleDefinition/?repage";
    }

    @RequiresPermissions("rule:ruleDefinition:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response,
            new AjaxJson().setMsg(ruleDefinitionService.batchDeleteOfCheck(ids)));
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
    public String exportFile(RuleDefinition ruleDefinition, HttpServletRequest request,
                             HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "规则定义数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<RuleDefinition> page = ruleDefinitionService.findPage(new Page<RuleDefinition>(
                request, response, -1), ruleDefinition);
            new ExportExcel("规则定义数据", RuleDefinition.class).setDataList(page.getList())
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出规则定义失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleDefinition/?repage";
    }

    /**
     * table树
     * 
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(HttpServletResponse response, String functionId) {
        RuleDefinition ruleDefinition = new RuleDefinition();
        ruleDefinition.setFunctionId(functionId);
        List<RuleDefinition> ruleDefinitions = ruleDefinitionService.findList(ruleDefinition);
        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (RuleDefinition definition : ruleDefinitions) {
            //规则
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", definition.getId());
            map.put("pId", definition.getTypeId());
            map.put("pIds", definition.getTypeId() + ",");
            map.put("name", definition.getName());
            map.put("type", "functionId");
            mapList.add(map);
            map = Maps.newHashMap();
            //类型
            RuleType ruleType = ruleTypeService.get(definition.getTypeId());
            map.put("id", ruleType.getId());
            map.put("pId", "0");
            map.put("pIds", "0,");
            map.put("name", ruleType.getName());
            map.put("type", "type");
            mapList.add(map);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", "0");
        map.put("pId", "0");
        map.put("pIds", "0,");
        map.put("name", "规则");
        mapList.add(map);
        return mapList;
    }
    
    /**
     * 获取规则
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDefinition")
    public RuleDefinition getDefinition(String id) {
        RuleDefinition ruleDefinition =  get(id);
        ruleDefinitionService.setSome(ruleDefinition);
        return ruleDefinition;
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
    public String importFileTemplate(HttpServletResponse response,
                                     RedirectAttributes redirectAttributes) {
        try {
            String fileName = "规则定义数据导入模板.xlsx";
            List<RuleDefinition> list = Lists.newArrayList();
            //插入演示数据
            new ExportExcel("规则定义数据模板", RuleDefinition.class, 2).setDataList(list)
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleDefinition/?repage";
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
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<RuleDefinition> list = ei.getDataList(RuleDefinition.class);
            for (RuleDefinition ruleDefinition : list) {
                try {
                    //					BeanValidators.validateWithException(validator, ruleDefinition);
                    //					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
                    //	orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
                    ruleDefinitionService.save(ruleDefinition);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>" + ruleDefinition.getName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex,
                        ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>" + ruleDefinition.getName() + " 导入失败："
                                      + ex.getMessage());
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
                addMessage(redirectAttributes, "导入失败！失败信息：" + failureMsg);
            } else {
                addMessage(redirectAttributes, "已成功导入 " + successNum + " 条数据" + failureMsg);
            }
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleDefinition/?repage";
    }

}