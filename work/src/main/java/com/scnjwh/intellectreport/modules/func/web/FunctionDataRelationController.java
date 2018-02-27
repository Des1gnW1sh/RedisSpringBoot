/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.web;

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
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelation;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelationDetail;
import com.scnjwh.intellectreport.modules.func.service.FunctionDataRelationDetailService;
import com.scnjwh.intellectreport.modules.func.service.FunctionDataRelationService;

/**
 * 功能数据表关联Controller
 * @author 彭俊豪
 * @version 2017-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/func/functionDataRelation")
public class FunctionDataRelationController extends BaseController {
    @Autowired
    private FunctionDataRelationService functionDataRelationService;

    @Autowired
    private FunctionDataRelationDetailService functionDataRelationDetailService;

    @ModelAttribute
    public FunctionDataRelation get(@RequestParam(required = false) String id) {
        FunctionDataRelation entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = functionDataRelationService.get(id);
        }
        if (entity == null) {
            entity = new FunctionDataRelation();
        }
        return entity;
    }

    @RequiresPermissions("func:functionDataRelation:view")
    @RequestMapping(value = { "list", "" })
    public String list(FunctionDataRelation functionDataRelation, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        functionDataRelationService.setSomePage(functionDataRelation);
        Page<FunctionDataRelation> page = functionDataRelationService.findPage(
            new Page<FunctionDataRelation>(request, response), functionDataRelation);
        model.addAttribute("page", page);
        return "modules/func/functionDataRelationList";
    }

    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<FunctionDataRelation> listData(FunctionDataRelation functionDataRelation,
                                               HttpServletRequest request,
                                               HttpServletResponse response, Model model) {
        functionDataRelationService.setSomePage(functionDataRelation);
        Page<FunctionDataRelation> page = functionDataRelationService.findPage(
            new Page<FunctionDataRelation>(request, response), functionDataRelation);
        return page;
    }

    @RequiresPermissions("func:functionDataRelation:view")
    @RequestMapping(value = "form")
    public String form(FunctionDataRelation functionDataRelation, Model model) {
        functionDataRelationService.setSomeFrom(functionDataRelation);
        functionDataRelationService.getDetails(functionDataRelation);
        model.addAttribute("functionDataRelation", functionDataRelation);
        return "modules/func/functionDataRelationForm";
    }

    @RequiresPermissions("func:functionDataRelation:view")
    @RequestMapping(value = "formView")
    public String formView(FunctionDataRelation functionDataRelation, Model model) {
        functionDataRelationService.getDetails(functionDataRelation);
        model.addAttribute("functionDataRelation", functionDataRelation);
        return "modules/func/functionDataRelationView";
    }

    @RequiresPermissions("func:functionDataRelation:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(FunctionDataRelation functionDataRelation, Model model,
                       HttpServletResponse response) {
        if (!beanValidator(model, functionDataRelation)) {
            return renderString(response,
                new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        if (StringUtils.isBlank(functionDataRelation.getId())) {
            //1个功能只能添加一个
            FunctionDataRelation dataRelation = new FunctionDataRelation();
            dataRelation.setFunctionId(functionDataRelation.getFunctionId());
            List<FunctionDataRelation> functionDataRelations = functionDataRelationService
                .findList(dataRelation);
            if (functionDataRelations.size() > 0) {
                return renderString(response, new AjaxJson().setMsg("一个功能只能添加一个").setSuccess(false));
            }
        }
        return renderString(response,
            new AjaxJson().setMsg(functionDataRelationService.saveOfCheck(functionDataRelation)));
    }

    @RequiresPermissions("func:functionDataRelation:edit")
    @RequestMapping(value = "delete")
    public String delete(FunctionDataRelation functionDataRelation,
                         RedirectAttributes redirectAttributes) {
        functionDataRelationService.delete(functionDataRelation);
        addMessage(redirectAttributes, "删除功能数据表关联成功");
        return "redirect:" + Global.getAdminPath() + "/func/functionDataRelation/?repage";
    }

    @RequiresPermissions("func:functionDataRelation:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response,
            new AjaxJson().setMsg(functionDataRelationService.batchDeleteOfCheck(ids)));
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
    public String exportFile(FunctionDataRelation functionDataRelation, HttpServletRequest request,
                             HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "功能数据表关联数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<FunctionDataRelation> page = functionDataRelationService.findPage(
                new Page<FunctionDataRelation>(request, response, -1), functionDataRelation);
            new ExportExcel("功能数据表关联数据", FunctionDataRelation.class).setDataList(page.getList())
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出功能数据表关联失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/func/functionDataRelation/?repage";
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
            String fileName = "功能数据表关联数据导入模板.xlsx";
            List<FunctionDataRelation> list = Lists.newArrayList();
            //插入演示数据
            new ExportExcel("功能数据表关联数据模板", FunctionDataRelation.class, 2).setDataList(list)
                .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/func/functionDataRelation/?repage";
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
            List<FunctionDataRelation> list = ei.getDataList(FunctionDataRelation.class);
            for (FunctionDataRelation functionDataRelation : list) {
                try {
                    //					BeanValidators.validateWithException(validator, functionDataRelation);
                    //					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
                    //	orgs.getFiscalType().getName() 获取对应name值需要添加template对应的类 保存父级使用code值或者id值直接保存，无法根据name或其他值查询
                    functionDataRelationService.save(functionDataRelation);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>" + functionDataRelation.getFunctionName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex,
                        ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>" + functionDataRelation.getFunctionName() + " 导入失败："
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
        return "redirect:" + adminPath + "/func/functionDataRelation/?repage";
    }

    /**
     * table lie树
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeDataColumn(HttpServletResponse response) {
        List<FunctionDataRelation> functionDataRelations = functionDataRelationService
            .findList(new FunctionDataRelation());
        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (FunctionDataRelation functionDataRelation : functionDataRelations) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", functionDataRelation.getFunctionId());
            map.put("pId", "0");
            map.put("pIds", "0,");
            map.put("name", functionDataRelation.getFunctionName());
            map.put("type", "l");
            mapList.add(map);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", "0");
        map.put("pId", "");
        map.put("pIds", "0,");
        map.put("name", "功能模块");
        map.put("type", "p");
        mapList.add(map);
        return mapList;
    }

    /**
     * 获取表
     * @param response
     * @param functionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getTable")
    public List<FunctionDataRelationDetail> getTable(HttpServletResponse response, String functionId) {
        if (StringUtils.isBlank(functionId)) {
            return null;
        }
        FunctionDataRelation functionDataRelation = new FunctionDataRelation();
        functionDataRelation.setFunctionId(functionId);
        //通过功能获取主表
        List<FunctionDataRelation> functionDataRelations = functionDataRelationService
            .findList(functionDataRelation);
        if (functionDataRelations.size() <= 0) {
            return null;
        }
        FunctionDataRelation relation = functionDataRelations.get(0);
        //通过主表获取从表表
        FunctionDataRelationDetail dataRelationDetail = new FunctionDataRelationDetail();
        dataRelationDetail.setFId(relation.getId());
        List<FunctionDataRelationDetail> dataRelationDetails = functionDataRelationDetailService
            .findList(dataRelationDetail);
        //封装主表到从表中 一起返回
        dataRelationDetail = new FunctionDataRelationDetail();
        dataRelationDetail.setRetinueTableId(relation.getTableId());
        dataRelationDetail.setRetinueTableName(relation.getTableName());
        dataRelationDetails.add(dataRelationDetail);
        return dataRelationDetails;

    }
}