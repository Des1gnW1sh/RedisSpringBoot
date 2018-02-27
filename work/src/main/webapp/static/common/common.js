/**
 * 获取jqGride选中列
 */
function getSelecteds(){  
	var ids = $("#dataGrid").jqGrid("getGridParam", "selarrrow");
	/* $(ids).each(function (index, id){  
		var row = $("#dataGrid").jqGrid('getRowData',id);  
		alert("row.ID:"+row.ID+"  "+"row.fieldName:"+row.fieldName);  
	});
	alert(ids); */
	return ids;
}
/**
 * 自定义公共组件
 * timo
 */
/**
 * 选中select
 * @param radio_oj  例：$("select[name=abrasion] option")
 * @param aValue
 */
function changeSelect_oj(radio_oj,aValue){//传入一个对象
   for(var i=0;i<radio_oj.length;i++) {//循环
        if(radio_oj[i].value==aValue){  //比较值
            radio_oj[i].selected=true; //修改选中状态
            break; //停止循环
        }
   }
}
/**
 * 内容 新增修改
 * @param url
 * @param text
 * @param width
 * @param height
 */
function contentSave(url,text,width,height){
	$.jBox.open('iframe:'+url,text,width,height,{
		/* iframeScrolling:'no', */
		dragLimit: true,
		showScrolling: true,			
		persistent: true,
		opacity: 0.5, 
		top:'1%',
		loaded: function (h) {
		    $("#jbox-content").css("overflow-y","hidden");
		}, 
		submit : function (v, h, f) {
			$("#jbox-iframe").contents().find("#btnSubmit").click();
		  	return false;
		}
	});	
}

/**
 * 修改
 * @param url 路径
 * @param text 标题
 * @param width 宽度
 * @param height  高度
 * @returns {Boolean}
 */
function contentUpdate(url,text,width,height){
	if(url.split("?id=")[1]==null || url.split("?id=")[1]==""){
		numbercheck();
		var obj = $("#dataGrid");
		if(obj.html()!=undefined){
			//	jqgrid
			var ids = getSelecteds();
			if(ids.length==0){
				$.jBox.tip('请选择一条数据');
				 return false;
			}else if(ids.length>1){
				$.jBox.tip('只能单独读一条数据修改');
				 return false;
			}
			url += ids;
		}else{
			//针对自己封装的gride多选
			if(url.indexOf("?id=")!=-1){
				//需要获取序列
				if(number_checkNum===0){
					 $.jBox.tip('请选择一条数据');
					 return false;
				}
				if(number_checkNum>1){
					 $.jBox.tip('只能单独读一条数据修改');
					 return false;
				}
				url += number_checkId;
			}
		}
	}
	$.jBox.open('iframe:'+url,text,width,height,{
		dragLimit: true,
		showScrolling: true,				
		persistent: true,
		opacity: 0.5, 
		top:'1%',
		loaded: function (h) {
		    $("#jbox-content").css("overflow-y","hidden");
		}, 
		submit : function (v, h, f) {
			$("#jbox-iframe").contents().find("#btnSubmit").click();
		  	return false;
		}
	});			
}



function contentAdd(url,text,width,height){
    $.jBox.open('iframe:'+url,text,width,height,{
        dragLimit: true,
        showScrolling: true,
        persistent: true,
        opacity: 0.5,
        top:'1%',
        loaded: function (h) {
            $("#jbox-content").css("overflow-y","hidden");
        },
        submit : function (v, h, f) {
            $("#jbox-iframe").contents().find("#btnSubmit").click();
            return false;
        }
    });
}
/**
 * 删除 
 * 传入路径为${ctx}/inco/incoStructure/deletes?ids=
 * ids 为后台接受数据的参数
 * */
function batchDelete(url){
	numbercheck();
	var obj = $("#dataGrid");
	if(obj.html()!=undefined){
		//	jqgrid
		var ids = getSelecteds();
		if(ids.length==0){
			$.jBox.tip('请选择一条数据');
			 return false;
		}
		url += ids;
	}else{
		if(number_checkNum===0){
			 $.jBox.tip('请选择一条数据');
			 return false;
		}
		url += number_checkId;
	}
	var submit = function (v, h, f) {  
		if (v == true) {  
			  $.ajax({
			    	url:url,
			    	success:function(data){
			    		numberCheckAll();
			    		numberCheckAll();
			    		top.showTip(data.msg);
			    		window.location.reload();
			    	}
			    })   
		}  
		if (v == false) {  
			top.$.jBox.close(true); 
		}else{
			return true; 
		}  
	};  
	// 可根据需求仿上例子定义按钮  
	$.jBox.confirm("确定删除被选中的数据？", "提示", submit ,{ buttons: { '确定': true, '取消': false} });  
}
/**
 * 内容查看
 * @param url
 * @param text
 * @param width
 * @param height
 */
function contentView(url,text,width,height){
	if(url.split("?id=").length==2){
		if(url.split("?id=")[1]==null || url.split("?id=")[1]==""){
			var ids = getSelecteds();
			if(ids.length==0){
				$.jBox.tip('请选择一条数据');
				 return false;
			}else if(ids.length>1){
				$.jBox.tip('只能单独读一条数据修改');
				 return false;
			}
			url += ids;
		}
	}
	$.jBox.open('iframe:'+url,text,width,height,{
		/* iframeScrolling:'no', */
		dragLimit: true,
		showScrolling: true,				
		persistent: true,
		opacity: 0.5,
		buttons: {}, 
		top:'1%',
		loaded: function (h) {
		    $("#jbox-content").css("overflow-y","hidden");
		}
	});	
}
/**
 * 重置form  未完成
 * @param id
 */
function TagQueryRest(id){
	$("#"+id)[0].reset();
	$("#"+id).find(".title-search").find("input").each(function(){
		if($(this).attr("type")=="submit"){
			return;
		}
		$(this).val("");
	});
	$("#"+id+" select").each(function(){
		$(this).val("").trigger('change');
		$(this).select2("val", "").trigger('change');
	});
	$("#"+id).find("input[type=submit]").click();
}


/** 
 * 多选序号 
 * <th><input type="checkbox" id="checkAll" onclick="numberCheckAll()">选择</th>
 * 
 * <tr onclick="trChecked(this)">
 * <td>
 * <input name="num" type="checkbox" value="放入该列id（如${incoStatute.id}）" onclick="oneCheck(this)"/>
 * </td>
 * </tr>
 * 加入列表位置
 * 
 * */
var number_checkId="";
var number_checkNum = 0;
function numbercheck(){
	number_checkId="";
	number_checkNum = 0;
	$('input:checkbox[name="num"]:checked').each(function(i){
		if(i===0){
			number_checkId = $(this).val()
		}else{
			number_checkId += ","+$(this).val()
		}
		number_checkNum++;
	});
}

/**
 * 全选
 * <th><input type="checkbox" id="checkAll" onclick="numberCheckAll()">选择</th>
 */
function numberCheckAll() {
    if ($('#checkAll').attr('checked')) {
        $("[name='num']").prop("checked", 'true');//全选
    } else {
        $("[name='num']").removeAttr("checked");//取消全选
    }
};

/**
 * 点击当前行 选择框选择
 * <tr onclick="trChecked(this)">
 * @param trThis
 */
function trChecked(trThis) {
	//点击当前行选择
	var getCheck = $(trThis).children().find("input[name='num']")
	if (getCheck.attr('checked')) {
		getCheck.removeAttr("checked")
	}else{
		getCheck.prop("checked", 'true');
	}
};
/**
 * 点击选择框 选择触发
 * <input name="num" type="checkbox" value="${incoStructure.id}" onclick="oneCheck(this)"/>
 * @param comBo
 */
function oneCheck(comBo) {
	if ($(comBo).attr('checked')) {
		$(comBo).removeAttr("checked")
	}else{
		$(comBo).prop("checked", 'true');
	}
}

/**
 * 阿拉伯数字转换为中文大写金额
 * @param n
 * @returns
 */
function DX(n) {
	if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)) return "数据非法";
	var unit = "京亿万仟佰拾兆万仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
	n += "00";
	var p = n.indexOf('.');
	if (p >= 0)
	n = n.substring(0, p) + n.substr(p+1, 2);
	unit = unit.substr(unit.length - n.length);	
	for (var i=0; i < n.length; i++) str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);	
	return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(兆|万|亿|元)/g, "$1").replace(/(兆|亿)万/g, "$1").replace(/(京|兆)亿/g, "$1").replace(/(京)兆/g, "$1").replace(/(京|兆|亿|仟|佰|拾)(万?)(.)仟/g, "$1$2零$3仟").replace(/^元零?|零分/g, "").replace(/(元|角)$/g, "$1整");
}