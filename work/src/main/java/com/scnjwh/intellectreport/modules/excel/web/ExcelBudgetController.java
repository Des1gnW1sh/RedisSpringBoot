/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scnjwh.intellectreport.modules.excel.entity.ExcelModel;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModelType;
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
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudget;
import com.scnjwh.intellectreport.modules.excel.service.ExcelBudgetService;
import com.scnjwh.intellectreport.modules.excel.service.ExcelModelService;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * excel财政预算Controller
 *
 * @author timo
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelBudget")
public class ExcelBudgetController extends BaseController {

    @Autowired
    private ExcelBudgetService excelBudgetService;
    @Autowired
    private ExcelModelService excelModelService;

    @ModelAttribute
    public ExcelBudget get(@RequestParam(required = false) String id) {
        ExcelBudget entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = excelBudgetService.get(id);
        }
        if (entity == null) {
            entity = new ExcelBudget();
        }
        return entity;
    }

    @RequiresPermissions("excel:excelBudget:view")
    @RequestMapping(value = "listCreat")
    public String listCreat(ExcelBudget excelBudget,
                            HttpServletRequest request, HttpServletResponse response,
                            Model model) {
        Page<ExcelBudget> page = excelBudgetService.findPage(
                new Page<ExcelBudget>(request, response), excelBudget);
        model.addAttribute("page", page);
        return "modules/excel/excelBudgetListOfCreat";
    }

    @RequiresPermissions("excel:excelBudget:view")
    @RequestMapping(value = "listRuntime")
    public String list(ExcelBudget excelBudget, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        excelBudget.setIsOver(DictUtils.getDictValue("未完成", "excel_buIsOver",
                "0"));
        Page<ExcelBudget> page = excelBudgetService.findPage(
                new Page<ExcelBudget>(request, response), excelBudget);
        model.addAttribute("page", page);
        return "modules/excel/excelBudgetListRuntime";
    }

    @RequiresPermissions("excel:excelBudget:view")
    @RequestMapping(value = "listOver")
    public String listOver(ExcelBudget excelBudget, HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        excelBudget.setIsOver(DictUtils.getDictValue("已完成", "excel_buIsOver",
                "1"));
        Page<ExcelBudget> page = excelBudgetService.findPage(
                new Page<ExcelBudget>(request, response), excelBudget);
        model.addAttribute("page", page);
        return "modules/excel/excelBudgetListOfOver";
    }


    @RequiresPermissions("excel:excelBudget:view")
    @RequestMapping(value = "form")
    public String form(ExcelBudget excelBudget, Model model) {
        model.addAttribute("excelBudget", excelBudget);
        if (excelBudget.getExcleModel() != null
                && excelBudget.getExcleModel().getId() != "") {
            ExcelModel model1 = excelBudget.getExcleModel();
            String id = model1.getId();
            model1 = excelModelService.get(id);
            ExcelModelType excelType = model1 != null ? model1.getType() : null;
            model.addAttribute("excelType", excelType);
        }
        return "modules/excel/excelBudgetForm";
    }

    @RequiresPermissions("excel:excelBudget:view")
    @RequestMapping(value = "over")
    public String over(ExcelBudget excelBudget, Model model) {
        model.addAttribute("excelBudget", excelBudget);
        return "modules/excel/excelBudgetOverForm";
    }

    @RequiresPermissions("excel:excelBudget:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(ExcelBudget excelBudget, Model model,
                       HttpServletResponse response) {
        if (!beanValidator(model, excelBudget)) {
            return renderString(
                    response,
                    new AjaxJson().setMsg(
                            model.asMap().get("message").toString())
                            .setSuccess(false));
        }
        return renderString(response, new AjaxJson().setMsg(excelBudgetService
                .saveOfcheck(excelBudget)));
    }

    @RequiresPermissions("excel:excelBudget:edit")
    @RequestMapping(value = "delete")
    public String delete(ExcelBudget excelBudget,
                         RedirectAttributes redirectAttributes) {
        addMessage(redirectAttributes,
                excelBudgetService.deleteOfcheck(excelBudget));
        return "redirect:" + Global.getAdminPath()
                + "/excel/excelBudget/listCreat?repage";
    }

    @ResponseBody
    @RequiresPermissions("excel:excelBudget:edit")
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && excelBudgetService.getByName(name) == null) {
            return "true";
        }
        return "false";
    }

    @RequestMapping(value = "getXmlById")
    @ResponseBody
    public String getXmlById(String id, HttpServletResponse response) {
        return renderString(response, excelBudgetService.getXmlById(id));
    }

    @RequestMapping(value = "getModelXmlById")
    @ResponseBody
    public String getModelXmlById(String id, HttpServletResponse response) {
        return renderString(response, excelBudgetService.getModelXmlById(id));
    }
}