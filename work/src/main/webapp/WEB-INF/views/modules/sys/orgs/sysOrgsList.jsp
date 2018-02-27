<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_Agency_Type'))}, row.type),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys.orgs/sysOrgs/">单位列表</a></li>
		<shiro:hasPermission name="sys.orgs:sysOrgs:edit"><li><a href="${ctx}/sys.orgs/sysOrgs/form">单位添加</a></li></shiro:hasPermission>
	</ul> --%>
	<sys:exportImport URL="${ctx}/sys.orgs/sysOrgs"></sys:exportImport>
	<form:form id="searchForm" modelAttribute="sysOrgs"  action="${ctx}/sys.orgs/sysOrgs/" method="post" class="breadcrumb form-search">
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<shiro:hasPermission name="sys.orgs:sysOrgsFiscal:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/sys.orgs/sysOrgs/form','新增',700,550)" ><i class="icon-add"></i>新增</a>
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
								<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			                </td>
			                <td class="formTitle">归属区域：</td>
				            <td class="formValue" >
								<sys:treeselect id="area" name="area.id" value="${sysOrgs.area.id}" labelName="area.name" labelValue="${sysOrgs.area.name}"
													title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>			
							</td>
							<td class="formTitle">机构类型：</td>
				            <td class="formValue" >
								<form:select path="type" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('sys_Agency_Type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
			                </td>
						</tr>
						<!-- <li><label>财政机构类型：</label>
						</li>
						<li><label>业务归口处室：</label>
						</li> -->
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
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>财政机构类型</th>
				<th>业务归口处室</th>
				<th>归属区域</th>
				<th>机构类型</th>
				<!-- <th>是否启用</th> -->
				<th>更新时间</th>
				<!-- <th>备注信息</th> -->
				<shiro:hasPermission name="sys.orgs:sysOrgs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a onclick="contentView('${ctx}/sys.orgs/sysOrgs/form?id={{row.id}}','查看',700,550)">
				{{row.name}}
			</a></td>
			<td>
				{{row.fiscalType.name}}
			</td>
			<td>
				{{row.putunder.name}}
			</td>
			<td>
				{{row.area.name}}
			</td>
			<td>
				{{dict.type}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<shiro:hasPermission name="sys.orgs:sysOrgs:edit"><td>
				<a onclick="contentView('${ctx}/sys.orgs/sysOrgsAdjust/list?idOld={{row.id}}','单位调整',1200,550)">单位调整</a>
   				<a onclick="contentSave('${ctx}/sys.orgs/sysOrgs/form?id={{row.id}}','新增',700,550)">修改</a>
				<a href="${ctx}/sys.orgs/sysOrgs/delete?id={{row.id}}" onclick="return confirmx('确认要删除该单位及所有子单位吗？', this.href)">删除</a>
				<a onclick="contentSave('${ctx}/sys.orgs/sysOrgs/form?parent.id={{row.id}}','新增',700,550)">添加下级单位</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
	<!-- <td>
				{{row.useable}}
			</td> -->
			<!-- <td>
				{{row.remarks}}
			</td> -->
	<!-- 
		<a onclick="batchDelete('${ctx}/sys.orgs/sysOrgs/delete?ids={{row.id}}')">删除</a>
	 -->
</body>
</html>