/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.orgs.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.scnjwh.intellectreport.common.beanvalidator.BeanValidators;
import com.scnjwh.intellectreport.common.config.Global;
import com.scnjwh.intellectreport.common.json.AjaxJson;
import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.utils.excel.ExportExcel;
import com.scnjwh.intellectreport.common.utils.excel.ImportExcel;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.sys.orgs.entity.SysOrgs;
import com.scnjwh.intellectreport.modules.sys.orgs.service.SysOrgsFiscalService;
import com.scnjwh.intellectreport.modules.sys.orgs.service.SysOrgsService;
import com.scnjwh.intellectreport.modules.sys.service.AreaService;

/**
 * 单位管理Controller
 * @author timo
 * @version 2017-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys.orgs/sysOrgs")
public class SysOrgsController extends BaseController {

	@Autowired
	private SysOrgsService sysOrgsService;
	
	@ModelAttribute
	public SysOrgs get(@RequestParam(required=false) String id) {
		SysOrgs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOrgsService.get(id);
		}
		if (entity == null){
			entity = new SysOrgs();
		}
		return entity;
	}
	
	@RequiresPermissions("sys.orgs:sysOrgs:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOrgs sysOrgs, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysOrgs> list = sysOrgsService.findList(sysOrgs); 
		model.addAttribute("list", list);
		return "modules/sys/orgs/sysOrgsList";
	}
	
	/**
	 * 获取业务归口股室
	 * @return
	 */
	@RequestMapping("getOneList")
	@ResponseBody
	public List<SysOrgs> getOneList(){
		return sysOrgsService.getOneList();
	}

	@RequiresPermissions("sys.orgs:sysOrgs:view")
	@RequestMapping(value = "form")
	public String form(SysOrgs sysOrgs, Model model) {
		if (sysOrgs.getParent()!=null && StringUtils.isNotBlank(sysOrgs.getParent().getId())){
			sysOrgs.setParent(sysOrgsService.get(sysOrgs.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(sysOrgs.getId())){
				SysOrgs sysOrgsChild = new SysOrgs();
				sysOrgsChild.setParent(new SysOrgs(sysOrgs.getParent().getId()));
				List<SysOrgs> list = sysOrgsService.findList(sysOrgs); 
				if (list.size() > 0){
					sysOrgs.setSort(list.get(list.size()-1).getSort());
					if (sysOrgs.getSort() != null){
						sysOrgs.setSort(sysOrgs.getSort() + 30);
					}
				}
			}
		}
		if (sysOrgs.getSort() == null){
			sysOrgs.setSort(30);
		}
		if(StringUtils.isBlank(sysOrgs.getUseable())){
			sysOrgs.setUseable("1");
		}
		model.addAttribute("sysOrgs", sysOrgs);
		return "modules/sys/orgs/sysOrgsForm";
	}

	@RequiresPermissions("sys.orgs:sysOrgs:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(SysOrgs sysOrgs, Model model, HttpServletResponse response) {
		if (!beanValidator(model, sysOrgs)) {
			return renderString(response,new AjaxJson().setMsg(model.asMap().get("message").toString()).setSuccess(false));
		}
		return renderString(response, new AjaxJson().setMsg(sysOrgsService.saveOfCheck(sysOrgs)));
	}
	
	@RequiresPermissions("sys.orgs:sysOrgs:remove")
	@RequestMapping(value = "delete")
	public String delete(SysOrgs sysOrgs, RedirectAttributes redirectAttributes) {
		sysOrgsService.delete(sysOrgs);
		addMessage(redirectAttributes, "删除单位成功");
		return "redirect:"+Global.getAdminPath()+"/sys.orgs/sysOrgs/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysOrgs> list = sysOrgsService.findList(new SysOrgs());
		for (int i=0; i<list.size(); i++){
			SysOrgs e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	
	@Autowired
	private SysOrgsFiscalService fiscalService;
	@Autowired
	private AreaService areaService;
	/**
	 * 导入单位数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
		
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysOrgs> list = ei.getDataList(SysOrgs.class);
			for (SysOrgs orgs : list) {
				try {
//					BeanValidators.validateWithException(validator, orgs);
					if(orgs.getParent()!=null){
						orgs.setParent(sysOrgsService.getByCode(orgs.getParent().getCode()));
					}
					if(orgs.getPutunder()!=null){
						orgs.setPutunder(sysOrgsService.getByName(orgs.getPutunder().getName()));
					}
					orgs.setFiscalType(fiscalService.getByName(orgs.getFiscalType().getName()));
					orgs.setArea(areaService.getByName(orgs.getArea().getName()));
					sysOrgsService.save(orgs);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>" + orgs.getName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>" + orgs.getName() + " 导入失败："+ ex.getMessage());
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条数据"+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys.orgs/sysOrgs/list?repage";
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			String fileName = "单位数据导入模板.xlsx";
			List<SysOrgs> list = Lists.newArrayList();
//			list.add(UserUtils.getUser());
			new ExportExcel("单位数据模板", SysOrgs.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys.orgs/sysOrgs/list?repage";
	}
	/**
	 * 导出数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(SysOrgs orgs, HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "单位数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			Page<SysOrgs> page = sysOrgsService.findPage(new Page<SysOrgs>(request, response, -1), orgs);
			new ExportExcel("单位数据", SysOrgs.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出单位失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys.orgs/sysOrgs/list?repage";
	}
}