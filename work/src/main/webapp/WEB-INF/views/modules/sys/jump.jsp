<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/23
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>跳转 . . .</title>
    <link rel="icon" href="${ctxStatic}/images/logo-title.png" type="image/x-icon">
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
    <style type="text/css">
        .red{
            color: red;
            /*text-decoration-line: none;*/
            cursor: pointer;
        }
    </style>
</head>
<body>
<div style="width: 100%;height: 100%;text-align: center">
    <c:if test="${sessionScope.userBrowser eq 'ie'}">
        <h1>本平台尚不支持IE浏览器，推荐使用【<a class="red" data-target="360se" href="javascript:void(0);">360安全浏览器</a>】/【<a class="red" data-target="360qs" href="javascript:void(0);">360极速浏览器</a>】，也可以</h1>
    </c:if>
    <c:if test="${sessionScope.isHasSupcan eq 'no'}">
        <h1>您的浏览器运行本平台尚缺少插件支持，您可以进入【<a class="red" data-target="helpDoc" href="javascript:void(0);">帮助文档</a>】查看，也可以</h1>
    </c:if>
    <h1><a class="red" data-target="into" href="javascript:void(0);">直接进入平台</a></h1>
</div>
</body>
<script type="text/javascript">
    $('a').click(function () {
        var $this = $(this);
        var target = $this.attr('data-target');
        switch (target){
            case '360se':
                $this.attr('href','http://se.360.cn/');
                break;
            case '360qs':
                $this.attr('href','http://chrome.360.cn/');
                break;
            case 'helpDoc':
                $this.attr('href','${ctx}/sys/menu/toHelpDocment');
                break;
            case 'into':
                var form = $('<form></form>');
                form.attr('action','${ctx}');
                form.attr('method','post');
                form.attr('hidden','true');
                var input = $('<input>');
                input.attr('name','into');
                input.val(1);
                form.append(input);
                $('body').append(form);
                form.submit();
                break;
            default:
                alert('无选择')
        }
    })
</script>

</html>
