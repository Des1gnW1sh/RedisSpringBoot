/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportCollectService;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.sys.service.DesktopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.scnjwh.intellectreport.common.web.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 区域Controller
 *
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/index")
public class DesktopController extends BaseController {
    @Autowired
    private DesktopService desktopService;

    @RequestMapping("desktop")
    public String desktop(Model model) {
        Integer month = desktopService.getThisMonthCountByDepart();
        Integer all = desktopService.getAllReportCountByDepart();
        Integer running = desktopService.getRunningTaskCountByNow();
        Integer willOver = desktopService.getWillOverTaskByOverDate();
        model.addAttribute("month",month);
        model.addAttribute("all",all);
        model.addAttribute("running",running);
        model.addAttribute("willOver",willOver);
        return "modules/sys/hplushome";
    }

    @RequestMapping("getEChartsValue")
    @ResponseBody
    public String getEChartsValue(HttpServletResponse response) {
        List<Map<String,String>> pie = desktopService.getTaskCountByTaskType();
        List<Map<String,String>> bar = desktopService.getTaskCountByDepart();
        List<Map<String,String>> line = desktopService.getEveryMonthTaskNum();
        Map<String,Object> result = new HashMap<>();
        result.put("pie",pie);
        result.put("bar",bar);
        result.put("line",line);
        return renderString(response,result);
    }

}
