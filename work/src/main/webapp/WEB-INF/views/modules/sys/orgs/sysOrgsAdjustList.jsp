<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调整管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys.orgs/sysOrgsAdjust/list?idOld=${orgsId}");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="sysOrgsAdjust" action="${ctx}/sys.orgs/sysOrgsAdjust/listData?idOld=${orgsId }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					   <input class="btn btn-primary"  type="submit" value="查询"/>
					   <a class="btn btn-primary" href="javascript:TagQueryRest('searchForm')" >重置</a>
					   <a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/sys.orgs/sysOrgsAdjust/form?id=','查看',800,530)" ><i class="icon-add"></i>查看</a>
					</li>
					<shiro:hasPermission name="sys.orgs:sysOrgsAdjust:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/sys.orgs/sysOrgsAdjust/form?idOld=${orgsId }','调整',800,530)" ><i class="icon-add"></i>调整</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
		<div class="title-search">
	 	    <table class="form"> 
	 	    	<tbody>
		            <tr>
						<td class="formTitle">名称：</td>
			            <td class="formValue" >
						<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			                </td>
						<td class="formTitle">财政机构类型：</td>
			            <td class="formValue" >
						<form:select path="fiscalType.id" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
		                </td>
		             	<td class="formTitle">机构类型：</td>
				            <td class="formValue" >
							<form:input path="type" htmlEscape="false" maxlength="1" class="input-medium"/>
		                </td>   
			                
					</tr>
					<tr>	
						<td class="formTitle">业务归口处室：</td>
			            <td class="formValue" >
							<form:select path="putunder.id" class="input-medium">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
		                </td>
						<td class="formTitle">所属区域：</td>
			            <td class="formValue" >
							<sys:treeselect id="area" name="area.id" value="${sysOrgsAdjust.area.id}" labelName="area.name" labelValue="${sysOrgsAdjust.area.name}"
								title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
		                </td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	
	<%-- <div style="overflow: auto;">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>机构编码</th>
				<th>父级编号</th>
				<th>名称</th>
				<th>财政机构类型</th>
				<th>业务归口处室</th>
				<th>所属区域</th>
				<th>机构类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys.orgs:sysOrgsAdjust:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysOrgsAdjust">
			<tr>
				<td>
					${sysOrgsAdjust.code}
			</td>
				<td>
					${sysOrgsAdjust.parent.id}
			</td>
				<td>
					${sysOrgsAdjust.name}
			</td>
				<td>
					${fns:getDictLabel(sysOrgsAdjust.fiscalType.id, '', '')}
			</td>
				<td>
					${fns:getDictLabel(sysOrgsAdjust.putunder.id, '', '')}
			</td>
				<td>
					${sysOrgsAdjust.area.name}
			</td>
				<td>
					${sysOrgsAdjust.type}
			</td>
				<td>
					<fmt:formatDate value="${sysOrgsAdjust.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
				<td>
					${sysOrgsAdjust.remarks}
			</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div> --%>
	
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#dataGrid').dataGrid({
			columnModel: [
				{header:'名称', name:'name', index:'', width:160,
				 	formatter: function(val, obj, row, act){
				 		var onclick = "contentView(\"${ctx}/sys.orgs/sysOrgsAdjust/form?id="+row.id+"\",\"查看\",700,250)";
						return "<a href='#' onclick='"+onclick+"' class='btnList'>"+val+"</a>";
					}
				},
				{header:'机构编码', name:'code', index:'',sortable:false, width:100},
				{header:'备注信息', name:'remarks', index:'',sortable:false, width:150}
			],
			ajaxSuccess: function(data){ 
				
			}
		});
	</script>
</body>
</html>