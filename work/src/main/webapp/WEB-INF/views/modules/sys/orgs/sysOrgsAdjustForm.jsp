<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调整管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {	
			$.ajax({
			     type:"post",
			     url:"${ctx}/sys.orgs/sysOrgsFiscal/select",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	var opt ="";
		    	  	for(var i in data){
		    	  		opt+="<option value='"+data[i].ID+"'>"+data[i].NAME+"</option>";
		    	    }
		    	  	$("#fiscalType").append(opt);
			     }
			 });
			 
			 var fiscalTypeSelect = "${sysOrgsAdjust.fiscalType.id}"; 
			 if(fiscalTypeSelect!=""){
				 $("#fiscalType").select2("val", fiscalTypeSelect).trigger('change'); 
				 var fistext = $("#fiscalType").select2("data");
				 if(fistext.text=="业务处室"){
					 $("#putunderDiv").hide();
				 }
			 }
			 $("#fiscalType").on("select2-selecting", function(e) {
				 if(e.object.text=="业务处室"){
					 $("#putunderDiv").hide();
					 $("#putunder").select2("val", "").trigger("change"); 
				 }else{
					 $("#putunderDiv").show();
				 }
			 })  // 选中事件
			 $.ajax({
			     type:"post",
			     url:"${ctx}/sys.orgs/sysOrgs/getOneList",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	var opt ="";
		    	  	for(var i in data){
		    	  		opt+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		    	    }
		    	  	$("#putunder").append(opt);
			     }
			 });
			 var putunderSelect = "${sysOrgsAdjust.putunder.id}"; 
			 if(putunderSelect!=""){
				 $("#putunder").select2("val", putunderSelect).trigger('change'); 
			 }
			
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
						$(form).ajaxSubmit({
						success:function(v){
							closeLoading();
							if(v.success){
								top.document.getElementById('mainFrame').contentWindow.location.reload(true);
								top.showTip(v.msg);
								top.$.jBox.close();
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
			
		});
	</script>
</head>
<body>

	<form:form id="inputForm" modelAttribute="sysOrgsAdjust" action="${ctx}/sys.orgs/sysOrgsAdjust/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<%-- <div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
				<form:input path="idOld" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">机构编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父级编号：</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${sysOrgsAdjust.parent.id}" labelName="parent.name" labelValue="${sysOrgsAdjust.parent.name}"
					title="父级编号" url="/sys.orgs/sysOrgs/treeData" extId="${sysOrgsAdjust.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">简称：</label>
			<div class="controls">
				<form:input path="smallName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">全称：</label>
			<div class="controls">
				<form:input path="fullName" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">财政机构类型：</label>
			<div class="controls">
				<form:select path="fiscalType.id" class="input-xlarge required" id="fiscalType">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="putunderDiv">
			<label class="control-label">业务归口处室：</label>
			<div class="controls">
				<form:select path="putunder.id" class="input-xlarge" id="putunder">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${sysOrgsAdjust.area.id}" labelName="area.name" labelValue="${sysOrgsAdjust.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sys_Agency_Type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主负责人：</label>
			<div class="controls">
				<form:input path="primaryPerson" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副负责人：</label>
			<div class="controls">
				<form:input path="deputyPerson" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码：</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人：</label>
			<div class="controls">
				<form:input path="master" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真：</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:radiobuttons path="useable" items="${fns:getDictList('useable')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="sys.orgs:sysOrgsAdjust:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>