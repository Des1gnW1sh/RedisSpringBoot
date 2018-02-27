<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>常用模板管理</title>
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
            $("#searchForm").attr("action", "${ctx}/report/excelBudgetReportMode/getlistdata");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body style="height: 1100px;">
 <ul class="nav nav-tabs" style="float: right;margin-right: 100px">
    <%--<li><a href="${ctx}/report/excelBudgetReportMode/form">报表模板添加</a></li>--%>
     <li class="btn-group">
         <a class="btn btn-primary" href="javascript:void(0);"
            onclick="contentAdd('${ctx}/report/excelBudgetReportMode/form','报表模板添加',1500,700)"><i
                 class="icon-add"></i>报表模板添加</a>
     </li>
</ul>

<%--<form:form id="searchForm" modelAttribute="excelBudgetReportMode"
           action="${ctx}/report/excelBudgetReportMode/getlistdata"
           method="post" class="breadcrumb form-search">--%>
<form:form id="searchForm" modelAttribute="excelBudgetReportMode" action="${ctx}/report/excelBudgetReportMode/getlistdata" method="post" class="breadcrumb form-search">
  <%--  <div class="titlePanel">
        <div class="toolbar">
            <ul>
                <li class="btn-group">
                    <a class="btn btn-primary" href="javascript:void(0);"
                       onclick="contentView('${ctx}/report/excelBudgetReportMode/get?id=','查看',1200,530)"><i
                            class="icon-add"></i>查看</a>
                </li>
                <shiro:hasPermission name="excel:excelModel:edit">
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="contentUpdate('${ctx}/report/excelBudgetReportMode/form?id=','修改',1200,530)"><i
                                class="icon-add"></i>修改</a>
                    </li>
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="batchDelete('${ctx}/report/excelBudgetReportMode/delete?ids=')"><i class="icon-add"></i>删除</a>
                    </li>
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="contentUpdate('${ctx}/report/excelBudgetReportCollect/list?excelBudgetReport.id=','预算状态查询',1200,550)"><i
                                class="icon-add"></i>填报状态查询</a>
                            &lt;%&ndash;<a  class="btn btn-primary" href="javascript:void(0);"onclick="contentUpdate('${ctx}/report/excelBudgetReport/over?id=','预算结果查询',1000,500)" ><i class="icon-add"></i>预算结果查询</a>&ndash;%&gt;
                    </li>
                    <li class="btn-group">
                        <a class="btn btn-primary" href="javascript:void(0);"
                           onclick="contentUpdate('${ctx}/report/excelBudgetReportMode/toSelectResult?id=','填报情况查看',1500,700)"><i
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
                               value="<fmt:formatDate value="${excelBudgetReportMode.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>

                    </td>
                    <td class="formTitle">创建结束时间：</td>
                    <td class="formValue">
                        <input name="endCreateDate" type="text" readonly="readonly" maxlength="20"
                               class="input-medium Wdate"
                               value="<fmt:formatDate value="${excelBudgetReportMode.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
    </div>--%>
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
            {header: '任务类型', name: 'tasktype', index: '', width: 100,
                formatter: function (val, obj, row, act) {
                    var type = row.tasktype;
                    if(type === '1'){
                        type = "汇总任务";
                    }else if(type === '0'){
                        type = "收集任务";
                    }
                    return type;
                }},
            {header: '完成时间', name: 'finishtime', index: '', sortable: false, width: 100},
            {header: '更新时间', name: 'updateDate', index: '', sortable: false, width: 100},
            {header: '备注说明', name: 'text', index: '', sortable: false, width: 100},
            {
                header: '操作', name: 'actions', width: 320, fixed: true, sortable: false,
                formatter: function (val, obj, row, act) {
                    var actions = [];
                    var onclick = "contentView(\"${ctx}/report/excelBudgetReportMode/form?id=" + row.id + "\",\"修改\",1200,550)";
                    actions.push("<a style='margin-left: 20px' onclick='" + onclick + "' class='btnList my_btn'>修改</a>&nbsp;");
                    <%--onclick='" + onclickSum + "'   var onclickSum = "contentView(\"${ctx}/report/excelBudgetReport/querySummary?id=" + row.id + "\",\"查看汇总数据\",1200,550)";--%>
                    var isSum = row.submit === undefined || row.submit == null || row.submit === '';
                    var arg = "\"" + row.id + "\"";
                    var args = "\"" + row.id + "\",\"" + row.finishtime + "\"";
                    actions.push("<a class='btnList my_btn' onclick='deleteMode(" + arg + ")'>删除</a>&nbsp;");
                    actions.push("<a class='btnList my_btn' onclick='release(" + args + ")'>发布</a>&nbsp;");

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
        multiselect: true
    });
    function deleteMode(id){
           if(id != null && id != ""){
               layer.open({
                   content: '是否删除'
                   , btn: ['确定', '取消']
                   , yes: function (index, layero) {
                       $.ajax({
                           url: '${ctx}/report/excelBudgetReportMode/delete',
                           type: 'post',
                           data: {
                               ids: id
                           },
                           dataType: 'json',
                           success: function (data) {
//                               console.log(data)
                               layer.open({
                                   content: data.msg,
                                   time: 20000, //20s后自动关闭
                                   btn: ['确定'],
                                   yes: function(){
                                       history.go(0);
                                 }
                               });
//
                           }
                       })
                       layer.closeAll();
                   }, cancel: function () {
                   }
               });
           }
    }

    function release(id,finishtime){
       var finishtimedate =  new Date(finishtime);

            if(id != null && id != ""){
                var myDate = new Date();
                if(finishtimedate <= myDate){
                   var index =  layer.open({
                        content: "完成时间不能小于当前时间!",
                        time: 20000, //20s后自动关闭
                        btn: ['确定'],
                        yes: function(){
                           layer.close(index);
                        }
                    });
                }else{
                   var indexTo = layer.open({
                        content: "确定发布?",
                        time: 20000, //20s后自动关闭
                        btn: ['确定','取消'],
                        yes: function(){
                            layer.close(indexTo);
                            loading('正在加载文件，请稍等...');
                            $.ajax({
                                url: '${ctx}/report/excelBudgetReport/release',
                                type: 'post',
                                data: {
                                    id: id
                                },
                                dataType: 'json',
                                success:function(data){
                                    if(data.success){
                                        closeLoading();
                                        var successlay =  layer.open({
                                            content: data.msg,
                                            time: 20000, //20s后自动关闭
                                            btn: ['确定'],
                                            yes: function(){
                                                layer.close(successlay);
                                            }
                                        });
                                    }else{
                                        var errorlay =  layer.open({
                                            content: data.msg,
                                            time: 20000, //20s后自动关闭
                                            btn: ['确定'],
                                            yes: function(){
                                                layer.close(errorlay);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            }
    }
    function convertDateFromString(dateString) {
        if (dateString) {
            var arr1 = dateString.split(" ");
            var sdate = arr1[0].split('-');
            var date = new Date(sdate[0], sdate[1]-1, sdate[2]);
            return date;
        }
    }
    function go(){
        setTimeout($("#searchForm").submit(),5000);
    }
    $(function () {
    });
</script>
</body>
</html>