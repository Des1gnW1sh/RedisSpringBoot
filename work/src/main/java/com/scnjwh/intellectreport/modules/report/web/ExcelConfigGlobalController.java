/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.web;

import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportMode;
import com.scnjwh.intellectreport.modules.report.entity.ExcelConfigGlobal;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportModeService;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.report.service.ExcelConfigGlobalService;
import com.scnjwh.intellectreport.modules.report.utils.ExcelUtil;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "${adminPath}/report/excelConfig")
public class ExcelConfigGlobalController extends BaseController {

    @Autowired
    private ExcelConfigGlobalService excelConfigGlobalService;




    @RequestMapping(value = "put")
    @ResponseBody
    public String put(HttpServletRequest request,HttpServletResponse response,Model model, ExcelConfigGlobal excelConfigGlobal){
        if(StringUtils.isBlank(excelConfigGlobal.getId())){
            return  excelConfigGlobalService.insert(excelConfigGlobal);
        }else{
            return  excelConfigGlobalService.update(excelConfigGlobal);
        }
    }

    @RequestMapping(value = "getConfig")
    public String getConfig(HttpServletRequest request,HttpServletResponse response,Model model){
     ExcelConfigGlobal excelConfigGlobal =  excelConfigGlobalService.getConfig(UserUtils.getPrincipal().getId());
     if(excelConfigGlobal == null){
        excelConfigGlobal = new ExcelConfigGlobal();
        excelConfigGlobal.setIsOpen(0);
     }
     model.addAttribute("excelConfigGlobal",excelConfigGlobal);
     return  "modules/report/excelConfigGlobalForm";
    }

}