<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function() {			
	$("#inputForm").validate({
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
	
});

	function btnClosed() {
		var submit = function(v, h, f) {
			if (v == true)
				window.parent.window.jBox.close();
			else
				return true;
		};
		// 自定义按钮
		$.jBox.warning("确认要关闭当前编辑页面吗？", "警告", submit, {
			buttons : {
				'是' : true,
				'否' : false
			}
		});
	}
	
	function gradeChange(){
	  var text = $('#fPSelect option:selected').text();//选中的文本

	  var val =  $('#fPSelect option:selected') .val();//选中的值

	  var selectedIndex =    $("#fPSelect ").get(0).selectedIndex;//索引
	 	 $("#fPutunder").val(val)
       }
</script>
</head>
<body>
	<div class="widget-body">

		<form:form id="inputForm" modelAttribute="office"
			action="${ctx}/sys/office/save" method="post" class="form-horizontal">

			<sys:message content="${message}" />
			<table class="form">

				<tr>
					<th class="formTitle"><label class="control-label">上级机构</label></th>
					<td class="formValue"><sys:treeselect id="office"
							name="parent.id" value="${office.parent.id}"
							labelName="parent.name" labelValue="${office.parent.name}"
							title="机构" url="/sys/office/treeData" extId="${office.id}"
							cssClass="" allowClear="${office.currentUser.admin}" /> <span
						class="help-inline"><font color="red" face="宋体">*</font> </span></td>
					<th class="formTitle"><label class="control-label">归属区域:</label></th>
					<td class="formValue"><sys:treeselect id="area" name="area.id"
							value="${office.area.id}" labelName="area.name"
							labelValue="${office.area.name}" title="区域"
							url="/sys/area/treeData" cssClass="required" /></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">机构编码(主键):</label>
					</th>
					<td class="formValue"><form:input path="id" htmlEscape="false"
							maxlength="50" class="required" /> <span class="help-inline">
							<font color="red">*</font>
					</span></td>


					<th class="formTitle"><label class="control-label">单位名称:</label>
					</th>
					<td class="formValue"><form:input path="name"
							htmlEscape="false" maxlength="50" class="required" /> <span
						class="help-inline"> <font color="red" face="宋体">*</font>
					</span></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">机构简称:</label>
					</th>
					<td class="formValue"><form:input path="fSmallName"
							htmlEscape="false" maxlength="50" class="required" /> <span
						class="help-inline"> <font color="red">*</font>
					</span></td>
					<th class="formTitle"><label class="control-label">机构全称:</label>
					</th>
					<td class="formValue"><form:input path="fFullName"
							htmlEscape="false" maxlength="50" /></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">财政机构类型:</label>
					</th>
					<td class="formValue">
					<form:select path="fOfficeType" class="input-medium">
					<form:options items="${fns:getDictList('sys_office_Type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select> <span class="help-inline"> <font color="red">*</font>
					</span></td>
					
					<th class="formTitle">
						<label class="control-label">业务归口处室:</label>
					</th>
					<td class="formValue">		
						<form:input path="fPutunder" htmlEscape="false" maxlength="50" class="required" cssStyle="display:none"/>
						<select id="fPSelect"  class="input-medium" onchange="gradeChange()">
							<option value="">${office.fPutunder}</option>
							<c:set var="details" value="${offices}" />
							<c:if test="${!empty details}">
								<c:forEach  items ="${details}" var='detail' varStatus="sNub">
									<option value="${detail.id}">${detail.name}</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>


				<tr>
					<th class="formTitle"><label class="control-label">机构类型:</label>
					</th>
					<td class="formValue"><form:select path="type"
							class="input-medium">
							<form:options items="${fns:getDictList('sys_office_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<th class="formTitle"><label class="control-label">机构级别:</label>
					</th>
					<td class="formValue"><form:select path="grade"
							class="input-medium">
							<form:options items="${fns:getDictList('sys_office_grade')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
				</tr>
				<tr>
					<th class="formTitle">单位类型</th>
					<td class="formValue"><form:select path="fTypeCode"
							class="input-medium">
							<form:options items="${fns:getDictList('sys_Agency_Type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<th class="formTitle"><label class="control-label">主负责人:</label></th>
					<td class="formValue">
					<sys:treeselect id="primaryPerson"
							name="primaryPerson.id" value="${office.primaryPerson.id}"
							labelName="office.primaryPerson.name"
							labelValue="${office.primaryPerson.name}" title="用户"
							url="/sys/office/treeData?type=3" allowClear="true"
							notAllowSelectParent="true" /></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">副负责人:</label>
					</th>
					<td class="formValue"><sys:treeselect id="deputyPerson"
							name="deputyPerson.id" value="${office.deputyPerson.id}"
							labelName="office.deputyPerson.name"
							labelValue="${office.deputyPerson.name}" title="用户"
							url="/sys/office/treeData?type=3" allowClear="true"
							notAllowSelectParent="true" /></td>
					<th class="formTitle"><label class="control-label">联系地址:</label>
					</th>
					<td class="formValue"><form:input path="address"
							htmlEscape="false" maxlength="50" /></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">邮政编码:</label>
					</th>
					<td class="formValue"><form:input path="zipCode"
							htmlEscape="false" maxlength="50" /></td>
					<th class="formTitle"><label class="control-label">负责人:</label>
					</th>
					<td class="formValue"><form:input path="master"
							htmlEscape="false" maxlength="50" /></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">电话:</label></th>
					<td class="formValue"><form:input path="phone"
							htmlEscape="false" maxlength="50" /></td>
					<th class="formTitle"><label class="control-label">传真:</label></th>
					<td class="formValue"><form:input path="fax"
							htmlEscape="false" maxlength="50" /></td>
				</tr>
				<tr>
					<th class="formTitle"><label class="control-label">邮箱:</label></th>
					<td class="formValue"><form:input path="email"
							htmlEscape="false" maxlength="50" /></td>
							<th class="formTitle"><label class="control-label">是否可用:</label></th>
					<td class="formValue"><form:select path="useable">
							<form:options items="${fns:getDictList('yes_no')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
						</tr>
				<tr>
					<th class="formTitle"><label class="control-label">备注:</label></th>
					<td class="formValue"><form:textarea path="remarks"
							htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" />
					</td>
					<th></th>
					<td></td>
				</tr>
			</table>

			<%-- <c:if test="${empty office.id}">
				<div class="control-group">
					<label class="control-label">快速添加下级部门:</label>
					<div class="controls">
						<form:checkboxes path="childDeptList"
							items="${fns:getDictList('sys_office_common')}" itemLabel="label"
							itemValue="value" htmlEscape="false" />
					</div>
				</div>
			</c:if> --%>
			<div class="form-actions"
				style="text-align: center;   display:none;  margin-bottom:-20px;">
				<shiro:hasPermission name="sys:office:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit"
						value="保 存" />&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn  btn-primary" type="button"
					value="关闭" onclick="return  btnClosed()" />
			</div>
		</form:form>
	</div>
</body>
</html>