<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇总状态管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#xml").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("#xml").val(inputForm.AF3.func("GetUploadXML",""));
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
		
		function OnReady(id) {
			$.ajax({
			     type:"post",
			     url:"${ctx}/excel/excelBudget/getModelXmlById?id=${excelCollect.budget.id}",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	 $("#xml").val(data);
			     }
		 	});
			inputForm.AF3.func("build",$("#xml").val());
	 		$.ajax({
			     type:"post",
			     url:"${ctx}/excel/excelCollect/getXmlById?id=${excelCollect.id}",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	 if(data!=""){
			    		 inputForm.AF3.func("setUploadXML", data);
			    	 }
			     }
			});    
			closeLoading();
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
	<form:form id="inputForm" modelAttribute="excelCollect" action="${ctx}/excel/excelCollect/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">预算填报：</label>
			<div class="controls">
				 <div style="position:relative;width:800px;height:380px">
				 	<script type="text/javascript">
					 	insertReport('AF3', 'workmode=uploadRuntime');
					 	loading('正在加载文件，请稍等...');
					</script>
				 </div>
				 <form:textarea path="xml" htmlEscape="false"  style="display:none;"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>