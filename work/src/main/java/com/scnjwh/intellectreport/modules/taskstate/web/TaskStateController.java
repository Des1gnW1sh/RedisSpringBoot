/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.taskstate.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.scnjwh.intellectreport.common.beanvalidator.BeanValidators;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.excel.ExportExcel;
import com.scnjwh.intellectreport.common.utils.excel.ImportExcel;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.taskstate.entity.TaskState;
import com.scnjwh.intellectreport.modules.taskstate.service.TaskStateService;

/**
 * 用户报表填报任务情况表Controller
 * @author zhou
 * @version 2018-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/taskstate/taskState")
public class TaskStateController extends BaseController {
	@Autowired
	private TaskStateService taskStateService;

	@ModelAttribute
	public TaskState get(@RequestParam(required=false) String id) {
		TaskState entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskStateService.get(id);
		}
		if (entity == null){
			entity = new TaskState();
		}
		return entity;
	}
	
	@RequiresPermissions("taskstate:taskState:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskState taskState, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskState> page = taskStateService.findPage(new Page<TaskState>(request, response), taskState); 
		model.addAttribute("page", page);
		model.addAttribute("taskState", taskState);
		return "modules/taskstate/taskStateList";
	}
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskState>  listData(TaskState taskState, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskState> page = taskStateService.findPage(new Page<TaskState>(request, response), taskState); 
		return page;
	}

	@RequiresPermissions("taskstate:taskState:view")
	@RequestMapping(value = "form")
	public String form(TaskState taskState, Model model) {
		model.addAttribute("taskState", taskState);
		return "modules/taskstate/taskStateForm";
	}
	@RequiresPermissions("taskstate:taskState:view")
	@RequestMapping(value = "formView")
	public String formView(TaskState taskState, Model model) {
		model.addAttribute("taskState", taskState);
		return "modules/taskstate/taskStateView";
	}

	@RequiresPermissions("taskstate:taskState:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(TaskState taskState, Model model, HttpServletResponse response) {
		if (!beanValidator(model, taskState)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		return  renderString(response, new AjaxJson().setMsg(taskStateService.saveOfCheck(taskState)));
	}
	
	@RequiresPermissions("taskstate:taskState:edit")
	@RequestMapping(value = "delete")
	public String delete(TaskState taskState, RedirectAttributes redirectAttributes) {
		taskStateService.delete(taskState);
		addMessage(redirectAttributes, "删除taskstate成功");
		return "redirect:"+Global.getAdminPath()+"/taskstate/taskState/?repage";
	}
	
	@RequiresPermissions("taskstate:taskState:edit")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
        return renderString(response, new AjaxJson().setMsg(taskStateService.batchDeleteOfCheck(ids)));
    }

	@RequestMapping(value = "updateCheck")
	@ResponseBody
  	public String updateCheck(String stateId, HttpServletResponse response){
		return renderString(response, new AjaxJson().setMsg(taskStateService.updateCheck(stateId)));
	}

}