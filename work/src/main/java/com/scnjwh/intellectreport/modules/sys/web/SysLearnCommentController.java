/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.entity.SysLearnComment;
import com.scnjwh.intellectreport.modules.sys.service.SysLearnCommentService;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 评论Controller
 * @author 彭俊豪
 * @version 2017-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysLearnComment")
public class SysLearnCommentController extends BaseController {

	@Autowired
	private SysLearnCommentService sysLearnCommentService;
	
	@ModelAttribute
	public SysLearnComment get(@RequestParam(required=false) String id) {
		SysLearnComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysLearnCommentService.get(id);
		}
		if (entity == null){
			entity = new SysLearnComment();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysLearnComment:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysLearnComment sysLearnComment, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysLearnComment> page = sysLearnCommentService.findPage(new Page<SysLearnComment>(
            request, response), sysLearnComment);
		model.addAttribute("page", page);
		return "modules/sys/sysLearnCommentList";
	}

	@RequiresPermissions("sys:sysLearnComment:view")
	@RequestMapping(value = "form")
	public String form(SysLearnComment sysLearnComment, Model model) {
		model.addAttribute("sysLearnComment", sysLearnComment);
		return "modules/sys/sysLearnCommentForm";
	}

	@RequestMapping(value = "save")
	public String save(SysLearnComment sysLearnComment, Model model, HttpServletResponse response) {
		if (!beanValidator(model, sysLearnComment)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		sysLearnComment.setCommentBy(UserUtils.getUser().getId());
		sysLearnComment.setCommentDate(new Date());
		sysLearnCommentService.save(sysLearnComment);
		return  renderString(response, new AjaxJson().setMsg("保存评论成功"));
		//addMessage(redirectAttributes, "保存评论成功");
		//return "redirect:"+Global.getAdminPath()+"/sys/sysLearnComment/?repage";
	}
	
	@RequiresPermissions("sys:sysLearnComment:edit")
	@RequestMapping(value = "delete")
	public String delete(SysLearnComment sysLearnComment, RedirectAttributes redirectAttributes) {
		sysLearnCommentService.delete(sysLearnComment);
		addMessage(redirectAttributes, "删除评论成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysLearnComment/?repage";
	}
	
	@RequiresPermissions("sys:sysLearnComment:edit")
    @RequestMapping(value = "deletes")
    public String deletes(String[] ids, HttpServletResponse response) {
        sysLearnCommentService.batchDelete(ids);
        return renderString(response, new AjaxJson().setMsg("批量删除评论成功"));
    }

}