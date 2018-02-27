/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
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
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.sys.entity.Dict;
import com.scnjwh.intellectreport.modules.sys.service.DictService;

/**
 * 字典Controller
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @ModelAttribute
    public Dict get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return dictService.get(id);
        } else {
            return new Dict();
        }
    }

    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = {"list"})
    public String list(Dict dict, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        List<Dict> typeList = dictService.findListType();
        model.addAttribute("typeList", typeList);
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
        model.addAttribute("page", page);
        return "modules/sys/dictList";
    }
    
    @RequestMapping(value="listData")
    @ResponseBody
    public  Page<Dict>  listData(Dict dict, HttpServletRequest request, HttpServletResponse response){
         Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
         return page;
    }

    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = { "" })
    public String index( Model model) {
        //        model.addAttribute("list", officeService.findAll());
        return "modules/sys/dictIndex";
    }

    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "form")
    public String form(Dict dict, Model model) {
        model.addAttribute("dict", dict);
        return "modules/sys/dictForm";
    }
    @RequestMapping(value = "formKey")
    public String formKey(Dict dict, Model model) {
    	dict.setId("");
    	dict.setLabel("");
    	dict.setValue("");
        model.addAttribute("dict", dict);
        return "modules/sys/dictForm";
    }
    @RequiresPermissions("sys:dict:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(Dict dict, Model model, HttpServletResponse response) {
        if (Global.isDemoMode()) {
            return renderString(response, new AjaxJson().setMsg("演示模式，不允许操作！").setSuccess(false));
        }
        if (!beanValidator(model, dict)) {
            return renderString(response,new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
        }
        dictService.save(dict);
        return renderString(response, new AjaxJson().setMsg("保存字典'" + dict.getLabel() + "'成功"));
    }

    @RequiresPermissions("sys:dict:edit")
    @RequestMapping(value = "delete")
    public String delete(Dict dict, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/dict/list?repage";
        }
        dictService.delete(dict);
        addMessage(redirectAttributes, "删除字典成功");
        return "redirect:" + adminPath + "/sys/dict/list?repage&type=" + dict.getType();
    }
    
	@RequestMapping(value = "deletes")
	@ResponseBody
	public String deletes(String[] ids, HttpServletResponse response) {
		return renderString(response, new AjaxJson().setMsg(dictService.deletes(ids)));
	}

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String type,
                                              HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Dict dict = new Dict();
        dict.setType(type);
        List<Dict> list = dictService.findList(dict);
        for (int i = 0; i < list.size(); i++) {
            Dict e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParentId());
            map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

//    @ResponseBody
//    @RequestMapping(value = "listData")
//    public List<Dict> listData(@RequestParam(required = false) String type) {
//        Dict dict = new Dict();
//        dict.setType(type);
//        return dictService.findList(dict);
//    }

    /**
     * 获取类型树
     * @return
     * @Author : pengjunhao. create at 2017年5月16日 下午3:51:08
     */
    @ResponseBody
    @RequestMapping(value = "treeDataList")
    public List<Map<String, Object>> treeData() {
        return dictService.getTreeData();
    }

}
