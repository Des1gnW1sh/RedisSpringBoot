/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.gen.dao;

import org.apache.ibatis.annotations.Param;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * 
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {

	public void deleteByGenTableId(String genTableId);
	
	/**
	 * 获取字典值
	 * @param tableId 表ID
	 * @param columnName 列名字
	 * @return
	 */
	public String getDictName(@Param(value="tableId")String tableId, @Param(value="columnName")String columnName);
	
	/**
     * 获取字典值
     * @param tableId 表ID
     * @param columnName 列名字
     * @return
     */
    public String getComments(@Param(value="tableId")String tableId, @Param(value="columnName")String columnName);
    
    /**
     * 获取列描述
     * @param tableName 表名
     * @param columnName 列名字
     * @return
     */
    public String getCommentsByTableName(@Param(value="tableName")String tableName, @Param(value="columnName")String columnName);
}
