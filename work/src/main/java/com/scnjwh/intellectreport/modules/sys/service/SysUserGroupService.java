/**
 * Copyright &copy; 2017.
 */
package com.scnjwh.intellectreport.modules.sys.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.service.TreeService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.sys.entity.SysUserGroup;
import com.scnjwh.intellectreport.modules.sys.dao.SysUserGroupDao;

/**
 * 组维护Service
 * @author hzl
 * @version 2017-07-12
 */
@Service
@Transactional(readOnly = true)
public class SysUserGroupService extends TreeService<SysUserGroupDao, SysUserGroup> {

	public SysUserGroup get(String id) {
		return super.get(id);
	}
	
	public List<SysUserGroup> findList(SysUserGroup sysUserGroup) {
		if ( sysUserGroup!=null){
			if(StringUtils.isBlank( sysUserGroup.getParentIds()))
				 sysUserGroup.setParentIds("%");
			else
			 sysUserGroup.setParentIds( sysUserGroup.getParentIds()+"%");
		return dao.findByParentIdsLike(sysUserGroup);
		}
		return new ArrayList<SysUserGroup>();
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserGroup sysUserGroup) {
	 if (StringUtils.isBlank(sysUserGroup.getId())) {
			super.save(sysUserGroup);
	 }
	 else{
		 
			super.save(sysUserGroup);
	 }
	 
		
	}
	
	@Transactional(readOnly = false)
	public String saveOfGroup(SysUserGroup sysUserGroup) {
	 if (StringUtils.isBlank(sysUserGroup.getId())) {
			super.save(sysUserGroup);
			 return "保存组成功";
	 }
	 else{		 
			super.save(sysUserGroup);
			 return "保存组成功";
	 }
	 
		
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserGroup sysUserGroup) {
		super.delete(sysUserGroup);
	}
	
    @Transactional(readOnly = false)
	public void batchDeleteByIds(String[] ids){
        super.batchDelete(ids);
       // return "批量删除规则定义成功";
    }
	
}