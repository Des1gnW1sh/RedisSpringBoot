/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.supcan.freeform;

import com.scnjwh.intellectreport.common.supcan.common.Common;
import com.scnjwh.intellectreport.common.supcan.common.properties.Properties;
import com.scnjwh.intellectreport.common.supcan.common.Common;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 硕正FreeForm
 * 
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("FreeForm")
public class FreeForm extends Common {

	public FreeForm() {
		super();
	}

	public FreeForm(Properties properties) {
		this();
		this.properties = properties;
	}

}
