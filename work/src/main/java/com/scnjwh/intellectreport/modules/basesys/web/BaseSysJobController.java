/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.basesys.web;

import java.util.ArrayList;
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
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.basesys.entity.BaseSysJob;
import com.scnjwh.intellectreport.modules.basesys.service.BaseSysJobService;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * 用户岗位Controller
 * @author timo
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/basesys/baseSysJob")
public class BaseSysJobController extends BaseController {

	@Autowired
	private BaseSysJobService baseSysJobService;
	
	@ModelAttribute
	public BaseSysJob get(@RequestParam(required=false) String id) {
		BaseSysJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseSysJobService.get(id);
		}
		if (entity == null){
			entity = new BaseSysJob();
		}
		return entity;
	}
	
	@RequiresPermissions("basesys:baseSysJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseSysJob baseSysJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseSysJob> page = baseSysJobService.findPage(new Page<BaseSysJob>(request, response), baseSysJob); 
		model.addAttribute("page", page);
		return "modules/basesys/baseSysJobList";
	}

	@RequiresPermissions("basesys:baseSysJob:view")
	@RequestMapping(value = "form")
	public String form(BaseSysJob baseSysJob, Model model) {
		model.addAttribute("baseSysJob", baseSysJob);
		return "modules/basesys/baseSysJobForm";
	}

	@RequiresPermissions("basesys:baseSysJob:edit")
	@RequestMapping(value = "save")
	public String save(BaseSysJob baseSysJob, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseSysJob)){
			return form(baseSysJob, model);
		}
		baseSysJobService.save(baseSysJob);
		addMessage(redirectAttributes, "保存岗位成功");
		return "redirect:"+Global.getAdminPath()+"/basesys/baseSysJob/?repage";
	}
	
	@RequiresPermissions("basesys:baseSysJob:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseSysJob baseSysJob, RedirectAttributes redirectAttributes) {
		baseSysJobService.delete(baseSysJob);
		addMessage(redirectAttributes, "删除岗位成功");
		return "redirect:"+Global.getAdminPath()+"/basesys/baseSysJob/?repage";
	}
	
	@RequestMapping(value="getList")
	@ResponseBody
	public String getList(String officeId,HttpServletResponse response){
		if(StringUtils.isBlank(officeId)){
			return renderString(response, new ArrayList<BaseSysJob>());
		}
		BaseSysJob baseSysJob = new BaseSysJob();
		Office office = new Office(officeId);
		baseSysJob.setOffice(office);
		baseSysJob.setValid(DictUtils.getDictValue("有效","valid","0"));
		List<BaseSysJob> li = baseSysJobService.findList(baseSysJob);
		return renderString(response, li);
	}
	
}