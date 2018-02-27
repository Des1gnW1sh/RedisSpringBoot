/**
 * Copyright &copy;2017.
 */
package com.scnjwh.intellectreport.modules.excel.web;

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
import com.scnjwh.intellectreport.common.utils.IdGen;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelModelType;
import com.scnjwh.intellectreport.modules.excel.service.ExcelModelTypeService;

/**
 * excel模板类型Controller
 * @author timo
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelModelType")
public class ExcelModelTypeController extends BaseController {

	@Autowired
	private ExcelModelTypeService excelModelTypeService;
	
	@ModelAttribute
	public ExcelModelType get(@RequestParam(required=false) String id) {
		ExcelModelType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = excelModelTypeService.get(id);
		}
		if (entity == null){
			entity = new ExcelModelType();
		}
		return entity;
	}
	
	@RequiresPermissions("excel:excelModelType:view")
    @RequestMapping(value = {""})
	public String index(ExcelModelType excelModelType, Model model) {           
		return "modules/excel/excelModelTypeIndex";
	}
	
	@RequiresPermissions("excel:excelModelType:view")
	@RequestMapping(value = {"list"})
	public String list(ExcelModelType excelModelType, HttpServletRequest request, HttpServletResponse response, Model model) {
		//如果树是数据字典数据 
		//List<String> typeList = dictService.findTypeList();
		//model.addAttribute("typeList", typeList);
		//默认为自身树
		List<ExcelModelType> list = excelModelTypeService.findList(excelModelType); 
		model.addAttribute("list", list);
		Page<ExcelModelType> page = excelModelTypeService.findPage(new Page<ExcelModelType>(request, response), excelModelType); 
        model.addAttribute("page", page);
		return "modules/excel/excelModelTypeList";
	}

	@RequiresPermissions("excel:excelModelType:view")
	@RequestMapping(value = "form")
	public String form(ExcelModelType excelModelType, Model model) {
		if (excelModelType.getParent()!=null && StringUtils.isNotBlank(excelModelType.getParent().getId())){
			excelModelType.setParent(excelModelTypeService.get(excelModelType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(excelModelType.getId())){
				ExcelModelType excelModelTypeChild = new ExcelModelType();
				excelModelTypeChild.setParent(new ExcelModelType(excelModelType.getParent().getId()));
				List<ExcelModelType> list = excelModelTypeService.findList(excelModelType); 
				if (list.size() > 0){
					excelModelType.setSort(list.get(list.size()-1).getSort());
					if (excelModelType.getSort() != null){
						excelModelType.setSort(excelModelType.getSort() + 30);
					}
				}
			}
		}
		if (excelModelType.getSort() == null){
			excelModelType.setSort(30);
		}
		model.addAttribute("excelModelType", excelModelType);
		return "modules/excel/excelModelTypeForm";
	}

	@RequiresPermissions("excel:excelModelType:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(ExcelModelType excelModelType, Model model, HttpServletResponse response) {
		//屏蔽code值
		if(StringUtils.isBlank(excelModelType.getCode())){
			excelModelType.setCode(IdGen.uuid());
		}
		if (!beanValidator(model, excelModelType)){
//			return form(excelModelType, model);
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		excelModelTypeService.save(excelModelType);
		return renderString(response, new AjaxJson().setMsg("保存excel模板类型成功"));
//		addMessage(redirectAttributes, "保存excel模板类型成功");
//		return "redirect:"+Global.getAdminPath()+"/excel/excelModelType/list?repage";
	}
	
	@RequiresPermissions("excel:excelModelType:edit")
	@RequestMapping(value = "delete")
	public String delete(ExcelModelType excelModelType, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, excelModelTypeService.deleteOfCheck(excelModelType));
		return "redirect:"+Global.getAdminPath()+"/excel/excelModelType/list?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ExcelModelType> list = excelModelTypeService.findList(new ExcelModelType());
		for (int i=0; i<list.size(); i++){
			ExcelModelType e = list.get(i);
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