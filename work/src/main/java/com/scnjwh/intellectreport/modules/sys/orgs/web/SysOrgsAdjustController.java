/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.web;

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
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgs;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsAdjust;
import com.scnjwh.intellectreport.modules.sys.orgs.service.SysOrgsAdjustService;
import com.scnjwh.intellectreport.modules.sys.orgs.service.SysOrgsService;

/**
 * 单位调整Controller
 * @author timo
 * @version 2017-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys.orgs/sysOrgsAdjust")
public class SysOrgsAdjustController extends BaseController {

	@Autowired
	private SysOrgsAdjustService sysOrgsAdjustService;
	@Autowired
	private SysOrgsService sysOrgsService;
	
	@ModelAttribute
	public SysOrgsAdjust get(@RequestParam(required=false) String id) {
		SysOrgsAdjust entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOrgsAdjustService.get(id);
		}
		if (entity == null){
			entity = new SysOrgsAdjust();
		}
		return entity;
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsAdjust:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOrgsAdjust sysOrgsAdjust, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOrgsAdjust> page = sysOrgsAdjustService.findPage(new Page<SysOrgsAdjust>(request, response), sysOrgsAdjust); 
		model.addAttribute("page", page);
		model.addAttribute("orgsId", sysOrgsAdjust.getIdOld());
		return "modules/sys/orgs/sysOrgsAdjustList";
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsAdjust:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysOrgsAdjust> listData(SysOrgsAdjust sysOrgsAdjust, HttpServletRequest request, HttpServletResponse response) {
		Page<SysOrgsAdjust> page = sysOrgsAdjustService.findPage(new Page<SysOrgsAdjust>(request, response), sysOrgsAdjust); 
		return page;
	}

	@RequiresPermissions("sys.orgs:sysOrgsAdjust:view")
	@RequestMapping(value = "form")
	public String form(SysOrgsAdjust sysOrgsAdjust, Model model) {
		model.addAttribute("sysOrgsAdjust", sysOrgsAdjust);
		if(StringUtils.isNoneBlank(sysOrgsAdjust.getIdOld())){
		    SysOrgs a = sysOrgsService.get(sysOrgsAdjust.getIdOld());
			a.setId(sysOrgsAdjust.getId());
			a.setParent(sysOrgsService.get(a.getParent().getId()));
			model.addAttribute("sysOrgsAdjust", a);
		}
		return "modules/sys/orgs/sysOrgsAdjustForm";
	}

	@RequiresPermissions("sys.orgs:sysOrgsAdjust:edit")
	@RequestMapping(value = "save")
	public String save(SysOrgsAdjust sysOrgsAdjust, Model model, HttpServletResponse response) {
		if (!beanValidator(model, sysOrgsAdjust)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		sysOrgsAdjustService.save(sysOrgsAdjust);
		return  renderString(response, new AjaxJson().setMsg("保存调整成功"));
		//addMessage(redirectAttributes, "保存调整成功");
		//return "redirect:"+Global.getAdminPath()+"/sys.orgs/sysOrgsAdjust/?repage";
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsAdjust:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOrgsAdjust sysOrgsAdjust, RedirectAttributes redirectAttributes) {
		sysOrgsAdjustService.delete(sysOrgsAdjust);
		addMessage(redirectAttributes, "删除调整成功");
		return "redirect:"+Global.getAdminPath()+"/sys.orgs/sysOrgsAdjust/?repage";
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsAdjust:edit")
    @RequestMapping(value = "deletes")
    public String deletes(String[] ids, HttpServletResponse response) {
        sysOrgsAdjustService.batchDelete(ids);
        return renderString(response, new AjaxJson().setMsg("批量删除调整成功"));
    }

}