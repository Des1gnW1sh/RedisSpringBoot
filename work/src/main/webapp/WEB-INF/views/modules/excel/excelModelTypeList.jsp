<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表模板类型管理</title>
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
			top.$.jBox.open('iframe:'+url,text,600,350,{
				iframeScrolling:'no',
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
	<form:form id="searchForm" modelAttribute="excelModelType" action="${ctx}/excel/excelModelType/list" method="post" >
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					    <input id="btn_Search" class="btn btn-primary" type="submit" value="查询"/>
					</li>
					<shiro:hasPermission name="sys:office:edit">
						<li class="btn-group">
							<a id="btnAdd"  class="btn btn-default"    href="javascript:void(0);" value="新增" onclick="save('${ctx}/excel/excelModelType/form','新增类型')" ><i class="icon-add"></i>新增</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		            <tr>
		                <th class="formTitle">智能查询：</th>
		                <td class="formValue" >
		                    <form:input path="likeQuery" htmlEscape="false" maxlength="255" class="input-medium"/>
		                </td>
		                <th class="formTitle">开始更新时间：</th>
		                <td class="formValue" >
		                    <input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								value="<fmt:formatDate value="${excelModelType.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
		                </td>
		                 <th class="formTitle">更新结束时间：</th>
		                <td class="formValue" >
						<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								value="<fmt:formatDate value="${excelModelType.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>	 </td>
		                <%-- <th class="formTitle">父级：</th>
		                <%-- <th class="formTitle">父级：</th>
		                <td class="formValue" >
		                    <sys:treeselect id="parent" name="parent.id" value="${excelModelType.parent.id}" labelName="parent.name" labelValue="${excelModelType.parent.name}"
											title="父级" url="/excel/excelModelType/treeData" extId="${excelModelType.id}" cssClass="" allowClear="true"/>
		                </td> --%>
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
				<th>类型名称</th>
				<!-- <th>父级</th> -->
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="excel:excelModelType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelModelType">
			<tr>
				<td><a  href="javascript:void(0);" onclick="save('${ctx}/excel/excelModelType/form?id=${excelModelType.id}','修改类型')" >
					${excelModelType.name}
				</a></td>
				<%-- <td>
					${excelModelType.parent.id}
				</td> --%>
				<td>
					<fmt:formatDate value="${excelModelType.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${excelModelType.remarks}
				</td>
				<shiro:hasPermission name="excel:excelModelType:edit"><td>
    				<a  href="javascript:void(0);" onclick="save('${ctx}/excel/excelModelType/form?id=${excelModelType.id}','修改类型')" >修改</a>
					<a href="${ctx}/excel/excelModelType/delete?id=${excelModelType.id}" onclick="return confirmx('确认要删除该excel模板类型吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>