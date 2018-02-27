<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>类型管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys.orgs/sysOrgsFiscal/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="sysOrgsFiscal" action="${ctx}/sys.orgs/sysOrgsFiscal/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<shiro:hasPermission name="sys.orgs:sysOrgsFiscal:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/sys.orgs/sysOrgsFiscal/form','新增',700,250)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/sys.orgs/sysOrgsFiscal/form?id=','修改',800,530)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/sys.orgs/sysOrgsFiscal/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search" style="margin-bottom: 5px">
		 	    <table class="form"> 
		 	    	<tbody>
			            <tr>
							<td class="formTitle">名称：</td>
				            <td class="formValue" >
								<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
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
	</form:form>
	<sys:message content="${message}"/>
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>

	<script type="text/javascript">
		$('#dataGrid').dataGrid({
			columnModel: [
				{header:'名称', name:'name', index:'', width:160,
				 	formatter: function(val, obj, row, act){
				 		var onclick = "contentView(\"${ctx}/sys.orgs/sysOrgsFiscal/form?id="+row.id+"\",\"查看\",700,250)";
						return "<a href='#' onclick='"+onclick+"' class='btnList'>"+val+"</a>";
					}
				},
				{header:'更新时间', name:'updateDate', index:'',sortable:false, width:100},
				{header:'备注信息', name:'remarks', index:'',sortable:false, width:150}
			],
			ajaxSuccess: function(data){ 
				
			},
			multiselect: true
		});
        function go(){
            $("#searchForm").submit();
        }
	</script>
</body>
</html>