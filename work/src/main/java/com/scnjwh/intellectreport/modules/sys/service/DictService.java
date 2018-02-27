/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.CacheUtils;
import com.scnjwh.intellectreport.modules.sys.dao.DictDao;
import com.scnjwh.intellectreport.modules.sys.entity.Dict;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

    @Autowired
    private DictDao dictDao;

    /**
     * 查询字段类型列表
     * @return
     */
    public List<String> findTypeList() {
        return dao.findTypeList(new Dict());
    }
    
    /**
     * 查询字段类型列表
     * @return
     */
    public List<Dict> findListType() {
        return dao.findListType(new Dict());
    }
    
    @Transactional(readOnly = false)
    public void save(Dict dict) {
        super.save(dict);
        CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
    }

    @Transactional(readOnly = false)
    public void delete(Dict dict) {
        super.delete(dict);
        CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
    }
    @Transactional(readOnly = false)
    public String deletes(String[] ids) {
        super.batchDelete(ids);
        CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
        return "删除字典成功";
    }

    /**
     * 获取类型树
     * @return
     * @Author : pengjunhao. create at 2017年5月16日 下午3:52:20
     */
    public List<Map<String, Object>> getTreeData() {
        logger.info("【获取类型树】");
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String, Object> pidMap = new HashMap<String, Object>();
        pidMap.put("name", "类型");
        pidMap.put("pId", "0");
        pidMap.put("id", "-1");
        pidMap.put("pIds", "0,");
        maps.add(pidMap);
        Dict dictNew = new Dict();
        List<Dict> dicts = dictDao.findListType(dictNew);
        if (dicts.size() <= 0) {
            return maps;
        }
        for (Dict dict : dicts) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", dict.getTypeName());
            map.put("pId", "-1");
            map.put("id", dict.getType());
            map.put("pIds", "-1,");
            maps.add(map);
        }

        return maps;
    }

}
