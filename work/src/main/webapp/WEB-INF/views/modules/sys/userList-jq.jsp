<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
		//修改
		function userView(href){
			top.$.jBox.open('iframe:${ctx}/sys/user/view','机构单位修改',760,550,{
				iframeScrolling:'no',
				showScrolling: true,		
				dragLimit: true,		
				persistent: true,
				opacity: 0.9, 
			     buttons: {}, 
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
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
<%-- 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/listData" method="post" class="breadcrumb form-search ">
		<form:input path="sysGroup.id"  cssStyle="display:none" />
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<shiro:hasPermission name="sys.orgs:sysOrgsFiscal:edit">
						<li class="btn-group" >
						   <a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/sys/user/form?id=','查看',700,580)" ><i class="icon-add"></i>查看</a>
						</li> 
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentSave('${ctx}/sys/user/form','新增',700,580)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/sys/user/form?id=','修改',700,580)" ><i class="icon-add"></i>修改</a>
						<%--  <a  class="btn btn-primary"  href="javascript:void(0)" onclick="userView('${ctx}/sys/user/userView');">新版修改</a> --%>
						 </li>
					</shiro:hasPermission>
					<li class="btn-group">
						<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
						<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>					 
				</ul>
			</div>	
			<div class="title-search">
				<table class="form"> 
		 	    	<tbody>
			            <tr>
			                <td class="formTitle">归属公司：</td>
			                <td class="formValue" >
								<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
									title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
			                </td>
			                 <td class="formTitle">登录名：</td>
			                <td class="formValue" >
								<form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			                </td>
			                <td class="formTitle">归属部门：</td>
			                <td class="formValue" >
								<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
									title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			                </td>
			                 <td class="formTitle">姓&nbsp;&nbsp;&nbsp;名：</td>
			                <td class="formValue" >
								<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
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
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="dataGrid"></table>
    <div class="pagination" id="dataGridPage"></div>

	<script type="text/javascript">
		// 初始化DataGrid对象
		$('#dataGrid').dataGrid({
			// 设置数据表格列
			columnModel: [
				{header:'登录名', name:'loginName', index:'login_name', sortable:false,width:100, frozen:true /* , 
					formatter: function(val, obj, row, act){
				 		var onclick = "contentSave(\"${ctx}/sys/user/form?id="+row.id+"\",\"修改\",700,580)";
						return "<a onclick='"+onclick+"' class='btnList'>"+val+"</a>";
					} */
				},
				{header:'归属公司',name:'company',sortable:false, width:160,
					formatter: function(val, obj, row, act){
						if(val!=null){
							 return val.name;
						 }else{
							 return "";
						 }
					}
				},
				{header:'组织机构',name:'office',sortable:false, width:160, 
					formatter: function(val, obj, row, act){
						if(val!=null){
							 return val.name;
						 }else{
							 return "";
						 }
					}
				},
				{header:'电话', name:'phone', index:'phone', width:100, sortable:false},
				{header:'手机', name:'mobile', index:'mobile', width:100, sortable:false}/* ,
				{header:'操作', name:'actions', width:120, fixed:true, sortable:false, fixed:true, 
					formatter: function(val, obj, row, act){
					var actions = [];<%--<--%>shiro:hasPermission name="sys:user:edit">
						var onclick = "contentSave(\"${ctx}/sys/user/form?id="+row.id+"\",\"修改\",700,580)";
						actions.push("<a onclick='"+onclick+"' class='btnList'>修改</a>&nbsp;");
					<%--</--%>shiro:hasPermission>
					return actions.join('');
				}} */
			],
			ajaxSuccess: function(data){ // 加载成功后执行方法
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/sys/user/form?id="+rowid,"查看",700,580);
			}
		});
	</script>
</body>
</html>