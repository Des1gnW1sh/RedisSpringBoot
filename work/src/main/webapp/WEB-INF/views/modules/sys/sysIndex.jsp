<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="blank"/>
    <link rel="icon" href="${ctxStatic}/images/logo-title.png" type="image/x-icon">
    <c:set var="tabmode" value="1"/>
    <%--如果要隐藏标签tab在Value中添加 ${empty cookie.tabmode.value ? '0' : cookie.tabmode.value} --%>
    <c:if test="${tabmode eq '1'}">
        <link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css"/>
        <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
    </c:if>
    <script src="${ctx}/static/jquery/dialog/jquery.dialog.js"></script>
    <style type="text/css">
        #main {
            padding: 0;
            margin: 0;
        }

        #main .container-fluid {
            padding: 0 4px 0 6px;
        }

        #header {
            margin: 0 0 8px;
            position: static;
        }

        #header li {
            font-size: 12px;
            _font-size: 12px;
        }

        #header .brand {
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
            font-size: 16px;
            padding-left: 33px;
            position: relative;
        }

        #footer {
            margin: 8px 0 0 0;
            padding: 3px 0 0 0;
            font-size: 11px;
            text-align: center;
            border-top: 2px solid #0663A2;
        }

        #footer, #footer a {
            color: #999;
        }

        #left {
            overflow-x: hidden;
            overflow-y: auto;
        }
        .hide-left{
            width: 0 !important;
        }
        .show-left{
            width: 200px !important;
        }
        #left .collapse {
            position: static;
        }

        #userControl > li > a { /*color:#fff;*/
            text-shadow: none;
        }

        #userControl > li > a:hover, #user #userControl > li.open > a {
            background: transparent;
        }
        .font {
            color: #fafafa;
            letter-spacing: 0;
            text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
            font-size: 18px;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function () {
            // <c:if test="${tabmode eq '1'}"> 初始化页签
            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: {'height': $('#right').height() - tabTitleHeight},
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });//</c:if>
            // 绑定菜单单击事件
            $("#menu a.menu").click(function () {
                var text = $(this).find('.main-nav-text').find('span').text();
                var p = $(this).parents('.nav-collapse');
                // 一级菜单焦点
                $("#menu li.menu").removeClass("active");
                $(this).parent().addClass("active");
                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame") {
                    $("#left,#openClose").hide();
                    wSizeWidth();
                    $(".jericho_tab").hide();
                    $("#mainFrame").show();
                    return true;
                }
                // 左侧区域显示
                if(text === '首页'){
                    $('#left').removeClass('show-left');
                    $('#left').addClass('hide-left');
                    $('#left,#openClose').hide();
                }else{
                    $('#left').removeClass('hide-left');
                    $('#left').addClass('show-left');
                    $("#left,#openClose").show();
                    if (!$("#openClose").hasClass("close")) {
                        $("#openClose").click();
                    }


                }
                // 显示二级菜单
                var menuId = "#menu-" + $(this).attr("data-id");
                getTwoLevelMenu(this,menuId);

                // 大小宽度调整
                wSizeWidth();
                return false;
            });

            $("#menu").find("li.menu").each(function(index){
                if($(this).hasClass("active")){
                    var child_a = $(this).find('a.menu');
                    var open_close = $("#openClose");
                    $(child_a).click();
                    if (open_close.hasClass("close")) {
                        open_close.removeClass('close');
                    }
                    return;
                }
            })

            /////////////////////////////////////////////////// 初始化点击第一个一级菜单
            //$("#menu a.menu:first span").click();
            // <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
            $("#userInfo .dropdown-menu a").mouseup(function () {
                return addTab($(this), true);
            });// </c:if>

            //初始化页面时候显示首页信息
            //$("#openClose").click();

            // 鼠标移动到边界自动弹出左侧菜单
            /*$("#openClose").mouseover(function () {
                if ($(this).hasClass("open")) {
                    $(this).click();
                }
            });*/
            // 获取通知数目  <c:set var="oaNotifyRemindInterval" value="${fns:getConfig('oa.notify.remind.interval')}"/>

            /*function getNotifyNum() {
                $.get("\${ctx}/oa/oaNotify/self/count?updateSession=0&t=" + new Date().getTime(), function (data) {
                    var num = parseFloat(data);
                    if (num > 0) {
                        $("#notifyNum,#notifyNum2").show().html("(" + num + ")");
                    } else {
                        $("#notifyNum,#notifyNum2").hide()
                    }
                });
            }*/

            /*getNotifyNum(); //
            &lt;c:if test="\${oaNotifyRemindInterval ne '' && oaNotifyRemindInterval ne '0'}">
			setInterval(getNotifyNum,
            \${oaNotifyRemindInterval}); //
            &lt;/c:if>*/


        });

        /**
         *获取二级菜单
         * @param obj 一级菜单节点
         * @param menuId 一级菜单ID
         */
        function getTwoLevelMenu(obj,menuId) {
            if ($(menuId).length > 0) {
                $("#left .accordion").hide();
                $(menuId).show();
                // 初始化点击第一个二级菜单
                if (!$(menuId + " .accordion-body:first").hasClass('in')) {
                    $(menuId + " .accordion-heading:first a").click();
                }
                if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")) {
                    $(menuId + " .accordion-body a:first i").click();
                }
                // 初始化点击第一个三级菜单
                $(menuId + " .accordion-body li:first li:first a:first i").click();
            } else {
                // 获取二级菜单数据
                $.get($(obj).attr("data-href"), function (data) {
                    if (data.indexOf("id=\"loginForm\"") != -1) {
                        alert('未登录或登录超时。请重新登录，谢谢！');
                        top.location = "${ctx}";
                        return false;
                    }
                    $("#left .accordion").hide();
                    $("#left").append(data);
                    defineClick(menuId);
                    // 默认选中第一个菜单
                    $(menuId + " .accordion-body a:first i").click();
                    $(menuId + " .accordion-body li:first li:first a:first i").click();
                });
            }

        }

        /**
         * 预定义二级菜单点击事件
         * @param menuId 二级菜单ID
         */
        function  defineClick(menuId) {
            // 链接去掉虚框
            $(menuId + " a").bind("focus", function () {
                if (this.blur) {
                    this.blur()
                }
            });
            // 二级标题
            $(menuId + " .accordion-heading a").click(function () {
                $(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
                if (!$($(this).attr('data-href')).hasClass('in')) {
                    $(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
                }
            });
            // 二级内容
            $(menuId + " .accordion-body a").click(function () {
                $(menuId + " li").removeClass("active");
                $(menuId + " li i").removeClass("icon-white");
                $(this).parent().addClass("active");
                $(this).children("i").addClass("icon-white");
            });
            // 展现三级
            $(menuId + " .accordion-inner a").click(function () {
                var href = $(this).attr("data-href");
                if ($(href).length > 0) {
                    $(href).toggle().parent().toggle();
                    return false;
                }
                // <c:if test="${tabmode eq '1'}"> 打开显示页签
                return addTab($(this)); // </c:if>
            });
        }

        // <c:if test="${tabmode eq '1'}"> 添加一个页签
        function addTab($this, refresh) {
            $(".jericho_tab").show();
            $("#mainFrame").hide();
            $.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $.trim($this.text()),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
            return false;
        }// </c:if>


    </script>

</head>
<body>
<div id="main">
    <div id="header" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <!-- 	<div class="brand"> -->
            <%-- <img alt="${fns:getConfig('productName')}" src="${ctxStatic}/images/logo.png" style="width:160px;height:60px;" /> --%>
            <%-- <img alt="${fns:getConfig('productName')}" src="${ctxStatic}/images/Logo-1.png" style="width:160px;height:60px;" /> --%>

            <!-- 	<span class="font">财政综合管理系统</span>
                </div> -->
            <ul id="userControl" class="nav pull-right">

                <li id="userInfo" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">
                        <div class="main-nav-icon">
                            <i class="icon-user" style="font-size: 24px;   margin-top: 6px; opacity: 0.9;"></i>
                        </div>
                        <div>您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span>
                        </div>

                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a>
                        </li>
                        <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp; 修改密码</a>
                        </li>
                        <%--<li><a href="${ctx}/oa/oaNotify/self" target="mainFrame"><i class="icon-bell"></i>&nbsp; 我的通知
                            <span id="notifyNum2" class="label label-info hide"></span></a></li>--%>
                    </ul>
                </li>

                <li><a href="${ctx}/logout" title="退出登录">
                    <div>
                        <div class="main-nav-text"><i class="icon-off"
                                                      style="font-size: 24px;margin-top: 6px; opacity: 0.9;"></i></div>
                        <div>退&nbsp; &nbsp; 出</div>
                    </div>
                </a></li>
                <li id="themeSwitch" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换">

                        <!-- <div class="main-nav-text">
                        <i class="icon-th-large" style="font-size: 24px; margin-left: 15px;margin-top: 6px; opacity: 0.9;"></i></div>
                        <div style="margin-left:15px">主题切换</div> -->
                    </a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${fns:getDictList('theme')}" var="dict">
                            <li><a href="#"
                                   onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a>
                            </li>
                        </c:forEach>
                        <li>
                            <a href="javascript:cookie('tabmode','${tabmode eq '1' ? '0' : '1'}');location=location.href">${tabmode eq '1' ? '关闭' : '开启'}页签模式</a>
                        </li>
                    </ul>
                    <!--[if lte IE 6]>
                    <script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
                </li>
                <li>&nbsp;</li>
            </ul>
            <%-- <c:if test="${cookie.theme.value eq 'cerulean'}">
                <div id="user" style="position:absolute;top:0;right:0;"></div>
                <div id="logo" style="background:url(${ctxStatic}/images/logo_bg.jpg) right repeat-x;width:100%;">
                    <div style="background:url(${ctxStatic}/images/logo.jpg) left no-repeat;width:100%;height:70px;"></div>
                </div>
                <script type="text/javascript">
                    $("#productName").hide();$("#user").html($("#userControl"));$("#header").prepend($("#user, #logo"));
                </script>
            </c:if> --%>
            <div class="nav pull-left" style="margin-left: 2%">
                <img src="${ctxStatic}/images/logo.png">
            </div>
            <div class="nav-collapse" style="margin-left: 90%">
                <ul id="menu" class="nav" style="*white-space:nowrap;float:none;">
                    <c:set var="firstMenu" value="true"/>
                    <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                        <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
                            <li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
                                <c:if test="${empty menu.href}">
                                    <a class="menu main-nav" href="javascript:"
                                       data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}">
                                        <div class="main-nav-icon">
                                            <i class="icon-${menu.icon }"
                                               style="font-size: 24px;  margin-top: 6px; opacity: 0.99;"></i>
                                        </div>
                                        <div class="main-nav-text">
                                            <span>${menu.name}</span></div>
                                    </a>
                                </c:if>
                                <c:if test="${not empty menu.href}">
                                    <a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}"
                                       data-id="${menu.id}" target="mainFrame">
                                        <span>${menu.name}</span>
                                    </a>
                                </c:if>
                            </li>
                            <c:if test="${firstMenu}">
                                <c:set var="firstMenuId" value="${menu.id}"/>
                            </c:if>
                            <c:set var="firstMenu" value="false"/>
                        </c:if>
                    </c:forEach><%--
						<shiro:hasPermission name="cms:site:select">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fnc:getSite(fnc:getCurrentSiteId()).name}<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<c:forEach items="${fnc:getSiteList()}" var="site"><li><a href="${ctx}/cms/site/select?id=${site.id}&flag=1">${site.name}</a></li></c:forEach>
							</ul>
						</li>
						</shiro:hasPermission> --%>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </div>
    <div class="container-fluid">
        <div id="content" class="row-fluid">
            <div id="left">
                <%--
                    <iframe id="menuFrame" name="menuFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe> --%>
            </div>
            <div id="openClose" class="close">&nbsp;</div>
            <div id="right" style="height:100%;overflow-y: hidden">
                <iframe id="mainFrame" name="mainFrame" src="${ctx}/sys/index/desktop" style="overflow-y: hidden;"
                        scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
            </div>
        </div>
        <div id="footer" class="row-fluid">
            Copyright &copy; 2016-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')}
            - ${fns:getConfig('version')}
        </div>

    </div>
</div>
<!-- 即时聊天插件 -->
<%--<script type="text/javascript">
    var currentId = '${fns:getUser().id}';
    var currentName = '${fns:getUser().name}';
    var currentFace ='${fns:getUser().photo}';
    var url="${ctx}";
    var wsServer = 'ws://'+window.document.domain+':8668';
</script>
<script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script>
<script src="${ctxStatic}/layer-v2.3/layer/laydate/laydate.js"></script>--%>
<script src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
<%--
<script src="${ctxStatic}/layer-v2.3/layim/layer.min.js"></script>
--%>
<script type="text/javascript">
    var currentName = '${fns:getUser().name}';
    var leftWidth = 200; // 左侧窗口大小
    var tabTitleHeight = 33; // 页签的高度
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $("#header"), footerObj = $("#footer");
    var frameObj = $("#left, #openClose, #right, #right iframe");

    function wSize() {
        var minHeight = 1100, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({
            "overflow-x": strs[1] < minWidth ? "hidden" : "hidden",
            "overflow-y": strs[0] < minHeight ? "hidden" : "hidden"
        });
        mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
        $("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}">
        $(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
        wSizeWidth();
    }

    function wSizeWidth() {
        if (!$("#openClose").is(":hidden")) {
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
            $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 5);
        } else {
            $("#right").width("100%");
        }
    }// <c:if test="${tabmode eq '1'}">
    function openCloseClickCallBack(b) {
        $.fn.jerichoTab.resize();
    } // </c:if>

    $(function () {
        var menu_count = $('#menu li').length;
        $('#menu').parent().attr('style','margin-left: ' + (90 - parseInt(menu_count) * 5) + '%;');
        var runNum = '${taskState.runningNum}' === '' ? 0 : '${taskState.runningNum}';
        var aboutNum = '${taskState.aboutEndNum}' === '' ? 0 : '${taskState.aboutEndNum}';
        var overNum = '${taskState.overtimeNum}' === '' ? 0 : '${taskState.overtimeNum}';
        var stateId = '${taskState.id}';
        if(stateId !== ''){
            //示范一个公告层
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: '300px;'
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,resize: false
                ,btn: ['前往处理', '我知道了']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff;"><span style="font-size: 14px; font-weight: 300;">' + currentName + '</span>&nbsp;您好：<br><p style="text-indent: 1em">您当前有<span style="color:green">&nbsp;' + runNum + '&nbsp;</span>个填报任务未完成，距配置时间有<span style="color:yellow">&nbsp;' + aboutNum + '&nbsp;</span>个任务即将结束，有<span style="color:red">&nbsp;' + overNum + '&nbsp;</span>个任务已超时。</p></div>'
                ,success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    var query = btn.find('.layui-layer-btn0');
                    var query1 = btn.find('.layui-layer-btn1');
                    $(query).click(function(){
                        $('#mainFrame').attr('src','${ctx}/report/excelBudgetReport/listOfMy?isOver=0');
                        $(".jericho_tab").hide();
                        $("#mainFrame").show();
                        wSizeWidth();
                        updateTaskState();
                    });
                    $(query1).click(function(){
                        updateTaskState();
                    })
                }

            });
        }


        function updateTaskState(){
            $.ajax({
                url:'${ctx}/taskstate/taskState/updateCheck',
                type:'post',
                data:{
                    stateId:stateId
                },
                dataType:'json',
                success:function(data){

                }
            })
        }
    })
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>