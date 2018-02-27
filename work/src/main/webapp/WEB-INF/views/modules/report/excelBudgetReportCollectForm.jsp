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
					$("#xml").val(inputForm.AF3.func("GetUploadXML","isSaveCalculateResult=true"));
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
		function exportExcel(){
			var filename = AF3.func("callfunc", "105"); 
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/report/excelBudgetReportCollect/list?excelBudgetReport.id=${excelBudgetReportCollect.excelBudgetReport.id}">提交状态列表</a></li>
		<li class="active"><a>提交状态<shiro:hasPermission name="report:excelBudgetReportCollect:edit">${not empty excelBudgetReportCollect.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="report:excelBudgetReportCollect:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="excelBudgetReportCollect" action="${ctx}/report/excelBudgetReportCollect/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">数据报表：</label>
			<div class="controls">
				 <div style="position:relative;width:800px;height:380px;">
				    <script type="text/javascript">
				  	    insertReport('AF3', 'workmode=uploadRuntime');
						loading('正在加载文件，请稍等...');
					</script>
				 </div>
				<form:input path="xml" style="display:none;"/>
			</div>
		</div>
	</form:form>
	<li id="tagBtLi" class="btn-group" style="float: right;">
		<input id="tag_btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportExcel();"/>
	</li> 
</body>
</html>