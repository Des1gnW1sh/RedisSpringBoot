<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function() {
			top.$.jBox.confirm("确认要导出用户数据吗？", "系统提示", function(v, h, f) {
				if (v == "ok") {
					$("#searchForm").attr("action", "${ctx}/sys/user/export");
					$("#searchForm").submit();
				}
			}, {
				buttonsFocus : 1
			});
			top.$('.jbox-body .jbox-icon').css('top', '55px');
		});
		$("#btnImport").click(function() {
			$.jBox($("#importBox").html(), {
				title : "导入数据",
				buttons : {
					"关闭" : true
				},
				bottomText : "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
			});
		});
	});
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/sys/user/list");
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left:20px;text-align:center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width:330px" /><br />
			<br /> <input id="btnImportSubmit" class="btn btn-primary"
				type="submit" value="   导    入   " /> <a
				href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit">
			<li><a href="${ctx}/sys/user/form">用户添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user"
		action="${ctx}/sys/user/list" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}"
			callback="page();" />
		<div class="titlePanel">
			<div class="toolbar">
				<ul>
					<li class="btn-group"><input id="btnSubmit"
						class="btn btn-primary" type="submit" value="查询"
						onclick="return page();" /> <input id="btnExport"
						class="btn btn-primary" type="button" value="导出" /> <input
						id="btnImport" class="btn btn-primary" type="button" value="导入" />
					</li>
					<shiro:hasPermission name="excel:excelModel:edit">
						<li class="btn-group"><a class="btn btn-primary"
							href="javascript:void(0);"
							onclick="contentSave('${ctx}/warehose/wh_warehoseform1','新增',1024,730)"><i
								class="icon-add"></i>新增</a> <a class="btn btn-primary"
							href="javascript:void(0);"
							onclick="contentUpdate('${ctx}/warehose/wh_warehoseform1?id=','修改',1024,730)"><i
								class="icon-add"></i>修改</a></li>
						<li class="btn-group"><a class="btn btn-primary"
							href="javascript:void(0);"
							onclick="batchDelete('${ctx}/warehose/wh_warehoseform1/deletes?ids=')"><i
								class="icon-add"></i>删除</a></li>
					</shiro:hasPermission>

				</ul>
			</div>


		</div>
		<div class="title-search">
			<table class="form">
				<tr>
					<td class="formTitle">归属公司：</td>
					<td class="formValue"><sys:treeselect id="company"
							name="company.id" value="${user.company.id}"
							labelName="company.name" labelValue="${user.company.name}"
							title="公司" url="/sys/office/treeData?type=1"
							cssClass="input-small" allowClear="true" /></td>

					<td class="formTitle">登录名：</td>
					<td class="formValue"><form:input path="loginName"
							htmlEscape="false" maxlength="50" class="input-medium" /></td>
					<td class="formTitle">归属部门：</td>
					<td class="formValue"><sys:treeselect id="office"
							name="office.id" value="${user.office.id}"
							labelName="office.name" labelValue="${user.office.name}"
							title="部门" url="/sys/office/treeData?type=2"
							cssClass="input-small" allowClear="true"
							notAllowSelectParent="true" /></td>

					<td class="formTitle">姓&nbsp;&nbsp;&nbsp;名：</td>
					<td class="formValue"><form:input path="name"
							htmlEscape="false" maxlength="50" placeholder="请输入要查询关键字"
							class="input-medium" /></td>

				</tr>
				 <tr>
			            	<td  colspan="8" style="text-align: center;">
						    	<input class="btn btn-primary"  type="submit" value="查询"/>
						   		<a class="btn btn-primary" href="javascript:TagQueryRest('searchForm')" >重置</a>
							</td>
			            </tr>
			</table>
		</div>

	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>归属公司</th>
				<th>归属部门</th>
				<th>归属职位</th>
				<th>归属岗位</th>
				<th class="sort-column login_name">登录名</th>
				<th class="sort-column name">姓名</th>
				<th>电话</th>
				<th>手机</th>
				<%--<th>角色</th> --%>
				<shiro:hasPermission name="sys:user:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="user">
				<tr>
					<td>${user.company.name}</td>
					<td>${user.office.name}</td>

					<td>${user.baseSysPost.name}</td>
					<td>${user.baseSysJob.name}</td>

					<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
					<td>${user.name}</td>
					<td>${user.phone}</td>
					<td>${user.mobile}</td>
					<%--
				<td>${user.roleNames}</td> --%>
					<td><a href="${ctx}/sys/user/form?id=${user.id}">修改</a> <a
						href="${ctx}/sys/user/delete?id=${user.id}"
						onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>