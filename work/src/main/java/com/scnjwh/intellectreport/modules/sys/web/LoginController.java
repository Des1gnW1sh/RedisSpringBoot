/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scnjwh.intellectreport.modules.taskstate.entity.TaskState;
import com.scnjwh.intellectreport.modules.taskstate.service.TaskStateService;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
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
public class LoginController extends BaseController {

    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TaskStateService taskStateService;

    /**
     * 管理登录
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        HttpSession session = request.getSession();
        String s = (String) session.getAttribute("isHasSupcan");
        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}", sessionDAO
                    .getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            CookieUtils.setCookie(response, "LOGINED", "false");
        }

        String jump = request.getParameter("jumpUrl");
        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin()) {
            session.setAttribute("jumpUrl", jump);
            return "redirect:" + adminPath;
        }
        Site site = CmsUtils.getSite(Site.defaultSiteId());
        model.addAttribute("site", site);
        if (StringUtils.isNotEmpty(request.getParameter("uid"))
                && StringUtils.isNotEmpty(jump)) {
            request.setAttribute("uid", request.getParameter("uid"));
            request.setAttribute("jumpUrl", jump);
            User user = this.systemService.findUid(request.getParameter("uid"));
            if (user != null && StringUtils.isNotEmpty(user.getId())) {
                model.addAttribute("user", user);
                request.setAttribute("username", user.getLoginName() + "_#_" + request.getParameter("uid") + "_#_" + jump);
                request.setAttribute("password", user.getPassword());
            }
            return "modules/sys/ossLogin";
        } else {
            return "modules/cms/front/themes/" + site.getTheme() + "/newLogin";
        }

//		return "modules/sys/sysLogin";
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        HttpSession session = request.getSession();
        String s = (String) session.getAttribute("isHasSupcan");
        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            String uid = request.getParameter("uid");
            String jumpUrl = request.getParameter("jumpUrl");
            if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(jumpUrl)) {
                return "redirect:" + Arrays.toString(Base64Utils.decode(jumpUrl.getBytes()));
            } else {
                return "redirect:" + adminPath;
            }
        }

        String username = WebUtils.getCleanParam(request,
                FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request,
                FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request,
                FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String) request
                .getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request
                .getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,
                username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM,
                rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM,
                mobile);
        model.addAttribute(
                FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
                exception);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM,
                message);

        if (logger.isDebugEnabled()) {
            logger.debug(
                    "login fail, active session size: {}, message: {}, exception: {}",
                    sessionDAO.getActiveSessions(false).size(), message,
                    exception);
        }

        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception)) {
            model.addAttribute("isValidateCodeLogin",
                    isValidateCodeLogin(username, true, false));
        }

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE,
                IdGen.uuid());

        // 如果是手机登录，则返回JSON字符串
        if (mobile) {
            return renderString(response, model);
        }
        Site site = CmsUtils.getSite(Site.defaultSiteId());
        model.addAttribute("site", site);
        return "modules/cms/front/themes/" + site.getTheme() + "/newLogin";
//		return "modules/sys/sysLogin";
    }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        String into = request.getParameter("into");
        HttpSession session = request.getSession();
        String browser = (String) session.getAttribute("userBrowser");
        String supcan = (String) session.getAttribute("isHasSupcan");
        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getLoginName(), false, true);
        if (logger.isDebugEnabled()) {
            logger.debug("show index, active session size: {}", sessionDAO
                    .getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            String logined = CookieUtils.getCookie(request, "LOGINED");
            if (StringUtils.isBlank(logined) || "false".equals(logined)) {
                CookieUtils.setCookie(response, "LOGINED", "true");
            } else if (StringUtils.equals(logined, "true")) {
                UserUtils.getSubject().logout();
                if (StringUtils.isEmpty(principal.getUid())) {
                     return "redirect:" + adminPath + "/login";
                }
            }
        }

        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin()) {
            if (request.getParameter("login") != null) {
                return renderString(response, principal);
            }
            if (request.getParameter("index") != null) {
                return "modules/sys/sysIndex";
            }
            return "redirect:" + adminPath + "/login";
        }
        String jumpUrl = request.getSession().getAttribute("jumpUrl") == null ? "" : request.getSession().getAttribute("jumpUrl").toString();
        // 登录成功后，获取上次登录的当前站点ID
        if (StringUtils.isNotEmpty(jumpUrl)) {
            System.out.println("jumpUrl" + jumpUrl);
            request.getSession().removeAttribute("jumpUrl");
            return "redirect:" + new String(Base64Utils.decode(jumpUrl.getBytes()));
        } else {
            boolean j = (StringUtils.isNoneBlank(browser) && "ie".equals(browser)) || (StringUtils.isNoneBlank(supcan) && "no".equals(supcan));
            if(j && StringUtils.isBlank(into)){
                return "modules/sys/jump";
            }
            String userId = principal.getId();
            TaskState taskState = taskStateService.getTaskState(userId);
            if(taskState == null){
                taskState = new TaskState();
                taskState.setRunningNum(0);
                taskState.setOvertimeNum(0);
            }
            model.addAttribute("taskState",taskState);
            return "modules/sys/sysIndex";
        }

    }

    /**
     * 获取主题方案
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme,
                                   HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }
        return "redirect:" + request.getParameter("url");
    }

    /**
     * 是否是验证码登录
     *
     * @param useruame 用户名
     * @param isFail   计数加1
     * @param clean    计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail,
                                              boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils
                .get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }
}
