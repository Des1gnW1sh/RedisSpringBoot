/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.enc.web;

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
import com.scnjwh.intellectreport.modules.enc.entity.TSysEncryption;
import com.scnjwh.intellectreport.modules.enc.service.TSysEncryptionService;

/**
 * 系统加密设置Controller
 * @author enc
 * @version 2017-06-13
 */
@Controller
@RequestMapping(value = "${adminPath}/enc/tSysEncryption")
public class TSysEncryptionController extends BaseController {

	@Autowired
	private TSysEncryptionService tSysEncryptionService;
	
	@ModelAttribute
	public TSysEncryption get(@RequestParam(required=false) String id) {
		TSysEncryption entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tSysEncryptionService.get(id);
		}
		if (entity == null){
			entity = new TSysEncryption();
		}
		return entity;
	}
	
	@RequiresPermissions("enc:tSysEncryption:view")
	@RequestMapping(value = {"list", ""})
	public String list(TSysEncryption tSysEncryption, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TSysEncryption> page = tSysEncryptionService.findPage(new Page<TSysEncryption>(request, response), tSysEncryption); 
		model.addAttribute("page", page);
		return "modules/enc/tSysEncryptionList";
	}

	@RequiresPermissions("enc:tSysEncryption:view")
	@RequestMapping(value = "form")
	public String form(TSysEncryption tSysEncryption, Model model) {
		model.addAttribute("tSysEncryption", tSysEncryption);
		return "modules/enc/tSysEncryptionForm";
	}

	@RequiresPermissions("enc:tSysEncryption:edit")
	@RequestMapping(value = "save")
	public String save(TSysEncryption tSysEncryption, Model model, HttpServletResponse response) {
		if (!beanValidator(model, tSysEncryption)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		tSysEncryptionService.save(tSysEncryption);
		return  renderString(response, new AjaxJson().setMsg("保存保存&ldquo;系统加密设置&rdquo;成功成功"));
		//addMessage(redirectAttributes, "保存保存&ldquo;系统加密设置&rdquo;成功成功");
		//return "redirect:"+Global.getAdminPath()+"/enc/tSysEncryption/?repage";
	}
	
	@RequiresPermissions("enc:tSysEncryption:edit")
	@RequestMapping(value = "delete")
	public String delete(TSysEncryption tSysEncryption, RedirectAttributes redirectAttributes) {
		tSysEncryptionService.delete(tSysEncryption);
		addMessage(redirectAttributes, "删除保存&ldquo;系统加密设置&rdquo;成功成功");
		return "redirect:"+Global.getAdminPath()+"/enc/tSysEncryption/?repage";
	}
	
	@RequiresPermissions("enc:tSysEncryption:edit")
    @RequestMapping(value = "deletes")
    public String deletes(String[] ids, HttpServletResponse response) {
        tSysEncryptionService.batchDelete(ids);
        return renderString(response, new AjaxJson().setMsg("批量删除保存&ldquo;系统加密设置&rdquo;成功成功"));
    }

}