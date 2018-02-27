/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.security.shiro.session.SessionDAO;
import com.scnjwh.intellectreport.common.servlet.ValidateCodeServlet;
import com.scnjwh.intellectreport.common.utils.CacheUtils;
import com.scnjwh.intellectreport.common.utils.CookieUtils;
import com.scnjwh.intellectreport.common.utils.IdGen;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.cms.entity.Site;
import com.scnjwh.intellectreport.modules.cms.utils.CmsUtils;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import com.scnjwh.intellectreport.modules.sys.security.FormAuthenticationFilter;
import com.scnjwh.intellectreport.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.scnjwh.intellectreport.modules.sys.service.SystemService;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class OssController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private SystemService systemService;
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/oss", method = RequestMethod.GET)
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		String uid = request.getParameter("uid");
		String jumpUrl = request.getParameter("jumpUrl");
		User user = this.systemService.findUid(uid);
		if(user!=null&& StringUtils.isNotEmpty(user.getId())){
			model.addAttribute("user", user);
		}
		model.addAttribute("jumpUrl", jumpUrl);
		model.addAttribute("uid", uid);
		return "modules/sys/ossLogin";
//		return "modules/sys/sysLogin";
	}

	
}
