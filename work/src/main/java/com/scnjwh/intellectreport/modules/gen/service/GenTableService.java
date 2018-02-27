/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.gen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.BaseService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.gen.entity.GenTable;
import com.scnjwh.intellectreport.modules.gen.entity.GenTableColumn;
import com.scnjwh.intellectreport.modules.gen.util.GenUtils;
import com.scnjwh.intellectreport.modules.gen.dao.GenDataBaseDictDao;
import com.scnjwh.intellectreport.modules.gen.dao.GenTableColumnDao;
import com.scnjwh.intellectreport.modules.gen.dao.GenTableDao;

/**
 * 业务表Service
 * 
 * @author ThinkGem
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTableService extends BaseService {

    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;
    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;

    public GenTable get(String id) {
        GenTable genTable = genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
        genTable.setPage(page);
        page.setList(genTableDao.findList(genTable));
        return page;
    }

    public List<GenTable> findAll() {
        return genTableDao.findAllList(new GenTable());
    }

    /**
     * 获取物理数据表列表
     * 
     * @param genTable
     * @return
     */
    public List<GenTable> findTableListFormDb(GenTable genTable) {
        return genDataBaseDictDao.findTableList(genTable);
    }

    /**
     * 验证表名是否可用，如果已存在，则返回false
     * 
     * @param genTable
     * @return
     */
    public boolean checkTableName(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> list = genTableDao.findList(genTable);
        return list.size() == 0;
    }

    /**
     * 获取物理数据表列表
     * 
     * @param genTable
     * @return
     */
    public GenTable getTableFormDb(GenTable genTable) {
        // 如果有表名，则获取物理表
        if (StringUtils.isNotBlank(genTable.getName())) {

            List<GenTable> list = genDataBaseDictDao.findTableList(genTable);
            if (list.size() > 0) {

                // 如果是新增，初始化表属性
                if (StringUtils.isBlank(genTable.getId())) {
                    genTable = list.get(0);
                    // 设置字段说明
                    if (StringUtils.isBlank(genTable.getComments())) {
                        genTable.setComments(genTable.getName());
                    }
                    genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
                }

                // 添加新列
                List<GenTableColumn> columnList = genDataBaseDictDao.findTableColumnList(genTable);
                for (GenTableColumn column : columnList) {
                    boolean b = false;
                    for (GenTableColumn e : genTable.getColumnList()) {
                        if (e.getName().equals(column.getName())) {
                            b = true;
                        }
                    }
                    if (!b) {
                        genTable.getColumnList().add(column);
                    }
                }

                // 删除已删除的列
                for (GenTableColumn e : genTable.getColumnList()) {
                    boolean b = false;
                    for (GenTableColumn column : columnList) {
                        if (column.getName().equals(e.getName())) {
                            b = true;
                        }
                    }
                    if (!b) {
                        e.setDelFlag(GenTableColumn.DEL_FLAG_DELETE);
                    }
                }

                // 获取主键
                genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));

                // 初始化列属性字段
                GenUtils.initColumnField(genTable);

            }
        }
        return genTable;
    }

    @Transactional(readOnly = false)
    public void save(GenTable genTable) {
        if (StringUtils.isBlank(genTable.getId())) {
            genTable.preInsert();
            genTableDao.insert(genTable);
        } else {
            genTable.preUpdate();
            genTableDao.update(genTable);
        }
        // 保存列
        for (GenTableColumn column : genTable.getColumnList()) {
            column.setGenTable(genTable);
            if (StringUtils.isBlank(column.getId())) {
                column.preInsert();
                genTableColumnDao.insert(column);
            } else {
                column.preUpdate();
                genTableColumnDao.update(column);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(GenTable genTable) {
        genTableDao.delete(genTable);
        genTableColumnDao.deleteByGenTableId(genTable.getId());
    }

    /**
     * 获取列
     * @param tableId
     * @return
     */
    public List<GenTableColumn> getColumns(String tableId) {
        GenTableColumn genTableColumn = new GenTableColumn();
        GenTable genTable = new GenTable();
        genTable.setId(tableId);
        genTableColumn.setGenTable(genTable);
        return genTableColumnDao.findAllList(genTableColumn);
    }

    /**
     * 获取列
     * @param tableId
     * @return
     */
    public List<GenTableColumn> getColumnsByName(String tableName) {
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> tables = genTableDao.findList(genTable);
        if (tables.size() <= 0) {
            return null;
        }
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(tables.get(0));
        return genTableColumnDao.findAllList(genTableColumn);
    }

    /**
     * 获取列描述
     * @param tableId 表名
     * @param name 列明 如 code
     * @return
     */
    public String getColumnComment(String tableId, String name) {
        GenTableColumn genTableColumn = new GenTableColumn();
        GenTable genTable = new GenTable();
        genTable.setId(tableId);
        genTableColumn.setName(name);
        genTableColumn.setGenTable(genTable);
        List<GenTableColumn> genTableColumns = genTableColumnDao.findAllList(genTableColumn);
        if (genTableColumns.size() > 0) {
            return genTableColumns.get(0).getComments();
        }
        return null;
    }

    /**
     * 获取表描述
     * @param tableName
     * @return
     */
    public String getTableComment(String tableName) {
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> genTables = genTableDao.findList(genTable);
        if(genTables.size()<=0){
            return null;
        }
        return genTables.get(0).getComments();
    }

}
