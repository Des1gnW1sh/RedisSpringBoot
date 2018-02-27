<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/dict/list");
			$("#searchForm").submit();
	    	return false;
	    }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/dict/list">字典列表</a></li>
		<%-- <shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10">字典添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="titlePanel">
			<div class="toolbar">
				<ul>
					<li class="btn-group" >
					   <a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/sys/dict/form?id=','查看',700,350)" ><i class="icon-add"></i>查看</a>
					</li> 
					<shiro:hasPermission name="sys:dict:edit">
						<li class="btn-group">
							<a id="btnAdd"  class="btn btn-primary" href="#" value="新增" onclick="contentSave('${ctx}/sys/dict/form?sort=10','新增',700,350)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/sys/dict/form?id=','修改',700,350)" ><i class="icon-add"></i>修改</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/sys/dict/formKey?id=','添加键值',700,350)" ><i class="icon-add"></i>添加键值</a>
						 </li>
						  <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/sys/dict/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div class="title-search">
				<table class="form"> 
					<tbody>
						<tr>
							<td class="formTitle">类型：</td>
			                <td class="formValue" ><form:input path="typeName" htmlEscape="false" maxlength="50" class="input-medium"/></td>
			                <form:input path="type" htmlEscape="false" maxlength="50" cssStyle="display:none"/>
							<td class="formTitle">描述：</td>
			                <td class="formValue" ><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/></td>
								<%-- <label>类型名称：</label>
								<form:select id="type" path="type" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${typeList}" htmlEscape="false" itemValue="type" itemLabel="typeName"/>
								</form:select> --%>
								<%-- <label>类型名称：</label>
								<form:select id="type" path="type" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${typeList}" htmlEscape="false" itemValue="type" itemLabel="typeName"/>
								</form:select> --%>
						</tr>
					</tbody>
					<tbody>
		          		 <tr>
			            	<td  colspan="8" style="text-align: center;">
						    	<input class="btn btn-primary"  type="submit" value="查询"/>
						   		<a class="btn btn-primary" href="javascript:TagQueryRest('searchForm')" >重置</a>
							</td>
			            </tr>
		          	</tbody>
				</table>
			</div>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#dataGrid').dataGrid({
			columnModel: [
				{header:'字典类型', name:'type', index:'', width:160/* ,
				 	formatter: function(val, obj, row, act){
				 		var onclick = "contentView(\"${ctx}/sys/dict/form?id="+row.id+"\",\"查看\",700,350)";
						return "<a href='#' onclick='"+onclick+"' class='btnList'>"+val+"</a>";
					} */
				},
				{header:'类型名称', name:'typeName', index:'',sortable:false, width:100},
				{header:'字典键', name:'value', index:'',sortable:false, width:150},
				{header:'字典值', name:'label', index:'',sortable:false, width:150}/* ,
				{header:'操作', name:'actions', width:120, fixed:true, sortable:false, fixed:true, 
					formatter: function(val, obj, row, act){
					var actions = [];
					<shiro:hasPermission name="sys:dict:edit">
						var onclick = "contentSave(\"${ctx}/sys/dict/form?id="+row.id+"\",\"修改\",700,380)";
						actions.push("<a onclick='"+onclick+"' class='btnList'>修改</a>&nbsp;");
						onclick ="${ctx}/sys/dict/delete?id="+row.id+"&type="+row.type+"";
						actions.push("<a href='"+onclick+"' onclick='return confirmx(\"确认要删除该字典吗？\", this.href)' class='btnList'>删除</a>&nbsp;")
						onclick ="${ctx}/sys/dict/form?type="+row.type+"&sort="+(row.sort+10)+"&typeName="+row.typeName+"&description="+row.description;
						onclick = "contentSave(\"${ctx}/sys/dict/form?type="+row.type+"&sort="+(row.sort+10)+"&typeName="+row.typeName+"&description="+row.description+"\",\"添加键值\",700,380)";
						actions.push("<a onclick='"+onclick+"' class='btnList'>添加键值</a>&nbsp;");
					</shiro:hasPermission>
					return actions.join('');
				}} */
			],
			ajaxSuccess: function(data){ 
				
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/sys/dict/form?id="+rowid,"查看",700,350);
			}
		});
	</script>
	
</body>
</html>