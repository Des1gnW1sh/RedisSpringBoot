<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>数据选择</title>
    <meta name="decorator" content="blank"/>
    <script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/style.css" />
    <style type="text/css">
        body {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body style="height: 100%">
<div style="width:1150px;margin-top: 60px">

    <div class="tabPanel">
        <ul>
            <li class="hit">本级查看</li>
            <li>下级汇总</li>
        </ul>
        <div class="panes" style="width:1152px;">
            <div class="pane" style="display:block;">
                <input id="leftVal" type="hidden" value="${requestScope.childrenExcel}"/>
                <input id="rightVal" type="hidden" value="${requestScope.parentExcel}"/>
                <input id="taskType" type="hidden" value="${requestScope.taskType}"/>
                <input id="MB" type="hidden" value="${requestScope.excelMb}"/>
                <input id="status" type="hidden" value="${requestScope.status}"/>
                <script>
                    insertReport('AF', 'Border=3D;Rebar=Property;Property=2,309;;WorkMode=uploadRuntime');
                </script>
            </div>

        </div>
    </div>

</div>
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
<script type="text/javascript">
    var mb = $("#MB").val();
    var leftvalue = $("#leftVal").val();
    var rightVal = $("#rightVal").val();
    var taskType = $("#taskType").val();
    var status = $("#status").val();
    function OnReady(id) {
        AF.func("build", mb);
        AF.func('Swkrntpomzqa','1,2')
        setExcelVal(leftvalue)
    }
    function OnEvent(id, Event, p1, p2, p3, p4) {

    }
    $(function () {
        var parent = $("#rightVal").val();
        if(taskType != "" && taskType != null && taskType != 0){
            if(parent == "" || parent == null){
                $(".tabPanel ul li").eq(1).hide();
            }else{
                $(".tabPanel ul li").eq(1).show();
            }
        }else{
            $(".tabPanel ul li").eq(1).hide();
        }

        $('.tabPanel ul li').eq(1).click(function(){
            $(this).addClass('hit').siblings().removeClass('hit');
            setExcelVal(rightVal)
        })
        $('.tabPanel ul li').eq(0).click(function(){
            $(this).addClass('hit').siblings().removeClass('hit');
            if(status == '1'){
                setExcelVal(leftvalue)
            }else{
                AF.func("build", mb);
            }
        })
    })

    function setExcelVal(valueXml){
        if(valueXml != null && valueXml !== ''){
            AF.func("SetUploadXML", valueXml);
        }
    }
</script>
</body>

</html>