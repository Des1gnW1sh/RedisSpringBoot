<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	  
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
			//初始化数据
// 			changeJob();
// 			changePost();
// 			var baseSysPostId = "${user.baseSysPost.id}";//职位
// 			var baseSysJobId = "${user.baseSysJob.id}";//岗位
// 			if(baseSysPostId!=""){
// 				$("#postSelect").select2("val", baseSysPostId).trigger('change');
// 			}
// 			if(baseSysJobId!=""){
// 				$("#jobSelect").select2("val", baseSysJobId).trigger('change');
// 			}
			
	
		
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
	var changeCom = true;
	function  changeJob() {
		//级联岗位信息
		 $.ajax({
			 type:"post",
			 url:"${ctx}/basesys/baseSysJob/getList?officeId="+$("#companyId").val(),
			 dataType:"json",
			 contentType:"application/json",
			 async: false,
			 success:function(data){
				$("#jobSelect").empty();
				var opt ="";
				for(var i in data){
					opt+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
				}
				$("#jobSelect").append(opt);
			 }
		 });
		//级联部门信息 做法为 修改tag中url为变量（tag页面为已生成完成页面）
		officeURL="/sys/office/treeData?type=2&pid="+$("#companyId").val();
		if(!changeCom){
			 $("#officeId").val("");
				$("#officeName").val("");
				changePost();
		}
		//是否已经初始化公司
		changeCom=false;

		$("#jobSelect").select2("val", "").trigger('change');
		//避免出现公司和部门不匹配的情况
		/* $("#officeId").val("");
		$("#officeName").val(""); */
	}
	function  changePost() {
			//级联职位信息
			 $.ajax({
			     type:"post",
			     url:"${ctx}/basesys/baseSysPost/getList?officeId="+$("#officeId").val(),
			     dataType:"json",
			     contentType:"application/json",
			     async: false,
			     success:function(data){
			    	$("#postSelect").empty();
			    	var opt ="";
		    	  	for(var i in data){
		    	  		opt+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		    	    }
		    	  	$("#postSelect").append(opt);
			     }
			 });
			 $("#postSelect").select2("val", "").trigger('change');
		}
	//初始化时如果不调用 changeJob  会用到
	function checkCompany(){
			if(changeCom){
				$.jBox.tip('请先选择公司');
				officebeforCheck=true;
			}else{
				officebeforCheck=false;
			}
		}
	</script>
	<style type="text/css">
	.form-actions{
		display: none;
	}
	</style>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/> --%>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<%--<form:hidden id="company_id" path="company.id" value="${user.company.id}"/>--%>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
				<form:input path="company.id" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
				<span class="help-inline"  style="color: rgb(255,0,0)">* </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
               <%--  <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name"
                 labelValue="${user.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" notAllowSelectParent="true" 
					submitFunction="changePost" beforCheck="checkCompany"/> --%>
					<sys:treeselect id="office"
							name="office.id" value="${user.office.id}"
							labelName="office.name" labelValue="${user.office.name}"
							title="机构" url="/sys/office/treeData"  
							cssClass="required"  	  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工号:</label>
			<div class="controls">
				<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" style="color: rgb(255,0,0)">* </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" style="color: rgb(255,0,0)">* </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline" style="color: rgb(255,0,0)">* </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
				<c:if test="${empty user.id}"><span class="help-inline" style="color: rgb(255,0,0)">* </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty user.id}"><span class="help-inline" style="color: rgb(255,0,0)">*</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否允许登录:</label>
			<div class="controls">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<form:select path="userType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline" style="color: rgb(255,0,0)">* </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">最后登陆:</label>
				<div class="controls">
					<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>