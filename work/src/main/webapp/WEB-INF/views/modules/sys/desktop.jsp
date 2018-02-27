<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的桌面</title>
    <!--框架必需start-->

    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
    <link href="${ctxStatic}/bootstrap/main/font-awesome.min.css" rel="stylesheet">

    <link href="${ctxStatic}/bootstrap/main/learun-ui.css" rel="stylesheet">

    <script src="${ctxStatic}/bootstrap/main/learun-ui.js"></script>
    <script src="${ctxStatic}/bootstrap/main/learun-form.js"></script>

    <!--框架必需end-->
    <!--第三方统计图start-->
    <%--     <script src="${ctxStatic}/highcharts/highcharts.js"></script>
        <script src="${ctxStatic}/highcharts/highcharts-more.js"></script>
        <script src="${ctxStatic}/highcharts/modules/exporting.js"></script>
     --%>    <!--第三方统计图end-->
    <script>
        $(document).ready(function () {
            InitialPage();
            /*             LoadInterfaceVisit();
                         LoadDepartmentApp();
             */
            /*beiwanglu();
            daiban();
           tongzhi();
           xuexi();
           gongwen();*/
        });

        /**
         * 内容 新增修改
         * @param url
         * @param text
         * @param width
         * @param height
         */
        function contentSave(url, text, width, height) {
            top.$.jBox.open('iframe:' + url, text, width, height, {
                dragLimit: true,
                showScrolling: true,
                persistent: false,
                opacity: 0.5,
                loaded: function (h) {
                    top.$("#jbox-content").css("overflow-y", "hidden");
                }
            });
        }

        //备忘录
        function beiwanglu() {
            //备忘录
            $.ajax({
                url: '${ctx}/memo/sysMemo/homeGet',
                success: function (data) {
                    if (data.length > 0) {
                        var conut = data.length
                        $('#beiWangCount').html(conut)
                        if (data.length <= 5) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/memo/sysMemo/homgForm?id=" + data[i].id + "','备忘录',740,500)\" style='text-decoration: none;'>[日历]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].memoDate + "</label>" +
                                    "</div>"
                            }
                            $("#beiwang").append(html)
                        } else {
                            var html = "";
                            for (var i = 0; i < 5; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/memo/sysMemo/homgForm?id=" + data[i].id + "','备忘录',740,500)\" style='text-decoration: none;'>[日历]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].memoDate + "</label>" +
                                    "</div>"
                            }
                            $("#beiwang").append(html)
                        }
                    } else {
                        $('#beiWangCount').html(0)
                    }
                }
            })
        }

        //代办事物
        function daiban() {
            $.ajax({
                url: '${ctx}/basesys/baseSysAssignment/stayHome',
                success: function (data) {
                    if (data.length > 0) {
                        var conut = data.length
                        $('#daibanCount').html(conut)
                        if (data.length <= 5) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/basesys/baseSysAssignmentMsg/?assignmentId=" + data[i].id + "','待办任务',1200,800)\" style='text-decoration: none;'>[任务]&nbsp;&nbsp;&nbsp;" + data[i].assignmentName + "</a>" +
                                    "<label style='float: right'>" + data[i].startDateValue + "</label>" +
                                    "</div>"
                            }
                            $("#daiban").append(html)
                        } else {
                            var html = "";
                            for (var i = 0; i < 5; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/basesys/baseSysAssignmentMsg/?assignmentId=" + data[i].id + "','待办任务',1200,800)\" style='text-decoration: none;'>[任务]&nbsp;&nbsp;&nbsp;" + data[i].assignmentName + "</a>" +
                                    "<label style='float: right'>" + data[i].startDateValue + "</label>" +
                                    "</div>"
                            }
                            $("#daiban").append(html)
                        }
                    } else {
                        $('#daibanCount').html(0)
                    }
                }
            })
        }

        //通知通告
        function tongzhi() {
            $.ajax({
                url: '${ctx}/oa/oaNotify/selfHome',
                success: function (data) {
                    if (data.length > 0) {
                        var conut = data.length
                        $('#tongzhiCount').html(conut)
                        if (data.length <= 5) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/oa/oaNotify/form?id=" + data[i].id + "','通知',800,600)\" style='text-decoration: none;'>[任务]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].dateValue + "</label>" +
                                    "</div>"
                            }
                            $("#tongzhi").append(html)
                        } else {
                            var html = "";
                            for (var i = 0; i < 5; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/oa/oaNotify/form?id=" + data[i].id + "','通知',800,600)\" style='text-decoration: none;'>[任务]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].dateValue + "</label>" +
                                    "</div>"
                            }
                            $("#tongzhi").append(html)
                        }
                    } else {
                        $('#tongzhiCount').html(0)
                    }
                }
            })
        }

        //学习交流
        function xuexi() {
            $.ajax({
                url: '${ctx}/sys/sysLearn/allListHome',
                success: function (data) {
                    if (data.length > 0) {
                        var conut = data.length
                        $('#xuexiCount').html(conut)
                        if (data.length <= 5) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/sys/sysLearn/discuss?id=" + data[i].id + "','待办任务',800,700)\" style='text-decoration: none;'>[交流]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].dateValue + "</label>" +
                                    "</div>"
                            }
                            $("#xuexi").append(html)
                        } else {
                            var html = "";
                            for (var i = 0; i < 5; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/sys/sysLearn/discuss?id=" + data[i].id + "','待办任务',800,700)\" style='text-decoration: none;'>[交流]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].dateValue + "</label>" +
                                    "</div>"
                            }
                            $("#xuexi").append(html)
                        }
                    } else {
                        $('#xuexiCount').html(0)
                    }
                }
            })
        }

        //公文管理
        function gongwen() {
            $.ajax({
                url: '${ctx}/sys/sysDocument/allListHome',
                success: function (data) {
                    console.log(data)
                    if (data.length > 0) {
                        var conut = data.length
                        $('#gongwenCount').html(conut)
                        if (data.length <= 5) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/sys/sysDocument/getDetail?id=" + data[i].id + "','待办任务',800,700)\" style='text-decoration: none;'>[公文]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].dateValue + "</label>" +
                                    "</div>"
                            }
                            $("#gongwen").append(html)
                        } else {
                            var html = "";
                            for (var i = 0; i < 5; i++) {
                                html += "<div style='line-height: 30px; border-bottom: 1px solid #ccc;'>" +
                                    "<a href='#' onclick=\"contentSave('${ctx}/sys/sysDocument/getDetail?id=" + data[i].id + "','待办任务',800,700)\" style='text-decoration: none;'>[公文]&nbsp;&nbsp;&nbsp;" + data[i].title + "</a>" +
                                    "<label style='float: right'>" + data[i].dateValue + "</label>" +
                                    "</div>"
                            }
                            $("#gongwen").append(html)
                        }
                    } else {
                        $('#gongwenCount').html(0)
                    }
                }
            })
        }

        //初始化
        function InitialPage() {
            $('#desktop').height($(window).height() - 22);
            $(window).resize(function (e) {
                window.setTimeout(function () {
                    $('#desktop').height($(window).height() - 22);
                }, 200);
                e.stopPropagation();
            });
        }

        //访问流量图表
        function LoadInterfaceVisit() {
            var chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'piecontainer',
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    defaultSeriesType: 'pie'
                },
                title: {
                    text: ''
                },
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: ' + this.percentage + ' %';
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true, //点击切换
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            formatter: function () {
                                return '<b>' + this.point.name + '</b>: ' + this.percentage + ' %';
                            }
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    data: [
                        ['市委', 10],
                        ['市委办公室', 10],
                        ['市委目标督察办', 10],
                        ['市委政研室', 10],
                        ['市委保密局', 10],
                        ['市委防邪办', 10],
                        ['市委教育办', 40]
                    ]
                }]
            });
        }

        //部门应用图表
        function LoadDepartmentApp() {
            $('#container').highcharts({
                chart: {
                    type: 'spline'
                },
                title: {
                    text: ''
                },
                xAxis: {
                    categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                },
                yAxis: {
                    title: {
                        text: '额度（元）'
                    },
                    labels: {
                        formatter: function () {
                            return this.value + '元'
                        }
                    }
                },
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    crosshairs: true,
                    shared: true
                },
                plotOptions: {
                    spline: {
                        marker: {
                            radius: 4,
                            lineColor: '#666666',
                            lineWidth: 1
                        }
                    }
                },
                series: [{
                    name: '预算',
                    marker: {
                        symbol: 'square'
                    },
                    data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 23.3, 18.3, 13.9, 9.6, 1]

                }, {
                    name: '实际',
                    marker: {
                        symbol: 'diamond'
                    },
                    data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
                }]
            });

        }

        //跳转到指定模块菜单
        function OpenNav(Navid) {
            top.$("#nav").find('a#' + Navid).trigger("click");
        }
    </script>
</head>
<body>
<div class="border" id="desktop" style="margin: 10px 10px 0 10px; background: #fff; overflow: auto;">
    <div class="portal-panel">
        <div class="row">
            <div class="portal-panel-title">
                <i class="fa fa-balance-scale"></i>&nbsp;&nbsp;统计指标
            </div>
            <div class="portal-panel-content" style="margin-top: 15px; overflow: hidden;">
                <div class="row">
                    <div style="width: 20%; position: relative; float: left;">
                        <div class="task-stat" style="background-color: #578ebe;">
                            <div class="visual">
                                <i class="fa fa-pie-chart"></i>
                            </div>
                            <div class="details">
                                <div class="number" id='beiWangCount'>
                                </div>
                                <div class="desc">
                                    备忘提醒
                                </div>
                            </div>
                            <a class="more" style="background-color: #4884b8;" href="${ctx}/memo/calendar/list">查看更多 <i
                                    class="fa fa-arrow-circle-right"></i>
                            </a>
                        </div>
                    </div>
                    <div style="width: 20%; position: relative; float: left;">
                        <div class="task-stat" style="background-color: #e35b5a;">
                            <div class="visual">
                                <i class="fa fa-bar-chart-o"></i>
                            </div>
                            <div class="details">
                                <div class="number" id="daibanCount">
                                </div>
                                <div class="desc">
                                    待办事务
                                </div>
                            </div>
                            <a class="more" style="background-color: #e04a49;"
                               href="${ctx}/basesys/baseSysAssignment/stayList">查看更多 <i
                                    class="fa fa-arrow-circle-right"></i>
                            </a>
                        </div>
                    </div>
                    <div style="width: 20%; position: relative; float: left;">
                        <div class="task-stat" style="background-color: #44b6ae;">
                            <div class="visual">
                                <i class="fa fa-windows"></i>
                            </div>
                            <div class="details">
                                <div class="number" id="tongzhiCount">
                                    3
                                </div>
                                <div class="desc">
                                    通知通告
                                </div>
                            </div>
                            <a class="more" style="background-color: #3ea7a0;" href="${ctx}/oa/oaNotify/self">查看更多 <i
                                    class="fa fa-arrow-circle-right"></i>
                            </a>
                        </div>
                    </div>
                    <div style="width: 20%; position: relative; float: left;">
                        <div class="task-stat" style="background-color: #8775a7;">
                            <div class="visual">
                                <i class="fa fa-globe"></i>
                            </div>
                            <div class="details">
                                <div class="number" id="xuexiCount">
                                </div>
                                <div class="desc">
                                    学习交流
                                </div>
                            </div>
                            <a class="more" style="background-color: #7c699f;" href="${ctx}/sys/sysLearn/allList">查看更多
                                <i class="fa fa-arrow-circle-right"></i>
                            </a>
                        </div>
                    </div>
                    <div style="width: 20%; position: relative; float: left;">
                        <div class="task-stat" style="background-color: #3598dc;">
                            <div class="visual">
                                <i class="fa fa-globe"></i>
                            </div>
                            <div class="details">
                                <div class="number" id="gongwenCount">
                                </div>
                                <div class="desc">
                                    公文管理
                                </div>
                            </div>
                            <a class="more" style="background-color: #258fd7;" href="${ctx}/sys/sysDocument/allList">查看更多
                                <i class="fa fa-arrow-circle-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="overflow: hidden; margin-bottom: 10px;">
            <div style="width: 50%; float: left;">
                <div class="portal-panel-title">
                    <i class="fa fa-coffee"></i>&nbsp;&nbsp;备忘提醒（Top 5）
                </div>
                <div class="portal-panel-content" id='beiwang'
                     style="overflow: hidden; padding-top: 20px; padding-left: 30px; padding-right: 50px;">
                </div>
            </div>
            <div style="width: 50%; float: left;">
                <div class="portal-panel-title">
                    <i class="fa fa-bullhorn"></i>&nbsp;&nbsp;待办事务（Top 5）
                </div>
                <div class="portal-panel-content" id="daiban"
                     style="overflow: hidden; padding-top: 20px; padding-left: 30px; padding-right: 50px;">
                </div>
            </div>
        </div>
        <div class="row" style="overflow: hidden; margin-bottom: 10px;">
            <div style="width: 50%; float: left;">
                <div class="portal-panel-title">
                    <i class="fa fa-coffee"></i>&nbsp;&nbsp;通知通告（Top 5）
                </div>
                <div class="portal-panel-content" id="tongzhi"
                     style="overflow: hidden; padding-top: 20px; padding-left: 30px; padding-right: 50px;">
                </div>
            </div>
            <div style="width: 50%; float: left;">
                <div class="portal-panel-title">
                    <i class="fa fa-bullhorn"></i>&nbsp;&nbsp;学习交流（Top 5）
                </div>
                <div class="portal-panel-content" id="xuexi"
                     style="overflow: hidden; padding-top: 20px; padding-left: 30px; padding-right: 50px;">
                </div>
            </div>
        </div>
        <div class="row" style="overflow: hidden; margin-bottom: 10px;">
            <div style="width: 50%; float: left;">
                <div class="portal-panel-title">
                    <i class="fa fa-coffee"></i>&nbsp;&nbsp;公文管理（Top 5）
                </div>
                <div class="portal-panel-content" id="gongwen"
                     style="overflow: hidden; padding-top: 20px; padding-left: 30px; padding-right: 50px;">
                </div>
            </div>
            <div style="width: 50%; float: left;">
                <div class="portal-panel-title">
                    <i class="fa fa-bullhorn"></i>&nbsp;&nbsp;任务管理（Top 5）
                </div>
                <div class="portal-panel-content"
                     style="overflow: hidden; padding-top: 20px; padding-left: 30px; padding-right: 50px;">
                    <div style="line-height: 30px; border-bottom: 1px solid #ccc;">
                        <a href="${ctx}/basesys/baseSysAssignment/stayList" style="text-decoration: none;">[完成]&nbsp;&nbsp;&nbsp;端午节放假安排</a>
                        <label style="float: right">2016-06-01</label>
                    </div>
                    <div style="line-height: 30px; border-bottom: 1px solid #ccc;">
                        <a href="${ctx}/basesys/baseSysAssignment/stayList" style="text-decoration: none;">[完成]&nbsp;&nbsp;&nbsp;苏州分公司总经理任命书</a>
                        <label style="float: right">2016-05-28</label>
                    </div>
                    <div style="line-height: 30px; border-bottom: 1px solid #ccc;">
                        <a href="${ctx}/basesys/baseSysAssignment/stayList" style="text-decoration: none;">[完成]&nbsp;&nbsp;&nbsp;公司开票信息更新通知</a>
                        <label style="float: right">2016-05-23</label>
                    </div>
                    <div style="line-height: 30px; border-bottom: 1px solid #ccc;">
                        <a href="${ctx}/basesys/baseSysAssignment/stayList" style="text-decoration: none;">[完成]&nbsp;&nbsp;&nbsp;全体员工体检通知</a>
                        <label style="float: right">2016-04-06</label>
                    </div>
                    <div style="line-height: 30px; border-bottom: 1px solid #ccc;">
                        <a href="${ctx}/basesys/baseSysAssignment/stayList" style="text-decoration: none;">[完成]&nbsp;&nbsp;&nbsp;华东区营销总监任命书</a>
                        <label style="float: right">2016-04-18</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
