<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">   
		$(document).ready(function(){
			initialPage();
			initStep1();
			initialHeight(); //定义panel-body高度自适应窗口
		});
		var tree;
		var tree2; 
	    //重设(表格)宽高
	    function initialHeight() {
	        //resize重设(表格、树形)宽高
	        $(window).resize(function (e) {
	            window.setTimeout(function () {        
	                $(".panel-body").height($(window).height() -130);
	            }, 200);
	            e.stopPropagation();
	        });
	    }
		/*------------初始化页面----------*/
		function initialPage() {
			//加载导向
			$('#wizard').wizard().on('change', function(e, data) {
				var $finish = $("#btn_finish");
				var $next = $("#btn_next");
				if (data.direction == "next") {
					switch (data.step) {
					case 1:
						initMenuListTree(); //初始化功能树
						break;
					case 2:
						setMenuTreeData(); //动态加载选择的功能树
						return true;
						break;
					case 3:
						setOfficeTree();   //加载组织机构树
						$finish.removeAttr('disabled');
						$next.attr('disabled', 'disabled');
						break;
					default:
						break;
					}
				} else {
					$finish.attr('disabled', 'disabled');
					$next.removeAttr('disabled');
				}
				$finish.click(function(){
					$('#inputForm').submit();
				});
			});
		}
		/*---------结束 初始化页面--------*/
		
			/*--------初始化页面State--------*/
			function initStep1(){
			$("#name").focus();			
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},
					enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
				},
				messages: {
					name: {remote: "角色名已存在"},
					enname: {remote: "英文名已存在"}
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2);
					loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
						success:function(v){
							closeLoading();
							if(v.success){
								top.showTip(v.msg);
								parent.location.reload();
								window.parent.window.jBox.close() ;
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
			}
		
		/*------初始化角色功能树 开始 --------*/		
		function initMenuListTree(){
			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
			
		
		}
		/*-----结束 初始化功能树------*/	
		
		/*----- 动态加载组织机构 开始------*/	
			function setOfficeTree(){				
				var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
						data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
							tree.checkNode(node, !node.checked, true, true);
							return false;
						}}};
				// 用户-机构
				var zNodes2=[
						<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
			            </c:forEach>];
				// 初始化树结构
				tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
				// 不选择父节点
				tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
				// 默认选择节点
				var ids2 = "${role.officeIds}".split(",");
				for(var i=0; i<ids2.length; i++) {
					var node = tree2.getNodeByParam("id", ids2[i]);
					try{tree2.checkNode(node, true, false);}catch(e){}
				}
				// 默认展开全部节点
				tree2.expandAll(true);
				// 刷新（显示/隐藏）机构
				refreshOfficeTree();
				$("#dataScope").change(function(){
					refreshOfficeTree();
				});
			}
		//刷新组织机构树
		function refreshOfficeTree(){
			if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			}
		}
		/*-----结束 动态加载组织机构------*/	
		
		/*-----动态加载选中内容 开始 ------*/
		function  setMenuTreeData(){			
			var setting2 = {
					check:{enable: true,chkStyle:"radio",radioType:"all"},
					view:{selectedMulti:false},
					data:{simpleData:{enable:true}},
					callback: {					
						onCheck: zTreeOnCheck,
						onClick: ontreeClick
					}
					};
			  var treeObj=$.fn.zTree.getZTreeObj("menuTree");
         var   nodes=treeObj.getCheckedNodes(true);
     	var zDataTree=[];
         for(var i=0;i<nodes.length;i++){
      	   var parentid=nodes[i].pId;
      	   var name=nodes[i].name;
      	   
      	   if(nodes[i].pId== null || nodes[i].pId == undefined || nodes[i].pId == ''){
      	   	 parentid=0;      	   
      	   }
      	   if(nodes[i].name== null || nodes[i].name == undefined || nodes[i].name == '')
      		 name="权限列表";
      	   var falg=nodes[i].isParent;
      		
      	var   nodeStr ={id:nodes[i].id, pId:parentid, name:name,nocheck:falg};
      	zDataTree.push(nodeStr);
         }
			// 初始化树结构
	 var menuTree=	 $.fn.zTree.init($("#menuDataTree"), setting2, zDataTree);
		// 默认展开全部节点
			menuTree.expandAll(true);
			return true;
		}
		function zTreeOnCheck(event, treeId, treeNode) {
			alert( treeNode.name );
		}	
		function ontreeClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuDataTree");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
		}		
		/*-----结束  动态加载选中内容------*/
	</script>
	<style type="text/css">
	/* .form-horizontal .controls{
		margin-left: 100px;
		overflow-x: hidden;
	}
	.form-horizontal .control-label{
		width:auto;
	} */
	.form-actions{
		display: none;
	}
	</style>
</head>
<body>
<body class="widget-body" style="position:relative; ">
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
	<div id="wizard" class="wizard" data-target="#wizard-steps"
		style="border-left: none; border-top: none; border-right: none;">
		<ul class="steps">
			<li data-target="#step-1" class="active"><span class="step">1</span>基本信息<span
				class="chevron"></span></li>
			<li data-target="#step-2"><span class="step">2</span>功能权限<span
				class="chevron"></span></li>
				
			<li data-target="#step-3"><span class="step">3</span>数据权限<span
				class="chevron"></span></li>
				<li data-target="#step-4"><span class="step">4</span>数据范围<span
				class="chevron"></span></li>
		</ul>
	</div>
<div class="step-content" id="wizard-steps" style="border-left: none; border-bottom: none; border-right: none;">
		<div class="step-pane active" id="step-1" style="margin-bottom: 0px;">
			<div class="panel panel-default" style="margin-bottom: 10px;">		
					<div class="panel-body"  style="width: 96%;overflow :auto;">

		<div class="control-group">
			<label class="control-label">归属机构:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
					title="机构" url="/sys/office/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色名称:</label>
			<div class="controls">
				<input id="oldName" name="oldName" type="hidden" value="${role.name}">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">英文名称:</label>
			<div class="controls">
				<input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
				<form:input path="enname" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> 工作流用户组标识</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色类型:</label>
			<div class="controls"><%--
				<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（security-role：管理员、assignment：可进行任务分配、user：普通用户）</span> --%>
				<form:select path="roleType" class="input-medium">
					<form:option value="assignment">任务分配</form:option>
					<form:option value="security-role">管理角色</form:option>
					<form:option value="user">普通角色</form:option>
				</form:select>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否系统数据:</label>
			<div class="controls">
				<form:select path="sysData">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">是否可用</label>
			<div class="controls">
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
	   </div>
							
			</div>
		</div>

	<div class="step-pane" id="step-2">
			<div class="panel panel-default" style="margin-bottom: 10px;">
					<div class="panel-body"   style="width: 96%;overflow :auto;">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="menuIds"/>
	</div>
	</div>

		<div class="step-pane" id="step-3">
			<div class="panel panel-default" style="margin-bottom: 10px;">
				<div class="panel-heading">
					<h3 class="panel-title">数据规则权限</h3>
				</div>
				<div class="panel-body"   style="width: 96%;overflow :auto;">
				<div id="menuDataTree" class="ztree" style="margin-top:3px;float:left;"></div>
				</div>
				</div>
				</div>
					<div class="step-pane" id="step-4">
			<div class="panel panel-default" style="margin-bottom: 10px;">
				<div class="panel-heading">
					<h3 class="panel-title">数据范围</h3>
				</div>
					<div class="panel-body"   style="width: 96%;overflow :auto;">
					<div class="control-group">
			<label class="control-label">数据范围:</label>
			<div class="controls">
				<%-- <form:select path="dataScope" class="input-medium">
					<form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<span class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
			</div>
			
		</div>
			<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
		<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
				<form:hidden path="officeIds"/>
					</div>
			
		</div>
				</div>
				</div>
				</div>
				</div>
			
				<div class="form-button" id="wizard-actions" style="position:absolute; bottom:10px; right:20px;">
			<a id="btn_last" disabled class="btn btn-default btn-prev">上一步</a>
			 <a id="btn_next" class="btn btn-default btn-next">下一步</a> 
				<shiro:hasPermission name="sys:user:edit">	
				<a id="btn_finish" disabled class="btn btn-success"  >完成</a>
		</shiro:hasPermission>
		</div>
	<%-- 	<div class="form-actions">
			<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
				<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> --%>
	</form:form>
</body>
</html>