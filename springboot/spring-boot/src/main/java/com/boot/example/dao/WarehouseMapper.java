package com.boot.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.example.entity.Warehouse;

@Mapper
public interface WarehouseMapper {
	List<Warehouse> getList();
	
	Integer update(@Param("warehouse") Warehouse entity);
	
	Warehouse getById(@Param("id") String id);
	
}
