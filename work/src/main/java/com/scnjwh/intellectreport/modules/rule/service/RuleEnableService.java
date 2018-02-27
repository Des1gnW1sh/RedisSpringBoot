/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.gen.service.GenTableService;
import com.scnjwh.intellectreport.modules.rule.dao.RuleEnableDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnable;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnableDetail;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 规则定义启动Service
 * 
 * @author 彭俊豪
 * @version 2017-07-10
 */
@Service
@Transactional(readOnly = true)
public class RuleEnableService extends CrudService<RuleEnableDao, RuleEnable> {

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private RuleEnableDetailService ruleEnableDetailService;

    public RuleEnable get(String id) {
        return super.get(id);
    }

    public List<RuleEnable> findList(RuleEnable ruleEnable) {
        return super.findList(ruleEnable);
    }

    public Page<RuleEnable> findPage(Page<RuleEnable> page, RuleEnable ruleEnable) {
        return super.findPage(page, ruleEnable);
    }

    @Transactional(readOnly = false)
    public String saveOfCheck(RuleEnable ruleEnable) {
        if (StringUtils.isBlank(ruleEnable.getId())) {
            //验证数据
            Integer count = dao.validateTable(ruleEnable.getTableId());
            if(count>0){
                return "该表已经被启用，无法再次添加";
            }
            // 添加
            ruleEnable.setStatus(0);
            ruleEnable.setOffice(UserUtils.getUser().getOffice());
            super.save(ruleEnable);

            // 保存从表
            saveDetail(ruleEnable);
            return "保存规则定义启动成功";
        }
        // 修改
        super.save(ruleEnable);
        // 删除以前的从表 保存新的从表
        ruleEnableDetailService.batchDeleteByEId(ruleEnable.getId());
        // 保存从表
        saveDetail(ruleEnable);
        return "保存规则定义启动成功";
    }

    /**
     * 保存从表
     * 
     * @param ruleEnable
     */
    private void saveDetail(RuleEnable ruleEnable) {
        if (StringUtils.isNotBlank(ruleEnable.getColumnIds())) {
            String[] columns = ruleEnable.getColumnIds().split(",");
            for (String column : columns) {
                String comment = genTableService.getColumnComment(ruleEnable.getTableId(), column);
                if (StringUtils.isNotBlank(comment)) {
                    RuleEnableDetail ruleEnableDetail = new RuleEnableDetail();
                    ruleEnableDetail.setColumnId(column);
                    ruleEnableDetail.setColumnName(comment);
                    ruleEnableDetail.setEId(ruleEnable.getId());
                    ruleEnableDetailService.save(ruleEnableDetail);
                }
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(RuleEnable ruleEnable) {
        ruleEnableDetailService.batchDeleteByEId(ruleEnable.getId());
        super.delete(ruleEnable);
    }

    @Transactional(readOnly = false)
    public String batchDeleteOfCheck(String[] ids) {
        super.batchDelete(ids);
        for (String id : ids) {
            ruleEnableDetailService.batchDeleteByEId(id);
        }
        return "批量删除规则定义启动成功";
    }

    /**
     * 启用
     * 
     * @param ids
     * @return
     */
    @Transactional(readOnly = false)
    public String enable(String[] ids) {
        dao.enable(ids);
        return "批量启用规则定义启动成功";
    }

    /**
     * 停用
     * 
     * @param ids
     * @return
     */
    @Transactional(readOnly = false)
    public String disable(String[] ids) {
        dao.disable(ids);
        return "批量启用规则定义启动成功";
    }

    /**
     * - 设置参数
     * 
     * @param ruleEnable
     */
    public void setSome(RuleEnable ruleEnable) {
        if (StringUtils.isBlank(ruleEnable.getId())) {
            return;
        }
        RuleEnableDetail ruleEnableDetail = new RuleEnableDetail();
        ruleEnableDetail.setEId(ruleEnable.getId());
        List<RuleEnableDetail> ruleEnableDetails = ruleEnableDetailService
            .findList(ruleEnableDetail);
        if (ruleEnableDetails.size() <= 0) {
            return;
        }
        String columnIds = "";
        for (RuleEnableDetail enableDetail : ruleEnableDetails) {
            columnIds += enableDetail.getColumnId()+",";
        }
        ruleEnable.setColumnIds(columnIds);

    }

}