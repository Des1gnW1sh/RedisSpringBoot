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
import com.scnjwh.intellectreport.modules.gen.service.GenTableService;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnable;
import com.scnjwh.intellectreport.modules.rule.service.RuleEnableService;

/**
 * 规则定义启动Controller
 * 
 * @author 彭俊豪
 * @version 2017-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rule/ruleEnable")
public class RuleEnableController extends BaseController {
    @Autowired
    private RuleEnableService ruleEnableService;

    @Autowired
    private GenTableService genTableService;

    @ModelAttribute
    public RuleEnable get(@RequestParam(required = false) String id) {
        RuleEnable entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = ruleEnableService.get(id);
        }
        if (entity == null) {
            entity = new RuleEnable();
        }
        return entity;
    }

    @RequiresPermissions("rule:ruleEnable:view")
    @RequestMapping(value = { "list", "" })
    public String list(RuleEnable ruleEnable, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        Page<RuleEnable> page = ruleEnableService.findPage(new Page<RuleEnable>(request, response),
            ruleEnable);
        model.addAttribute("page", page);
        return "modules/rule/ruleEnableList";
    }

    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<RuleEnable> listData(RuleEnable ruleEnable, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        Page<RuleEnable> page = ruleEnableService.findPage(new Page<RuleEnable>(request, response),
            ruleEnable);
        return page;
    }

    @RequiresPermissions("rule:ruleEnable:view")
    @RequestMapping(value = "form")
    public String form(RuleEnable ruleEnable, Model model) {
        // 设置参数
        ruleEnableService.setSome(ruleEnable);
        model.addAttribute("ruleEnable", ruleEnable);
        return "modules/rule/ruleEnableForm";
    }

    @RequiresPermissions("rule:ruleEnable:view")
    @RequestMapping(value = "formView")
    public String formView(RuleEnable ruleEnable, Model model) {
        // 设置参数
        ruleEnableService.setSome(ruleEnable);
        model.addAttribute("ruleEnable", ruleEnable);
        return "modules/rule/ruleEnableView";
    }

    @RequiresPermissions("rule:ruleEnable:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(RuleEnable ruleEnable, Model model, HttpServletResponse response) {
        if (!beanValidator(model, ruleEnable)) {
            return renderString(response,
                new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        return renderString(response,
            new AjaxJson().setMsg(ruleEnableService.saveOfCheck(ruleEnable)));
    }

    @RequiresPermissions("rule:ruleEnable:edit")
    @RequestMapping(value = "delete")
    public String delete(RuleEnable ruleEnable, RedirectAttributes redirectAttributes) {
        ruleEnableService.delete(ruleEnable);
        addMessage(redirectAttributes, "删除规则定义启动成功");
        return "redirect:" + Global.getAdminPath() + "/rule/ruleEnable/?repage";
    }

    @RequiresPermissions("rule:ruleEnable:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response,
            new AjaxJson().setMsg(ruleEnableService.batchDeleteOfCheck(ids)));
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
    public List<Map<String, Object>> treeData(HttpServletResponse response) {
        RuleEnable enable = new RuleEnable();
        // 0为启用
        enable.setStatus(0);
        List<RuleEnable> ruleEnables = ruleEnableService.findList(enable);
        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (RuleEnable ruleEnable : ruleEnables) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", ruleEnable.getId());
            map.put("pId", "0");
            map.put("pIds", "0,");
            map.put("name", ruleEnable.getTableName());
            mapList.add(map);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", "0");
        map.put("pId", "0");
        map.put("pIds", "0,");
        map.put("name", "功能模块");
        mapList.add(map);
        return mapList;
    }

    /**
     * 启用
     * 
     * @param ids
     * @param response
     * @return
     */
    @RequiresPermissions("rule:ruleEnable:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(ruleEnableService.enable(ids)));
    }

    /**
     * 停用
     * 
     * @param ids
     * @param response
     * @return
     */
    @RequiresPermissions("rule:ruleEnable:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(ruleEnableService.disable(ids)));
    }

    /**
     * 导出数据 导入导出需要给实体类字段配置注解 例：@ExcelField(title="父级编码", align=2,
     * sort=11,value="parent.code")
     * 
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(RuleEnable ruleEnable, HttpServletRequest request,
                             HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "规则定义启动数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<RuleEnable> page = ruleEnableService.findPage(new Page<RuleEnable>(request,
                response, -1), ruleEnable);
            new ExportExcel("规则定义启动数据", RuleEnable.class).setDataList(page.getList())
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出规则定义启动失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleEnable/?repage";
    }

    /**
     * 下载导入数据模板 导入导出需要给实体类字段配置注解 例：@ExcelField(title="父级编码", align=2,
     * sort=11,value="parent.code")
     * 
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response,
                                     RedirectAttributes redirectAttributes) {
        try {
            String fileName = "规则定义启动数据导入模板.xlsx";
            List<RuleEnable> list = Lists.newArrayList();
            // 插入演示数据
            new ExportExcel("规则定义启动数据模板", RuleEnable.class, 2).setDataList(list)
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleEnable/?repage";
    }

    /**
     * 导入单位数据 导入导出需要给实体类字段配置注解 例：@ExcelField(title="父级编码", align=2,
     * sort=11,value="parent.code")
     * 
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
            List<RuleEnable> list = ei.getDataList(RuleEnable.class);
            for (RuleEnable ruleEnable : list) {
                try {
                    // BeanValidators.validateWithException(validator,
                    // ruleEnable);
                    // orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
                    // orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类
                    // 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
                    ruleEnableService.save(ruleEnable);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>" + ruleEnable.getTableId() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex,
                        ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>" + ruleEnable.getTableId() + " 导入失败："
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
        return "redirect:" + adminPath + "/rule/ruleEnable/?repage";
    }

}