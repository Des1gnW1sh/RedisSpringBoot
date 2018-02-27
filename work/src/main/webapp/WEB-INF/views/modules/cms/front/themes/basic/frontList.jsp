<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${category.name}</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="${category.description}" />
	<meta name="keywords" content="${category.keywords}" />
</head>
<body>
	<div  class="mainDiv">
		<!--信息内容-->
		<div class="mapdiv">
			 <cms:frontCurrentPosition category="${category}"/>
		</div>
		<div class="left" >
			<div class="columnTitle"><span>栏目列表</span></div>
			<cms:frontCategoryList categoryList="${categoryList}"/>
			<div class="columnTitle"><span>推荐阅读</span></div>	
			<cms:frontArticleHitsTop category="${category}"/>
		</div>
		<div class="right">
	 		<%--  <h4>${category.name}</h4> --%>
       	 <c:if test="${category.module eq 'article'}">
       	 	<div style="min-height: 500px;">
          	 <table  class="ml-list1 tableCss" >
	           	 <tbody>
		    			<c:forEach items="${page.list}" var="article">
			    			 <tr>
				              	<td class="tdConter"><span class="ico-dot"></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,96)}</a></td>
			    				<td class="tdDate"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></td>
			   				</tr>
						</c:forEach>
				</tbody>	
			</table>
			</div>
			<div class="pagination">${page}</div>
			<script type="text/javascript">
				function page(n,s){
					location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+n+"&pageSize="+s;
				}
			</script>
		</c:if>
		<c:if test="${category.module eq 'link'}">
		<table  class="ml-list1 tableCss">
		 <tbody>
   			<c:forEach items="${page.list}" var="link">
   			 <tr>
   				<td class="tdConter"><span class="ico-dot"></span><a href="${link.href}" target="_blank" style="color:${link.color}"><c:out value="${link.title}" /></a></td>
          		</tr>
			</c:forEach>
		</tbody>
		</table>
		</c:if>
	          
	  		
		</div>
	</div>	
</body>
</html>