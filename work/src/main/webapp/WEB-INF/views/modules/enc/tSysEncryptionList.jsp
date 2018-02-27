<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存&ldquo;系统加密设置&rdquo;成功管理</title>
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

	<form:form id="searchForm" modelAttribute="tSysEncryption" action="${ctx}/enc/tSysEncryption/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					   <input class="btn btn-primary"  type="submit" value="查询"/>
					   <a class="btn btn-primary" href="javascript:TagQueryRest('searchForm')" >重置</a>
            		   <a class="btn btn-primary" href="javascript:TagBlackOrNone('divc')" id="btnBlack">显示</a>
					   <a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/enc/tSysEncryption/form?id=','查看',800,530)" ><i class="icon-add"></i>查看</a>
					</li>
		<shiro:hasPermission name="enc:tSysEncryption:edit">
			<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/enc/tSysEncryption/form','新增',800,530)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/enc/tSysEncryption/form?id=','修改',800,530)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/enc/tSysEncryption/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
					</shiro:hasPermission>
		<li class="btn-group">
					   <a class="btn btn-primary" href="#" >退回</a>
					   <a class="btn btn-primary" href="#" >取消退回</a>
					 </li>
					 <li class="btn-group">
					   <a class="btn btn-primary" href="#" >审核</a>
					   <a class="btn btn-primary" href="#" >取消审核</a>
					 </li>
					 <li class="btn-group">
					   <a class="btn btn-primary" href="#" >锁定</a>
					   <a class="btn btn-primary" href="#" >解锁</a>
					 </li>
					  <li class="btn-group">
					   <a class="btn btn-primary" href="#" >导入</a>
					   <a class="btn btn-primary" href="#" >导出</a>
					 </li>
					 <li class="btn-group">
					   <a class="btn btn-primary" href="#" >打印</a>
					 </li>
				</ul>
			</div>	
		<div class="title-search">
		
		 	    <table class="form"> 
		 	    	<tbody>
			            <tr>
						<td class="formTitle">名称：</td>
			            <td class="formValue" >
						<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
						</tr>
					</tbody>
				</table>
				
	</div>
		
	</form:form>
	<sys:message content="${message}"/>
	<div style="overflow: auto;">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>类型</th>
				<th>对象</th>
				<th>口令</th>
				<th>TOKEN</th>
				<shiro:hasPermission name="enc:tSysEncryption:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tSysEncryption">
			<tr>
				<td>
					${tSysEncryption.name}
			</td>
				<td>
					${tSysEncryption.type}
			</td>
				<td>
					${tSysEncryption.obj}
			</td>
				<td>
					${tSysEncryption.key}
			</td>
				<td>
					${tSysEncryption.token}
			</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>