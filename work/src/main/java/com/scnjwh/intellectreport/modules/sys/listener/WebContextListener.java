package com.scnjwh.intellectreport.modules.sys.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.scnjwh.intellectreport.modules.sys.service.SystemService;

import org.springframework.web.context.ContextLoaderListener;

public class WebContextListener extends  ContextLoaderListener{

	@Override
	public WebApplicationContext initWebApplicationContext(
			ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()) {
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
