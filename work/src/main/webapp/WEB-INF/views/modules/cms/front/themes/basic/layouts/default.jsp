<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title default="欢迎光临"/> - ${site.title} - 财务综合管理系统</title>
	<%-- <%@include file="/WEB-INF/views/modules/cms/front/include/head.jsp" %>  --%>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<!--头部单独css-->
	<link href="${ctxStatic}/index/css/head.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/index/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	
	<link href="${ctxStatic}/index/cms/css/area-style.css" rel="stylesheet" type="text/css">
	<sitemesh:head/>
</head>
<body >
<!--头部开始-->
<div style="width:100%;height: 200px;">
	<!-- <div class="top">
	  <ul>
	    站群导航
	    <li class="green-tit">网站导航</li>
	    <li class="xzqh"><a href="#" target="_blank">网站链接一</a><a href="#" target="_blank">网站链接二</a><a href="#" target="_blank">网站链接三</a></li>
	  </ul>
	</div> -->
	<!--banner&搜索&标题-->
	<div class="head-bnr">
	  <ul>
	    <li class="icon-tit">
    	 <c:choose>
   			<c:when test="${not empty site.logo}">
   				<img alt="${site.title}" src="${site.logo}" class="container" onclick="location='${ctx}/index-${site.id}${fns:getUrlSuffix()}'">
   			</c:when>
   			<c:otherwise><h1 class="b-tit" onclick="location='${ctx}/index-${site.id}${fns:getUrlSuffix()}'">${site.title}</h1></c:otherwise>
   		  </c:choose>
	      <div class="s-tit">CAIZHEGZHONGHEGUANLIXITONG</div>
	    </li>
	    <div class="znjs">
	      <form id="index_search" method="get" action="${ctx}/search" name="dataAction" target="_blank">
	       <!--  <select name="channelid" id="channelid">
	          <option selected="" value="all">全&nbsp;&nbsp;&nbsp;&nbsp;部</option>
	          <option value="title">标&nbsp;&nbsp;&nbsp;&nbsp;题</option>
	          <option value="content">内&nbsp;&nbsp;&nbsp;&nbsp;容</option>
	        </select>
	        <i class="fa fa-angle-down" style="padding:0 5px"></i> -->
	        <input type="text" name="content" id="searchword" value="请输入检索关键字" onFocus="if(this.value==&#39;请输入检索关键字&#39;)this.value=&#39;&#39;" onBlur="if(this.value==&#39;&#39;)this.value=&#39;请输入检索关键字&#39;">
	        <a   style="cursor:pointer;" onclick="$('#index_search').submit();">检 索</a>
	      </form>
	    </div>
	  </ul>
	</div>
	<!--nav-->
	<div class="index-nav">
	  <ul>
	  	<li target="_top"> <!-- class="${not empty isIndex && isIndex ? 'active' : ''}" -->
	  		<a href="${ctx}/index-1${fns:getUrlSuffix()}"><i class="fa fa-home"></i>${site.id eq '1'?'首　 页':'返回主站'}</a>
	  	</li>
	  	<c:forEach items="${fnc:getMainNavList(site.id)}" var="category" varStatus="status">
	  		<c:if test="${status.index lt 6}">
                <c:set var="menuCategoryId" value=",${category.id},"/>
    			<li class="${requestScope.category.id eq category.id||fn:indexOf(requestScope.category.parentIds,menuCategoryId) ge 1?'active':''}">
    				<a href="${category.url}" target="${category.target}">${category.name}</a>
    			</li>
    		</c:if>
    	</c:forEach>
	  </ul>
	</div>
</div>
<!--头部结束-->
<div class="container">
	<div class="content">
		<sitemesh:body/>
		<!-- 可用js放入mainDiv中 -->
		<div id="footer_nav"  style="float: left;display: none;margin-top: 40px;margin-left: 40%;">
			<%-- <a href="${ctx}/guestbook" target="_blank">公共留言</a> |  --%>
			<a href="${ctx}/search" target="_blank">全站搜索</a> | 
			<a href="${ctx}/map-${site.id}${fns:getUrlSuffix()}" target="_blank">站点地图</a> | 
			<!--<a href="mailto:thinkgem@163.com">技术支持</a> |-->
			<a href="${pageContext.request.contextPath}${fns:getAdminPath()}" target="_blank">后台管理</a>
		</div>
		<script type="text/javascript">
			$("#footer_nav").appendTo($(".mainDiv:first"));
			$("#footer_nav").show();
		</script>
		<%-- <div class="pull-right">${fns:getDate('yyyy年MM月dd日 E')}</div><div class="copyright">${site.copyright}</div> --%>
	</div>
</div> <!-- /container --> 
<div>
  <iframe width="100%" scrolling="no" height="120" frameborder="0" allowtransparency="true" src="${ctxStatic}/index/bottom.html"></iframe>
</div>
</body>
</html>