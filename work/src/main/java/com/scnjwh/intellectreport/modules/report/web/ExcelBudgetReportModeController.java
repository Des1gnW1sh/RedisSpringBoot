package com.scnjwh.intellectreport.modules.report.web;

import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportMode;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportModeService;
import com.scnjwh.intellectreport.modules.report.utils.ExcelUtil;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018-2-5.
 */
@Controller
@RequestMapping(value = "${adminPath}/report/excelBudgetReportMode")
public class ExcelBudgetReportModeController extends BaseController{

    @Autowired
    private ExcelBudgetReportModeService excelBudgetReportModeService;

    @RequestMapping(value = "get")
    public String get(HttpServletRequest request,String id,Model model) {
      ExcelBudgetReportMode excelBudgetReportMode =  excelBudgetReportModeService.get(id);
        return "modules/report/excelBudgetReportListOfOwn";
    }
    @RequiresPermissions("report:excelBudgetReportMode:view")
    @RequestMapping(value = "findlist")
    public String findlist(ExcelBudgetReportMode excelBudgetReportMode ,HttpServletRequest request,HttpServletResponse response,Model model) {
        Page<ExcelBudgetReportMode> page = excelBudgetReportModeService.findList(new Page<ExcelBudgetReportMode>(request, response), excelBudgetReportMode, UserUtils.getPrincipal().getId());
        model.addAttribute("page", page);
//        model.addAttribute("isOver", excelBudgetReport.getIsOver());
        return "modules/report/excelBudgetReportListOfDemo";
    }

    @RequestMapping(value = "getlistdata")
    @ResponseBody
    public Page<ExcelBudgetReportMode> listOfOwnData(ExcelBudgetReportMode excelBudgetReportMode, HttpServletRequest request, HttpServletResponse response) {
        Page<ExcelBudgetReportMode> page = excelBudgetReportModeService.findList(new Page<ExcelBudgetReportMode>(request, response), excelBudgetReportMode, UserUtils.getPrincipal().getId());
        return page;
    }


    @RequiresPermissions("report:excelBudgetReportMode:view")
    @RequestMapping(value = "form")
    public String form(ExcelBudgetReportMode excelBudgetReportMode, Model model) {
        String xml = "";
        if(excelBudgetReportMode.getId() != null){
            excelBudgetReportMode = excelBudgetReportModeService.get(excelBudgetReportMode.getId());
            xml = ExcelUtil.removeEscapeCharacter(excelBudgetReportMode.getXml());
        }
        model.addAttribute("excelBudgetReportMode", excelBudgetReportMode);
        model.addAttribute("xml", xml);
        return "modules/report/excelBudgetReportModeForm";
    }
    @RequiresPermissions("report:excelBudgetReport:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(ExcelBudgetReportMode excelBudgetReportMode, Model model, HttpServletResponse response) {
        if(StringUtils.isNotBlank(excelBudgetReportMode.getId())){
            String msg = excelBudgetReportModeService.update(excelBudgetReportMode);
            return renderString(response, new AjaxJson().setMsg(msg).setSuccess(true).setErrorCode("1"));
        }else{
            excelBudgetReportModeService.insert(excelBudgetReportMode);
            return renderString(response, new AjaxJson().setMsg("添加成功").setSuccess(true).setErrorCode("0"));
        }
    }

//    @RequiresPermissions("report:excelBudgetReport:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(excelBudgetReportModeService.delete(ids)));
    }

    @RequestMapping(value = "getXmlById")
    @ResponseBody
    public String getXmlById(String id, HttpServletResponse response) {
      String xml = excelBudgetReportModeService.getXmlById(id);
        xml =  ExcelUtil.removeEscapeCharacter(xml);
        return renderString(response, new AjaxJson().setMsg(xml));
    }
}
