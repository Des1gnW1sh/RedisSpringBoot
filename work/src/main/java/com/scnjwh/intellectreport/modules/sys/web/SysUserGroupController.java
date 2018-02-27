/**
 * Copyright &copy;2017.
 */
package com.scnjwh.intellectreport.modules.sys.web;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.rule.entity.RuleDefinition;
import com.scnjwh.intellectreport.modules.sys.entity.SysUserGroup;
import com.scnjwh.intellectreport.modules.sys.service.SysUserGroupService;

/**
 * 组维护Controller
 * @author hzl
 * @version 2017-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserGroup")
public class SysUserGroupController extends BaseController {

	@Autowired
	private SysUserGroupService sysUserGroupService;
	
	@ModelAttribute
	public SysUserGroup get(@RequestParam(required=false) String id) {
		SysUserGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserGroupService.get(id);
		}
		if (entity == null){
			entity = new SysUserGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:group:view")
    @RequestMapping(value = { "index" })
	public String index(SysUserGroup sysUserGroup, Model model) {           
		return "modules/sys/sysUserGroupIndex";
	}
	
	@RequiresPermissions("sys:group:view")
	@RequestMapping(value = {"list"})
	public String list(SysUserGroup sysUserGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		//如果树是数据字典数据 		
		sysUserGroup.setForder("1");//过滤第一个节点		
		Page<SysUserGroup> page=sysUserGroupService.findPage(new Page<SysUserGroup>(request, response), sysUserGroup); 
        model.addAttribute("page", page);
		return "modules/sys/sysUserGroupList";
	}
	
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysUserGroup>  listData(SysUserGroup sysUserGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysUserGroup.setForder("1");//过滤第一个节点		
		Page<SysUserGroup> page=sysUserGroupService.findPage(new Page<SysUserGroup>(request, response), sysUserGroup); 
        model.addAttribute("page", page);
	    
	    return page;
	}

	@RequiresPermissions("sys:group:edit")
	@RequestMapping(value = "form")
	public String form(SysUserGroup sysUserGroup, Model model) {
		if (sysUserGroup.getParent()!=null && StringUtils.isNotBlank(sysUserGroup.getParent().getId())){
			sysUserGroup.setParent(sysUserGroupService.get(sysUserGroup.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(sysUserGroup.getId())){
				SysUserGroup sysUserGroupChild = new SysUserGroup();
				sysUserGroupChild.setParent(new SysUserGroup(sysUserGroup.getParent().getId()));
				List<SysUserGroup> list = sysUserGroupService.findList(sysUserGroup); 
				if (list.size() > 0){
					sysUserGroup.setSort(list.get(list.size()-1).getSort());
					if (sysUserGroup.getSort() != null){
						sysUserGroup.setSort(sysUserGroup.getSort() + 30);
					}
				}
			}
		}
		if (sysUserGroup.getSort() == null){
			sysUserGroup.setSort(30);
		}
		model.addAttribute("sysUserGroup", sysUserGroup);
		return "modules/sys/sysUserGroupForm";
	}

	@RequiresPermissions("sys:group:edit")
	@RequestMapping(value = "save")
	public String save(SysUserGroup sysUserGroup, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		try{
		if (!beanValidator(model, sysUserGroup)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false)); //form(sysUserGroup, model);
		}
		sysUserGroupService.save(sysUserGroup);
		//addMessage(redirectAttributes, "保存组维护成功");
		}catch(Exception ex)
		{
			throw ex;
			
		}

		return  renderString(response, new AjaxJson().setMsg(sysUserGroupService.saveOfGroup(sysUserGroup)));
	 //"redirect:"+Global.getAdminPath()+"/sys/sysUserGroup/list?repage";
	}
	
	@RequiresPermissions("sys:group:delete")
	@RequestMapping(value = "delete")
	public String delete(SysUserGroup sysUserGroup, RedirectAttributes redirectAttributes) {
		sysUserGroupService.delete(sysUserGroup);
		addMessage(redirectAttributes, "删除组维护成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserGroup/list?repage";
	}
	@RequiresPermissions("sys:group:delete")
    @RequestMapping(value = "deletes")
    @ResponseBody
    public String deletes(String[] ids, HttpServletResponse response) {
		sysUserGroupService.batchDeleteByIds(ids);     
        return renderString(response, new AjaxJson().setMsg("删除组成功"));
    }
  	

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();		
		SysUserGroup userGroup=	new SysUserGroup();
		List<SysUserGroup> list = sysUserGroupService.findList(userGroup);
		for (int i=0; i<list.size(); i++){
			SysUserGroup e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeParentData")
	public List<Map<String, Object>> treeParentData(@RequestParam(required=false) String extId, @RequestParam(required=false) String typeId,HttpServletResponse response) {
		
		if (StringUtils.isBlank(typeId) ||typeId==null)
		{
			typeId="1";
		}
		SysUserGroup userGroup=	new SysUserGroup();
		userGroup.setFtype(typeId);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysUserGroup> list = sysUserGroupService.findList(userGroup);
		for (int i=0; i<list.size(); i++){
			SysUserGroup e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
		

	
}