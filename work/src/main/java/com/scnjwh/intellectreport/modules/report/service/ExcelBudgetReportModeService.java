package com.scnjwh.intellectreport.modules.report.service;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportModeDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportMode;
import com.scnjwh.intellectreport.modules.report.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2018-2-5.
 */
@Service
@Transactional(readOnly = true)
public class ExcelBudgetReportModeService extends CrudService<ExcelBudgetReportModeDao,ExcelBudgetReportMode> {

    @Autowired
    private ExcelBudgetReportModeDao excelBudgetReportModeDao;

    public ExcelBudgetReportMode get(String id){
        return excelBudgetReportModeDao.get(id);
    }

    public Page<ExcelBudgetReportMode> findList(Page<ExcelBudgetReportMode> page, ExcelBudgetReportMode excelBudgetReportMode, String userId) {
        excelBudgetReportMode.setPage(page);
        page.setList(dao.findList(excelBudgetReportMode));
        return page;
    }
    @Transactional(readOnly = false)
    public void insert(ExcelBudgetReportMode excelBudgetReportMode){
        String excel = excelBudgetReportMode.getXml();
        excel = ExcelUtil.setCellAlias(excel);
        excelBudgetReportMode.setXml(excel);
        excelBudgetReportMode.preInsert();
        if(excelBudgetReportMode.getId() != null){
            excelBudgetReportModeDao.insert(excelBudgetReportMode);
        }
    }

    @Transactional(readOnly = false)
    public String delete(String[] ids) {
            excelBudgetReportModeDao.batchDelete(ids);
            return "删除模板成功";
    }

    @Transactional(readOnly = false)
    public String update(ExcelBudgetReportMode excelBudgetReportMode) {
        try {
            String excel = excelBudgetReportMode.getXml();
            excel = ExcelUtil.setCellAlias(excel);
            excelBudgetReportMode.setXml(excel);
            excelBudgetReportMode.setUpdateDate(new Date());
            excelBudgetReportModeDao.update(excelBudgetReportMode);
            return "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败";
        }

    }

    public String getXmlById(String id){
        if(id != null){
            return excelBudgetReportModeDao.getXmlById(id);
        }else{
            return "";
        }
    }

}
