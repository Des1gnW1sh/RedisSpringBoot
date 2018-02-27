<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function() {
		initialPage();
		initialHeight(); //定义panel-body高度自适应窗口
	})
	
    //重设(表格)宽高
    function initialHeight() {
        //resize重设(表格、树形)宽高
        $(window).resize(function (e) {
            window.setTimeout(function () {        
                $("#panel-body").height($(window).height() -130);
            }, 200);
            e.stopPropagation();
        });
    }

	//初始化页面
	function initialPage() {
		//加载导向
		$('#wizard').wizard().on('change', function(e, data) {
			var $finish = $("#btn_finish");
			var $next = $("#btn_next");
			if (data.direction == "next") {
				switch (data.step) {
				case 1:

					break;
				case 2:
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
		});

		//完成
		$("#btn_finish").click(function() {
			 $.ajax({
			     type:"post",
			     url:"${ctx}/sys/user/save",
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    alert("baochun成功");
			     }
			 });
		})
	}
</script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
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
			//初始化数据

			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
		            </c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids2 = "${user.company.parentIds}".split(",");
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
			
		});
		
		
		
	function refreshOfficeTree(){
		if($("#dataScope").val()==9){
			$("#officeTree").show();
		}else{
			$("#officeTree").hide();
		}
	}
		
		
	</script>
	<style type="text/css">
	.form-actions{
		display: none;
	}
	</style>

</head>

<body class="widget-body" style="position:relative; ">
<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
	<div id="wizard" class="wizard" data-target="#wizard-steps"
		style="border-left: none; border-top: none; border-right: none;">
		<ul class="steps">
			<li data-target="#step-1" class="active"><span class="step">1</span>上传头像<span
				class="chevron"></span></li>
			<li data-target="#step-2"><span class="step">2</span>基本信息<span
				class="chevron"></span></li>
			<li data-target="#step-3"><span class="step">3</span>分配角色<span
				class="chevron"></span></li>
		</ul>
	</div>
	<div class="step-content" id="wizard-steps" style="border-left: none; border-bottom: none; border-right: none;">
		<div class="step-pane active" id="step-1" style="margin-bottom: 0px;">
			<div class="panel panel-default" style="margin-bottom: 10px;">		
					<div class="panel-body"  id="panel-body" style="width: 96%;overflow :auto;">
				
							<div class="control-group">
			<label class="control-label">头像</label>
			<div class="controls">
						<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100"  maxHeight="100"/>
				
                            <div style="margin-top: 30px; line-height: 14px; color: #75777A; text-align: left;">
                                建议上传图片尺寸为100x100，大小不超过2M。
                            </div>
                            </div>
                        </div>
                    </div>
							
			</div>
		</div>
		<div class="step-pane" id="step-2">
			<div class="panel panel-default" style="margin-bottom: 10px;">
					<div class="panel-body"  id="panel-body" style="width: 96%;overflow :auto;">
					<table class="form" >				
						<tr>
						<th class="formTitle">归属机构:</th>
							<td class="formValue">
							<sys:treeselect id="office"
							name="office.id" value="${user.office.id}"
							labelName="office.name" labelValue="${user.office.name}"
							title="机构" url="/sys/office/treeData"  
							cssClass="required"  	  />
							</td>
						
							<th class="formTitle">员工号:</th>
							<td class="formValue">
							<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
							</td>		
							</tr>					
						<tr>			
							<th class="formTitle">姓名:</th>
							<td class="formValue">
							<form:input path="name" htmlEscape="false" maxlength="50" class="required " />
				<span class="help-inline"><font color="red">*</font> </span>
							</td>
							<th class="formTitle">登录名:</th>
							<td class="formValue">
							<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline"><font color="red">*</font> </span>
							</td>
						</tr>
						
						<tr>			
							<th class="formTitle">密码:</th>
							<td class="formValue">
							<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
							</td>
							<th class="formTitle">确认密码:</th>
							<td class="formValue">
							<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
							</td>
						</tr>
						
						<tr>			
							<th class="formTitle">邮箱:</th>
							<td class="formValue">
							<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
							</td>
							<th class="formTitle">电话:</th>
							<td class="formValue">
						<form:input path="phone" htmlEscape="false" maxlength="100"/>
							</td>
						</tr>
							<tr>			
							<th class="formTitle">手机:</th>
							<td class="formValue">
								<form:input path="mobile" htmlEscape="false" maxlength="100"/>
							</td>
									</tr>
							<tr>
							<th class="formTitle">是否允许登录:</th>
							<td class="formValue" colspan="3">
					<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
							</td>
						</tr>
							
						<tr>
						<th class="formTitle" valign="top" style="padding-top: 4px;">
								备注:</th>
							<td class="formValue" colspan="3">							
							<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
									</tr>
										<c:if test="${not empty user.id}">
										<tr>
			<th class="formTitle" >创建时间:		</th>
			<td class="formValue">	
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
		
			</td>
			
		<th class="formTitle" >最后登陆:</th>
				<td class="formValue">
					<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
			
	</td>
			</tr>
		</c:if>
													
					</table>
				</div>
				
			</div>
		</div>
		<div class="step-pane" id="step-3">
			<div class="panel panel-default" style="margin-bottom: 10px;">
				<div class="panel-heading">
					<h3 class="panel-title">用户角色分配</h3>
				</div>
				<div class="panel-body">
					<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<form:select path="userType" class="input-smaill">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
				
					<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
		</form:form>

</body>
</html>