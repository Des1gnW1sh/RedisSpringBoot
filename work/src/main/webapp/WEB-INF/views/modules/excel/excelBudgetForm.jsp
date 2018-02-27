<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财政预算管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/excel/excelBudget/checkName?oldName=" + encodeURIComponent('${excelBudget.name}')}
				},
				messages: {
					name: {remote: "预算名已存在"}
				},
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
			changeModel();
			var modelSelect = "${excelBudget.excleModel.id}"; 
			$("#modelSelect").select2("val", modelSelect).trigger('change');
		});
		function  changeModel() {
			 $.ajax({
			     type:"post",
			     url:"${ctx}/excel/excelModel/getList?typeId="+$("#excleModelId").val(),
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	$("#modelSelect").empty();
			    	var opt ="";
		    	  	for(var i in data){
		    	  		opt+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		    	    }
		    	  	$("#modelSelect").append(opt);
			     }
			 });
			 $("#modelSelect").select2("val", "").trigger('change');
		}
	</script>
	<style type="text/css">
	.form-horizontal .controls{
		margin-left: 100px;
		overflow-x: hidden;
	}
	.form-horizontal .control-label{
		width:auto;
	}
	.form-actions{
		display: none;
	}
	</style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="excelBudget" action="${ctx}/excel/excelBudget/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">预算名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">汇总xml：</label>
			<div class="controls">
				<form:input path="excleXml" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<!-- 机构代码新增 -->
		<div class="control-group">
			<label class="control-label">汇总机构：</label>
			<div class="controls">
				<!-- office id name  逗号分隔 -->
				<sys:treeselect id="office" name="officeLab" value="${excelBudget.officeLab}" labelName="officeLabName" labelValue="${excelBudget.officeLabName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small required" allowClear="true"  checked="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">模板：</label>
			<div class="controls">
				<sys:treeselect id="excleModel" name="excleModel.type.id" value="${excelType.id}" 
					labelName="excleModel.name" labelValue="${excelType.name}"
					title="父级" url="/excel/excelModelType/treeData" allowClear="true" submitFunction="changeModel" />
				<select style="width: 130px;" id="modelSelect" name="excleModel.id" class="required"></select>	
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="excel:excelBudget:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>