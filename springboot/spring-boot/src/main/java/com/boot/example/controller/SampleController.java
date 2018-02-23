package com.boot.example.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping(value="/sample")
public class SampleController {
	
	@RequestMapping(value="/talk")
	@ResponseBody
	public Object talkAbout(){
		String [] strs = {"abc","123","def","456"};
		return Arrays.asList(strs);
	}
	
	
	@RequestMapping(value="/getMap")
	@ResponseBody
	public Object getMap(HttpServletRequest request, String callback){
		Map<String,String> result = new HashMap<String,String>();
		result.put("aaa", "111");
		result.put("bbb", "222");
		result.put("ccc", "333");
		result.put("ddd", "444");
		
		Gson gson = new Gson();
		String json =callback +"("+gson.toJson(result)+")";
		return json;
		
	}
	
	@RequestMapping(value = "authCode")
	@ResponseBody
	public String getMobileAuthCode( HttpServletRequest request, String callback)
	        throws Exception {
	    String result =  "{'ret':'true'}";
	    //加上返回参数
	    result=callback+"("+result+")";
	   return result;
	}
	
	
}
