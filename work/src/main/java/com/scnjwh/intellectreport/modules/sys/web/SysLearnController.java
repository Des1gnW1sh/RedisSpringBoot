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
import com.scnjwh.intellectreport.modules.sys.entity.SysLearn;
import com.scnjwh.intellectreport.modules.sys.entity.SysLearnComment;
import com.scnjwh.intellectreport.modules.sys.service.SysLearnCommentService;
import com.scnjwh.intellectreport.modules.sys.service.SysLearnService;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 学习交流Controller
 * @author 彭俊豪
 * @version 2017-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysLearn")
public class SysLearnController extends BaseController {

    @Autowired
    private SysLearnService sysLearnService;
    @Autowired
    private SysLearnCommentService sysLearnCommentService;

    @ModelAttribute
    public SysLearn get(@RequestParam(required = false) String id) {
        SysLearn entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sysLearnService.get(id);
        }
        if (entity == null) {
            entity = new SysLearn();
        }
        return entity;
    }

    @RequiresPermissions("sys:sysLearn:view")
    @RequestMapping(value = { "list", "" })
    public String list(SysLearn sysLearn, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        sysLearn.setCreateBy(UserUtils.getUser());
        Page<SysLearn> page = sysLearnService.findPage(new Page<SysLearn>(request, response),
            sysLearn);
        model.addAttribute("page", page);
        return "modules/sys/sysLearnList";
    }
    @RequiresPermissions("sys:sysLearn:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<SysLearn> listData(SysLearn sysLearn, HttpServletRequest request, HttpServletResponse response,Model model) {
        sysLearn.setCreateBy(UserUtils.getUser());
        Page<SysLearn> page = sysLearnService.findPage(new Page<SysLearn>(request, response),sysLearn);
        return page;
    }


    @RequiresPermissions("sys:sysLearn:view")
    @RequestMapping(value = "form")
    public String form(SysLearn sysLearn, Model model) {
        model.addAttribute("sysLearn", sysLearn);
        return "modules/sys/sysLearnForm";
    }

    @RequiresPermissions("sys:sysLearn:edit")
    @RequestMapping(value = "save")
    public String save(SysLearn sysLearn, Model model, HttpServletResponse response) {
        if (!beanValidator(model, sysLearn)) {
            return renderString(response,
                new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        if (StringUtils.isBlank(sysLearn.getId())) {
            //添加
            sysLearn.setStatus(0);
            sysLearn.setOffice(UserUtils.getUser().getOffice());
            sysLearnService.save(sysLearn);
            return renderString(response, new AjaxJson().setMsg("保存学习交流成功"));
        }
        //修改
        if (sysLearn.getStatus() == 1) {
            return renderString(response, new AjaxJson().setMsg("已审核状态无法修改").setSuccess(false));
        }
        sysLearnService.save(sysLearn);
        return renderString(response, new AjaxJson().setMsg("保存学习交流成功"));
        //addMessage(redirectAttributes, "保存学习交流成功");
        //return "redirect:"+Global.getAdminPath()+"/sys/sysLearn/?repage";
    }

    @RequiresPermissions("sys:sysLearn:edit")
    @RequestMapping(value = "delete")
    public String delete(SysLearn sysLearn, RedirectAttributes redirectAttributes) {
        sysLearnService.delete(sysLearn);
        addMessage(redirectAttributes, "删除学习交流成功");
        return "redirect:" + Global.getAdminPath() + "/sys/sysLearn/?repage";
    }

    @RequiresPermissions("sys:sysLearn:edit")
    @RequestMapping(value = "deletes")
    public String deletes(String[] ids, HttpServletResponse response) {
        String noDelete = "";
        for (String id : ids) {
            SysLearn sysLearn = get(id);
            if (sysLearn != null) {
                if (sysLearn.getStatus() == 1) {
                    noDelete += sysLearn.getTitle() + ",";
                    continue;
                }
            }
        }
        sysLearnService.batchDelete(ids);
        if (StringUtils.isBlank(noDelete)) {
            return renderString(response, new AjaxJson().setMsg("批量删除成功"));
        }
        return renderString(response, new AjaxJson().setMsg("批量删除成功,以下交流学习为已审核不能删除:" + noDelete));
    }

    /**
     * 审核页面
     * @param sysLearn
     * @param model
     * @return
     * @Author : pengjunhao. create at 2017年6月16日 上午9:49:49
     */
    @RequiresPermissions("sys:sysLearn:check")
    @RequestMapping(value = "checkList")
    public String checkList(SysLearn sysLearn, HttpServletRequest request,HttpServletResponse response, Model model) {
        Page<SysLearn> page = sysLearnService.findPage(new Page<SysLearn>(request, response),sysLearn);
        model.addAttribute("page", page);
        return "modules/sys/sysLearnCheckList";
    }
    @RequiresPermissions("sys:sysLearn:check")
    @RequestMapping(value = "checkListData")
    @ResponseBody
    public Page<SysLearn> checkListData(SysLearn sysLearn, HttpServletRequest request,HttpServletResponse response, Model model) {
        Page<SysLearn> page = sysLearnService.findPage(new Page<SysLearn>(request, response),sysLearn);
        return page;
    }

    /**
    * 审核
    * @param ids
    * @param response
    * @return
    * @Author : pengjunhao. create at 2017年6月15日 下午5:28:18
    */
    @RequiresPermissions("sys:sysLearn:check")
    @RequestMapping(value = "check")
    public String check(String[] ids, HttpServletResponse response) {
        String noCheck = "";
        for (String id : ids) {
            SysLearn sysLearn = get(id);
            if (sysLearn != null) {
                if (1 == sysLearn.getStatus()) {
                    noCheck += sysLearn.getTitle() + ",";
                    continue;
                }
                sysLearn.setStatus(1);
                sysLearnService.save(sysLearn);
            }
        }
        if (StringUtils.isBlank(noCheck)) {
            return renderString(response, new AjaxJson().setMsg("审批成功"));
        }
        return renderString(response, new AjaxJson().setMsg("审批成功,以下交流学习未审核成功:" + noCheck));

    }

    /***
     * 取消审核
     * @param ids
     * @param response
     * @return
     * @Author : pengjunhao. create at 2017年6月15日 下午5:28:23
     */
    @RequiresPermissions("sys:sysLearn:check")
    @RequestMapping(value = "qxCheck")
    public String qxCheck(String[] ids, HttpServletResponse response) {
        String noCheck = "";
        for (String id : ids) {
            SysLearn sysLearn = get(id);
            if (sysLearn != null) {
                if (0 == sysLearn.getStatus()) {
                    noCheck += sysLearn.getTitle() + ",";
                    continue;
                }
                sysLearn.setStatus(0);
                sysLearnService.save(sysLearn);
            }
        }
        if (StringUtils.isBlank(noCheck)) {
            return renderString(response, new AjaxJson().setMsg("取消审批成功"));
        }
        return renderString(response, new AjaxJson().setMsg("取消审批成功,以下交流学习未取消审核成功:" + noCheck));
    }
    
    /**
     * 全部交流学习
     * @param sysLearn
     * @param model
     * @return
     * @Author : pengjunhao. create at 2017年6月16日 上午9:49:49
     */
    @RequestMapping(value = "allList")
    public String allList(SysLearn sysLearn, HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        sysLearn.setStatus(1);
        Page<SysLearn> page = sysLearnService.findPage(new Page<SysLearn>(request, response),
            sysLearn);
        model.addAttribute("page", page);
        return "modules/sys/sysLearnAllList";
    }
    @RequestMapping(value = "allListData")
    @ResponseBody
    public Page<SysLearn> allListData(SysLearn sysLearn, HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        sysLearn.setStatus(1);
        Page<SysLearn> page = sysLearnService.findPage(new Page<SysLearn>(request, response),
            sysLearn);
        return page;
    }
    
    /**
     * 全部交流学习
     * @param sysLearn
     * @param model
     * @return
     * @Author : pengjunhao. create at 2017年6月16日 上午9:49:49
     */
    @RequestMapping(value = "allListHome")
    @ResponseBody
    public List<SysLearn> allListHome(HttpServletResponse response){
        SysLearn sysLearn = new SysLearn();
        sysLearn.setStatus(1);
        return sysLearnService.findList(sysLearn);
    }
    
    
    
    /**
     * 评论交流学习
     * @param sysLearn
     * @param model
     * @return
     * @Author : pengjunhao. create at 2017年6月16日 上午9:49:49
     */
    @RequestMapping(value = "discuss")
    public String discuss(SysLearn sysLearn, HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        sysLearn = get(sysLearn.getId());
        SysLearnComment sysLearnComment = new SysLearnComment();
        sysLearnComment.setLId(sysLearn.getId());
        Page<SysLearnComment> page = sysLearnCommentService.findPage(new Page<SysLearnComment>(
                request, response), sysLearnComment);
        model.addAttribute("sysLearn", sysLearn);
        model.addAttribute("page", page);
        return "modules/sys/sysLearnDiscussList";
    }
    
    
    
}