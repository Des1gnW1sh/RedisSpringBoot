<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提交状态管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/report/excelBudgetReportCollect/listSubmit?excelBudgetReport.id=${reportId}");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a >提交状态列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="excelBudgetReportCollect" action="${ctx}/report/excelBudgetReportCollect/listSubmitData?excelBudgetReport.id=${reportId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>提交状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('excel_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li> --%>
			<li><label>更新时间：</label>
				<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${excelBudgetReportCollect.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${excelBudgetReportCollect.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<%-- <sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>预算机构</th>
				<th>填报人</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelBudgetReportCollect">
			<tr>
				<td>
					${excelBudgetReportCollect.office.name}
				</td>
				<td>
					${excelBudgetReportCollect.sumitUser}
				</td>
				<td>
					${fns:getDictLabel(excelBudgetReportCollect.status, 'excel_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${excelBudgetReportCollect.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${excelBudgetReportCollect.status==0}">
						<a href="${ctx}/report/excelBudgetReportCollect/formSubmit?id=${excelBudgetReportCollect.id}"><i class="icon-add"></i>修改文件</a>
					</c:if>
					<c:if test="${excelBudgetReportCollect.status==0 && excelBudgetReportCollect.sumitUser ne null}">
						<a href="${ctx}/report/excelBudgetReportCollect/submitExcel?collectId=${excelBudgetReportCollect.id}&buId=${excelBudgetReportCollect.excelBudgetReport.id}">提交汇总</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div> --%>
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#dataGrid').dataGrid({
			columnModel: [
				{header:'机构', name:'office', index:'', width:160,
				 	formatter: function(val, obj, row, act){
						return val.name;
					}
				},
				{header:'填报人', name:'sumitUser', index:'',sortable:false, width:100},
				{header:'提交状态', name:'status', index:'',sortable:false, width:100,
					formatter: function(val, obj, row, act){
					return getDictLabel(${fns:getDictListJson('excel_status')}, val, '未知', true);
				}},
				{header:'更新时间', name:'updateDate', index:'',sortable:false, width:150},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true, 
					formatter: function(val, obj, row, act){
						var actions = [];
						if(row.status == "0" || row.status == "2"){
					 		var url = "${ctx}/report/excelBudgetReportCollect/formSubmit?id="+row.id;
							actions.push("<a href='"+url+"' class='btnList'>数据填报</a>&nbsp;");
							if(row.sumitUser != null && row.sumitUser != ""){
								url = "${ctx}/report/excelBudgetReportCollect/submitExcel?collectId="+row.id+"&buId="+row.excelBudgetReport.id;
								actions.push("<a href='"+url+"' class='btnList'>提交汇总</a>&nbsp;");
							}
						}	
						return actions.join('');
				}}
			],
			ajaxSuccess: function(data){ 
			}
		});
		
	</script>
</body>
</html>