<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>全局配置</title>
    <meta name="decorator" content="default"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctxStatic}/layui/common/layui/css/layui.css"  media="all">
    <script src="${ctxStatic}/supcan/binary/dynaload.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
    <script type="text/javascript" src="${ctxStatic}/layui/common/layui/layui.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    $(form).ajaxSubmit({
                        success: function (v) {
                            closeLoading();
                            var errorlay =  layer.open({
                                content: v,
                                time: 20000, //20s后自动关闭
                                btn: ['确定'],
                                yes: function(){
                                    layer.close(errorlay);
                                }
                            });
                            return false;
                        }
                    });

                }

            });




            var $time = $('.isOpen');
            var $open = "${excelConfigGlobal.isOpen}";
            if($open === "1"){
                $time.css('display', 'block');
            }else{
                $time.css('display', 'none');
            }
            $('input:radio[name="isOpen"]').change(function () {
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
        .form-actions {
            display: none;
        }
    </style>
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    你可以在这里完成你的报表提醒时间的设置,该设置会在你登录系统提醒你需要完成的任务状态
</blockquote>
<form:form id="inputForm" modelAttribute="excelConfigGlobal" action="${ctx}/report/excelConfig/put" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <div class="control-group">
        <label class="control-label" style="margin-right: 20px">是否开启提醒</label>
        <form:radiobuttons path="isOpen" items="${fns:getDictList('yes_no')}" itemLabel="label"
                           itemValue="value" htmlEscape="false" class="required"/>
        <span class="help-inline"><font color="red">*</font> </span>
    </div>
    <div class="control-group">

        <div class="controls isOpen">
            <label class="control-label" style="margin-right: 20px">配置</label>
            <form:select path="isDay" class="required" cssStyle="width: 77px;height: 30px">
                <form:option value="0">天数</form:option>
                <form:option value="1">小时</form:option>
            </form:select>
            <span class="help-inline" style="margin-right: 50px"><font color="red">*</font> </span>

            <form:select path="time" class="required" cssStyle="width: 77px;height: 30px">
                <form:option value="1">1</form:option>
                <form:option value="2">2</form:option>
                <form:option value="3">3</form:option>
                <form:option value="4">4</form:option>
                <form:option value="5">5</form:option>
                <form:option value="6">6</form:option>
                <form:option value="7">7</form:option>
                <form:option value="8">8</form:option>
                <form:option value="9">9</form:option>
                <form:option value="10">10</form:option>
                <form:option value="11">11</form:option>
                <form:option value="12">12</form:option>
                <form:option value="13">13</form:option>
                <form:option value="14">14</form:option>
                <form:option value="15">15</form:option>
                <form:option value="16">16</form:option>
                <form:option value="17">17</form:option>
                <form:option value="18">18</form:option>
                <form:option value="19">19</form:option>
                <form:option value="20">20</form:option>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>

        </div>

    </div>
    <div class="control-group">
        <button class="layui-btn" id="inputForm"  style="margin-left: 80%">保存</button>
    </div>
</form:form>

</body>

</html>