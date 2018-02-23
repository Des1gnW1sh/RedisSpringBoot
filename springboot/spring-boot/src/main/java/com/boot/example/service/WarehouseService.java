package com.boot.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.example.dao.WarehouseMapper;
import com.boot.example.entity.Warehouse;
import com.github.pagehelper.PageHelper;

@Service
public class WarehouseService {
	
	@Autowired
	WarehouseMapper mapper;
	
	/**
	 * 分页查询
	 * @return
	 */
	public Object getList(){
		PageHelper.startPage(1,2); 
		return mapper.getList();
	}

	/**
	 * change（测试事务是否正常）
	 * @return
	 */
	@Transactional
	public Object change() {
		
		Warehouse data = mapper.getById("14190df02175493eb036897de1d56aa3");
		
		System.out.println(data.toString());
		
		Warehouse entity = new Warehouse();
		entity.setId("14190df02175493eb036897de1d56aa3");
		entity.setDelStatus(1);
		
		mapper.update(entity);
		
		/**
		 * 测试事务回滚
		 */
		throw new RuntimeException("boom ~ ~ ~  出车祸了 ");
		
	}

	/**
	 * 存取缓存
	 * @param id
	 * @return
	 */
	@Cacheable(value="warehouseCache")
	public Object getById(String id) {
		
		System.out.println("--------------comeing getById()");
		
		return mapper.getById(id);
	}
	
}
