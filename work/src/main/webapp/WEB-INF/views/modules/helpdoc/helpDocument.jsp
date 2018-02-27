<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>帮助说明</title>
    <link rel="stylesheet" href="${ctxStatic}/helpdoc/css/helpdoc.css"/>
    <link rel="icon" href="${ctxStatic}/images/logo-title.png" type="image/x-icon">
</head>
<body style="background-color: #c7edcc;">
<center>
    <div>
        <table>
            <tr>
                <th><h1 style="color: cornflowerblue">报表操作说明</h1></th>
            </tr>
            <tr>
                <td>
                    <h3>功能由来</h3>
                    <p>
                        日常办公中，经常需要面对内部股室、预算单位和下级部门进行数据收集、审核、汇总、统计工作。按照传统操作模式，数据收集填报采用【任务管理者下发EXCEL文件】—【数据填报点填报数据并上报EXCEL文件】—【任务管理者数据审核】—【数据汇总】的流程，当数据填报单位较多或是填报数据反复出现审核错误时，整个数据收集过程将变得十分复杂和漫长，会大量耗费人员精力。</p>
                    <p>
                        为简化数据采集，提升工作效率，让部门人员从重复、繁重的“收数”劳动中解脱出来，更好从事部门分析管理工作，需要得到网络数据报表系统的应用支撑。此子系统的功能应用特点决定了，它与平台中其他业务子系统关联不大而相对独立，因它不是业务系统，而是一个数据收集平台。</p>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>基本功能</h3>
                    <p>1.可以使用系统自建或导入EXCEL表方式，新建数据采集或统计报表。</p>
                    <p>2.发布报表任务以及之后的每个填报环节，可以向指定人员和下一个任务接收者发送系统消息通知。</p>
                    <p>3.报表单元格可以设置相关控制规则。如是否必填、是否允许修改、限定只能填入文本或数值、数值取值范围等。</p>
                    <p>4.支持报表公式审核。根据报表发布者预先定义的审核公式，可以对数据报送情况进行审核，并提供相关错误信息。</p>
                </td>
            </tr>
            <tr>
                <td>
                    <h3><a href="#xjrw"> 新建任务</a></h3>
                    <b>新建报表任务，有两种方式：一是手动新建，二是使用任务模板新建。</b>
                    <p>1.手动新建，即在新建页面中手动发起新建报表任务（现有的任务新建模式和页面）。</p>
                    <p>2.从任务模板新建，即将需要经常用的新建报表任务存为一个模板，日后只需引入模板并作简单修改即可快速新建报表任务。</p>
                </td>
            </tr>
            <tr>
                <td>
                    <h3><a href="#bbxf"> 报表下发</a></h3>
                    <b>下发方式：直接下发、分级下发。</b>
                    <p>
                        直接下发：由报表任务发起人，直接将报表分发给所有报表填报人。报表填报人只能填报报表，不能再次分发任务。适用于报表任务发布者可直接确定填报对象的情形。此模式涉及用户为：报表任务发起人、报表任务填报人。</p>
                    <p>
                        分级下发：是报表任务发起人无法直接确定报表填报人对象时，而先将报表任务下发给下级报表任务人（可多次逐级下派任务），并最终具体确定报表填报人，落实报表填报任务。特点是，处于中间环节的报表任务人可再次下派报表任务。适用于报表任务发布者无法确定具体填报对象范围，而需要将其逐级细化，由下级或下下级报表任务人具体实施细化的情形。</p>
                </td>
            </tr>
            <tr>
                <td>
                    <h3><a href="#bbhz"> 报表汇总</a></h3>
                    <p>在【进行中的报表任务】中，在只有部分报表收集完毕的情况下，系统提供手动汇总功能，可手动选择汇总单位范围，以满足临时性提前报送任务要求。</p>
                    <p>汇总报表处理。无论是【进行中的报表任务】和【已完成的报表任务】，其汇总功能的具体实现.</p>
                </td>
            </tr>
            <tr>
                <td>
                    <h2><a href="#ct1">一·插件安装说明</a><---点击跳转</h2>
                </td>
            </tr>
            <tr>
                <td>
                    <h2><a href="#ct2">二·报表操作说明</a><---点击跳转</h2>
                </td>
            </tr>
            <tr>
                <td id="ct1">
                    <h3>请下载<<a href="${ctxStatic}/supcan/binary/supcan.crx" download="supcan.crx"
                               style="color: red;font-size: 24px">扩展插件</a>>按照下图安装
                    </h3>
                    <h3>注意：需使用<<a href="http://chrome.360.cn/" style="color: red;font-size: 24px">360浏览器</a>></h3>
                </td>
            </tr>
            <tr>
                <td><img src="${ctxStatic}/helpdoc/img/01.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td id="ct2">
                    <h3>进入登录页面</h3>
                    <img src="${ctxStatic}/helpdoc/img/001.png" width="800" height="400">
                </td>
            </tr>

            <tr>
                <td>
                    <h3 id="ct4">登陆之后，选择<a href="#ct4" style="color: red;font-size: 24px">网络数据报表</a>选项</h3>
                    <p>网络数据报表-->新建任务-->创建数据报表</p>
                    <img src="${ctxStatic}/helpdoc/img/02.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td>
                    <h3 id="bbxf">选择需要下发的部门</h3>
                    <img src="${ctxStatic}/helpdoc/img/03.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td>
                    <h3 id="xjrw">新建表单</h3>
                    <p>选择新建表单操作</p>
                    <img src="${ctxStatic}/helpdoc/img/002.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td>
                    <p>选择模板表单操作</p>
                    <img src="${ctxStatic}/helpdoc/img/003.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td>
                    <h3 id="bbhz">据图提示，选择需要汇总的单元格</h3>
                    <img src="${ctxStatic}/helpdoc/img/04.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td>
                    <h3 id="ct3">下发后，各部门在
                        <<a href="#ct3" style="color: red;font-size: 24px">我参与的报表</a>>中填写报表
                    </h3>
                    <img src="${ctxStatic}/helpdoc/img/05.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td><img src="${ctxStatic}/helpdoc/img/06.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td>
                    <h3>注：红色标识框才能修改</h3>
                    <img src="${ctxStatic}/helpdoc/img/07.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td><img src="${ctxStatic}/helpdoc/img/08.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td><img src="${ctxStatic}/helpdoc/img/09.png" width="800" height="400"></td>
            </tr>
            <tr>
                <td><img src="${ctxStatic}/helpdoc/img/010.png" width="800" height="400"></td>
            </tr>
        </table>
    </div>
</center>
</body>
</html>
<SCRIPT Language=VBScript><!--

//-->
</SCRIPT>