<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
						success:function(v){
							closeLoading();
							if(v.success){
								top.showTip(v.msg);
								parent.location.reload();
								window.parent.window.jBox.close() ;
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
	<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="form">
			<tr>
				<th class="formTitle">
			
					<label class="control-label">字典键:</label>
				</th>
				<td class="formValue">
					<form:input path="value" htmlEscape="false" maxlength="50" class="required"/>
				</td>
				<th class="formTitle">
					
					<label class="control-label">字典值:</label>
				</th>
				<td class="formValue">
					<form:input path="label" htmlEscape="false" maxlength="50" class="required"/>
				</td>
			</tr>	
			<tr>
				<th class="formTitle">
					<label class="control-label">类型名称:</label>
				</th>
				<td class="formValue">
					<form:input path="typeName" htmlEscape="false" maxlength="50" class="required"/>
				</td>
				<th class="formTitle">
					<label class="control-label">类型:</label>
				</th>
				<td class="formValue">
					<form:input path="type" htmlEscape="false" maxlength="50" class="required abc"/>
				</td>
			</tr>	
			<tr>	
				<th class="formTitle">
					<label class="control-label">描述:</label>
				</th>
				<td class="formValue">
					<form:input path="description" htmlEscape="false" maxlength="50" class="required"/>
				</td>
				<th class="formTitle">
					<label class="control-label">排序:</label>
				</th>
				<td class="formValue">
					<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
				</td>
			</tr>	
			<tr>	
				<th class="formTitle">
					<label class="control-label">备注:</label>
				</th>
				<td class="formValue">
					<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
				</td>
			</tr>
			</table>	
			<div class="form-actions" style="text-align: center;  margin-bottom:-20px;">
				<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="return  btnClosed()"/>
			</div>
	</form:form>
</body>
</html>