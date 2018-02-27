<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html >
<html>
<head>
    <meta charset="utf-8">
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/layui/common/layui/css/layui.css" media="all">
    <%----%><link rel="stylesheet" type="text/css" href="${ctxStatic}/layui/common/bootstrap/css/bootstrap.css" media="all">
    <link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" rel="stylesheet">
    <link href="${ctxStatic}/bootstrap/main/font-awesome.min.css" rel="stylesheet">
    <%--<link href="${ctxStatic}/bootstrap/main/learun-ui.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/layui/common/global.css">--%>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/layui/css/main.css" media="all">
</head>
<body>
<section class="larry-wrapper">
    <!-- overview -->
    <div class="row state-overview">
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol userblue"><i class="icon-file"></i>
                </div>
                <div class="value">
                    <p>本月收集报表数量</p>
                    <a href="#">
                        <h1 id="count1">${month}</h1>
                    </a>
                </div>
            </section>
        </div>
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol commred"><i class=" icon-file-alt"></i>
                </div>
                <div class="value">
                    <p>报表填报总数</p>
                    <a href="#">
                        <h1 id="count2">${all}</h1>
                    </a>
                </div>
            </section>
        </div>
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol articlegreen">
                    <i class="icon-time"></i>
                </div>
                <div class="value">
                    <p>进行中的任务</p>
                    <a herf="#">
                        <h1 id="count3">${running}</h1>
                    </a>
                </div>
            </section>
        </div>
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol rsswet">
                    <i class="icon-off"></i>
                </div>
                <div class="value">
                    <p>近三天结束的任务</p>
                    <a href="#">
                        <h1 id="count4">${willOver}</h1>
                    </a>
                </div>
            </section>
        </div>
    </div>
    <!-- overview end -->
    <div class="row">
        <div class="col-lg-12">
                <div class="col-lg-6">
                    <section class="panel">
                        <header class="panel-heading bm0">
                            <span class='span-title'>近一月任务完成趋势</span>
                            <span class="tools pull-right"><a href="javascript:;" class="iconpx-chevron-down"></a></span>
                        </header>
                        <div class="panel-body laery-seo-box">
                            <div class="larry-seo-stats" id="line"></div>
                        </div>
                    </section>
                </div>
                <div class="col-lg-6">
                    <section class="panel">
                        <header class="panel-heading bm0">
                            <span class='span-title'>任务类型占比统计</span>
                            <span class="tools pull-right"><a href="javascript:;" class="iconpx-chevron-down"></a></span>
                        </header>
                        <div class="panel-body laery-cpu-box">
                            <div class="larry-cpu-stats" style='height: 300px;' id="pie"></div>
                        </div>
                    </section>
                </div>
                <section class="panel">
                    <header class="panel-heading bm0">
                        <span class='span-title'>各部门任务填报数量</span>
                        <span class="tools pull-right"><a href="javascript:;" class="iconpx-chevron-down"></a></span>
                    </header>
                    <div class="panel-body larry-cjsl-box">
                        <div class="larry-cjsl-stats" id="bar" style='height:300px;'></div>
                    </div>
                </section>

        </div>
    </div>

</section>
<script type="text/javascript" src="${ctxStatic}/layui/jsplug/echarts.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/layui/common/layui/layui.js"></script>
<script type="text/javascript" src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url: '${ctx}/sys/index/getEChartsValue',
            type: 'post',
            data: {},
            dataType: 'json',
            success: function (data) {
                var line = echarts.init(document.getElementById('line'));
                var bar = echarts.init(document.getElementById('bar'));
                var pie = echarts.init(document.getElementById('pie'));
                var bar_series = [];
                var bar_data = [];
                var line_series = [];
                var line_data = [];
                var pie_data = [];
                var legendData = [];
                var datas = eval(data);
                var pieData = datas.pie;
                var barData = datas.bar;
                var lineData = datas.line;
                $.each(pieData,function(index,item){
                    var type = item.taskType;
                    var num = item.num;
                    var val = {
                        value: num,
                        name: type

                    };
                    pie_data.push(val);
                    legendData.push(type);
                });
                $.each(barData,function(index,item){
                    var name = item.NAME;
                    var num = item.num;
                    bar_data.push(name);
                    bar_series.push(num);
                });
                $.each(lineData,function(index,item){
                    var name = item.date;
                    var num = item.num;
                    line_data.push(name);
                    line_series.push(num);
                });
                //柱状图
                var bar_option = {
                    title: {
                        text: '各部门任务填报数量'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        show: true ,
                        data: ['填报数量']
                    },
                    //右上角工具条
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: [ 'line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: true ,
                            data: bar_data
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '填报数量',
                            type: 'bar',
                            barWidth : 30,//柱图宽度
                            data: bar_series
                        }
                    ]
                };
                //饼状图
                var pie_option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {d}%"
                    },
                    visualMap: {
                        show: false,
                        min: 500,
                        max: 600,
                        inRange: {
                            colorLightness: [0, 1]
                        }
                    },
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        right: 10,
                        top: 20,
                        bottom: 20,
                        data: legendData
                    },
                    series: [
                        {
                            name: '任务类型占比',
                            type: 'pie',
                            clockwise: 'true',
                            startAngle: '0',
                            radius: '60%',
                            center: ['50%', '50%'],
                            data: pie_data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]

                };
                //线状图
                var line_option = {
                    title: {
                        text: '每月任务发布量'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['任务发布量']
                    },
                    //右上角工具条
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: true,
                            data: line_data
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '填报数量',
                            type: 'line',
                            barWidth : 30,//柱图宽度
                            data: line_series
                        }
                    ]
                };

                bar.setOption(bar_option);
                pie.setOption(pie_option);
                line.setOption(line_option);
            },
            error: function () {
                layer.alert("加载数据失败...")
            }
        });

        layui.use(['jquery', 'layer', 'element'], function () {
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            window.element = layui.element();

            $('.panel .tools .iconpx-chevron-down').click(function () {
                var el = $(this).parents(".panel").children(".panel-body");
                if ($(this).hasClass("iconpx-chevron-down")) {
                    $(this).removeClass("iconpx-chevron-down").addClass("iconpx-chevron-up");
                    el.slideUp(200);
                } else {
                    $(this).removeClass("iconpx-chevron-up").addClass("iconpx-chevron-down");
                    el.slideDown(200);
                }
            })
        });

    });
    var getRandomColor = function(){
        return '#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).substr(-6);
    }
</script>
