package com.scnjwh.intellectreport.modules.cms.dao;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created by Administrator on 2017-10-17.
 */
public interface DBSynchronizeDao {
    public String DBynchronize(String tableName, List<T> list);
}
