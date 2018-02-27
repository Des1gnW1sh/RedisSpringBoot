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
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.common.utils.IdGen;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudegetFormulaType;
import com.scnjwh.intellectreport.modules.excel.service.ExcelBudegetFormulaTypeService;

/**
 * 预算公式类型Controller
 * @author timo
 * @version 2017-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/excelBudegetFormulaType")
public class ExcelBudegetFormulaTypeController extends BaseController {

	@Autowired
	private ExcelBudegetFormulaTypeService excelBudegetFormulaTypeService;
	
	@ModelAttribute
	public ExcelBudegetFormulaType get(@RequestParam(required=false) String id) {
		ExcelBudegetFormulaType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = excelBudegetFormulaTypeService.get(id);
		}
		if (entity == null){
			entity = new ExcelBudegetFormulaType();
		}
		return entity;
	}
	
	@RequiresPermissions("excel:excelBudegetFormulaType:view")
    @RequestMapping(value = { "" })
	public String index(ExcelBudegetFormulaType excelBudegetFormulaType, Model model) {           
		return "modules/excel/excelBudegetFormulaTypeIndex";
	}
	
	@RequiresPermissions("excel:excelBudegetFormulaType:view")
	@RequestMapping(value = {"list"})
	public String list(ExcelBudegetFormulaType excelBudegetFormulaType, HttpServletRequest request, HttpServletResponse response, Model model) {
		//如果树是数据字典数据 
		//List<String> typeList = dictService.findTypeList();
		//model.addAttribute("typeList", typeList);
		//默认为自身树
		List<ExcelBudegetFormulaType> list = excelBudegetFormulaTypeService.findList(excelBudegetFormulaType); 
		model.addAttribute("list", list);
		Page<ExcelBudegetFormulaType> page = excelBudegetFormulaTypeService.findPage(new Page<ExcelBudegetFormulaType>(request, response), excelBudegetFormulaType); 
        model.addAttribute("page", page);
		return "modules/excel/excelBudegetFormulaTypeList";
	}

	@RequiresPermissions("excel:excelBudegetFormulaType:view")
	@RequestMapping(value = "form")
	public String form(ExcelBudegetFormulaType excelBudegetFormulaType, Model model) {
		if (excelBudegetFormulaType.getParent()!=null && StringUtils.isNotBlank(excelBudegetFormulaType.getParent().getId())){
			excelBudegetFormulaType.setParent(excelBudegetFormulaTypeService.get(excelBudegetFormulaType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(excelBudegetFormulaType.getId())){
				ExcelBudegetFormulaType excelBudegetFormulaTypeChild = new ExcelBudegetFormulaType();
				excelBudegetFormulaTypeChild.setParent(new ExcelBudegetFormulaType(excelBudegetFormulaType.getParent().getId()));
				List<ExcelBudegetFormulaType> list = excelBudegetFormulaTypeService.findList(excelBudegetFormulaType); 
				if (list.size() > 0){
					excelBudegetFormulaType.setSort(list.get(list.size()-1).getSort());
					if (excelBudegetFormulaType.getSort() != null){
						excelBudegetFormulaType.setSort(excelBudegetFormulaType.getSort() + 30);
					}
				}
			}
		}
		if (excelBudegetFormulaType.getSort() == null){
			excelBudegetFormulaType.setSort(30);
		}
		model.addAttribute("excelBudegetFormulaType", excelBudegetFormulaType);
		return "modules/excel/excelBudegetFormulaTypeForm";
	}

	@RequiresPermissions("excel:excelBudegetFormulaType:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(ExcelBudegetFormulaType excelBudegetFormulaType, Model model,HttpServletResponse response) {
		//屏蔽code值
		if(StringUtils.isBlank(excelBudegetFormulaType.getCode())){
			excelBudegetFormulaType.setCode(IdGen.uuid());
		}
		if (!beanValidator(model, excelBudegetFormulaType)){
			return renderString(response, new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		excelBudegetFormulaTypeService.save(excelBudegetFormulaType);
		return renderString(response, new AjaxJson().setMsg("保存公式类型成功"));
	}
	
	@RequiresPermissions("excel:excelBudegetFormulaType:edit")
	@RequestMapping(value = "delete")
	public String delete(ExcelBudegetFormulaType excelBudegetFormulaType, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, excelBudegetFormulaTypeService.deleteOfCheck(excelBudegetFormulaType));
		return "redirect:"+Global.getAdminPath()+"/excel/excelBudegetFormulaType/list?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ExcelBudegetFormulaType> list = excelBudegetFormulaTypeService.findList(new ExcelBudegetFormulaType());
		for (int i=0; i<list.size(); i++){
			ExcelBudegetFormulaType e = list.get(i);
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