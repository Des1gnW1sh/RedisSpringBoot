/**
 * Copyright (c) 2015 - 2016 elabcare Inc.
 * All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * OA办公首页
 * @create pengjunhao
 * @createDate 2017年6月9日 下午4:03:11
 * @update 
 * @updateDate 
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/oaHome")
public class OaHomeController {
    
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "modules/sys/desktop";
    }

}
