<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>

<html lang="en">
<head>
    <title>智能报表系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${ctxStatic}/images/logo-title.png" type="image/x-icon">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/layui/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/layui/css/login.css" media="all">
</head>
<body>
<div class="layui-canvs"></div>
<div class="layui-layout layui-layout-login">
    <h1>
        <strong>五环科技综合开发平台</strong>
        <em>Management System</em>
    </h1>
    <form id="loinForm" action="/a/login"  method="post">
        <div class="layui-user-icon larry-login">
            <input type="text"  name="username" class="login_txtbx" placeholder="请输入用户名" style="height: 42px;width: 352px" id="username" value="thinkgem"/>
        </div>
        <div class="layui-pwd-icon larry-login">
            <input type="password" name="password" class="login_txtbx" style="height: 42px;width: 352px" placeholder="请输入密码" id="password" value="admin"/>
        </div>

        <div class="layui-submit larry-login">
            <input type="button" value="立即登陆"  id="but_login" onclick="checkUser()"   class="submit_btn"/>
        </div>
    </form>
    <div class="layui-login-text">
        <p>© 2017-2019 五环科技 版权所有</p>
        <!--  <p> <a href="http://www.whkj.com" title="">www.whkj.com</a></p> -->
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>

<script type="text/javascript" src="${ctxStatic}/layui/common/layui/lay/dest/layui.all.js"></script>
<script type="text/javascript" src="${ctxStatic}/layui/js/login.js"></script>
<script type="text/javascript" src="${ctxStatic}/layui/jsplug/jparticle.jquery.js"></script>

<script type="text/javascript" src="${ctxStatic}/mutiLang/en.js"></script>
<script type="text/javascript" src="${ctxStatic}/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="${ctxStatic}/login/js/iphone.check.js"></script>



<script type="text/javascript">
    $(function(){
        $(".layui-canvs").jParticle({
            background: "#141414",
            color: "#E6E6E6"
        });
    });
    var $supcan = NewBrowserObj();
    var thisBrowser = $supcan['type'];
    var input1 = $('<input/>');
    var input2 = $('<input/>');
    var $form = $('#loinForm');
    if(thisBrowser === 'ie'){
        //window.location.href = '${ctx}/views/modules/sys/jump.jsp';
        input1.attr('name','browser');
        input1.attr('style','display:none');
        input1.val(thisBrowser);
        input1.attr('hidden',true);
        $form.prepend(input1);
    }else{
        if(!($supcan.npapi || $supcan.ppapi)){
            //window.location.href = '${ctx}/views/modules/helpdoc/helpDocument.jsp';
            input2.attr('name','hasSupcan');
            input2.attr('style','display:none');
            input2.val('no');
            input2.attr('hidden',true);
            $form.prepend(input2);
        }
    }
    function NewBrowserObj() {
        var obj = new Object();
        obj.npapi = false;  //是否已安装 npapi
        obj.ppapi = false;  //是否已安装 ppapi
        obj.chrome_install_apitype = "";   //如果Chrome插件未安装, 允许安装的类型 (npapi, ppapi)
        obj.version = 0;
        var sver = "";
        var agnt = navigator.userAgent.toLowerCase();
        if(agnt.indexOf("edge")>0)  //edge浏览器
            obj.type = "edge";
        else if(agnt.indexOf("msie")>0 || agnt.indexOf("trident")>0) {  //ie浏览器
            obj.type = "ie";
            var off = agnt.indexOf("msie");
            if(off >= 0) sver = agnt.substring(off + 4);
        }
        else {  //chrome 或 firefox 浏览器
            if(navigator.mimeTypes["application/supcan-plugin"])  obj.npapi = true;  //npapi已经安装
            if(navigator.mimeTypes["application/x-ppapi-supcan"]) obj.ppapi = true;  //ppapi已经安装
            if(agnt.indexOf("chrome")>0) {
                obj.type = "chrome";
                var off = agnt.indexOf("chrome/");
                if(off >= 0) sver = agnt.substring(off + 7);
            }
            else
                obj.type = "firefox";
        }

        if(sver != "") {
            var off = sver.indexOf(".");
            if(off > 0) obj.version = parseInt(sver.substr(0, off));
        }

        if(obj.type == "chrome") {
            if(obj.npapi == false && obj.ppapi == false) {  //未安装插件
                if(obj.version <35 || navigator.mimeTypes.length > 15)  //老版本的 Chrome、或国产浏览器
                    obj.chrome_install_apitype = "npapi";
                else
                    obj.chrome_install_apitype = "ppapi";
            }
            else if(obj.npapi && obj.ppapi) { //2 种都安装了, 为了兼容, 35版前仍使用 npapi (ppapi也是支持的)
                if(obj.version <35)
                    obj.ppapi = false;
                else
                    obj.npapi = false;
            }
        }
        obj.is64 = (agnt.indexOf("win64")>=0 || agnt.indexOf("x64")>=0) ? true : false;
        return obj;
    }
</script>
<script type="text/javascript">
    $(function(){
        optErrMsg();
    });
    $("#errMsgContiner").hide();
    function optErrMsg(){
        $("#showErrMsg").html('');
        $("#errMsgContiner").hide();
    }
    var errorMsg = '${requestScope.message}';
    if(errorMsg !== ''){
        showErrorMsg(errorMsg);
    }
    //输入验证码，回车登录
    $(document).keydown(function(e){
        if(e.keyCode === 13) {
            $("#but_login").click();
        }
    });

    //验证用户信息
    function checkUser(){
        if(!validForm()){
            return false;
        }

        $("#loinForm").submit();
    }
    //表单验证
    function validForm(){
        if($.trim($("#username").val()).length===0){
            showErrorMsg("请输入用户名");
            return false;
        }

        if($.trim($("#password").val()).length===0){
            showErrorMsg("请输入密码");
            return false;
        }
        return true;
    }

    //登录处理函数
    function newLogin(orgId) {
        setCookie();
        var actionurl=$('form').attr('action');//提交路径
        var formData = new Object();
        var data=$(":input").each(function() {
            formData[this.name] =$("#"+this.name ).val();
        });

        //语言
        formData['langCode']=$("#langCode").val();
        formData['langCode'] = $("#langCode option:selected").val();

        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            url: '/a/login.do',// 请求的action路径
            data: formData,
            error: function () {// 请求失败处理函数
            },
            success: function (data) {
                window.location.href = actionurl;
            }
        });
    }
    //登录提示消息显示
    function showErrorMsg(msg){
        layer.alert(msg)
    }
    /**
     * 刷新验证码
     */
    $('#randCodeImage').click(function(){
        reloadRandCodeImage();
    });
    function reloadRandCodeImage() {
        var date = new Date();
        var img = document.getElementById("randCodeImage");
        img.src='randCodeImage?a=' + date.getTime();
    }

    function darkStyle(){
        $('body').attr('class', 'login-layout');
        $('#id-text2').attr('class', 'red');
        $('#id-company-text').attr('class', 'blue');
        e.preventDefault();
    }
    function lightStyle(){
        $('body').attr('class', 'login-layout light-login');
        $('#id-text2').attr('class', 'grey');
        $('#id-company-text').attr('class', 'blue');

        e.preventDefault();
    }
    function blurStyle(){
        $('body').attr('class', 'login-layout blur-login');
        $('#id-text2').attr('class', 'white');
        $('#id-company-text').attr('class', 'light-blue');

        e.preventDefault();
    }
    //设置cookie
    function setCookie()
    {
        if ($('#on_off').val() == '1') {
            $("input[iscookie='true']").each(function() {
                $.cookie(this.name, $("#"+this.name).val(), "/",24);
                $.cookie("COOKIE_NAME","true", "/",24);
            });
        } else {
            $("input[iscookie='true']").each(function() {
                $.cookie(this.name,null);
                $.cookie("COOKIE_NAME",null);
            });
        }
    }
    //读取cookie
    function getCookie()
    {
        var COOKIE_NAME=$.cookie("COOKIE_NAME");
        if (COOKIE_NAME !=null) {
            $("input[iscookie='true']").each(function() {
                $($("#"+this.name).val( $.cookie(this.name)));
                if("admin" == $.cookie(this.name)) {
                    $("#randCode").focus();
                } else {
                    $("#password").val("");
                    $("#password").focus();
                }
            });
            $("#on_off").attr("checked", true);
            $("#on_off").val("1");
        }
        else
        {
            $("#on_off").attr("checked", false);
            $("#on_off").val("0");
            $("#randCode").focus();
        }
    }
</script>

</body>
</html>
