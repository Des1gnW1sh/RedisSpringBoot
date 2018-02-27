<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/role/listData");
			$("#searchForm").submit();
	    	return false;
	    }
		
		//修改
		function userView(href,title){
			top.$.jBox.open('iframe:${ctx}/sys/role/form',title,760,550,{
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
		
		 $(window).resize(function (e) {
		        window.setTimeout(function () {             	
		        	 top.$("#jbox-iframe").height($(top.window).height() -130);
		        	 top.$("#jbox-iframe").width($(top.window).width() -130);
		        	 
		        }, 200);
		        e.stopPropagation();
		    });
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="role" action="${ctx}/sys/role/listData" method="post" class="breadcrumb form-search ">
		<form:input path="sysGroup.id"  cssStyle="display:none" />
		<div class="titlePanel">
			<div class="toolbar">		 
				<ul >	
					<li class="btn-group" >
						   <a  class="btn btn-primary"    href="javascript:void(0);"onclick="contentView('${ctx}/sys/role/form?id=','查看',700,580)" ><i class="icon-add"></i>查看</a>
						</li> 
						<li class="btn-group">
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="userView('${ctx}/sys/role/form','新增',900,580)" ><i class="icon-add"></i>新增</a>
							<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/sys/role/form?id=','修改',700,580)" ><i class="icon-add"></i>修改</a>
						 </li>
				</ul>
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
			colModel: [			
			{header:'角色名称', name:'name', index:'', sortable:false, width:160},
				{header:'英文', name:'enname', index:'',sortable:false,  width:160},
				{header:'归属机构',name:'office.name',sortable:false, width:160},
				{header:'数据范围',name:'dataScope',sortable:false, width:160, 
					formatter: function(val, obj, row, act){						
						return	getDictLabel(${fns:getDictListJson('sys_data_scope')}, val, '无', true);
					}
				},
				{header:'所属角色组',name:'sysGroup',sortable:false, width:160,
					formatter: function(val, obj, row, act){	
						if(val !=null)
							return val.name;
						else
							return "";
					}
				}
			],
			ajaxSuccess: function(data){ // 加载成功后执行方法
			},
			multiselect: true,
			ondblClickRow:function(rowid,iRow,iCol,e){
				contentView("${ctx}/sys/role/form?id="+rowid,"查看",700,580);
			}		
		});

	</script>
</body>
</html>