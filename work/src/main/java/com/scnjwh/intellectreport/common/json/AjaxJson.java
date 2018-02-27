/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.json;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scnjwh.intellectreport.common.mapper.JsonMapper;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {

	private boolean success = true;// 是否成功
	private String errorCode = "-1";// 错误代码
	private String msg = "操作成功";// 提示信息
	private LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();// 封装json的map

	public LinkedHashMap<String, Object> getBody() {
		return body;
	}

	public void setBody(LinkedHashMap<String, Object> body) {
		this.body = body;
	}

	public AjaxJson put(String key, Object value) {// 向json中添加属性，在js中访问，请调用data.map.key
		body.put(key, value);
		return this;
	}

	public AjaxJson remove(String key) {
		body.remove(key);
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public AjaxJson setMsg(String msg) {// 向json中添加属性，在js中访问，请调用data.msg
		this.msg = msg;
		if (msg.indexOf("成功") == -1) {
			this.success = false;
		}
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public AjaxJson setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@JsonIgnore
	// 返回对象时忽略此属性
	public String getJsonStr() {// 返回json字符串数组，将访问msg和key的方式统一化，都使用data.key的方式直接访问。

		String json = JsonMapper.getInstance().toJson(this);
		return json;
	}

	public AjaxJson setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
