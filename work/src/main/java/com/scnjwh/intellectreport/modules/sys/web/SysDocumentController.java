/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.scnjwh.intellectreport.modules.sys.entity.SysDocument;
import com.scnjwh.intellectreport.modules.sys.service.SysDocumentService;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 公文管理Controller
 * @author 彭俊豪
 * @version 2017-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysDocument")
public class SysDocumentController extends BaseController {

    @Autowired
    private SysDocumentService sysDocumentService;

    @ModelAttribute
    public SysDocument get(@RequestParam(required = false) String id) {
        SysDocument entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sysDocumentService.get(id);
        }
        if (entity == null) {
            entity = new SysDocument();
        }
        return entity;
    }

    @RequiresPermissions("sys:sysDocument:view")
    @RequestMapping(value = { "list", "" })
    public String list(SysDocument sysDocument, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        sysDocument.setCreateBy(UserUtils.getUser());
        Page<SysDocument> page = sysDocumentService.findPage(new Page<SysDocument>(request,
            response), sysDocument);
        model.addAttribute("page", page);
        return "modules/sys/sysDocumentList";
    }

    @RequiresPermissions("sys:sysDocument:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<SysDocument> listData(SysDocument sysDocument, HttpServletRequest request,
                                      HttpServletResponse response) {
        sysDocument.setCreateBy(UserUtils.getUser());
        return sysDocumentService.findPage(new Page<SysDocument>(request, response), sysDocument);
    }

    @RequiresPermissions("sys:sysDocument:view")
    @RequestMapping(value = "form")
    public String form(SysDocument sysDocument, Model model) {
        model.addAttribute("sysDocument", sysDocument);
        return "modules/sys/sysDocumentForm";
    }

    @RequiresPermissions("sys:sysDocument:edit")
    @RequestMapping(value = "save")
    public String save(SysDocument sysDocument, Model model, HttpServletResponse response) {
        if (!beanValidator(model, sysDocument)) {
            return renderString(response,
                new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        if (StringUtils.isBlank(sysDocument.getId())) {
            //添加
            sysDocument.setOffice(UserUtils.getUser().getOffice());
            sysDocument.setStatus(0);

            sysDocumentService.save(sysDocument);
            return renderString(response, new AjaxJson().setMsg("保存公文管理成功"));
        }
        if (sysDocument.getStatus() == 1) {
            return renderString(response, new AjaxJson().setMsg("已审核公文,无法修改").setSuccess(false));
        }
        //修改
        sysDocumentService.save(sysDocument);
        return renderString(response, new AjaxJson().setMsg("保存公文管理成功"));
        //addMessage(redirectAttributes, "保存公文管理成功");
        //return "redirect:"+Global.getAdminPath()+"/sys/sysDocument/?repage";
    }

    @RequiresPermissions("sys:sysDocument:edit")
    @RequestMapping(value = "delete")
    public String delete(SysDocument sysDocument, RedirectAttributes redirectAttributes) {
        sysDocumentService.delete(sysDocument);
        addMessage(redirectAttributes, "删除公文管理成功");
        return "redirect:" + Global.getAdminPath() + "/sys/sysDocument/?repage";
    }

    @RequiresPermissions("sys:sysDocument:edit")
    @RequestMapping(value = "deletes")
    public String deletes(String[] ids, HttpServletResponse response) {
        sysDocumentService.batchDelete(ids);
        return renderString(response, new AjaxJson().setMsg("批量删除公文管理成功"));
    }

    /**
     * 全部公文
     * @return
     * @Author : pengjunhao. create at 2017年6月16日 上午9:49:49
     */
    @RequestMapping(value = "allListHome")
    @ResponseBody
    public List<SysDocument> allListHome(HttpServletResponse response) {
        SysDocument sysDocument = new SysDocument();
        sysDocument.setStatus(1);
        return sysDocumentService.findList(sysDocument);
    }

    /**
     * 审核页面
     * @param sysLearn
     * @param model
     * @return
     * @Author : pengjunhao. create at 2017年6月16日 上午9:49:49
     */
    @RequiresPermissions("sys:sysDocument:check")
    @RequestMapping(value = "checkList")
    public String checkList(SysDocument sysDocument, HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        Page<SysDocument> page = sysDocumentService.findPage(new Page<SysDocument>(request,
            response), sysDocument);
        model.addAttribute("page", page);
        return "modules/sys/sysDocumentCheckList";
    }

    @RequiresPermissions("sys:sysDocument:check")
    @RequestMapping(value = "checkListData")
    @ResponseBody
    public Page<SysDocument> checkListData(SysDocument sysDocument, HttpServletRequest request,
                                           HttpServletResponse response) {
        return sysDocumentService.findPage(new Page<SysDocument>(request, response), sysDocument);
    }

    /**
    * 审核
    * @param ids
    * @param response
    * @return
    * @Author : pengjunhao. create at 2017年6月15日 下午5:28:18
    */
    @RequiresPermissions("sys:sysDocument:check")
    @RequestMapping(value = "check")
    public String check(String[] ids, HttpServletResponse response) {
        String noCheck = "";
        for (String id : ids) {
            SysDocument sysDocument = get(id);
            if (sysDocument != null) {
                if (1 == sysDocument.getStatus()) {
                    noCheck += sysDocument.getTitle() + ",";
                    continue;
                }
                sysDocument.setStatus(1);
                sysDocumentService.save(sysDocument);
            }
        }
        if (StringUtils.isBlank(noCheck)) {
            return renderString(response, new AjaxJson().setMsg("审批成功"));
        }
        return renderString(response, new AjaxJson().setMsg("审批成功,以下公文未审核成功:" + noCheck));

    }

    /***
     * 取消审核
     * @param ids
     * @param response
     * @return
     * @Author : pengjunhao. create at 2017年6月15日 下午5:28:23
     */
    @RequiresPermissions("sys:sysDocument:check")
    @RequestMapping(value = "qxCheck")
    public String qxCheck(String[] ids, HttpServletResponse response) {
        String noCheck = "";
        for (String id : ids) {
            SysDocument sysDocument = get(id);
            if (sysDocument != null) {
                if (0 == sysDocument.getStatus()) {
                    noCheck += sysDocument.getTitle() + ",";
                    continue;
                }
                sysDocument.setStatus(0);
                sysDocumentService.save(sysDocument);
            }
        }
        if (StringUtils.isBlank(noCheck)) {
            return renderString(response, new AjaxJson().setMsg("取消审批成功"));
        }
        return renderString(response, new AjaxJson().setMsg("取消审批成功,以下交流学习未取消审核成功:" + noCheck));
    }

    @RequestMapping(value = "getDetail")
    public String getDetail(SysDocument sysDocument, Model model) {
        model.addAttribute("sysDocument", sysDocument);
        return "modules/sys/sysDocumentDetail";
    }
    
    /**
     * 全部（已审核）
     * @param sysDocument
     * @param request
     * @param response
     * @param model
     * @return
     * @Author : pengjunhao. create at 2017年6月20日 下午4:47:34
     */
    @RequiresPermissions("sys:sysDocument:view")
    @RequestMapping(value = { "allList" })
    public String allList(SysDocument sysDocument, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        sysDocument.setStatus(1);
        Page<SysDocument> page = sysDocumentService.findPage(new Page<SysDocument>(request,
            response), sysDocument);
        model.addAttribute("page", page);
        return "modules/sys/sysDocumentAllList";
    }
    
    /**
     * 全部（已审核）
     * @param sysDocument
     * @param request
     * @param response
     * @return
     * @Author : pengjunhao. create at 2017年6月20日 下午4:47:45
     */
    @RequiresPermissions("sys:sysDocument:view")
    @RequestMapping(value = "allListData")
    @ResponseBody
    public Page<SysDocument> allListData(SysDocument sysDocument, HttpServletRequest request,
                                      HttpServletResponse response) {
        sysDocument.setStatus(1);
        return sysDocumentService.findPage(new Page<SysDocument>(request, response), sysDocument);
    }

}