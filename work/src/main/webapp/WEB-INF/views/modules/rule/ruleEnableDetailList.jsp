<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义启动明细管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/rule/ruleEnableDetail/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<sys:exportImport URL="${ctx}/rule/ruleEnableDetail"></sys:exportImport>
	<form:form id="searchForm" modelAttribute="ruleEnableDetail" action="${ctx}/rule/ruleEnableDetail/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
            		   <a class="btn btn-primary" href="javascript:TagBlackOrNone('divc')" id="btnBlack">显示</a>
            		   <a  class="btn btn-primary"href="javascript:void(0);"onclick="contentView('${ctx}/rule/ruleEnableDetail/formView?id=','查看',800,530)" ><i class="icon-add"></i>查看</a>
					</li>
					<shiro:hasPermission name="rule:ruleEnableDetail:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/rule/ruleEnableDetail/form','新增',800,530)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/rule/ruleEnableDetail/form?id=','修改',800,530)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/rule/ruleEnableDetail/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		 	    	<tbody>
			            <tr>
							<td class="formTitle">规则定义启动主键：</td>
				            <td class="formValue" >
								<form:input path="eId" htmlEscape="false" maxlength="64" class="input-medium"/>
			                </td>
							<td class="formTitle">启用列：</td>
				            <td class="formValue" >
								<form:input path="column" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
							<td class="formTitle">启动列名称：</td>
				            <td class="formValue" >
								<form:input path="columnName" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
						</tr>
					</tbody>
					 <tbody  id="divc" style="display:none">
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
	<script type="text/javascript">
		$('#dataGrid').dataGrid({
			columnModel: [
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true,hidden:true,
					formatter: function(val, obj, row, act){
						var actions = [];
						/* var onclick = "contentView(\"${ctx}/rule/ruleEnableDetail/formView?id="+row.id+"\",\"查看\",1200,530)";
						actions.push("<a href='#' onclick='"+onclick+"' class='btnList'>查看111</a>&nbsp;");  */
						return actions.join('');
					}
				}
			],
			ajaxSuccess: function(data){
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/rule/ruleEnableDetail/formView?id="+rowid,"查看",1200,530);
			}
		});
	</script>
</body>
</html>