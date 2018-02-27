<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/excel/excelBudgetLog/">日志列表</a></li>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="excelBudgetLog" action="${ctx}/excel/excelBudgetLog/" method="post" class="breadcrumb form-search">
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					    <input id="btn_Search" class="btn btn-primary" type="submit" value="查询"/>
					</li>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		            <tr>
		                <th class="formTitle">预算名称：</th>
		                <td class="formValue" >
		                    <form:input path="budget.name" htmlEscape="false" maxlength="255" class="input-medium"/>
		                </td>
		                <th class="formTitle">开始操作时间：</th>
		                <td class="formValue" >
		                    <input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								value="<fmt:formatDate value="${ExcelBudgetLog.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
							
		                </td>
		                 <th class="formTitle">操作结束时间：</th>
		                <td class="formValue" >
		                   <input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								value="<fmt:formatDate value="${excelBudgetLog.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		                </td>
		            </tr>
	        	</table>
			</div>
		</div>	
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>操作动作</th>
				<th>操作人</th>
				<th>操作时间</th>
				<th>预算</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelBudgetLog">
			<tr>
				<td>
					${excelBudgetLog.content}
				</td>
				<td>
					${excelBudgetLog.createBy.name}
				</td>
				
				<td>
					<fmt:formatDate value="${excelBudgetLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${excelBudgetLog.budget.name}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>