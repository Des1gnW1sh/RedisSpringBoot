<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>excel模板管理</title>
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
	<form:form id="searchForm" modelAttribute="excelModel" action="${ctx}/excel/excelModel/" method="post" class="breadcrumb form-search">
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					    <input id="btn_Search" class="btn btn-primary" type="submit" value="查询"/>
					</li>
					<shiro:hasPermission name="excel:excelModel:edit">
						<li class="btn-group">
							<a id="btnAdd"  class="btn btn-default"    href="javascript:void(0);" value="新增" onclick="save('${ctx}/excel/excelModel/form','新增模板')" ><i class="icon-add"></i>新增</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		            <tr>
		                <th class="formTitle">模板名称：</th>
		                <td class="formValue" >
		                    <form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
		                </td>
		                
		                <th class="formTitle">模板类型：</th>
		                <td class="formValue" >
		                    <sys:treeselect id="type" name="type.id" value="${excelModel.type.id}" labelName="type.name" labelValue="${excelModel.type.name}"
							title="父级" url="/excel/excelModelType/treeData" extId="${excelModel.type.id}" cssClass="" allowClear="true"/>
		                </td>
		                <th class="formTitle">是否有效：</th>
		                <td class="formValue" >
		                    <form:radiobuttons path="valid" items="${fns:getDictList('valid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>模板名称</th>
				<th>模板类型</th>
				<th>是否有效</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="excel:excelModel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelModel">
			<tr>
				<td><a  href="javascript:void(0);" onclick="save('${ctx}/excel/excelModel/form?id=${excelModel.id}','修改模板')" >
					${excelModel.name}
				</a></td>
				<td>
					${excelModel.type.name}
				</td>
				<td>
					${fns:getDictLabel(excelModel.valid, 'valid', '')}
				</td>
				<td>
					<fmt:formatDate value="${excelModel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${excelModel.remarks}
				</td>
				<shiro:hasPermission name="excel:excelModel:edit"><td>
					<a  href="javascript:void(0);" onclick="save('${ctx}/excel/excelModel/form?id=${excelModel.id}','修改模板')" >修改</a>
					<a href="${ctx}/excel/excelModel/delete?id=${excelModel.id}" onclick="return confirmx('确认要删除该excel模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>