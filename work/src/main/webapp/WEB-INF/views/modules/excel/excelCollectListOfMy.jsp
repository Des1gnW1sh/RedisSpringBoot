<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇总状态管理</title>
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
	<form:form id="searchForm" modelAttribute="excelCollect" action="${ctx}/excel/excelCollect/" method="post" class="breadcrumb form-search">
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					    <input id="btn_Search" class="btn btn-primary" type="submit" value="查询"/>
					</li>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		            <tr>
		                <th class="formTitle">预算名称：</th>
		                <td class="formValue" >
		                    <form:input path="budget.name" htmlEscape="false" maxlength="255" class="input-medium"/>
		                </td>
		                <th class="formTitle">开始更新时间：</th>
		                <td class="formValue" >
		                    <input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								value="<fmt:formatDate value="${excelCollect.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
		                </td>
		                <th class="formTitle">更新结束时间：</th>
		                <td class="formValue" >
							<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								value="<fmt:formatDate value="${excelCollect.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>		                </td>
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
				<th>机构</th>
				<th>预算</th>
				<th>填报人</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>文件</th>
				<%-- <shiro:hasPermission name="excel:excelCollect:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="excelCollect">
			<tr>
				<td>
					${excelCollect.office.name}
				</td>
				<td>
					${excelCollect.budget.name}
				</td>
				<td>
					${excelCollect.sumitUser}
				</td>
				<td>
					${fns:getDictLabel(excelCollect.status, 'excel_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${excelCollect.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<td>
					<c:if test="${excelCollect.status==0}">
						<a href="javascript:void(0);" onclick="save('${ctx}/excel/excelCollect/formMy?id=${excelCollect.id}','修改文件')" >修改文件</a>
					</c:if>
					<c:if test="${excelCollect.status==0 && excelCollect.sumitUser ne null}">
						<a href="${ctx}/excel/excelCollect/submitExcel?collectId=${excelCollect.id}">提交汇总</a>
					</c:if>
				</td>
				<%-- <shiro:hasPermission name="excel:excelCollect:edit">
				<td>
    				<a href="${ctx}/excel/excelCollect/form?id=${excelCollect.id}">修改</a>
					<a href="${ctx}/excel/excelCollect/delete?id=${excelCollect.id}" onclick="return confirmx('确认要删除该汇总状态吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>