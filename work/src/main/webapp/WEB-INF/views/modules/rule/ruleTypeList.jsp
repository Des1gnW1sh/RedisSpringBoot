<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则类型管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		var isFreshFlag="1";
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/rule/ruleType/list");
			$("#searchForm").submit();
        	return false;
        }

		function contentSaveTop(url,text,width,height){
			  isFreshFlag="1";
			$.jBox.open('iframe:'+url,text,width,height,{
				/* iframeScrolling:'no', */
				buttons:{},
				dragLimit: true,
				showScrolling: true,			
				dragLimit: true,
				persistent: true,
				opacity: 0.5, 
				loaded: function (h) {
				    $("#jbox-content").css("overflow-y","hidden");
				},
			 closed:function (){
				 //alert(isFreshFlag);		
		              window.location.href=window.location.href;
			 }
			
			});	
		}
		
		/**
		 * 修改
		 * @param url 路径
		 * @param text 标题
		 * @param width 宽度
		 * @param height  高度
		 * @returns {Boolean}
		 */
		function contentUpdateTop(url,text,width,height){
			if(url.split("?id=")[1]==null || url.split("?id=")[1]==""){
				numbercheck();
				var obj = $("#dataGrid");
				if(obj.html()!=undefined){
					//	jqgrid
					var ids = getSelecteds();
					if(ids.length==0){
						$.jBox.tip('请选择一条数据');
						 return false;
					}else if(ids.length>1){
						$.jBox.tip('只能单独读一条数据修改');
						 return false;
					}
					url += ids;
				}
			$.jBox.open('iframe:'+url,text,width,height,{
				buttons:{},
				dragLimit: true,
				showScrolling: true,				
				persistent: true,
				opacity: 0.5, 
				loaded: function (h) {
				    $("#jbox-content").css("overflow-y","hidden");
				}, 
				closed:function (){
					 //alert(isFreshFlag);		
			              window.location.href=window.location.href;
				 }
			});			
			}
		 }
	</script>
</head>
<body>
	<sys:exportImport URL="${ctx}/rule/ruleType"></sys:exportImport>
	<form:form id="searchForm" modelAttribute="ruleType" action="${ctx}/rule/ruleType/listData" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
            		   <a class="btn btn-primary" href="javascript:TagBlackOrNone('divc')" id="btnBlack">显示</a>
            		   <a  class="btn btn-primary"   href="javascript:void(0);"onclick="contentView('${ctx}/rule/ruleType/formView?id=','查看',600,130)" ><i class="icon-add"></i>查看</a>
					<a  href="javascript:void(0);" onclick="frashView()"></a>
					</li>
					<shiro:hasPermission name="rule:ruleType:edit">
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSaveTop('${ctx}/rule/ruleType/form','新增',600,230)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdateTop('${ctx}/rule/ruleType/form?id=','修改',600,230)" ><i class="icon-add"></i>修改</a>
						 </li>
						 <li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="batchDelete('${ctx}/rule/ruleType/deletes?ids=','删除')" ><i class="icon-add"></i>删除</a>
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
						</tr>
					</tbody>
					 <tbody  id="divc" style="display:none">
					 </tbody>
					<tbody>
		          		 <tr>
			            	<td  colspan="8" style="text-align: center;">
						    	<input class="btn btn-primary"  id="btnQuery" type="submit" value="查询"/>
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
				{header:'名称', name:'name', index:'',sortable:false, width:150},
				{header:'操作', name:'actions', width:220, fixed:true, sortable:false, fixed:true,hidden:true,
					formatter: function(val, obj, row, act){
						var actions = [];
						/* var onclick = "contentView(\"${ctx}/rule/ruleType/formView?id="+row.id+"\",\"查看\",1200,530)";
						actions.push("<a href='#' onclick='"+onclick+"' class='btnList'>查看111</a>&nbsp;");  */
						return actions.join('');
					}
				}
			],
			ajaxSuccess: function(data){
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/rule/ruleType/formView?id="+rowid,"查看",600,130);
			}
		});
	</script>
</body>
</html>