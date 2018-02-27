<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
		$("#treeTable").treeTable({expandLevel : 5});
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/office/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			$("input[val='隐藏']").click(function(){
				alert("1");
			     $("#formTable").css({height:"20px",overflow:"hidden"});
			    // $('input[val="隐藏"]').val("显示");
			});
			$("input[val='显示']").click(function(){
				alert("1");
				$("#formTable").css({height:"auto",overflow:"hidden"});
			    //  $('input[val="显示"]').val("隐藏");
			});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}

		function Add(){		
			top.$.jBox.open('iframe:${ctx}/sys/office/form','机构单位添加',1000,450,{
				showClose: true,
				iframeScrolling:'yes',
				showScrolling: true,		
				persistent: true,
				dragLimit: true,		
				opacity: 0.5, /* 窗口隔离层的透明度,如果设置为0,则不显示隔离层 */
				
				loaded:function(h){
					top.$("#jbox-content").css("overflow-y","hidden");
				},
				submit : function (v, h, f) {
					top.$("#jbox-iframe").contents().find("#btnSubmit").click();
				  	return false;
				}
			});			
			
		}
		
		//修改
		function Edit(href){
			top.$.jBox.open('iframe:'+href,'机构单位修改',950,550,{
				iframeScrolling:'yes',
				showScrolling: true,		
				dragLimit: true,		
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
		

		//修改
		function View(href){
			top.$.jBox.open('iframe:${ctx}/sys/office/view','机构单位修改',950,550,{
				iframeScrolling:'yes',
				showScrolling: true,		
				dragLimit: true,		
				persistent: true,
				opacity: 0.5, 
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
		
		//批量删除
		function btnDelete()
		{
			
		}
		
	</script>
</head>
<body>
<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/office/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px" accept="application/msexcel"/><br/><br/>　
			
		   <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/office/import/template">下载模板</a>
		</form>
	</div>

	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/office/list" method="post" class="breadcrumb form-search">
		<div class="titlePanel">		
		 <div class="toolbar">		 
			<ul >	
				<li class="btn-group" >
		             <a type="button"  href="javascript:TagBlackOrNone('divc')"  class="btn btn-primary"  id="btnBlack">显示</a> 
				</li>
				<shiro:hasPermission name="sys:office:edit">
				<li class="btn-group">
					<a id="btnAdd"  class="btn btn-primary"    href="javascript:btnDelete();"  onclick="return Add()" ><i class="icon-add"></i>新增</a>
								<a id="btnEdit" class="btn btn-default"  href="javascript:btnDelete();"  onclick="View('${ctx}/sys/office/view')" ><i class="icon-user"></i>查看</a>
					<!--<a class="btn btn-default" href="javascript:btnDelete();" onclick="return confirmx('确认要删除选择数据吗？', this.href)"><i class="icon-remove"></i>批量删除</a>
				 -->
				 </li>
					</shiro:hasPermission>
				<li class="btn-group" >
						<input id="btnExport" type="button"  class="btn btn-primary" value="导出"/>
						<input id="btnImport"  type="button"  class="btn btn-primary" value="导入"/>
				</li>
			</ul>
	
		</div>
		 <div class="title-search" >            	
		      <table class="form">
		      	<tbody>
		      <tr>
		        <th class="formTitle">
		            		查询语句
		                </th>
		                <td class="formValue" >
		                     <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字"  />
		                </td>
		                   <th class="formTitle">
		            		查询语句
		                </th>
		                <td class="formValue" >
		                    <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字"  />
		                </td>
		                    <th class="formTitle">
		            		查询语句
		                </th>
		                <td class="formValue" >
		                    <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字"  />
		                </td>
		            </tr>
		            </tbody>
		            <tbody  id="divc" style="display:none">
			             <tr>
			                 <th class="formTitle">
			            		查询语句
			                </th>
			                <td class="formValue"  >
			                     <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字"  />
			                </td>
			                   <th class="formTitle">
			            		查询语句
			                </th>
			                <td class="formValue"  >
			                      <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字"  />
			                </td>
			                   <th class="formTitle">
			            		查询语句
			                </th>
			                <td class="formValue"  >
			                    <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字"  />
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
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th  >机构名称</th><th>归属区域</th><th>机构编码</th><th>机构类型</th><th>备注</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>

	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="javascript:void(0)" onclick="Edit('${ctx}/sys/office/form?id={{row.id}}');">{{row.name}}</a></td>
			<td>{{row.area.name}}</td>
			<td>{{row.code}}</td>
			<td>{{dict.type}}</td>
			<td>{{row.remarks}}</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="javascript:void(0)" onclick="Edit('${ctx}/sys/office/form?id={{row.id}}');">修改</a>
				<a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除</a>
				<a href="javascript:void(0)" onclick="Edit('${ctx}/sys/office/form?parent.id={{row.id}}')">添加下级机构</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>