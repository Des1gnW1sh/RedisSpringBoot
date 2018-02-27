<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇总状态管理</title>
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
	<form:form id="searchForm" modelAttribute="excelCollect" action="${ctx}/excel/excelCollect/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>机构：</label>
				<sys:treeselect id="office" name="office.id" value="${excelCollect.office.id}" labelName="office.name" labelValue="${excelCollect.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<%-- <li><label>预算：</label>
				<form:input path="budget.name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>更新时间：</label>
				<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${excelCollect.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${excelCollect.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>机构</th>
				<th>预算</th>
				<th>填报人</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>查看文件</th>
				<%-- <shiro:hasPermission name="excel:excelCollect:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelCollect">
			<tr>
				<td><%-- <a href="${ctx}/excel/excelCollect/form?id=${excelCollect.id}"> --%>
					${excelCollect.office.name}
				<!-- </a> --></td>
				<td>
					${excelCollect.budget.name}
				</td>
				<td>
					${excelCollect.sumitUser}
				</td>
				<td>
					${fns:getDictLabel(excelCollect.status, 'excel_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${excelCollect.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${excelCollect.status == 1}">
						<a href="${ctx}/excel/excelCollect/form?id=${excelCollect.id}">查看文件</a>
						<a href="${ctx}/excel/excelCollect/rejectExcel?collectId=${excelCollect.id}&buId=${excelCollect.budget.id}">驳回文件</a>
					</c:if>
				</td>
				
				
				<%-- <shiro:hasPermission name="excel:excelCollect:edit">
				<td>
    				<a href="${ctx}/excel/excelCollect/form?id=${excelCollect.id}">修改</a>
					<a href="${ctx}/excel/excelCollect/delete?id=${excelCollect.id}" onclick="return confirmx('确认要删除该汇总状态吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>