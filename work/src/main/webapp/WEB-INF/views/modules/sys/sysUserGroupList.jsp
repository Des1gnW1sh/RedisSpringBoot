<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组维护管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/sysUserGroup/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="sysUserGroup" action="${ctx}/sys/sysUserGroup/listData" method="post" class="breadcrumb form-search">
<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
			<form:input path="parent.id"   cssStyle="display:none"/>
		<div class="titlePanel">		
		 <div class="toolbar">		 
			<ul >	
				<li class="btn-group">
				<shiro:hasPermission name="sys:group:add">
					<a id="btnAdd"  class="btn btn-primary"    href="javascript:void(0);"  onclick="contentSave('${ctx}/sys/sysUserGroup/form','新增',700,450)" ><i class="icon-add"></i>新增</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="sys:group:edit">
								<a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/sys/sysUserGroup/form?id=','修改',700,450)" ><i class="icon-add"></i>修改</a>

					</shiro:hasPermission>
					<shiro:hasPermission name="sys:group:delete">
					<a class="btn btn-primary" href="javascript:void(0);" onclick="batchDelete('${ctx}/sys/sysUserGroup/deletes?ids=','删除')"><i class="icon-remove"></i>删除</a>
					</shiro:hasPermission>
					 </li>
			
					</ul>
					</div>
					</div>
		 <div class="title-search" >            	
		      <table class="form">
		      	<tbody>
		      <tr>
		        <th class="formTitle">编码查询：</th>
		        <td class="formValue">
				<form:input path="parentIds" htmlEscape="false" maxlength="64" class="input-medium"/>
			</td>
			<th class="formTitle">名称：</th>
		        <td class="formValue">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
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
		
			<!-- 手动添加过滤条件，根据父级查询下面子数据 -->
	
	</form:form>
	<sys:message content="${message}"/>
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>
    
	<link href="/intellectreport/static/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="/intellectreport/static/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="/intellectreport/static/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 初始化DataGrid对象
		$('#dataGrid').dataGrid({
			// 设置数据表格列
			columnModel: [
				{header:'名称', name:'name', index:'name', sortable:false,width:100, frozen:true },
				{header:'类型',name:'ftype',sortable:false, width:160, 
					formatter: function(val, obj, row, act){
						return getDictLabel(${fns:getDictListJson('group_type')}, val, '其他组', true);
						//return getDictLabel(${fns:getDictListJson("group_type")}, val, '其他组');
					}
				},	
				{header:'备注',name:'remarks',sortable:false, width:160},					
			],
			ajaxSuccess: function(data){ // 加载成功后执行方法				
			},
			
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/sys/sysUserGroup/form?id="+rowid,"查看",700,580);
			}
		});
	</script>

</body>
</html>