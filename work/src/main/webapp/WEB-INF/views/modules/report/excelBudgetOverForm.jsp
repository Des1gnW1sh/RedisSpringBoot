<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇总状态管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
	<script type="text/javascript">
		function OnReady(id) {

			$.ajax({
			     type:"post",
			     url:"${ctx}/report/excelBudgetReport/getXmlById?id=${excelBudgetReport.id}",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	 $("#excle").val(data);
			     }
		 	});
			inputForm.AF3.func("build",$("#excle").val());
            inputForm.AF3.func("Swkrntpomzqa", "1, 2");
			/* inputForm.AF3.func("SetToolBarItemStatus", "104 Visible false"); */
	 		$.ajax({
			     type:"post",
			     url:"${ctx}/report/excelBudgetReport/getSubmitById?id=${excelBudgetReport.id}",
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
	<form:form id="inputForm" modelAttribute="excelBudgetReport" action="" method="post" class="form-horizontal" enctype="multipart/form-data">
		<div class="control-group">
			<label class="control-label">预算结果：</label>
			<div class="controls">
				 <div style="position:relative;width:800px;height:380px">
				 	<script type="text/javascript">
					 	insertReport('AF3', 'workmode=uploadRuntime;Rebar=none');
					 	loading('正在加载文件，请稍等...');
					</script>
				 </div>
				 <form:textarea path="excle"  style="display:none;"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<li id="tagBtLi" class="btn-group" style="float: right;">
		<input id="tag_btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportExcel();"/>
	</li> 
</body>
</html>