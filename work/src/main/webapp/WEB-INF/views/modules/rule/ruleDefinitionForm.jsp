		<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {			
			$("#inputForm").validate({
				submitHandler: function(form){
					setConent();
					if(!checkDetails()){
						$.jBox.tip('请完整填写条件');
						return false;
					}
					loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
						success:function(v){
							closeLoading();
							if(v.success){
								top.showTip(v.msg);
								parent.location.reload();
								window.parent.window.jBox.close();
							}else{
								showTip(v.msg,"error");
							}
							return false;
						}
				    });
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			var functionId = "${ruleDefinition.functionId}"
			if(functionId!=null&&functionId!=""){
				$.ajax({
			        url: "${ctx}/func/functionDataRelation/getTable?functionId="+functionId,
			        success: function (data) {
			        	allTable=data;
			        }
		        })
			}
			
			//修改读取树值
			updateZtree();
			var length = 	$("#length").val();
			if(length!=0){
				index = length-1;
			}
		});
		
		function checkDetails(){
			for(var i=-1;i<index;i++){
				var a= i+1;
				var condition = $("#condition"+a)
				var lengthC = $("#condition"+a).length;
				if(lengthC<=0){
					continue;
				}
				var value = condition.val();
				if(value==""||value==null){
					return false;
					break;
				}
			}
			return true;
		}
		
		var allTable;
		function getAllTable(){
			var functionId = $("#functionIdId").val()
			$.ajax({
		        url: "${ctx}/func/functionDataRelation/getTable?functionId="+functionId,
		        success: function (data) {
		        	allTable=data;
		        	for(var i=-1;i<index;i++){
			        	var a = i+1;
			        	var html ="<option></option>";
			       		 for (var e = 0; e < data.length; e++) {
			       			 var detail = data[e];
			       			 html +="<option value="+detail.retinueTableId+">"+detail.retinueTableName+"</option>";
			       		 }
			 			$("#select"+a).empty();
			       		$("#select"+a).append(html)
			       		$("#s2id_select"+a).children("a").children(".select2-chosen").html("")
			       		$("#selectDetail"+a).empty();
			       		$("#s2id_selectDetail"+a).children("a").children(".select2-chosen").html("")
			       		$("#s2id_selectFh"+a).children("a").children(".select2-chosen").html("")
			       		$("#filtration"+a).val("");
			       		html="<option></option>";
			        }
		        }
	        })
		}
		
		function setAlltable(tableIndex){
			if(allTable!=null && allTable!="" && allTable !="undefined"){
				if(allTable.length>0){
		        	var html ="<option></option>";
		       		for (var e = 0; e < allTable.length; e++) {
		       			 var detail = allTable[e];
		       			 html +="<option value="+detail.retinueTableId+">"+detail.retinueTableName+"</option>";
		       		}
		 			$("#select"+tableIndex).empty();
		       		$("#select"+tableIndex).append(html)
				}
			}
		}
			
		
		var index = 0;
		function addbq() {
			
			index++;
			var nowIndex = index-1
			var showBq= index+1;
			$("#tjbq").children().attr("class","titileDiv-noRead")
			var html = "<div class='titileDiv' id='tj"+index+"' onclick='show("+index+")'>"+
						"条件"+showBq+"<img src='${ctxStatic}/images/remove.png' class='imgRemove' onclick='remove("+index+")'>"+
						"</div>"
			$("#tjbq").append(html)
			$('#allTjDiv').children().attr("class","tjDiv_display")
			var divHtml = "<div class='tjDiv' id='tjd"+index+"'>"+
							"<div class='control-group'>"+
								"<label class='control-label'>条件规则：</label>"+
								"<div class='controls'>"+
									"<c:set var='conds' value="${fns:getDictList('condition_condition')}" />"+
									"<c:if test='${!empty conds}'>"+
										"<c:forEach  items ='${conds}' var='cond' varStatus='eNub'>"+
											"<input name='danxuan"+index+"' type='radio' value='${cond.value}' onchange='radioFun("+index+")'/>${cond.label} "+
										"</c:forEach>"+
									"</c:if>"+
									"<input name='ruleConditions["+index+"].condition' style='display:none;' id='condition"+index+"' value=''>"+
									"<span class='help-inline'><font color='red'>*</font> </span>"+
								"</div>"+
							"</div>"+
							
							"<div class='control-group'>"+
								"<label class='control-label'>条件表：</label>"+
								"<div class='controls'>"+
									"<select id='select"+index+"' style='width: 50%;' onchange='selectFun("+index+")'>"+
										"<option></option>"+
									"</select>"+
									"<input name='ruleConditions["+index+"].tableId' style='display:none;' id='tableId"+index+"' value=''>"+
									"<span class='help-inline'><font color='red'>*</font> </span>"+
								"</div>"+
							"</div>"+
							"<div class='control-group'>"+
								"<label class='control-label'>条件列：</label>"+
								"<div class='controls'>"+
									"<select id='selectDetail"+index+"' style='width: 50%;' onchange='selectDetailFun("+index+")'>"+	
									"</select>"+
									"<input name='ruleConditions["+index+"].tableColumn' style='display:none;' id='tableColumn"+index+"' value=''>"+
									"<input name='ruleConditions["+index+"].dictName' style='display:none;' id='dictName"+index+"' value=''>"+
									"<span class='help-inline'><font color='red'>*</font> </span>"+
								"</div>"+
							"</div>"+
							"<div class='control-group' id='fh"+index+"'>"+
								"<label class='control-label'>符号：</label>"+
								"<div class='controls'>"+
									"<select id='selectFh"+index+"' style='width: 50%;' onchange='selectFiltrationFun("+index+")'>"+
										"<option></option>"+
										"<c:set var='filtrations' value="${fns:getDictList('condition_filtration')}" />"+
										"<c:if test='${!empty filtrations}'>"+
											"<c:forEach  items ='${filtrations}' var='filtration' varStatus='fNub'>"+
												"<option value='${filtration.value}'>${filtration.label}</option>"+
											"</c:forEach>"+
										"</c:if>"+
									"</select>"+
									"<input name='ruleConditions["+index+"].filtration' style='display:none;' id='filtration"+index+"' value=''>"+
									"<span class='help-inline'><font color='red'>*</font> </span>"+
								"</div>"+
							"</div>"+
							"<div class='control-group' id='nr"+index+"'>"+
								"<label class='control-label'>内容：</label>"+
								"<div class='controls'>"+
									"<input name='ruleConditions["+index+"].content' class='inputCon' id='content"+index+"' value=''>"+
									"<span class='help-inline'><font color='red'>*</font> </span>"+
								"</div>"+
							"</div>"+
							"<div class='control-group'>"+
								"<div class='controls'>"+
									"<div id='zidian"+index+"' class='ztree' style='margin-top:3px;float:left;'></div>"+
								"</div>"+
							"</div>"+
						  "</div>"
			$('#allTjDiv').append(divHtml)	
			setAlltable(index)
			//所有下拉框使用select2
			$("select").select2({
				allowClear: true//允许清空
			});
		}
		
		//点击标签获取下面div的显示
		function show(showIndex) {
			if(showIndex==removeEdIndex){
				//排除已经被删除掉的
				return false;
			}
			$('#allTjDiv').children().attr("class","tjDiv_display")
			$("#tjd"+showIndex).attr("class","tjDiv")
			if($("#tjd"+showIndex).length==0){
				var id = $("div.tjDiv_display:last").attr("id");
				if(id==""||id=="undefind"||id==null){
					id = $("div.tjDiv:last").attr("id");
				}
				$("#"+id).attr("class","tjDiv")
			}
			$("#tjbq").children().attr("class","titileDiv-noRead")
			$("#tj"+showIndex).attr("class","titileDiv")
		}
		
		
		var removeEdIndex;
		//删除
		function remove(removeIndex){
			removeEdIndex = removeIndex;
			var length = $("div.titileDiv").length;
			var noReadLength = $("div.titileDiv-noRead").length;
			var allLength = length+noReadLength;
			if(allLength>1){
				$("#tj"+removeIndex).remove();
				$("#tjd"+removeIndex).remove();
				var id = $("div.tjDiv_display:last").attr("id");
				if(id==""||id=="undefind"||id==null){
					id = $("div.tjDiv:last").attr("id");
				}
				/* $("#"+id).attr("class","tjDiv") */
				var indexBq = id.replace(/[^0-9]/ig,"");
				show(indexBq)
			}else{
				$.jBox.tip('必须保留一个条件');				
			}
		}
		
		//条件规则选择放入input
		function radioFun(radioIndex) {
			var name = "danxuan"+radioIndex
			var radioValue  = $("input[name='"+name+"']:checked").val();
			$("#condition"+radioIndex).val(radioValue)
		}
		
		//条件表 选择触发的事件
		function selectFun(selectIndex) {
			var id = "select"+selectIndex
			var selected= $("#"+id).find("option:selected")
			var text = selected.html(); // 选中文本
			var value = selected.val(); // 选中值
			$("#tableId"+selectIndex).val(value);
			$("#selectDetail"+selectIndex).empty()
			$("#s2id_selectDetail"+selectIndex).children("a").children(".select2-chosen").html("")
			$("#s2id_selectFh"+selectIndex).children("a").children(".select2-chosen").html("")
			$("#filtration"+selectIndex).val("");
			$("#dictName"+selectIndex).val("")
			$("#fh"+selectIndex).show();
			$("#nr"+selectIndex).show();
			$("#zidian"+selectIndex).hide();
			//获取列
			 $.ajax({
		        url: "${ctx}/gen/genTable/getColumnsByName?tableName="+value,
		        success: function (data) {
			        if(data.length>0){
			        	var html = "<option></option>";
			        	for (var a = 0; a < data.length; a++) {
							var enableDetail = data[a];
							var type = enableDetail.dictType;
							if(type==null || type ==""){
								type = "1"
							}
							html +="<option value='"+enableDetail.name+"' type='"+type+"'>"+enableDetail.comments+"</option>"
						}
			        	$("#selectDetail"+selectIndex).append(html);
			        }
		        }
			 })
		}
		
		//条件列触发的方法
		function selectDetailFun(selectDetailIndex) {
			var id = "selectDetail"+selectDetailIndex
			var selected= $("#"+id).find("option:selected")
			var text = selected.html(); // 选中文本
			var value = selected.val(); // 选中值
			var type = selected.attr("type");
			$("#tableColumn"+selectDetailIndex).val(value)
			if(type!="1"){
				$("#dictName"+selectDetailIndex).val(type)
				$("#fh"+selectDetailIndex).hide();
				$("#nr"+selectDetailIndex).hide();
				$("#zidian"+selectDetailIndex).show();
				$("#filtration"+selectDetailIndex).val("");
				var setting = {
						check: {
							enable: true,
							chkStyle: "checkbox",
							chkboxType: { "Y" : "ps", "N" : "s" }
						},
						data: {
							simpleData: {
								enable: true
							}
						}
					};
					var dataZtree;
					$.ajax({
				        url: "${ctx}/rule/ruleEnableDetail/treeData?dictName="+type,
				        success: function (data)
				        {
				        	var zNodes =data;
	      					// 初始化树结构
	      					var tree = $.fn.zTree.init($("#zidian"+selectDetailIndex), setting, zNodes);
	      					// 默认选择节点
	      					/* var ids = "${role.officeIds}".split(",");
	      					for(var i=0; i<ids2.length; i++) {
	      						var node = tree2.getNodeByParam("id", ids2[i]);
	      						try{tree2.checkNode(node, true, false);}catch(e){}
	      					} */
	      					// 默认展开全部节点
	      					tree.expandAll(true);
				        }
				     });
			}else{
				$("#dictName"+selectDetailIndex).val("")
				$("#fh"+selectDetailIndex).show();
				$("#nr"+selectDetailIndex).show();
				$("#zidian"+selectDetailIndex).hide();
				$("#filtration"+selectDetailIndex).val("");
				var treeName = "zidian"+selectDetailIndex;
				var treeObj=$.fn.zTree.getZTreeObj(treeName)
				if(treeObj!=null){
		            //选中节点  
		            var nodes=treeObj.getCheckedNodes(true)  
		            for (var i=0, l=nodes.length; i < l; i++)   
		            {  
		                //删除选中的节点  
		                treeObj.removeNode(nodes[i]);  
		            }  
				}
			}
		}
		
		//符号选择触发的方法
		function selectFiltrationFun(filtrationIndex) {
			var id = "selectFh"+filtrationIndex
			var selected= $("#"+id).find("option:selected")
			var text = selected.html(); // 选中文本
			var value = selected.val(); // 选中值
			$("#filtration"+filtrationIndex).val(value);
		}	
		
		//设置内容
		function setConent() {
			for(var i = -1; i<index;i++){
				var indexS= i + 1;
				var treeName = "zidian"+indexS;
				var treeObj=$.fn.zTree.getZTreeObj(treeName)
				if(treeObj!=null){
	            var nodes=treeObj.getCheckedNodes(true)
		            if(nodes.length>0){
		            	var dicsIds ="";
		     			for(var i=0;i<nodes.length;i++){
		     				if(nodes[i].id!="-1"){
		     					dicsIds+=nodes[i].id + ",";
		     				}
		     		    }
		     			$("#content"+indexS).val(dicsIds)
		            }
				}
			}
		}
		
		var indd;
		function updateZtree(){
		 	var length = 	$("#length").val();
		 	for(var i =0 ; i<length;i++){
		 		var filtration = $("#filtration"+i).val()
		 		if(filtration==""||filtration==null){
		 			indd =i 
		 			var checkNow = $("#content"+i).val().split(",");
		 			var type = $("#dictName"+i).val()
		 			var setting = {
							check: {
								enable: true,
								chkStyle: "checkbox",
								chkboxType: { "Y" : "ps", "N" : "s" }
							},
							data: {
								simpleData: {
									enable: true
								}
							}
						};
						var dataZtree;
						$.ajax({
					        url: "${ctx}/rule/ruleEnableDetail/treeData?dictName="+type,
					        success: function (data)
					        {
					        	var zNodes =data;
					        	var ztreeId = "zidian"+indd
		      					// 初始化树结构
		      					var tree = $.fn.zTree.init($("#"+ztreeId), setting, zNodes);
		      					// 默认选择节点
		      					for(var i=0; i<checkNow.length; i++) {
		      						var node = tree.getNodeByParam("id", checkNow[i]);
		      						try{tree.checkNode(node, true, false);}catch(e){}
		      					} 
		      					// 默认展开全部节点
		      					tree.expandAll(true);
					        }
					     }); 
		 		}
		 	}
		}
		
	</script>
	<style type="text/css">
	.form-actions{
		display: none;
	}
	.tjDiv{
		width: 100%;
		border: 1px solid #ccc;
		float: left;
		border-radius:0 5px 5px 5px;
	}
	.tjDiv_display{
		width: 100%;
		height: 300px;
		border: 1px solid #ccc;
		float: left;
		display:none;
	}
	.allDiv{
		width: 60%;
		margin-left: 100px;
	}
	.bqDiv{
		width: 100%;
		float: left;
	}
	.titileDiv{
		width: 15%;
		height: 30px;
		border: 1px solid #ccc;
		font-size: 15px;
		text-align: left;
		padding-top: 5px;
		float: left;
		border-bottom: medium none;
		background-color: #ccc;
	}
	.titileDiv-noRead{
		width: 15%;
		height: 30px;
		border: 1px solid #ccc;
		font-size: 15px;
		text-align: left;
		padding-top: 5px;
		float: left;
		border-bottom: medium none;
	}
	.addDiv{
		width: 10%;
		height: 30px;
		font-size: 15px;
		text-align: center;
		padding-top: 5px;
		float: left;
	}
	.imgAdd{
		
    width: 25px;
		
	}
	.imgRemove{
    	width: 18px;
    	float: right;
    	margin-top: 2px;
	}
	
	.inputCon{
		border: 1px solid #ccc;
		height: 28px;
		border-radius:5px;
	}
	</style>
</head>
<body>

	<form:form id="inputForm" modelAttribute="ruleDefinition" action="${ctx}/rule/ruleDefinition/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">规则类型：</label>
			<div class="controls">
				<sys:treeselect id="typeId" name="typeId" value="${ruleDefinition.typeId}" labelName="" labelValue="${ruleDefinition.typeName}"
						title="类型" url="/rule/ruleType/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">功能模块：</label>
			<div class="controls">
				<sys:treeselect id="functionId" name="functionId" value="${ruleDefinition.functionId}" labelName="" labelValue="${ruleDefinition.functionName}"
						title="功能模块" url="/func/functionDataRelation/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true" submitFunction="getAllTable"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:set var="details" value="${ruleDefinition.ruleConditions}" />
		<c:set var="length" value="${fn:length(details)}"/>
		<input id="length" value="${length}" style="display: none"/>
		<c:if test="${!empty details}">
		<div class="allDiv">
			<div class="bqDiv">
				<div id="tjbq">
					<c:forEach  items ="${details}" var='detail' varStatus="sNub">
						<!-- 标签头 -->
						<c:if test="${sNub.index==0}">
							<div class="titileDiv" id="tj${sNub.index}" onclick="show(${sNub.index})">
								条件${sNub.index+1}<img src="${ctxStatic}/images/remove.png" class="imgRemove" onclick="remove(0)">
							</div>
						</c:if>
						<c:if test="${sNub.index!=0}">
							<div class="titileDiv-noRead" id="tj${sNub.index}" onclick="show(${sNub.index})">
								条件${sNub.index+1}<img src="${ctxStatic}/images/remove.png" class="imgRemove" onclick="remove(0)">
							</div>
						</c:if>
					</c:forEach>
				</div>
				<!-- 图标+ -->
				<div class="addDiv" id="tj${sNub.index}" onclick="addbq()">
					<img src="${ctxStatic}/images/add.png" class="imgAdd">
				</div>
			</div>
			<div id="allTjDiv">
				<c:forEach  items ="${details}" var='detail' varStatus="sNub">
					<c:if test="${sNub.index==0}">
					<div class="tjDiv" id="tjd${sNub.index}">
					</c:if>
					<c:if test="${sNub.index!=0}">
					<div class="tjDiv_display" id="tjd${sNub.index}">
					</c:if>
						<div class="control-group">
							<label class="control-label">条件规则：</label>
							<div class="controls">
								<c:set var="conds" value="${fns:getDictList('condition_condition')}" />
								<c:if test="${!empty conds}">
									<c:set var="condition" value="${detail.condition}"/>
									<c:forEach  items ="${conds}" var='cond' varStatus="eNub">
										<c:set var="value" value="${cond.value}"/>
										<c:if test="${condition==value}">
											<input name="danxuan${sNub.index}" type="radio" value="${cond.value}" onchange="radioFun(${sNub.index})" checked="checked"/>${cond.label}
										</c:if>
										<c:if test="${condition!=value}">
											<input name="danxuan${sNub.index}" type="radio" value="${cond.value}" onchange="radioFun(${sNub.index})"/>${cond.label} 
										</c:if>
									</c:forEach>
								</c:if>
								<input name="ruleConditions[${sNub.index}].condition" style="display:none;" id="condition${sNub.index}" value="${detail.condition}">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						<!-- 条件表 -->
						<div class="control-group">
							<label class="control-label">条件表：</label>
							<div class="controls">
								<select id="select${sNub.index}" style="width: 50%;" onchange="selectFun(${sNub.index})">
									<option value="${detail.tableId}">${detail.tableName}</option>
								</select>
								<input name="ruleConditions[${sNub.index}].tableId"  style="display:none;" id="tableId${sNub.index}" value="">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						<!-- 条件列 -->
						<div class="control-group">
							<label class="control-label">条件列：</label>
							<div class="controls">
								<select id='selectDetail${sNub.index}' style='width: 50%;' onchange='selectDetailFun(${sNub.index})'>
									<option value="${detail.tableColumn}">${detail.tableColumnName}</option>
								</select>
								<input name="ruleConditions[${sNub.index}].tableColumn" style="display:none;" id="tableColumn${sNub.index}" value="${detail.tableColumn}">
								<input name="ruleConditions[${sNub.index}].dictName" style="display:none;" id="dictName${sNub.index}" value="${detail.dictName}">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						<c:set var="dictName" value="${detail.dictName}"/>
						<c:if test="${!empty dictName}">
							<!-- 显示树 -->
							<!-- 符号 -->
							<div class="control-group" id="fh${sNub.index}" style="display: none;">
								<label class="control-label">符号：</label>
								<div class="controls">
									<select id="selectFh${sNub.index}" style="width: 50%;" onchange="selectFiltrationFun(${sNub.index})">
										<option value="${detail.filtration}">${detail.filtrationName}</option>
										<c:set var="filtrations" value="${fns:getDictList('condition_filtration')}" />
										<c:if test="${!empty filtrations}">
											<c:forEach  items ="${filtrations}" var='filtration' varStatus="fNub">
												<option value="${filtration.value}">${filtration.label}</option>
											</c:forEach>
										</c:if>
									</select>
									<input name="ruleConditions[${sNub.index}].filtration"  style="display:none;" id="filtration${sNub.index}" value="${detail.filtration}">
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</div>
							
							<!-- 内容 -->
							<div class="control-group" id="nr${sNub.index}" style="display: none;">
								<label class="control-label">内容：</label>
								<div class="controls">
									<input name="ruleConditions[${sNub.index}].content" id="content${sNub.index}" value="${detail.content}" class="inputCon">
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</div>
							
							<!-- 字典 -->
							<div class="control-group">
								<div class="controls">
									<div id="zidian${sNub.index}" class="ztree" style="margin-top:3px;float:left;"></div>
								</div>
							</div>
						</c:if>
						<c:if test="${empty dictName}">
							<!-- 显示符号和内容 -->
							<!-- 符号 -->
							<div class="control-group" id="fh${sNub.index}">
								<label class="control-label">符号：</label>
								<div class="controls">
									<select id="selectFh${sNub.index}" style="width: 50%;" onchange="selectFiltrationFun(${sNub.index})">
										<option value="${detail.filtration}">${detail.filtrationName}</option>
										<c:set var="filtrations" value="${fns:getDictList('condition_filtration')}" />
										<c:if test="${!empty filtrations}">
											<c:forEach  items ="${filtrations}" var='filtration' varStatus="fNub">
												<option value="${filtration.value}">${filtration.label}</option>
											</c:forEach>
										</c:if>
									</select>
									<input name="ruleConditions[${sNub.index}].filtration"  style="display:none;" id="filtration${sNub.index}" value="${detail.filtration}">
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</div>
							
							<!-- 内容 -->
							<div class="control-group" id="nr${sNub.index}">
								<label class="control-label">内容：</label>
								<div class="controls">
									<input name="ruleConditions[${sNub.index}].content" id="content${sNub.index}" value="${detail.content}" class="inputCon">
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</div>
							
							<div class="control-group" style="display: none;">
								<div class="controls">
									<div id="zidian${sNub.index}" class="ztree" style="margin-top:3px;float:left;"></div>
								</div>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
			
		</c:if>
		<c:if test="${empty details}">
			<div class="allDiv">
				<div class="bqDiv">
					<!-- 标签头 -->
					<div id="tjbq">
						<div class="titileDiv" id="tj0" onclick="show(0)">
							条件1<img src="${ctxStatic}/images/remove.png" class="imgRemove" onclick="remove(0)">
						</div>
					</div>
					<!-- 图标+ -->
					<div class="addDiv" id="tj0" onclick="addbq()">
						<img src="${ctxStatic}/images/add.png" class="imgAdd">
					</div>
				</div>
				<div id="allTjDiv">
					
					<!-- 条件规则 -->
					<div class="tjDiv" id="tjd0">
						<div class="control-group">
							<label class="control-label">条件规则：</label>
							<div class="controls">
								<c:set var="conds" value="${fns:getDictList('condition_condition')}" />
								<c:if test="${!empty conds}">
									<c:forEach  items ="${conds}" var='cond' varStatus="eNub">
										<input name="danxuan0" type="radio" value="${cond.value}" onchange="radioFun(0)"/>${cond.label} 
									</c:forEach>
								</c:if>
								<input name="ruleConditions[0].condition" style="display:none;" id="condition0" value="">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						<!-- 条件表 -->
						<div class="control-group">
							<label class="control-label">条件表：</label>
							<div class="controls">
								<select id="select0" style="width: 50%;" onchange="selectFun(0)">
									<option></option>
								</select>
								<input name="ruleConditions[0].tableId"  style="display:none;" id="tableId0" value="">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
							
						<!-- 条件列 -->
						<div class="control-group">
							<label class="control-label">条件列：</label>
							<div class="controls">
								<select id='selectDetail0' style='width: 50%;' onchange='selectDetailFun(0)'>
									
								</select>
								<input name="ruleConditions[0].tableColumn" style="display:none;" id="tableColumn0" value="">
								<input name="ruleConditions[0].dictName" style="display:none;" id="dictName0" value="">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						
						<!-- 符号 -->
						<div class="control-group" id="fh0">
							<label class="control-label">符号：</label>
							<div class="controls">
								<select id="selectFh0" style="width: 50%;" onchange="selectFiltrationFun(0)">
									<option></option>
									<c:set var="filtrations" value="${fns:getDictList('condition_filtration')}" />
									<c:if test="${!empty filtrations}">
										<c:forEach  items ="${filtrations}" var='filtration' varStatus="fNub">
											<option value="${filtration.value}">${filtration.label}</option>
										</c:forEach>
									</c:if>
								</select>
								<input name="ruleConditions[0].filtration"  style="display:none;" id="filtration0" value="">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						<!-- 内容 -->
						<div class="control-group" id="nr0">
							<label class="control-label">内容：</label>
							<div class="controls">
								<input name="ruleConditions[0].content" id="content0" value="" class="inputCon">
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						
						<!-- 字典 -->
						<div class="control-group">
							<div class="controls">
								<div id="zidian0" class="ztree" style="margin-top:3px;float:left;"></div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</c:if>
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="rule:ruleDefinition:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>