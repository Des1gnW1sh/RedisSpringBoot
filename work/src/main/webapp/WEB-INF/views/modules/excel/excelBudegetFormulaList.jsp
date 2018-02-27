<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公式管理</title>
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

		function save(url,text){		
			top.$.jBox.open('iframe:'+url,text,1000,530,{
				/* iframeScrolling:'no', */
				dragLimit: true,
				showScrolling: true,				
				persistent: true,
				opacity: 0.5, 
				loaded: function (h) {
				    top.$("#jbox-content").css("overflow-y","hidden");
				}, 
				submit : function (v, h, f) {
					top.$("#jbox-iframe").contents().find("#btnSubmit").click();
				  	return false;
				}
			});			
		}
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/excel/excelBudegetFormula/">公式列表</a></li>
		<shiro:hasPermission name="excel:excelBudegetFormula:edit"><li><a href="${ctx}/excel/excelBudegetFormula/form">公式添加</a></li></shiro:hasPermission>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="excelBudegetFormula" action="${ctx}/excel/excelBudegetFormula/" method="post" class="breadcrumb form-search">
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					    <input id="btn_Search" class="btn btn-primary" type="submit" value="查询"/>
					</li>
					<shiro:hasPermission name="excel:excelBudegetFormula:edit">
						<li class="btn-group">
							<a id="btnAdd"  class="btn btn-default"    href="javascript:void(0);" value="新增" onclick="save('${ctx}/excel/excelBudegetFormula/form','新增公式')" ><i class="icon-add"></i>新增</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		            <tr>
		                <th class="formTitle">公式名称：</th>
		                <td class="formValue" >
		                    <form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
		                </td>
		                <th class="formTitle">公式值：</th>
		                <td class="formValue" >
		                    <form:input path="content" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>公式名称</th>
				<th>公式值</th>
				<th>类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="excel:excelBudegetFormula:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelBudegetFormula">
			<tr>
				<td><a href="javascript:void(0);" onclick="save('${ctx}/excel/excelBudegetFormula/form?id=${excelBudegetFormula.id}','修改预算')" >
					${excelBudegetFormula.name}
				</a></td>
				<td>
					${excelBudegetFormula.content}
				</td>
				<td>
					${excelBudegetFormula.type.name}
				</td>
				<td>
					<fmt:formatDate value="${excelBudegetFormula.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${excelBudegetFormula.remarks}
				</td>
				<shiro:hasPermission name="excel:excelBudegetFormula:edit"><td>
    				<a href="javascript:void(0);" onclick="save('${ctx}/excel/excelBudegetFormula/form?id=${excelBudegetFormula.id}','修改预算')" >修改</a>
					<a href="${ctx}/excel/excelBudegetFormula/delete?id=${excelBudegetFormula.id}" onclick="return confirmx('确认要删除该公式吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>