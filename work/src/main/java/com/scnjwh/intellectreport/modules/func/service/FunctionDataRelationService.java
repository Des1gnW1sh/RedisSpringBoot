/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.func.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.func.dao.FunctionDataRelationDao;
import com.scnjwh.intellectreport.modules.func.dao.FunctionDataRelationDetailDao;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelation;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelationDetail;
import com.scnjwh.intellectreport.modules.gen.dao.GenTableDao;
import com.scnjwh.intellectreport.modules.gen.entity.GenTable;
import com.scnjwh.intellectreport.modules.gen.service.GenTableService;
import com.scnjwh.intellectreport.modules.sys.dao.MenuDao;
import com.scnjwh.intellectreport.modules.sys.entity.Menu;

/**
 * 功能数据表关联Service
 * @author 彭俊豪
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true)
public class FunctionDataRelationService extends
                                        CrudService<FunctionDataRelationDao, FunctionDataRelation> {

    @Autowired
    private FunctionDataRelationDetailService functionDataRelationDetailService;

    @Autowired
    private FunctionDataRelationDetailDao functionDataRelationDetailDao;

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private GenTableDao genTableDao;

    @Autowired
    private MenuDao menuDao;

    public FunctionDataRelation get(String id) {
        return super.get(id);
    }

    public List<FunctionDataRelation> findList(FunctionDataRelation functionDataRelation) {
        return super.findList(functionDataRelation);
    }

    public Page<FunctionDataRelation> findPage(Page<FunctionDataRelation> page,
                                               FunctionDataRelation functionDataRelation) {
        return super.findPage(page, functionDataRelation);
    }

    @Transactional(readOnly = false)
    public String saveOfCheck(FunctionDataRelation functionDataRelation) {
        if (StringUtils.isBlank(functionDataRelation.getId())) {
            getTableName(functionDataRelation);
            getMenuName(functionDataRelation);
            //添加
            super.save(functionDataRelation);
            if (functionDataRelation.getFunctionDataRelationDetails().size() < 0) {
                return "保存功能数据表关联成功";
            }
            //添加从表
            for (FunctionDataRelationDetail functionDataRelationDetail : functionDataRelation
                .getFunctionDataRelationDetails()) {
                if (StringUtils.isBlank(functionDataRelationDetail.getRetinueTable())) {
                    continue;
                }
                getTableDetailName(functionDataRelationDetail);
                functionDataRelationDetail.setFId(functionDataRelation.getId());
                functionDataRelationDetailService.save(functionDataRelationDetail);
            }
            return "保存功能数据表关联成功";
        }
        getTableName(functionDataRelation);
        getMenuName(functionDataRelation);
        //修改
        super.save(functionDataRelation);
        //删除从表 重新添加
        functionDataRelationDetailDao.batchDeleteByFid(functionDataRelation.getId());
        if (functionDataRelation.getFunctionDataRelationDetails().size() < 0) {
            return "保存功能数据表关联成功";
        }
        for (FunctionDataRelationDetail functionDataRelationDetail : functionDataRelation
            .getFunctionDataRelationDetails()) {
            if (StringUtils.isBlank(functionDataRelationDetail.getRetinueTable())) {
                continue;
            }
            getTableDetailName(functionDataRelationDetail);
            functionDataRelationDetail.setFId(functionDataRelation.getId());
            functionDataRelationDetailService.save(functionDataRelationDetail);
        }
        return "保存功能数据表关联成功";
    }

    /**
     * 设置从表数据
     * @param functionDataRelationDetail
     */
    private void getTableDetailName(FunctionDataRelationDetail functionDataRelationDetail) {
        GenTable genTable = genTableDao.get(functionDataRelationDetail.getRetinueTable());
        if (genTable != null) {
            functionDataRelationDetail.setRetinueTableId(genTable.getName());
            functionDataRelationDetail.setRetinueTableName(genTable.getComments());
        }
    }

    /**
     * 获取菜单名称
     * @param functionDataRelation
     */
    private void getMenuName(FunctionDataRelation functionDataRelation) {
        Menu menu = menuDao.get(functionDataRelation.getFunctionId());
        if (menu != null) {
            functionDataRelation.setFunctionName(menu.getName());
        }
    }

    /**
     * 获取表名
     * @param functionDataRelation
     */
    private void getTableName(FunctionDataRelation functionDataRelation) {
        GenTable genTable = genTableDao.get(functionDataRelation.getTable());
        if (genTable != null) {
            functionDataRelation.setTableId(genTable.getName());
            functionDataRelation.setTableName(genTable.getComments());
        }

    }

    @Transactional(readOnly = false)
    public void delete(FunctionDataRelation functionDataRelation) {
        super.delete(functionDataRelation);
    }

    @Transactional(readOnly = false)
    public String batchDeleteOfCheck(String[] ids) {
        for (String id : ids) {
            functionDataRelationDetailDao.batchDeleteByFid(id);
        }
        super.batchDelete(ids);
        return "批量删除功能数据表关联成功";
    }

    /**
     * 获取从表数据
     * @param functionDataRelation
     */
    public void getDetails(FunctionDataRelation functionDataRelation) {
        if (StringUtils.isBlank(functionDataRelation.getId())) {
            return;
        }
        FunctionDataRelationDetail functionDataRelationDetail = new FunctionDataRelationDetail();
        functionDataRelationDetail.setFId(functionDataRelation.getId());
        List<FunctionDataRelationDetail> functionDataRelationDetails = functionDataRelationDetailService
            .findList(functionDataRelationDetail);
        if (functionDataRelationDetails.size() > 0) {
            for (FunctionDataRelationDetail detail : functionDataRelationDetails) {
                GenTable genTable = new GenTable();
                genTable.setName(detail.getRetinueTableId());
                genTable.setComments(detail.getRetinueTableName());
                List<GenTable> genTables = genTableDao.findList(genTable);
                if (genTables.size() > 0) {
                    detail.setRetinueTable(genTables.get(0).getId());
                }

            }
        }
        functionDataRelation.setFunctionDataRelationDetails(functionDataRelationDetails);
    }

    /**
     * 设置查询参数
     * @param functionDataRelation
     */
    public void setSomePage(FunctionDataRelation functionDataRelation) {
        if (StringUtils.isNotBlank(functionDataRelation.getTable())) {
            GenTable genTable = genTableDao.get(functionDataRelation.getTable());
            if (genTable != null) {
                functionDataRelation.setTableId(genTable.getName());
                functionDataRelation.setTableName(genTable.getComments());
            }
        }
    }

    /**
     * 设置参数
     * @param functionDataRelation
     */
    public void setSomeFrom(FunctionDataRelation functionDataRelation) {
        if (StringUtils.isBlank(functionDataRelation.getId())) {
            return;
        }
        GenTable genTable = new GenTable();
        genTable.setName(functionDataRelation.getTableId());
        genTable.setComments(functionDataRelation.getTableName());
        List<GenTable> genTables = genTableDao.findList(genTable);
        if (genTables.size() > 0) {
            functionDataRelation.setTable(genTables.get(0).getId());
        }
    }
}