<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表预算管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var id= '${excelBudgetReport.id}';
			if(id==""){
				$(".form-actions").show();
			}
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("#excle").val(inputForm.AF3.func("GetFileXML", "isSaveCalculateResult=true"));
					$(form).ajaxSubmit({
						success:function(v){
							closeLoading();
							if(v.success){
								/* top.document.getElementById('mainFrame').contentWindow.location.reload(true); */
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
			
			$(":input[name=informUrge]").on('click', function(event){    
		        if($(event.target).val()=="1"){
		        	$("#urgeTimeHide").show();
		        }else{
		        	$("#urgeTimeHide").hide();
		        }
		    });  
		});
		function OnReady(id) {
			$.ajax({
			     type:"post",
			     url:"${ctx}/report/excelBudgetReport/getXmlById?id=${excelBudgetReport.id}",
			     dataType:"text/html",
			     contentType:"text/html;charset=UTF-8",
//				 dataType:"json",
//               contentType:"application/json",
			     async: false,
			     success:function(result){
                     var data = $.parseJSON(result);
			    	 $("#excle").val(data);

			    	 closeLoading();
			     }
		 	});
			inputForm.AF3.func("build",$("#excle").val());
            inputForm.AF3.func("Swkrntpomzqa", "1, 2");
		}
        $(function () {
            var $time = $('.open-email-time');
            var $open = "${excelBudgetReport.isOpenEmailWarn}";
            if($open === "1"){
                $time.css('display', 'block');
            }
            $('.is-open-email input:radio[name="isOpenEmailWarn"]').change(function () {
                var $val = $(this).val();

                if ($val === 1 || $val === '1') {
                    $time.css('display', 'block');
                } else {
                    $time.css('display', 'none');
                }
            })
        })
	</script>
	<style type="text/css">
	.form-actions{
		display: none;
	}
	</style>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/report/excelBudgetReport/">报表预算列表</a></li>
		<li class="active"><a href="${ctx}/report/excelBudgetReport/form?id=${excelBudgetReport.id}">报表预算<shiro:hasPermission name="report:excelBudgetReport:edit">${not empty excelBudgetReport.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="report:excelBudgetReport:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/> --%>
	<form:form id="inputForm" modelAttribute="excelBudgetReport" action="${ctx}/report/excelBudgetReport/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys n:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">要求及说明：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge" cssStyle="resize:none;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下发模式：</label>
			<div class="controls">
				<form:radiobuttons path="issueFlag" items="${fns:getDictList('issue_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下级填报单位：</label>
			<div class="controls">
				<!-- office id name  逗号分隔 -->
				<sys:treeselect id="office" name="officeLab" value="${excelBudgetReport.officeLab}" labelName="officeLabname" labelValue="${excelBudgetReport.officeLabname}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small required" allowClear="true"  checked="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否开启邮件提醒：</label>
			<div class="controls is-open-email">

				<form:radiobuttons path="isOpenEmailWarn" items="${fns:getDictList('yes_no')}" itemLabel="label"
								   itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group open-email-time" style="display: none">
			<label class="control-label">邮件提醒时间：</label>
			<div class="controls">
				<form:select path="warnTime" cssStyle="width: 177px;height: 30px">
					<option>请选择时间...</option>
					<form:option value="6">6h</form:option>
					<form:option value="12">12h</form:option>
					<form:option value="24">24h</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成时间：</label>
			<div class="controls">
				<input name="overTimo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${excelBudgetReport.overTimo}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group" >
			<label class="control-label">短信流程通知：</label>
			<div class="controls">
				<form:radiobuttons path="informSchedule" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">短信自动催报：</label>
			<div class="controls">
				<t>
					<form:radiobuttons path="informUrge" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</t>
				<t id="urgeTimeHide" style="display:none;">
					&nbsp;<t style="cursor: pointer;font-size:13px;">自动催报间隔：</t>
					<form:input path="urgeTime" htmlEscape="false" maxlength="18"  class="digits" cssStyle="width:40px;"/>
					<select><option>分钟</option><option>小时</option></select>
				</t>
			</div>
		</div>
		
		<%-- <div class="control-group">
			<label class="control-label">上传表内公式：</label>
			<div class="controls">
				<form:input path="insideFormula" htmlEscape="false" class="input-xlarge" />
				<input type="file" name="insideFormula">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传表间公式：</label>
			<div class="controls">
				<form:input path="outsideFormula" htmlEscape="false" class="input-xlarge "/>
				<input type="file" name="outsideFormula">
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">强制公式审核：</label>
			<div class="controls">
				<form:radiobuttons path="forceFormula" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公式报表：</label>
			<div class="controls">
				<%-- <form:input path="excle" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span> --%>
				 <div style="width:800px;height:380px;">
				    <script type="text/javascript">
						loading('正在加载文件，请稍等...');
						insertReport('AF3', 'workmode=uploadDesigntime;Main=105, 100, 189, 20, 32, 106, 10');
                        closeLoading();
					</script>
				 </div>
				 <form:textarea path="excle" htmlEscape="false"  style="display:none;"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="report:excelBudgetReport:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>