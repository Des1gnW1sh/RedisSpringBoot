<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提交状态管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
                    $("#xml").val(inputForm.AF3.func("GetUploadXML", "isSaveCalculateResult=true"));
					form.submit();
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
		var old_data ='';
		function OnReady(id) {
			$.ajax({
			     type:"post",
			     url:"${ctx}/report/excelBudgetReportCollect/getReportExcel?id=${excelBudgetReportCollect.id}",
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
			     url:"${ctx}/report/excelBudgetReportCollect/getXmlById?id=${excelBudgetReportCollect.id}",
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/report/excelBudgetReportCollect/listSubmit?excelBudgetReport.id=${excelBudgetReportCollect.excelBudgetReport.id}">提交状态列表</a></li>
		<li class="active"><a>提交状态<shiro:hasPermission name="report:excelBudgetReportCollect:edit">${not empty excelBudgetReportCollect.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="report:excelBudgetReportCollect:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="excelBudgetReportCollect" action="${ctx}/report/excelBudgetReportCollect/save?excelBudgetReport.id=${excelBudgetReportCollect.excelBudgetReport.id}" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">数据报表：</label>
			<div class="controls">
				 <div style="position:relative;width:800px;height:380px;">
				    <script type="text/javascript">
				  	    insertReport('AF3', 'workmode=uploadRunTime;Main=104, 1074, 105, 100, 189, 20, 32, 106, 107');
						loading('正在加载文件，请稍等...');
					</script>
				 </div>
				<form:input path="xml" style="display:none"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>