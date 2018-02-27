/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.excel.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.Encodes;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudgetDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelBudgetLogDao;
import com.scnjwh.intellectreport.modules.excel.dao.ExcelCollectDao;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudget;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelBudgetLog;
import com.scnjwh.intellectreport.modules.excel.entity.ExcelCollect;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * excel汇总状态Service
 * @author timo
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class ExcelCollectService extends CrudService<ExcelCollectDao, ExcelCollect> {
	
	@Autowired
	private ExcelBudgetDao budgetDao;
	@Autowired
	private ExcelBudgetLogDao budgetLogDao;
	public ExcelCollect get(String id) {
		return super.get(id);
	}
	
	public List<ExcelCollect> findList(ExcelCollect excelCollect) {
		return super.findList(excelCollect);
	}
	
	public Page<ExcelCollect> findPage(Page<ExcelCollect> page, ExcelCollect excelCollect) {
		return super.findPage(page, excelCollect);
	}
	
	public Page<ExcelCollect> findPageOfMy(Page<ExcelCollect> page, ExcelCollect excelCollect,List<Office> offices) {
	/*	String officeIds = "";
		for (Iterator<Office> iterator = offices.iterator(); iterator.hasNext();) {
			officeIds+=iterator.next().getId();
			if(iterator.hasNext()){
				officeIds+=",";
			}
		}*/
		excelCollect.setPage(page);
		page.setList(dao.findListOfMy(excelCollect,offices));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ExcelCollect excelCollect) {
		super.save(excelCollect);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExcelCollect excelCollect) {
		super.delete(excelCollect);
	}
	@Transactional(readOnly = false)
	public void saveXml(ExcelCollect excelCollect) {
		excelCollect.preUpdate();
//		excelCollect.setStatus(DictUtils.getDictValue("已提交","excel_status","1"));
		excelCollect.setSumitUser(excelCollect.getUpdateBy().getName());
		super.dao.saveXml(excelCollect);
	}

	public String getXmlById(String id) {
		return super.dao.getXmlById(id);
	}
	/**
	 * 提交汇总数据
	 * @param collectId 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String submitExcel(String collectId){
		String xml = super.dao.getXmlById(collectId);
		ExcelCollect c = super.dao.get(collectId);
		ExcelBudget ex = c.getBudget();
		String buId = ex.getId();
		String buXml = budgetDao.getXmlById(buId);
		
		ExcelBudgetLog a = new ExcelBudgetLog();
		a.preInsert();
		a.setContent("提交预算汇总-"+ex.getName()+"-"+c.getOffice().getName());
		a.setBudget(ex);
		budgetLogDao.insert(a);
		
		if(StringUtils.isBlank(buXml)){
	    	return submitExcelFirst(xml, buId, collectId);
	    }
		Boolean isfirst = false;
		ExcelCollect excelCollect = new ExcelCollect();
		excelCollect.setBudget(ex);
		excelCollect.setStatus(DictUtils.getDictValue("已提交","excel_status","1"));
		List<ExcelCollect> li2 = super.dao.findList(excelCollect);
		if(li2.size()==0){
			isfirst=true;
		}
		
		try {
			Document document = DocumentHelper.parseText(Encodes.unescapeHtml(xml));  
			Element root = document.getRootElement(); 
			if(root.getName().equals("WorkSheet")){//只有一个分页 sheet
				HashMap<String,String> map = new HashMap<String, String>();
				List<?> nodes = root.elements("data"); 
			    for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
			    	Element elm = (Element) it.next();
			    	if(StringUtils.isNoneBlank(elm.getText())){
//			    		if(StringUtils.isNumeric(elm.getText())){
				    		map.put(elm.attributeValue("name"), elm.getText());
//				    	}else{
//				    		return "汇总失败：数据错误-"+elm.attributeValue("Cell");
//				    	}
			    	}
				} 
			    //上面为提取当前需要汇总的数据 - 下面为将这些数据填充汇总到ExcelBudget中xml
			    document = DocumentHelper.parseText(Encodes.unescapeHtml(buXml));
				root = document.getRootElement(); 
				nodes = root.elements("data"); 
			    for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
			    	Element elm = (Element) it.next();
			    	if(StringUtils.isNoneBlank(elm.getText())){
			    		if(isfirst && StringUtils.isNoneBlank(map.get(elm.attributeValue("name")))){
			    			elm.setText(new BigDecimal(map.get(elm.attributeValue("name")))+"");
			    		}
			    		//map.get(elm.attributeValue("name")) 如果说第二次提交 又不是数字 那以第一次为标准 
			    		if( StringUtils.isNumeric(elm.getText()) && StringUtils.isNoneBlank(map.get(elm.attributeValue("name"))) && StringUtils.isNumeric(map.get(elm.attributeValue("name")))){
			    			elm.setText(new BigDecimal(elm.getText()).add(new BigDecimal(map.get(elm.attributeValue("name"))))+"");
			    		}
			    	}else if(StringUtils.isNoneBlank(map.get(elm.attributeValue("name")))){
			    		elm.setText(map.get(elm.attributeValue("name")));
			    	}
				}
			    return submitExcelSave(buId, document, collectId);
			}else if(root.getName().equals("WorkSheets")){//有多个sheet
				HashMap<String,String> map = new HashMap<String, String>();
				List<?> nodes = root.elements("WorkSheet"); 
			    for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
			    	Element elm = (Element) it.next();
			    	List<?> nodes2 = elm.elements("data"); 
			    	for (Iterator<?> it2 = nodes2.iterator(); it2.hasNext();) {  
			    		Element elm2 = (Element) it2.next();
			    		if(StringUtils.isNoneBlank(elm2.getText())){
//			    			if(StringUtils.isNumeric(elm2.getText())){
				    			map.put(elm.attributeValue("name")+elm2.attributeValue("name"), elm2.getText());
//				    		}else{
//					    		return "汇总失败：数据错误-"+elm.attributeValue("name")+"-"+elm2.attributeValue("Cell");
//					    	}
			    		}
			    	}
				} 
			    //上面为提取当前需要汇总的数据 - 下面为将这些数据填充汇总到ExcelBudget中xml
			    document = DocumentHelper.parseText(Encodes.unescapeHtml(buXml));
				root = document.getRootElement(); 
				nodes = root.elements("WorkSheet"); 
				for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
					Element elm = (Element) it.next();
			    	List<?> nodes2 = elm.elements("data"); 
			    	for (Iterator<?> it2 = nodes2.iterator(); it2.hasNext();) {  
			    		Element elm2 = (Element) it2.next();
			    		if(StringUtils.isNoneBlank(elm2.getText())){
			    			if(isfirst && StringUtils.isNoneBlank(map.get(elm.attributeValue("name")))){
				    			elm.setText(new BigDecimal(map.get(elm.attributeValue("name")+elm2.attributeValue("name")))+"");
				    		}
			    			if(StringUtils.isNumeric(elm2.getText()) && StringUtils.isNoneBlank(map.get(elm.attributeValue("name")+elm2.attributeValue("name"))) && StringUtils.isNumeric(map.get(elm.attributeValue("name")+elm2.attributeValue("name")))){
				    			elm2.setText(new BigDecimal(elm2.getText()).add(new BigDecimal((map.get(elm.attributeValue("name")+elm2.attributeValue("name")))))+"");
				    		}
			    		}else if(StringUtils.isNoneBlank(map.get(elm.attributeValue("name")+elm2.attributeValue("name")))){
			    			elm2.setText(map.get(elm.attributeValue("name")+elm2.attributeValue("name")));
			    		}
			    	}
				}
				return submitExcelSave(buId, document, collectId);
			}else{
				//暂无
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return "未知结果";
	}
	/**
	 * 回退汇总
	 * @param collectId
	 * @return
	 */
	@Transactional(readOnly = false)
	public String rejectExcel(String collectId){
		String xml = super.dao.getXmlById(collectId);//需要扣减的xml
		ExcelCollect c = super.dao.get(collectId);
		ExcelBudget ex = c.getBudget();
		String buId = ex.getId();
		ex = budgetDao.get(buId);
		String buXml = budgetDao.getXmlById(buId);//被扣减的xml
		if(ex.getIsOver().equals("1")){
			ex.setIsOver(DictUtils.getDictValue("未完成","excel_buIsOver","0"));
			budgetDao.update(ex);
		}
		ExcelBudgetLog a = new ExcelBudgetLog();
		a.preInsert();
		a.setContent("预算汇总被回退-"+ex.getName()+"-"+c.getOffice().getName());
		a.setBudget(ex);
		budgetLogDao.insert(a);
		try {
			Document document = DocumentHelper.parseText(Encodes.unescapeHtml(xml));  
			Element root = document.getRootElement(); 
			if(root.getName().equals("WorkSheet")){//只有一个分页 sheet
				HashMap<String,String> map = new HashMap<String, String>();
				List<?> nodes = root.elements("data"); 
			    for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
			    	Element elm = (Element) it.next();
			    	if(StringUtils.isNoneBlank(elm.getText())){
//			    		if(StringUtils.isNumeric(elm.getText())){
				    		map.put(elm.attributeValue("name"), elm.getText());
//				    	}else{
//				    		return "回退失败：数据错误-"+elm.attributeValue("Cell");
//				    	}
			    	}
				} 
			    //上面为提取当前需要汇总的数据 - 下面为将这些数据填充汇总到ExcelBudget中xml
			    document = DocumentHelper.parseText(Encodes.unescapeHtml(buXml));
				root = document.getRootElement(); 
				nodes = root.elements("data"); 
			    for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
			    	Element elm = (Element) it.next();
			    	if(StringUtils.isNoneBlank(elm.getText())){
			    		if(StringUtils.isNumeric(elm.getText()) && StringUtils.isNoneBlank(map.get(elm.attributeValue("name")))  && StringUtils.isNumeric(map.get(elm.attributeValue("name")))){
			    			elm.setText(new BigDecimal(elm.getText()).subtract(new BigDecimal(map.get(elm.attributeValue("name"))))+"");
			    		}
			    	}
				}
			    return rejectExcelSave(buId, document, collectId);
			}else if(root.getName().equals("WorkSheets")){//有多个sheet
				HashMap<String,String> map = new HashMap<String, String>();
				List<?> nodes = root.elements("WorkSheet"); 
			    for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
			    	Element elm = (Element) it.next();
			    	List<?> nodes2 = elm.elements("data"); 
			    	for (Iterator<?> it2 = nodes2.iterator(); it2.hasNext();) {  
			    		Element elm2 = (Element) it2.next();
			    		if(StringUtils.isNoneBlank(elm2.getText())){
//			    			if(StringUtils.isNumeric(elm2.getText())){
				    			map.put(elm.attributeValue("name")+elm2.attributeValue("name"), elm2.getText());
//				    		}else{
//				    			return "退回失败：数据错误-"+elm.attributeValue("name")+"-"+elm2.attributeValue("Cell");
//					    	}
			    		}
			    	}
				} 
			    if(StringUtils.isBlank(buXml)){
			    	return submitExcelFirst(xml, buId, collectId);
			    }
			    //上面为提取当前需要汇总的数据 - 下面为将这些数据填充汇总到ExcelBudget中xml
			    document = DocumentHelper.parseText(Encodes.unescapeHtml(buXml));
				root = document.getRootElement(); 
				nodes = root.elements("WorkSheet"); 
				for (Iterator<?> it = nodes.iterator(); it.hasNext();) {  
					Element elm = (Element) it.next();
			    	List<?> nodes2 = elm.elements("data"); 
			    	for (Iterator<?> it2 = nodes2.iterator(); it2.hasNext();) {  
			    		Element elm2 = (Element) it2.next();
			    		if(StringUtils.isNoneBlank(elm2.getText())){
			    			if(StringUtils.isNumeric(elm2.getText()) && StringUtils.isNoneBlank(map.get(elm.attributeValue("name")+elm2.attributeValue("name"))) && StringUtils.isNumeric(map.get(elm.attributeValue("name")+elm2.attributeValue("name")))){
				    			elm2.setText(new BigDecimal(elm2.getText()).subtract(new BigDecimal((map.get(elm.attributeValue("name")+elm2.attributeValue("name")))))+"");
				    		}
			    		}
			    	}
				}
				return rejectExcelSave(buId, document, collectId);
			}else{
				//暂无
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return "未知结果";
	}
	//如果是第一次提交的文件  并非合并
	public String submitExcelFirst(String xml ,String buId,String collectId){
		ExcelBudget buget = new ExcelBudget(buId);
		buget.setExcleXml(Encodes.unescapeHtml(xml));
		budgetDao.saveXml(buget);
		ExcelCollect excelCollect = new ExcelCollect(collectId);
		excelCollect.setStatus(DictUtils.getDictValue("已提交","excel_status","1"));
		super.dao.updateStatus(excelCollect);
		return "汇总成功";
	}
	////保存新的ExcelBudget XML 并修改记录状态
	public String submitExcelSave(String buId,Document document,String collectId){
	    ExcelBudget buget = new ExcelBudget(buId);
		buget.setExcleXml(document.asXML());//此处直接存的xml 没有转html 未测试有无问题
		budgetDao.saveXml(buget);
		ExcelCollect excelCollect = new ExcelCollect(collectId);
		excelCollect.setStatus(DictUtils.getDictValue("已提交","excel_status","1"));
		super.dao.updateStatus(excelCollect);
		//检查是否完成汇总
		excelCollect = new ExcelCollect();
		excelCollect.setBudget(buget);
		List<ExcelCollect> li = super.dao.findList(excelCollect);
		excelCollect.setStatus(DictUtils.getDictValue("已提交","excel_status","1"));
		List<ExcelCollect> li2 = super.dao.findList(excelCollect);
		if(li.size()==li2.size()){
			buget.setIsOver(DictUtils.getDictValue("已完成","excel_buIsOver","1"));
			budgetDao.updateIsOver(buget);
		}
	    return "汇总成功";
	}
	////保存新的ExcelBudget XML 并修改记录状态
	public String rejectExcelSave(String buId,Document document,String collectId){
	    ExcelBudget buget = new ExcelBudget(buId);
		buget.setExcleXml(document.asXML());//此处直接存的xml 没有转html 未测试有无问题
		budgetDao.saveXml(buget);
		ExcelCollect excelCollect = new ExcelCollect(collectId);
		excelCollect.setStatus(DictUtils.getDictValue("未提交","excel_status","0"));
		super.dao.updateStatus(excelCollect);
	    return "驳回成功";
	}
	public static void main(String[] args) {
		System.out.println(new BigDecimal("a"));
	}
	
}