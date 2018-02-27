<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/rule/ruleDefinition/list");
			$("#searchForm").submit();
        	return false;
        }
		
		function viewComment(href){
			$.jBox.open('iframe:'+href,'类型管理',1200,700,{
				opacity: 0.5, /* 窗口隔离层的透明度,如果设置为0,则不显示隔离层 */
				buttons:{"关闭":true},
				dragLimit: true,
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				},
				closed:function (){
					 //alert(isFreshFlag);		
					 window.parent.refreshTree()
				 }
			});
			
			return false; 
		}
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="ruleDefinition" action="${ctx}/rule/ruleDefinition/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<shiro:hasPermission name="rule:ruleType:edit">
						<shiro:hasPermission name="rule:ruleType:view">
							 <li class="btn-group">
								<a  class="btn btn-primary" href="javascript:void(0);"onclick="viewComment('${ctx}/rule/ruleType/list')" ><i class="icon-add"></i>类型</a>
							 </li>
						</shiro:hasPermission>
					</shiro:hasPermission>
					<li class="btn-group" >
            		   <a class="btn btn-primary" href="javascript:TagBlackOrNone('divc')" id="btnBlack">显示</a>
            		   <a  class="btn btn-primary"href="javascript:void(0);"onclick="contentView('${ctx}/rule/ruleDefinition/formView?id=','查看',800,530)" ><i class="icon-add"></i>查看</a>
					</li>
					<shiro:hasPermission name="rule:ruleDefinition:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/rule/ruleDefinition/form?typeId=${ruleDefinition.typeId}','新增',800,530)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/rule/ruleDefinition/form?id=','修改',800,530)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/rule/ruleDefinition/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		 	    	<tbody>
			            <tr>
							<td class="formTitle">规则类型：</td>
				            <td class="formValue" >
								<sys:treeselect id="typeId" name="typeId" value="${ruleDefinition.typeId}" labelName="typeName" labelValue="${ruleDefinition.typeName}"
								title="类型" url="/rule/ruleType/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
			                </td>
							<td class="formTitle">规则名称：</td>
				            <td class="formValue" >
								<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				{header:'规则类型', name:'typeName', index:'',sortable:false, width:150},
				{header:'规则名称', name:'name', index:'',sortable:false, width:150},
				{header:'功能名称', name:'functionName', index:'',sortable:false, width:150},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true,hidden:true,
					formatter: function(val, obj, row, act){
						var actions = [];
						/* var onclick = "contentView(\"${ctx}/rule/ruleDefinition/formView?id="+row.id+"\",\"查看\",1200,530)";
						actions.push("<a href='#' onclick='"+onclick+"' class='btnList'>查看111</a>&nbsp;");  */
						return actions.join('');
					}
				}
			],
			ajaxSuccess: function(data){
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/rule/ruleDefinition/formView?id="+rowid,"查看",800,530);
			}
		});
	</script>
</body>
</html>