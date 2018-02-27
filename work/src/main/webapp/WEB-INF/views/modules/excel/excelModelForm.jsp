<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>excel模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("#xml").val(inputForm.AF3.func("GetFileXML", "isSaveCalculateResult=true"));
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
					debugger;
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function OnReady(id) {
			$.ajax({
			     type:"post",
			     url:"${ctx}/excel/excelModel/getXmlById?id=${excelModel.id}",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	 $("#xml").val(data);
			    	 closeLoading();
			     }
		 	});
			inputForm.AF3.func("build",$("#xml").val());
			/* AF3.func("build", "http://localhost:8080/intellectreport/static/supcan/binary/report3.xml"); */
			/* AF3.func("Swkrntpomzqa", "16"); */
			/* var a = AF3.func("SetToolbarItemStatus","104,Visible,false");
			alert(a); */
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
	<script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
	<form:form id="inputForm" modelAttribute="excelModel" action="${ctx}/excel/excelModel/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">模板类型：</label>
			<div class="controls">
				<sys:treeselect id="type" name="type.id" value="${excelModel.type.id}" labelName="type.name" labelValue="${excelModel.type.name}"
					title="父级" url="/excel/excelModelType/treeData" extId="${excelModel.type.id}" cssClass="required" allowClear="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下发标识：</label>
			<div class="controls">
				<form:radiobuttons path="issueFlag" items="${fns:getDictList('issue_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="255" class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有效：</label>
			<div class="controls">
				<form:radiobuttons path="valid" items="${fns:getDictList('valid')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板设计：</label>
			<!-- <input id="" class="btn btn-primary" onclick="a()" value="保 存11"/> -->
			<div class="controls">
				 <div style="position:relative;width:800px;height:380px;">
				    <script type="text/javascript">
						insertReport('AF3', 'workmode=uploadDesigntime');
					 	/* insertReport('AF3', 'workmode=uploadRuntime'); */
					 	loading('正在加载文件，请稍等...');
					</script>
				 </div>
				 <form:textarea path="xml" htmlEscape="false"  style="display:none;"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="excel:excelModel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>