/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportMode;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportModeService;
import com.scnjwh.intellectreport.modules.report.utils.ExcelUtil;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网络数据报表预算Controller
 *
 * @author timo
 * @version 2017-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/report/excelBudgetReport")
public class ExcelBudgetReportController extends BaseController {

    @Autowired
    private ExcelBudgetReportService excelBudgetReportService;

    @Autowired
    private ExcelBudgetReportModeService excelBudgetReportModeService;

    @RequestMapping(value = "release")
    @ResponseBody
    public String release(String id,HttpServletResponse response,Model model) throws ParseException {
        ExcelBudgetReportMode excelBudgetReportMode = excelBudgetReportModeService.get(id);
        ExcelBudgetReport excelBudgetReport = new ExcelBudgetReport();
        excelBudgetReport = format(excelBudgetReportMode,excelBudgetReport);
        if (!beanValidator(model, excelBudgetReport)) {
            return resultString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        return resultString(response, new AjaxJson().setMsg(excelBudgetReportService.saveOfCheck(excelBudgetReport,null   )));
    }


    @ModelAttribute
    public ExcelBudgetReport get(@RequestParam(required = false) String id) {
        ExcelBudgetReport entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = excelBudgetReportService.get(id);
        }
        if (entity == null) {
            entity = new ExcelBudgetReport();
        }
        return entity;
    }

    //我创建的
    @RequiresPermissions("report:excelBudgetReport:view")
    @RequestMapping(value = "listOfOwn")
    public String list(ExcelBudgetReport excelBudgetReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExcelBudgetReport> page = excelBudgetReportService.findPageOfOwn(new Page<ExcelBudgetReport>(request, response), excelBudgetReport, UserUtils.getPrincipal().getId());
        model.addAttribute("page", page);
        model.addAttribute("isOver", excelBudgetReport.getIsOver());
        return "modules/report/excelBudgetReportListOfOwn";
    }

    @RequestMapping(value = "listOfOwnData")
    @ResponseBody
    public Page<ExcelBudgetReport> listOfOwnData(ExcelBudgetReport excelBudgetReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExcelBudgetReport> page = excelBudgetReportService.findPageOfOwn(new Page<ExcelBudgetReport>(request, response), excelBudgetReport, UserUtils.getPrincipal().getId());
        return page;
    }

    //我参与的
    @RequestMapping(value = "listOfMy")
    public String listOfMy(ExcelBudgetReport excelBudgetReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExcelBudgetReport> page = excelBudgetReportService.findPageOfMy(new Page<ExcelBudgetReport>(request, response), excelBudgetReport, UserUtils.getOfficeList());
        model.addAttribute("page", page);
        model.addAttribute("isOver", excelBudgetReport.getIsOver());
        return "modules/report/excelBudgetReportList";
    }

    @RequestMapping(value = "listOfMyData")
    @ResponseBody
    public Page<ExcelBudgetReport> listOfMyData(ExcelBudgetReport excelBudgetReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExcelBudgetReport> page = excelBudgetReportService.findPageOfMy(new Page<ExcelBudgetReport>(request, response), excelBudgetReport, UserUtils.getOfficeList());
        return page;
    }

    @RequestMapping(value = "over")
    public String over(ExcelBudgetReport excelBudgetReport, Model model) {
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/excelBudgetOverForm";
    }

    @RequestMapping(value = "toSelectResult")
    public String toSelectResult(ExcelBudgetReport excelBudgetReport,Model model){
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/excelBudgetResultSelectFrom";
    }

    /**
     * 跳转查看汇总数据窗口
     * @param excelBudgetReport 填报任务
     * @param model
     * @return
     */
    @RequestMapping(value = "querySummary")
    public String querySummary(ExcelBudgetReport excelBudgetReport, Model model) {
        String excel = excelBudgetReport.getExcle();
        excel = ExcelUtil.removeEscapeCharacter(excel);
        excelBudgetReport.setExcle(excel);
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/querySummaryData";
    }

    /**
     * 报表模板公式修改
     * @param excelBudgetReport
     * @param model
     * @return
     */
    @RequestMapping(value = "updateModel")
    public String updateModel(ExcelBudgetReport excelBudgetReport, Model model){
        String excel = excelBudgetReport.getExcle();
        excel = ExcelUtil.removeEscapeCharacter(excel);
        excelBudgetReport.setExcle(excel);
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/updateReportModel";
    }

    @RequiresPermissions("report:excelBudgetReport:view")
    @RequestMapping(value = "form")
    public String form(ExcelBudgetReport excelBudgetReport, Model model) {
        if (StringUtils.isBlank(excelBudgetReport.getId())) {
            excelBudgetReport.setIssueFlag("0");
            excelBudgetReport.setInformSchedule("0");
            excelBudgetReport.setInformUrge("0");
            excelBudgetReport.setIsOver("0");
            excelBudgetReport.setForceFormula("0");
            excelBudgetReport.setIsOpenEmailWarn("0");
        }
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/excelBudgetReportDesignForm";
    }

    @RequiresPermissions("report:excelBudgetReport:view")
    @RequestMapping(value = "find")
    public String find(ExcelBudgetReport excelBudgetReport, Model model) {
        if (StringUtils.isBlank(excelBudgetReport.getId())) {
            excelBudgetReport.setIssueFlag("0");
            excelBudgetReport.setInformSchedule("0");
            excelBudgetReport.setInformUrge("0");
            excelBudgetReport.setIsOver("0");
            excelBudgetReport.setForceFormula("0");
            excelBudgetReport.setIsOpenEmailWarn("0");
        }
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/excelBudgetReportSee";
    }


    @RequiresPermissions("report:excelBudgetReport:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(ExcelBudgetReport excelBudgetReport, Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = excelBudgetReport.getId();
        String doActive = request.getParameter("doActive");
        if(!StringUtils.isNoneBlank(id)){
            String excel = excelBudgetReport.getExcle();
            excel = ExcelUtil.setCellAlias(excel);
            excelBudgetReport.setExcle(excel);
        }
        if (!beanValidator(model, excelBudgetReport)) {
            return resultString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
//		response.setContentType("text/html;charset=UTF-8");
//		return renderString(response, new AjaxJson().setMsg(excelBudgetReportService.saveOfCheck(excelBudgetReport)));
        return resultString(response, new AjaxJson().setMsg(excelBudgetReportService.saveOfCheck(excelBudgetReport,doActive)));
    }

    @RequiresPermissions("report:excelBudgetReport:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(excelBudgetReportService.deleteOfCheck(ids)));
    }

    @RequestMapping(value = "getXmlById")
    @ResponseBody
    public String getXmlById(String id, HttpServletResponse response) {
        return renderString(response, excelBudgetReportService.getXmlById(id));
    }

    @RequestMapping(value = "getSubmitById")
    @ResponseBody
    public String getSubmitById(String id, HttpServletResponse response) {
        return renderString(response, excelBudgetReportService.getSubmitById(id));
    }

    @RequestMapping(value = "getChildXml")
    public String getChildXml(String xmlId, String parentId, String children, Model model) {
        String sum = ""; //汇总以后的数据源
        String[] childids = children.split(",");
        Integer length = childids.length;
        List<String> num = new ArrayList<>();
        for(int i = 0;i<length;i++){
            String xml = excelBudgetReportService.getBudget(parentId,childids[i],"1");
            if(xml != null){
                num.add(xml);
            }
        }
        String[] arr = new String[num.size()];
        String[] xmls =  num.toArray(arr);

        if(xmls.length != 0 ){
            sum = ExcelUtil.summaryExcels(xmls);
        }

        if(xmlId != null && !xmlId.equals("")){
            Map<String,String> map = excelBudgetReportService.getBudgetChild(parentId,xmlId);
            String excelBudgetReport = ""; //个人单位报表数据源
            String status = ""; //个人单位报表状态
            String taskType = ""; //报表类型
            String excelMb = ""; //报表模板
            if(map != null){
                if(map.get("xml") != null){
                    excelBudgetReport = map.get("xml");
                }
                if(map.get("status") != null){
                    status = map.get("status");
                }
            }
            Map<String,String> parentMap = excelBudgetReportService.getBudgetMB(parentId);
            if(parentMap != null){
                if(parentMap.get("task_type") != null){
                    taskType = String.valueOf(parentMap.get("task_type"));
                }
                if(parentMap.get("excle") != null){
                    excelMb = parentMap.get("excle");
                }
            }
            excelBudgetReport = excelBudgetReport != null ? excelBudgetReport.replaceAll("\r\n"," ") : "";
            String excel = excelBudgetReportService.getXmlById(parentId);
            excel = excel.replaceAll("\r\n"," ");
            excelMb = ExcelUtil.addEscapeCharacter(excelMb);
            model.addAttribute("childrenExcel",excelBudgetReport);
            model.addAttribute("taskType",taskType);
            model.addAttribute("parentExcel",sum);
            model.addAttribute("excelMb",excelMb);
            model.addAttribute("status",status);
        }
        return "modules/sys/labelRight";
    }

    /**
     * 进行数据汇总
     * @param id 汇总任务id
     * @param resp HttpServletResponse
     * @return
     */
    @RequestMapping(value = "summary")
    @ResponseBody
    public String summary(String id,HttpServletResponse resp){
        String msg = excelBudgetReportService.summary(id);
        if(msg == null || "".equals(msg)){
            msg = "汇总成功。";
        }else{
            msg = "汇总失败。<br>" + msg;
        }
        return renderString(resp,msg);
    }

    public ExcelBudgetReport format(ExcelBudgetReportMode excelBudgetReportMode,ExcelBudgetReport excelBudgetReport) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        excelBudgetReport.setName(excelBudgetReportMode.getName());
        excelBudgetReport.setTaskType(Integer.valueOf(excelBudgetReportMode.getTasktype() != null && excelBudgetReportMode.getTasktype() != "" ? excelBudgetReportMode.getTasktype() : "0"));
        excelBudgetReport.setOfficeLab(excelBudgetReportMode.getOffice());
        excelBudgetReport.setOfficeLabname(excelBudgetReportMode.getOfficeLabname());
        excelBudgetReport.setOverTimo(formatter.parse(excelBudgetReportMode.getFinishtime()));
        excelBudgetReport.setIssueFlag(excelBudgetReportMode.getMode());
        excelBudgetReport.setIsOpenEmailWarn(excelBudgetReportMode.getIsemail());
        excelBudgetReport.setWarnTime(Integer.valueOf(excelBudgetReportMode.getWarntime() != null && excelBudgetReportMode.getWarntime() != "" ? excelBudgetReportMode.getWarntime() : "0"));
        excelBudgetReport.setInformSchedule(excelBudgetReportMode.getIstasksms());
        excelBudgetReport.setInformUrge(excelBudgetReportMode.getIssms());
        excelBudgetReport.setIsOver("0");
        excelBudgetReport.setUrgeTime(Long.valueOf(excelBudgetReportMode.getUrgetime() != null && excelBudgetReportMode.getUrgetime() != "" ? excelBudgetReportMode.getUrgetime() : "0"));
        excelBudgetReport.setForceFormula(excelBudgetReportMode.getIsformula());
        excelBudgetReport.setExcle(excelBudgetReportMode.getXml());
        excelBudgetReport.setCreateDate(new Date());
        excelBudgetReport.setUpdateDate(new Date());
        excelBudgetReport.setDelFlag("0");
        String excel = excelBudgetReport.getExcle();
        excel = ExcelUtil.removeEscapeCharacter(excel);
        excelBudgetReport.setExcle(excel);
        return excelBudgetReport;
    }

    @RequestMapping(value = "newForm")
    public String newForm(ExcelBudgetReport excelBudgetReport, Model model) {
        if (StringUtils.isBlank(excelBudgetReport.getId())) {
            excelBudgetReport.setIssueFlag("0");
            excelBudgetReport.setInformSchedule("0");
            excelBudgetReport.setInformUrge("0");
            excelBudgetReport.setIsOver("0");
            excelBudgetReport.setForceFormula("0");
            excelBudgetReport.setIsOpenEmailWarn("0");
        }
        model.addAttribute("excelBudgetReport", excelBudgetReport);
        return "modules/report/excelBudgetReportDesignNewForm";
    }
}