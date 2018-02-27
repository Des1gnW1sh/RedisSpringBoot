<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>报表模板公式修改</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    $("#excle").val(inputForm.AF3.func("GetFileXML", "isSaveCalculateResult=false"));
                    /*form.submit();*/
                    $(form).ajaxSubmit({
                        dataType: 'json',
                        success: function (v) {
                            debugger;
                            var data = eval(v);

                            closeLoading();
                            if (data.success) {
                                msgNotice(data.msg, 'Success');
                            } else {
                                msgNotice(data.msg, "Error");
                            }
                            //window.parent.window.jBox.close();
                            return false;
                        },
                        error: function () {
                            msgNotice('数据提交失败', "Error");
                        }
                    });
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        var old_data = '';

        function OnReady(id) {
            inputForm.AF3.func("build", $("#excle").val());
            inputForm.AF3.func('CallFunc', '2');
            closeLoading();
        }

        function OnEvent() {

        }

        /**
         * 消息框
         */
        function msgNotice(str, type) {
            inputForm.AF3.func("MessageBoxFloat", str + "\r\n title=" + type + ";icon=Info;");
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="excelBudgetReport" action="${ctx}/report/excelBudgetReport/save" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <input style="display: none" name="doActive" value="updateFormula"/>
    <sys:message content="${message}"/>
    <%--<div class="control-group">
        &lt;%&ndash;<label class="control-label">报表模板：</label>&ndash;%&gt;

    </div>--%>
    <div class="controls" style="margin-left: 0">
        <div style="position:relative;width:100%;height:380px;">
            <script type="text/javascript">
                insertReport('AF3', 'WorkMode=UploadDesigntime;Rebar=Text;Text=585');
                loading('正在加载文件，请稍等...');
            </script>
        </div>
        <form:input path="excle" style="display:none"/>
    </div>
    <div class="form-actions" style="padding-left: 47%;">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>