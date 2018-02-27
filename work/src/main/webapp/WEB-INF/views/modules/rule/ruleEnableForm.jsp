<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义启动管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {			
			$("#inputForm").validate({
				submitHandler: function(form){
					setColumnIds();
					loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
						success:function(v){
							closeLoading();
							if(v.success){
								top.document.getElementById('mainFrame').contentWindow.location.reload(true);
								top.showTip(v.msg);
								top.$.jBox.close();
							}else{
								showTip(v.msg,"error");
							}
							return false;
						}
				    });
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			treeColumnUpadate();
		});
		
		function treeColumnUpadate() {
			var tableId = $("#tableIdId").val();
			if(tableId==""||tableId=="undefind"||tableId==null){
				return;
			}
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
		
		function treeColumn() {
			var tableId = $("#tableIdId").val();
			if(tableId==""||tableId=="undefind"||tableId==null){
				return;
			}
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
      					/* var ids = "${role.officeIds}".split(",");
      					for(var i=0; i<ids2.length; i++) {
      						var node = tree2.getNodeByParam("id", ids2[i]);
      						try{tree2.checkNode(node, true, false);}catch(e){}
      					} */
      					// 默认展开全部节点
      					tree.expandAll(true);
			        }
			     });
		}
		function setColumnIds() {
			var treeObj=$.fn.zTree.getZTreeObj("columns")
            var nodes=treeObj.getCheckedNodes(true)
            var columnIds ="";
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].type=="l"){
					columnIds+=nodes[i].id + ",";
				}
		    }
			$("#columnIds").val(columnIds)
		}
	</script>
	<style type="text/css">
	.form-actions{
		display: none;
	}
	</style>
</head>
<body>

	<form:form id="inputForm" modelAttribute="ruleEnable" action="${ctx}/rule/ruleEnable/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">功能模块：</label>
			<div class="controls">
				<sys:treeselect id="tableId" name="tableId" value="${ruleEnable.tableId}" labelName="name" labelValue="${ruleEnable.tableName}"
								title="功能模块" url="/gen/genTable/treeData" cssClass="required" notAllowSelectParent="true" submitFunction="treeColumn"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">条件列：</label>
			<div class="controls">
				<div id="columns" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:input path="columnIds" htmlEscape="false" maxlength="255" class="input-xlarge" cssStyle="display:none"/>
			</div>
		</div>
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="rule:ruleEnable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>