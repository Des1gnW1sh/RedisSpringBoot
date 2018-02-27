<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>数据填报情况</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctxStatic}/ztree/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/ztree/js/jquery.ztree.core.js"></script>
    <script src="${ctxStatic}/supcan/binary/dynaload.js?20" type="text/javascript"></script>

    <style type="text/css">
        body{
            width: 100%;
        }
        .main{
            width: 100%;
            height: 620px;
            overflow: hidden;
        }
        .main_1{
            float: left;
            width: 300px;
            height: 90%;
            margin-top: 20px;
            margin-left: 20px;
            border-right: 2px solid #666666;
        }
        .main_2{
            height: 620px;
            width: 1150px;
            float: left;
        }
        .ztree{
            font-size: 16px;
        }
        .main_2_top{
            width: 100%;
            height: 20%;
            border: 1px solid red;
        }
        .main_2_bottom{
            width: 100%;
            height: 80%;
            border: 1px solid blue;
        }

    </style>
</head>
<body>
<div class="main">
    <div class="main_1">
        <iframe id="frame_left" width="100%" height="100%" frameborder="0" src="">
        </iframe>
    </div>
    <div class="main_2">
        <iframe id="frame_right" width="100%" height="100%"  frameborder="0" src="">
        </iframe>
    </div>
</div>
<script type="text/javascript">
        var parentId= "${requestScope.excelBudgetReport.id}";
        function test(par,children){
            $('#frame_right').attr('src','/a/report/excelBudgetReport/getChildXml.do?xmlId='+par+'&parentId='+parentId+'&children='+children);
        }

    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    $(function(){
        //zTreeObj = $.fn.zTree.init($("#tt"), setting);
        $('#frame_left').attr('src','/a/tag/treeselectown?url='+encodeURIComponent('/sys/office/treeSmallData.do?type=2&id=${requestScope.excelBudgetReport.id}&module=&checked=&extId=&isAll=false&office=&title=部门'));

    });


</script>
</body>

</html>