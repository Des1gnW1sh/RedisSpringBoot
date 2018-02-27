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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.scnjwh.intellectreport.common.beanvalidator.BeanValidators;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.excel.ExportExcel;
import com.scnjwh.intellectreport.common.utils.excel.ImportExcel;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.gen.entity.GenTableColumn;
import com.scnjwh.intellectreport.modules.gen.service.GenTableService;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnable;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnableDetail;
import com.scnjwh.intellectreport.modules.rule.service.RuleEnableDetailService;
import com.scnjwh.intellectreport.modules.rule.service.RuleEnableService;
import com.scnjwh.intellectreport.modules.sys.entity.Dict;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * 规则定义启动明细Controller
 * @author pjh
 * @version 2017-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rule/ruleEnableDetail")
public class RuleEnableDetailController extends BaseController {
    @Autowired
    private RuleEnableDetailService ruleEnableDetailService;

    @Autowired
    private RuleEnableService ruleEnableService;

    @Autowired
    private GenTableService genTableService;

    @ModelAttribute
    public RuleEnableDetail get(@RequestParam(required = false) String id) {
        RuleEnableDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = ruleEnableDetailService.get(id);
        }
        if (entity == null) {
            entity = new RuleEnableDetail();
        }
        return entity;
    }

    @RequiresPermissions("rule:ruleEnableDetail:view")
    @RequestMapping(value = { "list", "" })
    public String list(RuleEnableDetail ruleEnableDetail, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        Page<RuleEnableDetail> page = ruleEnableDetailService.findPage(new Page<RuleEnableDetail>(
            request, response), ruleEnableDetail);
        model.addAttribute("page", page);
        return "modules/rule/ruleEnableDetailList";
    }

    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<RuleEnableDetail> listData(RuleEnableDetail ruleEnableDetail,
                                           HttpServletRequest request,
                                           HttpServletResponse response, Model model) {
        Page<RuleEnableDetail> page = ruleEnableDetailService.findPage(new Page<RuleEnableDetail>(
            request, response), ruleEnableDetail);
        return page;
    }

    @RequiresPermissions("rule:ruleEnableDetail:view")
    @RequestMapping(value = "form")
    public String form(RuleEnableDetail ruleEnableDetail, Model model) {
        model.addAttribute("ruleEnableDetail", ruleEnableDetail);
        return "modules/rule/ruleEnableDetailForm";
    }

    @RequiresPermissions("rule:ruleEnableDetail:view")
    @RequestMapping(value = "formView")
    public String formView(RuleEnableDetail ruleEnableDetail, Model model) {
        model.addAttribute("ruleEnableDetail", ruleEnableDetail);
        return "modules/rule/ruleEnableDetailView";
    }

    @RequiresPermissions("rule:ruleEnableDetail:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(RuleEnableDetail ruleEnableDetail, Model model, HttpServletResponse response) {
        if (!beanValidator(model, ruleEnableDetail)) {
            return renderString(response,
                new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        return renderString(response,
            new AjaxJson().setMsg(ruleEnableDetailService.saveOfCheck(ruleEnableDetail)));
    }

    @RequiresPermissions("rule:ruleEnableDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(RuleEnableDetail ruleEnableDetail, RedirectAttributes redirectAttributes) {
        ruleEnableDetailService.delete(ruleEnableDetail);
        addMessage(redirectAttributes, "删除规则定义启动明细成功");
        return "redirect:" + Global.getAdminPath() + "/rule/ruleEnableDetail/?repage";
    }

    @RequiresPermissions("rule:ruleEnableDetail:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response,
            new AjaxJson().setMsg(ruleEnableDetailService.batchDeleteOfCheck(ids)));
    }

    @RequestMapping(value = "getColumn")
    @ResponseBody
    public List<RuleEnableDetail> getColumn(String tableId, HttpServletResponse response) {
        RuleEnable ruleEnable = new RuleEnable();
        ruleEnable.setTableId(tableId);
        List<RuleEnable> ruleEnables = ruleEnableService.findList(ruleEnable);
        return ruleEnableDetailService.getDetails(ruleEnables);
    }

    /**
     * 字典树
     * @param dictName 字典名称
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(String dictName, HttpServletResponse response) {
        List<Dict> dicts = DictUtils.getDictList(dictName);
        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (Dict dict : dicts) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", dict.getValue());
            map.put("pId", "-1");
            map.put("pIds", "-1,");
            map.put("name", dict.getLabel());
            mapList.add(map);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", "-1");
        map.put("pId", "-1");
        map.put("pIds", "-1,");
        map.put("name", "字典值");
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
    public String exportFile(RuleEnableDetail ruleEnableDetail, HttpServletRequest request,
                             HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "规则定义启动明细数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<RuleEnableDetail> page = ruleEnableDetailService.findPage(
                new Page<RuleEnableDetail>(request, response, -1), ruleEnableDetail);
            new ExportExcel("规则定义启动明细数据", RuleEnableDetail.class).setDataList(page.getList())
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出规则定义启动明细失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleEnableDetail/?repage";
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
            String fileName = "规则定义启动明细数据导入模板.xlsx";
            List<RuleEnableDetail> list = Lists.newArrayList();
            //插入演示数据
            new ExportExcel("规则定义启动明细数据模板", RuleEnableDetail.class, 2).setDataList(list)
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rule/ruleEnableDetail/?repage";
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
            List<RuleEnableDetail> list = ei.getDataList(RuleEnableDetail.class);
            for (RuleEnableDetail ruleEnableDetail : list) {
                try {
                    //					BeanValidators.validateWithException(validator, ruleEnableDetail);
                    //					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
                    //	orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
                    ruleEnableDetailService.save(ruleEnableDetail);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>" + ruleEnableDetail.getColumnName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex,
                        ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>" + ruleEnableDetail.getColumnName() + " 导入失败："
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
        return "redirect:" + adminPath + "/rule/ruleEnableDetail/?repage";
    }

    @RequestMapping(value = "getColumns")
    @ResponseBody
    public List<GenTableColumn> getColumns(String tableId, HttpServletResponse response) {
        return genTableService.getColumns(tableId);
    }

}