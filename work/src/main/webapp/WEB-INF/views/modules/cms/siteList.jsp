<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>站点管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/cms/site/");
			$("#searchForm").submit();
        	return false;
	    }
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/site/">站点列表</a></li>
		<shiro:hasPermission name="cms:site:edit"><li><a href="${ctx}/cms/site/form">站点添加</a></li></shiro:hasPermission>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="site" action="${ctx}/cms/site/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<shiro:hasPermission name="cms:site:edit">
			<a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentSave('${ctx}/cms/site/form','新增',800,530)" ><i class="icon-add"></i>新增</a>
		</shiro:hasPermission>
		<label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	</form:form>
	<sys:message content="${message}"/>
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>
	<link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 初始化DataGrid对象
		$('#dataGrid').dataGrid({
			// 设置数据表格列
			columnModel: [
				{header:'名称', name:'name', index:'',  sortable:false,width:160},          	
				{header:'标题', name:'title',edittype:'select', index:'',  sortable:false,width:100},
				{header:'描述', name:'description', index:'', sortable:false, width:100},
				{header:'关键字', name:'keywords', index:'', sortable:false, width:150},
				{header:'主题', name:'theme', index:'', sortable:false, width:150},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true,
					formatter: function(val, obj, row, act){
						var actions = [];
						<shiro:hasPermission name="cms:site:edit">
							var onclick = "contentUpdate(\"${ctx}/cms/site/form?id="+row.id+"\",\"修改\",1200,530)";
							actions.push("<a href='#' onclick='"+onclick+"' class='btnList'>修改</a>&nbsp;");
							var delflag = row.delFlag;
							var text ="删除";
							if(delflag!=0){
								delflag = "&isRe=true";
								text="恢复";
							}else{
								delflag="";
							} 
							actions.push("<a href='${ctx}/cms/site/delete?id="+row.id+delflag+"' onclick='return confirmx(\"确认要"+text+"该站点吗？\", this.href)' >"+text+"</a>");
						</shiro:hasPermission>
						return actions.join('');
					}
				}
			]
		});
	</script>
</body>
</html>