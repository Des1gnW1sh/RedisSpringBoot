<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>岗位管理</title>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/basesys/baseSysJob/">岗位列表</a></li>
		<shiro:hasPermission name="basesys:baseSysJob:edit"><li><a href="${ctx}/basesys/baseSysJob/form">岗位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="baseSysJob" action="${ctx}/basesys/baseSysJob/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>岗位名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>所在公司：</label>
				<sys:treeselect id="office" name="office.id" value="${baseSysJob.office.id}" labelName="office.name" labelValue="${baseSysJob.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" /><!-- notAllowSelectParent="true" -->
			</li>
			<li><label>岗位编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="60" class="input-medium"/>
			</li>
			<li><label>是否有效：</label>
				<form:radiobuttons path="valid" items="${fns:getDictList('valid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>岗位名称</th>
				<th>所在公司</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th>岗位编号</th>
				<th>是否有效</th>
				<shiro:hasPermission name="basesys:baseSysJob:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseSysJob">
			<tr>
				<td><a href="${ctx}/basesys/baseSysJob/form?id=${baseSysJob.id}">
					${baseSysJob.name}
				</a></td>
				<td>
					${baseSysJob.office.name}
				</td>
				<td>
					<fmt:formatDate value="${baseSysJob.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${baseSysJob.remarks}
				</td>
				<td>
					${baseSysJob.code}
				</td>
				<td>
					${fns:getDictLabel(baseSysJob.valid, 'valid', '')}
				</td>
				<shiro:hasPermission name="basesys:baseSysJob:edit"><td>
    				<a href="${ctx}/basesys/baseSysJob/form?id=${baseSysJob.id}">修改</a>
					<a href="${ctx}/basesys/baseSysJob/delete?id=${baseSysJob.id}" onclick="return confirmx('确认要删除该岗位吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>