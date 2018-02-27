<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>报表管理</title>
    <meta name="decorator" content="default"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/layui/css/layui.css" />
    <style type="text/css">
        .my_btn {
            display: inline-block;
            width: 75px;
            height: 20px;
            background: #3ca2e0;
            border-radius: 5px;
            text-align: center;
            line-height: 20px;
            color: #f5f5f5;
            text-decoration: none;
            cursor: pointer;
        }

        .my_btn:hover {
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            if (n) $("#pageNo").val(n);
            if (s) $("#pageSize").val(s);
            $("#searchForm").attr("action", "${ctx}/report/excelBudgetReport/listOfOwn?isOver=${isOver}");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body style="height: 100%;">
<%-- <ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/report/excelBudgetReport/">报表预算列表</a></li>
    <shiro:hasPermission name="report:excelBudgetReport:edit"><li><a href="${ctx}/report/excelBudgetReport/form">报表预算添加</a></li></shiro:hasPermission>
</ul> --%>
<form:form id="searchForm" modelAttribute="excelBudgetReport"
           action="${ctx}/report/excelBudgetReport/listOfOwnData?isOver=${isOver}" method="post"
           class="breadcrumb form-search">
    <div class="titlePanel">
        <div class="toolbar">
            <ul>
                <li class="btn-group">
                    <a class="btn btn-primary" href="javascript:void(0);"
                       onclick="contentView('${ctx}/report/excelBudgetReport/find?id=','查看',1200,530)"><i
                            class="icon-add"></i>查看</a>
                </li>
                <shiro:hasPermission name="excel:excelModel:edit">
                    <c:if test="${isOver eq 0}">
                        <li class="btn-group">
                            <a class="btn btn-primary" href="javascript:void(0);"
                               onclick="contentUpdate('${ctx}/report/excelBudgetReport/form?id=','修改',1200,530)"><i
                                    class="icon-add"></i>修改</a>
                        </li>
                        <li class="btn-group">
                            <a class="btn btn-primary" href="javascript:void(0);"
                               onclick="contentUpdate('${ctx}/report/excelBudgetReport/updateModel?id=','公式修改',1200,550)"><i
                                    class="icon-add"></i>公式修改</a>
                        </li>
                    </c:if>
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="batchDelete('${ctx}/report/excelBudgetReport/delete?ids=')"><i class="icon-add"></i>删除</a>
                    </li>
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="contentUpdate('${ctx}/report/excelBudgetReportCollect/list?excelBudgetReport.id=','填报状态查询',1200,550)"><i
                                class="icon-add"></i>填报状态查询</a>
                            <%--<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/report/excelBudgetReport/over?id=','预算结果查询',1000,500)" ><i class="icon-add"></i>预算结果查询</a>--%>
                    </li>
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="contentUpdate('${ctx}/report/excelBudgetReport/toSelectResult?id=','填报情况查看',1500,700)"><i
                                class="icon-add"></i>查看填报情况</a>
                    </li>
                </shiro:hasPermission>
            </ul>
        </div>
        <div class="title-search">
            <table class="form">
                <tbody>
                <tr>
                    <td class="formTitle">名称：</td>
                    <td class="formValue">
                        <form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
                    </td>
                    <td class="formTitle">开始创建时间：</td>
                    <td class="formValue">
                        <input name="beginCreateDate" type="text" readonly="readonly" maxlength="20"
                               class="input-medium Wdate"
                               value="<fmt:formatDate value="${excelBudgetReport.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>

                    </td>
                    <td class="formTitle">创建结束时间：</td>
                    <td class="formValue">
                        <input name="endCreateDate" type="text" readonly="readonly" maxlength="20"
                               class="input-medium Wdate"
                               value="<fmt:formatDate value="${excelBudgetReport.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </td>
                </tr>
                </tbody>
                <tbody>
                <tr>
                    <td colspan="8" style="text-align: center;">
                        <input class="btn btn-primary" type="submit" value="查询"/>
                        <a class="btn btn-primary" href="javascript:TagQueryRest('searchForm')">重置</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
</form:form>
<sys:message content="${message}"/>
<table id="dataGrid"></table>
<div class="pagination" id="dataGridPage"></div>
<link href="${ctxStatic}/jqGrid/4.7/css/ui.jqgrid.css" type="text/css" rel="stylesheet"/>
<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/layui/js/layui.js"></script>
<script type="text/javascript" src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
<script type="text/javascript" src="${ctxStatic}/layui/js/lay/modules/laypage.js"></script>
<script type="text/javascript" src="${ctxStatic}/layui/jsplug/jparticle.jquery.js"></script>
<script type="text/javascript">
    $('#dataGrid').dataGrid({
        columnModel: [
            {header: '名称', name: 'name', index: '', width: 100},
            {header: '任务类型', name: 'taskType', index: '', width: 100,
                formatter: function (val, obj, row, act) {
                    var type = row.taskType;
                    if(type === 1){
                        type = "汇总任务";
                    }else if(type === 0){
                        type = "收集任务";
                    }
                    return type;
                }},
            {header: '完成时间', name: 'overTimo', index: '', sortable: false, width: 100},
            {header: '更新时间', name: 'updateDate', index: '', sortable: false, width: 100},
            {
                header: '任务进度',
                name: 'progress',
                index: '',
                sortable: false,
                width: 60,
                formatter: function (val, obj, row, act) {
                    if (row.progress == null || row.progress == '') {
                        return "0.00%"
                    } else {
                        return row.progress;
                    }
                }
            },
            {
                header: '报表类型',
                name: 'taskType',
                index: '',
                sortable: false,
                width: 60,
                formatter: function (val, obj, row, act) {
                    if (row.taskType == 0) {
                        return "收集任务"
                    } else {
                        return "汇总任务";
                    }
                }
            },
            {
                header: '状态',
                name: 'status',
                index: '',
                sortable: false,/*hidden:true,*/
                width: 150,
                formatter: function (val, obj, row, act) {
                    var overTime = new Date(row.overTimo).getTime();
                    var nowTime = new Date().getTime();
                    var result = "<span";
                    if (nowTime > overTime) {
                        row.status = 1;
                        result += " style='color:red'>已结束";
                    } else if (row.isOver == 1) {
                        row.status = 1;
                        result += " style='color:red'>已结束";
                    } else if (row.isOver == 0) {
                        row.status = 0;
                        result += " style='color:green'>进行中";
                    }
                    result += "</span>";
                    return result;
                }
            },
            {header: '备注信息', name: 'remarks', index: '', sortable: false, width: 150},
            {
                header: '操作', name: 'actions', width: 220, fixed: true, sortable: false,
                formatter: function (val, obj, row, act) {
                    var type = row.taskType;
                    var actions = [];
                    if(type === 0){
                        return "";
                    }
                    var onclick = "contentView(\"${ctx}/report/excelBudgetReport/querySummary?id=" + row.id + "\",\"查看汇总数据\",1200,550)";
                    actions.push("<a onclick='" + onclick + "' class='btnList my_btn'>查看汇总数据</a>&nbsp;");
                    <%--onclick='" + onclickSum + "'   var onclickSum = "contentView(\"${ctx}/report/excelBudgetReport/querySummary?id=" + row.id + "\",\"查看汇总数据\",1200,550)";--%>
                    var isOver = '${isOver}';
                    if(isOver === '0'){
                        var isSum = row.submit === undefined || row.submit == null || row.submit === '';
                        var arg = "\"" + row.id + "\",\"" + row.progress + "\",\"" + row.status + "\"," + isSum;
                        actions.push("<a class='btnList my_btn' onclick='getSum(" + arg + ")'>进行汇总操作</a>&nbsp;");
                    }

                    return actions.join('');
                }
            }
        ],
        ajaxSuccess: function (data) {
            $(".jqgrow").each(function (i) {
                if ($(this).find("td").eq(5).html() == '&nbsp;' || $(this).find("td").eq(5).html() == '' || $(this).find("td").eq(5).html() == null) {
                    $(this).find("td").eq(7).find("a").eq(1).hide();
                }
            })
        },
        multiselect: true,
        ondblClickRow: function (rowid, iRow, iCol, e) {
            contentView("${ctx}/report/excelBudgetReport/find?id=" + rowid, "查看", 1200, 530);
        }
    });

    function getSum(id, progress, status, isSum) {
        if (status === '1') {
            layer.alert('该任务已结束，不能再进行汇总。');
            return;
        }
        if (progress === '100%' && isSum === false) {
            layer.alert('该任务已汇总完毕，不需要再进行汇总。');
            return;
        }
        var msg = '';
        if(progress === '0.00%'){
            layer.alert('该任务还没有上报数据，不需要汇总。');
            return;
        }else if(progress === '100%'){
            msg = '是否进行汇总？';
        }else{
            msg = '该任务没有全部上报，是否进行汇总？';
        }
        layer.open({
            content: msg
            , btn: ['确定', '关闭']
            , yes: function (index, layero) {
                $.ajax({
                    url: '${ctx}/report/excelBudgetReport/summary',
                    type: 'post',
                    data: {
                        id: id
                    },
                    dataType: 'json',
                    success: function (data) {
                        layer.alert(data);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        layer.alert(jqXHR.statusText + ":" + jqXHR.responseText);
                    }
                })
                layer.closeAll();
            }, cancel: function () {
            }
        });
    }
    $(function () {
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'Page'
                ,count: 50
                ,limit:30
                ,jump: function(obj){
                    findPage(obj.curr);
                }
            });
        });
    });
</script>
</body>
</html>