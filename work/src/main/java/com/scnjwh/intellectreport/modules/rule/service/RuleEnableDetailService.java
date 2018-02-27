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
import com.scnjwh.intellectreport.modules.gen.dao.GenTableColumnDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnable;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnableDetail;
import com.scnjwh.intellectreport.modules.rule.dao.RuleEnableDetailDao;

/**
 * 规则定义启动明细Service
 * 
 * @author pjh
 * @version 2017-07-10
 */
@Service
@Transactional(readOnly = true)
public class RuleEnableDetailService extends CrudService<RuleEnableDetailDao, RuleEnableDetail> {

    @Autowired
    private GenTableColumnDao genTableColumnDao;

    public RuleEnableDetail get(String id) {
        return super.get(id);
    }

    public List<RuleEnableDetail> findList(RuleEnableDetail ruleEnableDetail) {
        return super.findList(ruleEnableDetail);
    }

    public Page<RuleEnableDetail> findPage(Page<RuleEnableDetail> page,
                                           RuleEnableDetail ruleEnableDetail) {
        return super.findPage(page, ruleEnableDetail);
    }

    @Transactional(readOnly = false)
    public String saveOfCheck(RuleEnableDetail ruleEnableDetail) {
        super.save(ruleEnableDetail);
        return "保存规则定义启动明细成功";
    }

    @Transactional(readOnly = false)
    public void delete(RuleEnableDetail ruleEnableDetail) {
        super.delete(ruleEnableDetail);
    }

    @Transactional(readOnly = false)
    public String batchDeleteOfCheck(String[] ids) {
        super.batchDelete(ids);
        return "批量删除规则定义启动明细成功";
    }

    @Transactional(readOnly = false)
    public String batchDeleteByEId(String eId) {
        dao.batchDeleteByEId(eId);
        return "批量删除规则定义启动明细成功";
    }

    /**
     * 获取列
     * @param ruleEnables
     * @return
     */
    public List<RuleEnableDetail> getDetails(List<RuleEnable> ruleEnables) {
        if (ruleEnables.size() <= 0) {
            return null;
        }
        //只会存在一个启用的表
        RuleEnable ruleEnable = ruleEnables.get(0);

        //获取从表
        RuleEnableDetail ruleEnableDetail = new RuleEnableDetail();
        ruleEnableDetail.setEId(ruleEnable.getId());
        List<RuleEnableDetail> ruleEnableDetails = findList(ruleEnableDetail);
        if (ruleEnableDetails.size() <= 0) {
            return null;
        }

        for (RuleEnableDetail enableDetail : ruleEnableDetails) {
            String dictName = genTableColumnDao.getDictName(ruleEnable.getTableId(),
                enableDetail.getColumnId());
            enableDetail.setDiscName("1");
            if (StringUtils.isNotBlank(dictName)) {
                enableDetail.setDiscName(dictName);
            }
        }
        return ruleEnableDetails;
    }

}