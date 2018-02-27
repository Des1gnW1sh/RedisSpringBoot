/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.web;

import java.util.List;
import java.util.Map;

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
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgsFiscal;
import com.scnjwh.intellectreport.modules.sys.orgs.service.SysOrgsFiscalService;

/**
 * 财政机构类型Controller
 * @author timo
 * @version 2017-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys.orgs/sysOrgsFiscal")
public class SysOrgsFiscalController extends BaseController {

	@Autowired
	private SysOrgsFiscalService sysOrgsFiscalService;
	
	@ModelAttribute
	public SysOrgsFiscal get(@RequestParam(required=false) String id) {
		SysOrgsFiscal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOrgsFiscalService.get(id);
		}
		if (entity == null){
			entity = new SysOrgsFiscal();
		}
		return entity;
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsFiscal:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOrgsFiscal sysOrgsFiscal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOrgsFiscal> page = sysOrgsFiscalService.findPage(new Page<SysOrgsFiscal>(request, response), sysOrgsFiscal); 
		model.addAttribute("page", page);
		return "modules/sys/orgs/sysOrgsFiscalList";
	}
	@RequiresPermissions("sys.orgs:sysOrgsFiscal:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysOrgsFiscal> listData(SysOrgsFiscal sysOrgsFiscal, HttpServletRequest request, HttpServletResponse response) {
		return sysOrgsFiscalService.findPage(new Page<SysOrgsFiscal>(request, response), sysOrgsFiscal);
	}
	/**
	 * 只有id和name的select
	 * @param sysOrgsFiscal
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	public  List<Map<String, String>> select(SysOrgsFiscal sysOrgsFiscal) {
		return sysOrgsFiscalService.select(sysOrgsFiscal);
	}
	

	@RequiresPermissions("sys.orgs:sysOrgsFiscal:view")
	@RequestMapping(value = "form")
	public String form(SysOrgsFiscal sysOrgsFiscal, Model model) {
		model.addAttribute("sysOrgsFiscal", sysOrgsFiscal);
		return "modules/sys/orgs/sysOrgsFiscalForm";
	}

	@RequiresPermissions("sys.orgs:sysOrgsFiscal:edit")
	@RequestMapping(value = "save")
	public String save(SysOrgsFiscal sysOrgsFiscal, Model model, HttpServletResponse response) {
		if (!beanValidator(model, sysOrgsFiscal)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		sysOrgsFiscalService.save(sysOrgsFiscal);
		return  renderString(response, new AjaxJson().setMsg("保存类型成功"));
		//addMessage(redirectAttributes, "保存类型成功");
		//return "redirect:"+Global.getAdminPath()+"/sys.orgs/sysOrgsFiscal/?repage";
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsFiscal:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOrgsFiscal sysOrgsFiscal, RedirectAttributes redirectAttributes) {
		sysOrgsFiscalService.delete(sysOrgsFiscal);
		addMessage(redirectAttributes, "删除类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/orgs/sysOrgsFiscal/?repage";
	}
	
	@RequiresPermissions("sys.orgs:sysOrgsFiscal:edit")
    @RequestMapping(value = "deletes")
    public String deletes(String[] ids, HttpServletResponse response) {
        sysOrgsFiscalService.batchDelete(ids);
        return renderString(response, new AjaxJson().setMsg("批量删除类型成功"));
    }

}