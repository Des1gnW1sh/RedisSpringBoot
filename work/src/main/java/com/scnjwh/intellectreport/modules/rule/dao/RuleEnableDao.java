/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.dao;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleEnable;

/**
 * 规则定义启动DAO接口
 * @author 彭俊豪
 * @version 2017-07-10
 */
@MyBatisDao
public interface RuleEnableDao extends CrudDao<RuleEnable> {
    public RuleEnable getByName(String name);

    /**
     * 启用
     * @param ids
     */
    public void enable(@Param(value = "ids") String[] ids);

    /**
     * 停用
     * @param ids
     */
    public void disable(@Param(value = "ids") String[] ids);

    /**
     * 验证是否已有该表
     * @param tableId
     * @return
     */
    public Integer validateTable(@Param(value = "tableId") String tableId);
}