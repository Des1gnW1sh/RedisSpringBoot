<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.my_btn {
			display: inline-block;
			width: 75px;
			height: 20px;
			background: #3ca2e0;
			border-radius: 5px;
			text-align: center;
			line-height: 20px;
			color: #f5f5f5;
			text-decoration: none;
			cursor: pointer;
		}

		.my_btn:hover {
			text-decoration: none;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/report/excelBudgetReport/listOfMy?isOver=${isOver}");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/excelBudgetReport/">报表预算列表</a></li>
		<shiro:hasPermission name="report:excelBudgetReport:edit"><li><a href="${ctx}/report/excelBudgetReport/form">报表预算添加</a></li></shiro:hasPermission>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="excelBudgetReport" action="${ctx}/report/excelBudgetReport/listOfMyData?isOver=${isOver}" method="post" class="breadcrumb form-search">
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
					   <a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/report/excelBudgetReport/find?id=','查看',1200,530)" ><i class="icon-add"></i>查看</a>
					</li>
					<c:if test="${isOver eq 0}">
						<shiro:hasPermission name="excel:excelModel:edit">
							<li class="btn-group">
								<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/report/excelBudgetReportCollect/listSubmit?excelBudgetReport.id=','报表填报',1200,530)" ><i class="icon-add"></i>报表填报</a>
							</li>
						</shiro:hasPermission>
					</c:if>
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
			                 <td class="formTitle">开始创建时间：</td>
			                <td class="formValue" >
								<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${excelBudgetReport.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
								
			                </td>
			                <td class="formTitle">创建结束时间：</td>
			                <td class="formValue" >
								<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${excelBudgetReport.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
		</div>	
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
				{header:'名称', name:'name', index:'', width:100},
				{header:'完成时间', name:'overTimo', index:'',sortable:false, width:100},
				{header:'更新时间', name:'updateDate', index:'',sortable:false, width:100},
				{header:'填报状态', name:'status', index:'',sortable:false, width:80},
				{header:'备注信息', name:'remarks', index:'',sortable:false, width:100},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true, 
					formatter: function(val, obj, row, act){
						var actions = [];
						var issue_flag = row.issueFlag;
						if(issue_flag == '1'){
                            var onclick = "contentAdd(\"${ctx}/report/excelBudgetReport/newForm?id=" + row.id + "\",\"继续下发\",1200,550)";
                            actions.push("<a class='btnList my_btn' onclick='"+onclick+"' >继续下发</a>&nbsp;");
						}
					return actions.join('');
				}}
			],
			ajaxSuccess: function(data){ 
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/report/excelBudgetReport/find?id="+rowid,"查看",1200,530);
			}
		});

		
	</script>
</body>
</html>