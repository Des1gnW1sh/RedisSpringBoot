/**
 * Copyright (c) 2015 - 2016 elabcare Inc.
 * All rights reserved.
 */
package com.scnjwh.intellectreport.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析activit判断列
 * 
 * @create pengjunhao
 * @createDate 2017年4月28日 下午5:00:07
 * @update
 * @updateDate
 */
public class ResolveActivitColumn {

	/**
	 * 解析properties返回的value
	 * 
	 * @param getValue
	 *            properties返回的value
	 * @return
	 * @Author : pengjunhao. create at 2017年4月28日 下午5:01:32
	 */
	public static Map<String, String> resolvePropertiesValue(String getValue) {
		if (StringUtils.isBlank(getValue)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		String[] list = getValue.split(";");
		for (String str : list) {
			String key = str.substring(0, str.indexOf(":"));
			String value = str.substring(str.indexOf(":") + 1);
			map.put(key, value);
		}
		return map;
	}

	public static String[] getUniversalSymbol(String getValue) {
		String[] list = getValue.split(";");
		return list;
	}

	/**
	 * 解析properties返回的value中的column
	 * 
	 * @param getValue
	 *            properties返回的value
	 * @return
	 * @Author : pengjunhao. create at 2017年4月28日 下午5:07:19
	 */
	public static List<String> getColumns(String getValue) {
		if (StringUtils.isBlank(getValue)) {
			return null;
		}
		List<String> columns = new ArrayList<String>();
		String[] list = getValue.split(";");
		for (String str : list) {
			String key = str.substring(0, str.indexOf(":"));
			columns.add(key);
		}
		return columns;
	}

	/***
	 * 获取activiti封装条件的值
	 * 
	 * @param columns
	 *            列
	 * @param object
	 *            对象
	 * @return
	 * @Author : pengjunhao. create at 2017年4月28日 下午5:12:54
	 */
	public static Map<String, Object> getQuery(List<String> columns,
			Object object) {
		if (object == null) {
			return null;
		}
		Map<String, Object> query = new HashMap<String, Object>();
		Class<? extends Object> objectClass = object.getClass();
		Field[] fields = object.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			// 获取参数名
			String fieldName = fields[j].getName();
			if (!columns.contains(fieldName)) {
				continue;
			}
			// 获取方法名
			String methodName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			Method method = null;
			Object obj = null;
			try {
				// 获取值
				method = objectClass.getMethod("get" + methodName);
				obj = method.invoke(object);
			} catch (Exception e) {
				continue;
			}
			if (obj != null) {
				query.put(fieldName, obj);
			}
		}
		// 默认添加同意为0
		query.put("agree", 0);
		return query;
	}

	/**
	 * 获取后台使用的判断条件显示
	 * 
	 * @param symbol
	 *            判断条件
	 * @return
	 * @Author : pengjunhao. create at 2017年5月2日 上午11:47:07
	 */
	public static String getSymbol(String symbol) {
		if (symbol.equals("&lt;")) {
			symbol = "<";
		}
		if (symbol.equals("&gt;")) {
			symbol = ">";
		}
		if (symbol.equals("&lt;=")) {
			symbol = "<=";
		}
		if (symbol.equals("&gt;=")) {
			symbol = ">=";
		}
		return symbol;
	}
}
