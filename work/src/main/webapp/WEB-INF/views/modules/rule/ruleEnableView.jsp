<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义启动查看</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
	$(document).ready(function() {			
		treeColumnUpadate();
	});
	
	function treeColumnUpadate() {
		var tableId = "${ruleEnable.tableId}"
		var setting = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y" : "ps", "N" : "s" }
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
			var dataZtree;
			$.ajax({
		        url: "${ctx}/gen/genTable/treeDataColumn?tableId="+tableId,
		        success: function (data)
		        {
		        	var zNodes =data;
  					// 初始化树结构
  					var tree = $.fn.zTree.init($("#columns"), setting, zNodes);
  					// 默认选择节点
  					var ids = "${ruleEnable.columnIds}".split(",");
  					for(var i=0; i<ids.length; i++) {
  						var node = tree.getNodeByParam("id", ids[i]);
  						try{tree.checkNode(node, true, false);}catch(e){}
  					} 
  					// 默认展开全部节点
  					tree.expandAll(true);
		        }
		     });
	}
	
	</script>
</head>
<body>
<form  class="form-horizontal">
	<div class="control-group">
		<label class="control-label">功能模块：</label>
		<div class="controls">
			${ruleEnable.tableName}
		</div>
	</div>
	<div class="control-group">
			<label class="control-label">条件列：</label>
			<div class="controls">
				<div id="columns" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
		</div>
</form>	
</body>
</html>