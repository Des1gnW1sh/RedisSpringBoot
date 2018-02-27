<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义启动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/rule/ruleEnable/list");
			$("#searchForm").submit();
        	return false;
        }
		
		function enable(url) {
			var obj = $("#dataGrid");
			if(obj.html()!=undefined){
				//	jqgrid
				var ids = getSelecteds();
				if(ids.length==0){
					$.jBox.tip('请选择一条数据');
					 return false;
				}
				url += ids;
			}
			var submit = function (v, h, f) {  
				if (v == true) {  
					  $.ajax({
					    	url:url,
					    	success:function(data){
					    		numberCheckAll();
					    		numberCheckAll();
					    		top.showTip(data.msg);
					    		window.location.reload();
					    	}
					    })   
				}  
				if (v == false) {  
					top.$.jBox.close(true); 
				}else{
					return true; 
				}  
			};  
			// 可根据需求仿上例子定义按钮  
			$.jBox.confirm("确定启用被选中的数据？", "提示", submit ,{ buttons: { '确定': true, '取消': false} });  
		}
		
		function disable(url) {
			var obj = $("#dataGrid");
			if(obj.html()!=undefined){
				//	jqgrid
				var ids = getSelecteds();
				if(ids.length==0){
					$.jBox.tip('请选择一条数据');
					 return false;
				}
				url += ids;
			}
			var submit = function (v, h, f) {  
				if (v == true) {  
					  $.ajax({
					    	url:url,
					    	success:function(data){
					    		numberCheckAll();
					    		numberCheckAll();
					    		top.showTip(data.msg);
					    		window.location.reload();
					    	}
					    })   
				}  
				if (v == false) {  
					top.$.jBox.close(true); 
				}else{
					return true; 
				}  
			};  
			// 可根据需求仿上例子定义按钮  
			$.jBox.confirm("确定启用被选中的数据？", "提示", submit ,{ buttons: { '确定': true, '取消': false} });  
		}
	</script>
</head>
<body>
	<sys:exportImport URL="${ctx}/rule/ruleEnable"></sys:exportImport>
	<form:form id="searchForm" modelAttribute="ruleEnable" action="${ctx}/rule/ruleEnable/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
            		   <a class="btn btn-primary" href="javascript:TagBlackOrNone('divc')" id="btnBlack">显示</a>
            		   <a  class="btn btn-primary"href="javascript:void(0);"onclick="contentView('${ctx}/rule/ruleEnable/formView?id=','查看',500,700)" ><i class="icon-add"></i>查看</a>
					</li>
					<shiro:hasPermission name="rule:ruleEnable:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/rule/ruleEnable/form','新增',500,700)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/rule/ruleEnable/form?id=','修改',500,700)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/rule/ruleEnable/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="enable('${ctx}/rule/ruleEnable/enable?ids=')" ><i class="icon-add"></i>启用</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="disable('${ctx}/rule/ruleEnable/disable?ids=')" ><i class="icon-add"></i>停用</a>
						 </li>
					</shiro:hasPermission>
				</ul>
			</div>	
			<div class="title-search">
		 	    <table class="form"> 
		 	    	<tbody>
			            <tr>
							<td class="formTitle">功能模块：</td>
				            <td class="formValue" >
				            	<sys:treeselect id="tableId" name="tableId" value="${ruleEnable.tableId}" labelName="name" labelValue="${ruleEnable.tableName}"
								title="表" url="/gen/genTable/treeData" cssClass="required" notAllowSelectParent="true"/>
			                </td>
							<td class="formTitle">是否启用：</td>
				            <td class="formValue" >
								<form:select path="status" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('enable_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
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
				{header:'功能模块', name:'tableName', index:'',sortable:false, width:150},
				{header:'状态', name:'status', index:'',width:150,sortable:false,fixed:true,align:"center",
					formatter: function(val, obj, row, act){
						return getDictLabel(${fns:getDictListJson('enable_status')}, val, '未知');
					}
				},			
				{header:'创建时间', name:'createDate', index:'',sortable:false, width:150},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true,hidden:true,
					formatter: function(val, obj, row, act){
						var actions = [];
						/* var onclick = "contentView(\"${ctx}/rule/ruleEnable/formView?id="+row.id+"\",\"查看\",1200,530)";
						actions.push("<a href='#' onclick='"+onclick+"' class='btnList'>查看111</a>&nbsp;");  */
						return actions.join('');
					}
				}
			],
			ajaxSuccess: function(data){
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/rule/ruleEnable/formView?id="+rowid,"查看",500,700);
			}
		});
	</script>
</body>
</html>