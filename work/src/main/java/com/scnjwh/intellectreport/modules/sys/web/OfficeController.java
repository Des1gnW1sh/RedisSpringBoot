/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.sys.entity.Tree;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
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
import com.scnjwh.intellectreport.modules.sys.dao.UserDao;
import com.scnjwh.intellectreport.modules.sys.entity.JsonTree;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import com.scnjwh.intellectreport.modules.sys.service.OfficeService;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private ExcelBudgetReportService excelBudgetReportService;


	@Autowired
	private UserDao userDao;

	@ModelAttribute("office")
	public Office get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return officeService.get(id);
		} else {
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "" })
	public String index(Office office, Model model) {
		// model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "list" })
	public String list(Office office, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "view" })
	public String view(Office office, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/sys/officeView";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent() == null || office.getParent().getId() == null) {
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea() == null) {
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId()) && office.getParent() != null) {
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i = 0; i < list.size(); i++) {
				Office e = list.get(i);
				if (e.getParent() != null
						&& e.getParent().getId() != null
						&& e.getParent().getId()
								.equals(office.getParent().getId())) {
					size++;
				}
			}
			office.setCode(office.getParent().getCode()
					+ StringUtils.leftPad(
							String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
		}
		//获取业务归口处室
		List<Office> offices = officeService.findListByFOfficeType("1-业务处室");
		model.addAttribute("offices", offices);
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Office> list = ei.getDataList(Office.class);
			for (Office office : list) {
				try {
					// if ("true".equals(checkIdName("", office.getId()))){
					// office.setPassword(SystemService.entryptPassword("123456"));
					BeanValidators.validateWithException(validator, office);
					officeService.save(office);
					successNum++;
					// }else{
					// failureMsg.append("<br/>主键 "+office.getId()()+" 已存在; ");
					// failureNum++;
					// }
				} catch (ConstraintViolationException ex) {
					failureMsg
							.append("<br/>登录名 " + office.getName() + " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + office.getName() + " 导入失败："
							+ ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/office/list?repage";
	}

	/**
	 * 下载数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "机构数据导入模板.xlsx";
			List<Office> list = Lists.newArrayList();
			list.add(officeService.get("1"));
			ExportExcel excel = new ExportExcel("机构数据", Office.class, 2);
			excel.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/office/list?repage";
	}

	/**
	 * 导出用户数据
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Office office, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "机构单位数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<Office> page = officeService.findPage(new Page<Office>(
					request, response, -1), office);
			new ExportExcel("机构单位数据", Office.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出机构单位失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/office/list?repage";
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, HttpServletResponse response) {
		if (Global.isDemoMode()) {
			return renderString(response, new AjaxJson().setMsg("保存机构失败")
					.setSuccess(false));
		}
		if (!beanValidator(model, office)) {
			return form(office, model);
		}
		officeService.save(office);

		if (office.getChildDeptList() != null) {
			Office childOffice = null;
			for (String id : office.getChildDeptList()) {
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id,
						"sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office
						.getGrade()) + 1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}

		String id = "0".equals(office.getParentId()) ? "" : office
				.getParentId();
		return renderString(response, new AjaxJson().setMsg("保存机构成功"));
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
		// if (Office.isRoot(id)){
		// addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
		// }else{
		officeService.delete(office);
		addMessage(redirectAttributes, "删除机构成功");
		// }
		return "redirect:" + adminPath + "/sys/office/list?id="
				+ office.getParentId() + "&parentIds=" + office.getParentIds();
	}

	/**
	 * 获取用户选择json
	 * 
	 * @return
	 * @Author : pengjunhao. create at 2017年4月21日 上午11:13:48
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "jsonTree")
	public List<JsonTree> jsonTree(HttpServletResponse response) {
		logger.info("【获取用户选择json】");
		return officeService.getJsonTrees();
	}

	/**
	 * 获取用户选择json
	 * 
	 * @return
	 * @Author : pengjunhao. create at 2017年4月21日 上午11:13:48
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "jsonTreeRole")
	public List<JsonTree> jsonTreeRole(HttpServletResponse response) {
		logger.info("【获取角色选择json】");
		return officeService.getJsonTreesRole();
	}

	/**
	 * 获取用户组名称
	 * 
	 * @param response
	 * @param ids
	 *            用户id集合
	 * @return
	 * @Author : pengjunhao. create at 2017年4月24日 上午11:25:57
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getUserName")
	public String getUserName(HttpServletResponse response, String ids) {
		logger.info("【获取用户组名称】ids={}", ids);
		return officeService.getUserName(ids);
	}

	/**
	 * 获取机构JSON数据。
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade
	 *            显示级别
	 * @param pid
	 *            是否指定上一级
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) Long grade,
			@RequestParam(required = false) Boolean isAll,
			@RequestParam(required = false) String pid,// 是否指定上一级 or：timo
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			if (StringUtils.isNoneBlank(pid) && !e.getId().equals(pid)) {// 屏蔽所有不是传入的pid的节点
				if (!e.getParentId().equals(pid)) {// 屏蔽所有父节点为pid的节点
					continue;
				}
				if (e.getType().equals("1")) {
					continue;
				}
			}
			if ((StringUtils.isBlank(extId) || (extId != null
					&& !extId.equals(e.getId()) && e.getParentIds().indexOf(
					"," + extId + ",") == -1))
					&& (type == null || (type != null && (type.equals("1") ? type
							.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e
							.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("typeOf", e.getFOfficeType());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)) {
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeAllData")
	public List<Tree> treeAllData(@RequestParam(required = false) Boolean isAll,@Param("type") String type){
			List<Map<String,Object>> result = new ArrayList<>();
			/*List<Office> list = officeService.findList(isAll);
			for(Office i:list){
				List<Office> list1 = officeService.findListByParentId(i);
				System.out.println(123);
			}
*/
			Office office = new Office();
			office.setParent_Id(0);
			if(type != null && type != ""){
				office.setType(type);
			}else{
				office.setType("0");
			}
			List<Tree> list = getTree(office);

			return list;
	}
	public List<Tree> getTree(Office office){
		if(office.getParent_Id() == null){
			office.setParent_Id(0);
		}
		List<Tree> resultList = new ArrayList<>();
		List<Office> listParent = officeService.findListByParentId(office);
		if(listParent.size() != 0){
			for(Office parent : listParent){
				Tree tree = new Tree();
				tree.setId(Integer.valueOf(parent.getId()));
				tree.setName(parent.getName());
				parent.setParent_Id(Integer.valueOf(parent.getId()));
				tree.setChildren(getTree(parent));
				resultList.add(tree);
			}
		}
		return resultList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeSmallData")
	public  List<Map<String, Object>> treeSmallData(@RequestParam(required = false) Boolean isAll,@Param("type") String type,@Param("id") String id){
		List<Map<String,Object>> result = new ArrayList<>();
			/*List<Office> list = officeService.findList(isAll);
			for(Office i:list){
				List<Office> list1 = officeService.findListByParentId(i);
				System.out.println(123);
			}
*/
		Office office = new Office();
		office.setParent_Id(0);
		if(type != null && type != ""){
			office.setType(type);
		}else{
			office.setType("0");
		}
		ExcelBudgetReport excelBudgetReport = excelBudgetReportService.get(id);
		String node = excelBudgetReport.getOfficeLab();
		String[] arr = node.split(",");
		List<Office> list1 = new ArrayList<>();
		for(String i : arr){
			Office office1 = officeService.get(i);
			list1.add(office1);
		}
		List<Office> list = officeService.findList(true);
		List<Office> data = new ArrayList<Office>();
		for(Office o : list){
			for(String sid : arr){
				if(StringUtils.equals(o.getId(),sid)){
					data.add(o);
					break;
				}
			}
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i = 0; i < data.size(); i++) {
			Office e = data.get(i);

				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("typeOf", e.getFOfficeType());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)) {
					map.put("isParent", true);
				}
				mapList.add(map);
		}

		return mapList;
	}

	public void getParent(List<Office> list){

		}




	/**
	 * 获取机构JSON数据。
	 * 
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataByfTypeCode")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String fTypeCode, // 单位类型
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Office office = new Office();
		office.setFOfficeType("1-业务处室");
		List<Office> list = officeService.findListByfTypeCode(office);
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "0");
		map.put("pId", "0");
		map.put("pIds", "");
		map.put("name", "行政单位");
		mapList.add(map);
		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 获取用户
	 * 
	 * @param response
	 * @param ids
	 * @return
	 * @Author : pengjunhao. create at 2017年5月3日 上午11:31:07
	 */
	@ResponseBody
	@RequestMapping(value = "getUserString")
	public String getUserString(HttpServletResponse response, String ids) {
		String[] idss = ids.split(",");
		String names = "";
		for (String id : idss) {
			names += UserUtils.get(id).getName() + ",";
		}
		return names;
	}

	/**
	 * 获取角色
	 * 
	 * @param response
	 * @param ids
	 * @return
	 * @Author : pengjunhao. create at 2017年5月3日 上午11:31:07
	 */
	@ResponseBody
	@RequestMapping(value = "getRoleString")
	public String getRoleString(HttpServletResponse response, String ids) {

		return officeService.getRoleNames(ids);
	}

}
