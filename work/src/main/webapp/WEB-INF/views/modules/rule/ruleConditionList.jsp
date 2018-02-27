<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则条件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/rule/ruleCondition/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<sys:exportImport URL="${ctx}/rule/ruleCondition"></sys:exportImport>
	<form:form id="searchForm" modelAttribute="ruleCondition" action="${ctx}/rule/ruleCondition/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
            		   <a class="btn btn-primary" href="javascript:TagBlackOrNone('divc')" id="btnBlack">显示</a>
            		   <a  class="btn btn-primary"href="javascript:void(0);"onclick="contentView('${ctx}/rule/ruleCondition/formView?id=','查看',800,530)" ><i class="icon-add"></i>查看</a>
					</li>
					<shiro:hasPermission name="rule:ruleCondition:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/rule/ruleCondition/form','新增',800,530)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/rule/ruleCondition/form?id=','修改',800,530)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/rule/ruleCondition/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		 	    	<tbody>
			            <tr>
							<td class="formTitle">规则表ID：</td>
				            <td class="formValue" >
								<form:input path="dId" htmlEscape="false" maxlength="64" class="input-medium"/>
			                </td>
							<td class="formTitle">表ID：</td>
				            <td class="formValue" >
								<form:input path="tableId" htmlEscape="false" maxlength="64" class="input-medium"/>
			                </td>
							<td class="formTitle">规则条件（包含/排除）：</td>
				            <td class="formValue" >
								<form:input path="condition" htmlEscape="false" maxlength="10" class="input-medium"/>
			                </td>
							<td class="formTitle">表字段：</td>
				            <td class="formValue" >
								<form:input path="tableColumn" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
							<td class="formTitle">等于，包含，排除：</td>
				            <td class="formValue" >
								<form:input path="filtration" htmlEscape="false" maxlength="10" class="input-medium"/>
			                </td>
							<td class="formTitle">数据过滤内容：</td>
				            <td class="formValue" >
								<form:input path="content" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
							<td class="formTitle">创建人：</td>
				            <td class="formValue" >
								<form:input path="createBy.id" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				{header:'更新时间', name:'updateDate', index:'',sortable:false, width:150},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true,hidden:true,
					formatter: function(val, obj, row, act){
						var actions = [];
						/* var onclick = "contentView(\"${ctx}/rule/ruleCondition/formView?id="+row.id+"\",\"查看\",1200,530)";
						actions.push("<a href='#' onclick='"+onclick+"' class='btnList'>查看111</a>&nbsp;");  */
						return actions.join('');
					}
				}
			],
			ajaxSuccess: function(data){
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/rule/ruleCondition/formView?id="+rowid,"查看",1200,530);
			}
		});
	</script>
</body>
</html>