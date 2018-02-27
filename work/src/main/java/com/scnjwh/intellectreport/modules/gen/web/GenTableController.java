/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.gen.web;

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
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.gen.entity.GenTable;
import com.scnjwh.intellectreport.modules.gen.entity.GenTableColumn;
import com.scnjwh.intellectreport.modules.gen.service.GenTableService;
import com.scnjwh.intellectreport.modules.gen.util.GenUtils;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 业务表Controller
 * @author ThinkGem
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTable")
public class GenTableController extends BaseController {

	@Autowired
	private GenTableService genTableService;
	
	@ModelAttribute
	public GenTable get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genTableService.get(id);
		}else{
			return new GenTable();
		}
	}
	
	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			genTable.setCreateBy(user);
		}
        Page<GenTable> page = genTableService.find(new Page<GenTable>(request, response), genTable); 
        model.addAttribute("page", page);
		return "modules/gen/genTableList";
	}

	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = "form")
	public String form(GenTable genTable, Model model) {
		// 获取物理表列表
		List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
		model.addAttribute("tableList", tableList);
		// 验证表是否存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			addMessage(model, "下一步失败！" + genTable.getName() + " 表已经添加！");
			genTable.setName("");
		}
		// 获取物理表字段
		else{
			genTable = genTableService.getTableFormDb(genTable);
		}
		model.addAttribute("genTable", genTable);
		model.addAttribute("config", GenUtils.getConfig());
		return "modules/gen/genTableForm";
	}

	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "save")
	public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTable)){
			return form(genTable, model);
		}
		// 验证表是否已经存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			addMessage(model, "保存失败！" + genTable.getName() + " 表已经存在！");
			genTable.setName("");
			return form(genTable, model);
		}
		genTableService.save(genTable);
		addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}
	
	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "delete")
	public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
		genTableService.delete(genTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}
	
	/**
	 * table树
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response){
		List<GenTable> genTables = genTableService.findAll();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (GenTable genTable : genTables) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", genTable.getId());
			map.put("pId", "0");
			map.put("pIds", "0,");
			map.put("name", genTable.getComments());
			mapList.add(map);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "0");
		map.put("pId", "0");
		map.put("pIds", "0,");
		map.put("name", "功能模块");
		mapList.add(map);
		return mapList;
	}
	
	
	/**
	 * table lie树
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataColumn")
	public List<Map<String, Object>> treeDataColumn(HttpServletResponse response,@RequestParam(required = true)String tableId){
		List<GenTableColumn> genTableColumns = genTableService.getColumns(tableId);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (GenTableColumn genTableColumn : genTableColumns) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", genTableColumn.getName());
			map.put("pId", "0");
			map.put("pIds", "0,");
			map.put("name", genTableColumn.getComments());
			map.put("type", "l");
			mapList.add(map);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "0");
		map.put("pId", "");
		map.put("pIds", "0,");
		map.put("name", "启用列");
		map.put("type", "p");
		mapList.add(map);
		return mapList;
	}
	
	/**
     * table lie
     * @param response
	 * @return 
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getColumns")
    public  List<GenTableColumn> getColumns(HttpServletResponse response,@RequestParam(required = true)String tableId){
        List<GenTableColumn> genTableColumns = genTableService.getColumns(tableId);
        return genTableColumns;
    }
    
    /**
     * table lie
     * @param response
     * @return 
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getColumnsByName")
    public  List<GenTableColumn> getColumnsByName(HttpServletResponse response,@RequestParam(required = true)String tableName){
        List<GenTableColumn> genTableColumns = genTableService.getColumnsByName(tableName);
        return genTableColumns;
    }
    
    /**
     * table lie
     * @param response
     * @return 
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getTables")
    public  List<GenTable> getTables(HttpServletResponse response){
        List<GenTable> genTables = genTableService.findAll();
        return genTables;
    }
}
