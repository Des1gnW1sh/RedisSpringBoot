<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>帮助文档</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" href="${ctxStatic}/images/logo-title.png" type="image/x-icon">
    <style type="text/css">
        /* GitHub stylesheet for MarkdownPad (http://markdownpad.com) */
        /* Author: Nicolas Hery - http://nicolashery.com */
        /* Version: b13fe65ca28d2e568c6ed5d7f06581183df8f2ff */
        /* Source: https://github.com/nicolahery/markdownpad-github */

        /* RESET
        =============================================================================*/

        html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
            margin: 0;
            padding: 0;
            border: 0;
        }

        /* BODY
        =============================================================================*/

        body {
            font-family: Helvetica, arial, freesans, clean, sans-serif;
            font-size: 14px;
            line-height: 1.6;
            color: #333;
            background-color: #fff;
            padding: 20px;
            max-width: 960px;
            margin: 0 auto;
        }

        body > *:first-child {
            margin-top: 0 !important;
        }

        body > *:last-child {
            margin-bottom: 0 !important;
        }

        /* BLOCKS
        =============================================================================*/

        p, blockquote, ul, ol, dl, table, pre {
            margin: 15px 0;
        }

        /* HEADERS
        =============================================================================*/

        h1, h2, h3, h4, h5, h6 {
            margin: 20px 0 10px;
            padding: 0;
            font-weight: bold;
            -webkit-font-smoothing: antialiased;
        }

        h1 tt, h1 code, h2 tt, h2 code, h3 tt, h3 code, h4 tt, h4 code, h5 tt, h5 code, h6 tt, h6 code {
            font-size: inherit;
        }

        h1 {
            font-size: 28px;
            color: #000;
        }

        h2 {
            font-size: 24px;
            border-bottom: 1px solid #ccc;
            color: #000;
        }

        h3 {
            font-size: 18px;
        }

        h4 {
            font-size: 16px;
        }

        h5 {
            font-size: 14px;
        }

        h6 {
            color: #777;
            font-size: 14px;
        }

        body > h2:first-child, body > h1:first-child, body > h1:first-child + h2, body > h3:first-child, body > h4:first-child, body > h5:first-child, body > h6:first-child {
            margin-top: 0;
            padding-top: 0;
        }

        a:first-child h1, a:first-child h2, a:first-child h3, a:first-child h4, a:first-child h5, a:first-child h6 {
            margin-top: 0;
            padding-top: 0;
        }

        h1 + p, h2 + p, h3 + p, h4 + p, h5 + p, h6 + p {
            margin-top: 10px;
        }

        /* LINKS
        =============================================================================*/

        a {
            color: #4183C4;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        /* LISTS
        =============================================================================*/

        ul, ol {
            padding-left: 30px;
        }

        ul li > :first-child,
        ol li > :first-child,
        ul li ul:first-of-type,
        ol li ol:first-of-type,
        ul li ol:first-of-type,
        ol li ul:first-of-type {
            margin-top: 0px;
        }

        ul ul, ul ol, ol ol, ol ul {
            margin-bottom: 0;
        }

        dl {
            padding: 0;
        }

        dl dt {
            font-size: 14px;
            font-weight: bold;
            font-style: italic;
            padding: 0;
            margin: 15px 0 5px;
        }

        dl dt:first-child {
            padding: 0;
        }

        dl dt > :first-child {
            margin-top: 0px;
        }

        dl dt > :last-child {
            margin-bottom: 0px;
        }

        dl dd {
            margin: 0 0 15px;
            padding: 0 15px;
        }

        dl dd > :first-child {
            margin-top: 0px;
        }

        dl dd > :last-child {
            margin-bottom: 0px;
        }

        /* CODE
        =============================================================================*/

        pre, code, tt {
            font-size: 12px;
            font-family: Consolas, "Liberation Mono", Courier, monospace;
        }

        code, tt {
            margin: 0 0px;
            padding: 0px 0px;
            white-space: nowrap;
            border: 1px solid #eaeaea;
            background-color: #f8f8f8;
            border-radius: 3px;
        }

        pre > code {
            margin: 0;
            padding: 0;
            white-space: pre;
            border: none;
            background: transparent;
        }

        pre {
            background-color: #f8f8f8;
            border: 1px solid #ccc;
            font-size: 13px;
            line-height: 19px;
            overflow: auto;
            padding: 6px 10px;
            border-radius: 3px;
        }

        pre code, pre tt {
            background-color: transparent;
            border: none;
        }

        kbd {
            -moz-border-bottom-colors: none;
            -moz-border-left-colors: none;
            -moz-border-right-colors: none;
            -moz-border-top-colors: none;
            background: #DDDDDD linear-gradient(#F1F1F1, #DDDDDD) repeat-x;
            border-color: #DDDDDD #CCCCCC #CCCCCC #DDDDDD;
            border-image: none;
            border-radius: 2px 2px 2px 2px;
            border-style: solid;
            border-width: 1px;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            line-height: 10px;
            padding: 1px 4px;
        }

        /* QUOTES
        =============================================================================*/

        blockquote {
            border-left: 4px solid #DDD;
            padding: 0 15px;
            color: #777;
        }

        blockquote > :first-child {
            margin-top: 0px;
        }

        blockquote > :last-child {
            margin-bottom: 0px;
        }

        /* HORIZONTAL RULES
        =============================================================================*/

        hr {
            clear: both;
            margin: 15px 0;
            height: 0px;
            overflow: hidden;
            border: none;
            background: transparent;
            border-bottom: 4px solid #ddd;
            padding: 0;
        }

        /* TABLES
        =============================================================================*/

        table th {
            font-weight: bold;
        }

        table th, table td {
            border: 1px solid #ccc;
            padding: 6px 13px;
        }

        table tr {
            border-top: 1px solid #ccc;
            background-color: #fff;
        }

        table tr:nth-child(2n) {
            background-color: #f8f8f8;
        }

        /* IMAGES
        =============================================================================*/

        img {
            max-width: 100%
        }
    </style>
</head>
<body>
    <h3>一、 系统使用限制</h3>
    <ol>
        <li>
            <p>浏览器限制</p>
            <p>此系统必须使用360浏览器，点击下载（提供360浏览器集成到系统中，提供下载）</p>
        </li>
        <li>
            <p>插件安装</p>
            <p>安装步骤**提供下载地址（提供硕正插件，集成到系统中，提供下载，对客户不要让他到硕正官网去下载）</p>
        </li>
    </ol>
    <h3>二、 功能概述</h3>
    <blockquote>
        <p>
            日常办公中，经常需要面对内部股室、预算单位和下级部门进行数据收集、审核、汇总、统计工作。按照传统操作模式，数据收集填报采用【任务管理者下发EXCEL文件】—【数据填报点填报数据并上报EXCEL文件】—【任务管理者数据审核】—【数据汇总】的流程，当数据填报单位较多或是填报数据反复出现审核错误时，整个数据收集过程将变得十分复杂和漫长，会大量耗费人员精力。</p>
        <p>
            为简化数据采集，提升工作效率，让部门人员从重复、繁重的“收数”劳动中解脱出来，更好从事部门分析管理工作，需要得到网络数据报表系统的应用支撑。此子系统的功能应用特点决定了，它与平台中其他业务子系统关联不大而相对独立，因它不是业务系统，而是一个数据收集平台。</p>
    </blockquote>
    <h3>1. 新建报表任务</h3>
    <p>1.1 介绍填报内容中的：下发模式，下级填报单位，任务类型，强制公式审核。这些关键术语的含义及如何填写。</p>
    <pre><code>下发模式：直接下发，分级下发
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/1.png" alt="图片1.png"/></p>
    <pre><code>任务类型：收集任务，汇总任务
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/2.png" alt="图片2.png"/></p>
    <p>1.2 重点阐述如何创建报表（插件的使用，如：sheet页之间的引用，如何修改公式，如何限制用户填写等）</p>
    <h3>2. 常用模板管理</h3>
    <pre><code>重点介绍模板的作用，如何创建任务模板，如何使用任务模板创建报表填报任务
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/3.png" alt="图片3.png"/>
        创建报表任务，点击保存
        <img src="${ctxStatic}/helpdoc/img/4.png" alt="图片4.png"/></p>
    <pre><code>模板保存后可随时修改，进行发布（注意：结束时间不能小于当前时间）
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/5.png" alt="图片5.png"/></p>
    <h3>3. 进行中的报表任务</h3>
    <p>3.1 我发出的任务</p>
    <pre><code>此功能用于查看发出任务的状态以及填报情况，可对数据下级部门填报数据进行管理，根据任务类型的不同，
    系统会对数据自动进行汇总或收集处理。此功能中包含对各部门填报任务的互动（审核驳回）、强制公式修改操作，
    下面一一介绍此功能的用法和任务数据的一些计算方式
    </code></pre>

    <p>3.1.1 查看下级部门的填报情况
        <img src="${ctxStatic}/helpdoc/img/6.png" alt="图片6.png"/>
        可以查看各部门的填写情况
        <img src="${ctxStatic}/helpdoc/img/7.png" alt="图片7.png"/></p>
    <p>3.1.2 审核驳回下级填报数据</p>
    <pre><code>可随时检查填写情况并驳回
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/8.png" alt="图片8.png"/></p>
    <p>3.1.3 强制公式修改</p>
    <pre><code>报表下发后，也能进行公式的修改
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/9.png" alt="图片9.png"/></p>
    <p>3.1.4 任务进度计算方式</p>
    <pre><code>直观的任务填写进度
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/10.png" alt="图片10.png"/></p>
    <p>3.2 我参与的任务</p>
    <pre><code>在我参与的任务里面进行报表填写操作
    </code></pre>

    <p><img src="${ctxStatic}/helpdoc/img/11.png" alt="图片11.png"/></p>
    <h3>4. 已完成的报表任务</h3>

</body>
</html>
<!-- This document was created with MarkdownPad, the Markdown editor for Windows (http://markdownpad.com) -->
