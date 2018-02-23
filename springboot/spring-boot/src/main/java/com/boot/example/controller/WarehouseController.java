package com.boot.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.example.service.WarehouseService;

@RestController
@RequestMapping(value="/warehouse")
public class WarehouseController {
	
	@Autowired
	WarehouseService service;
	
	@RequestMapping(value="/list")
	public Object getList(){
		return service.getList();
	}
	
	@RequestMapping(value="/change")
	public Object change(){
		return service.change();
	}
	
	@RequestMapping(value="/getById")
	public Object getById(){
		String id = "14190df02175493eb036897de1d56aa3";
		return service.getById(id);
	}
	
}
