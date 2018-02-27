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
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;
import com.scnjwh.intellectreport.modules.basesys.entity.BaseSysPost;
import com.scnjwh.intellectreport.modules.basesys.service.BaseSysPostService;

/**
 * 用户职位Controller
 * 
 * @author timo
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/basesys/baseSysPost")
public class BaseSysPostController extends BaseController {

	@Autowired
	private BaseSysPostService baseSysPostService;

	@ModelAttribute
	public BaseSysPost get(@RequestParam(required = false) String id) {
		BaseSysPost entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = baseSysPostService.get(id);
		}
		if (entity == null) {
			entity = new BaseSysPost();
		}
		return entity;
	}

	@RequiresPermissions("basesys:baseSysPost:view")
	@RequestMapping(value = { "list", "" })
	public String list(BaseSysPost baseSysPost, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<BaseSysPost> page = baseSysPostService.findPage(
				new Page<BaseSysPost>(request, response), baseSysPost);
		model.addAttribute("page", page);
		return "modules/basesys/baseSysPostList";
	}

	@RequiresPermissions("basesys:baseSysPost:view")
	@RequestMapping(value = "form")
	public String form(BaseSysPost baseSysPost, Model model) {
		model.addAttribute("baseSysPost", baseSysPost);
		return "modules/basesys/baseSysPostForm";
	}

	@RequiresPermissions("basesys:baseSysPost:edit")
	@RequestMapping(value = "save")
	public String save(BaseSysPost baseSysPost, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseSysPost)) {
			return form(baseSysPost, model);
		}
		baseSysPostService.save(baseSysPost);
		addMessage(redirectAttributes, "保存职位成功");
		return "redirect:" + Global.getAdminPath()
				+ "/basesys/baseSysPost/?repage";
	}

	@RequiresPermissions("basesys:baseSysPost:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseSysPost baseSysPost,
			RedirectAttributes redirectAttributes) {
		baseSysPostService.delete(baseSysPost);
		addMessage(redirectAttributes, "删除职位成功");
		return "redirect:" + Global.getAdminPath()
				+ "/basesys/baseSysPost/?repage";
	}

	@RequestMapping(value = "getList")
	@ResponseBody
	public String getList(String officeId, HttpServletResponse response) {
		if (StringUtils.isBlank(officeId)) {
			return renderString(response, new ArrayList<BaseSysPost>());
		}
		BaseSysPost baseSysPost = new BaseSysPost();
		Office office = new Office(officeId);
		baseSysPost.setOffice(office);
		baseSysPost.setValid(DictUtils.getDictValue("有效", "valid", "0"));
		List<BaseSysPost> li = baseSysPostService.findList(baseSysPost);
		return renderString(response, li);
	}

}