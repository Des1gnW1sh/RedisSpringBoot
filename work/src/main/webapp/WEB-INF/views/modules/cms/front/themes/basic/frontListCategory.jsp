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
		  <c:forEach items="${categoryList}" var="tpl">
			<c:if test="${tpl.inList eq '1' && tpl.module ne ''}">
				<div  style="background-color:#1574b8;height: 30px;color: white;">
					<h4 style="padding-top: 5px;"><small><a href="${ctx}/list-${tpl.id}${urlSuffix}" class="pull-right"  style="color: white;">更多&gt;&gt;</a></small>${tpl.name}</h4>
				</div>
				<table  class="ml-list1 tableCss"> 
		          <tbody>
		            	<c:if test="${tpl.module eq 'article'}">
			    			<c:forEach items="${fnc:getArticleList(site.id, tpl.id, 5, '')}" var="article">
			    			 <tr>
				              	<td class="tdConter"><span class="ico-dot"></span><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" style="color:${article.color}">${fns:abbr(article.title,40)}</a></td>
			    				<td class="tdDate"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></td>
		    				</tr>
							</c:forEach>
						</c:if>
						<c:if test="${tpl.module eq 'link'}">
			    			<c:forEach items="${fnc:getLinkList(site.id, tpl.id, 5, '')}" var="link">
			    			 <tr>
			    				<td class="tdConter"><span class="ico-dot"></span><a target="_blank" href="${link.href}" style="color:${link.color}">${fns:abbr(link.title,40)}</a></td>
		            			<td  class="tdDate" ><fmt:formatDate value="${link.updateDate}" pattern="yyyy.MM.dd"/></td>
		            		</tr>
							</c:forEach>
						</c:if>
		          </tbody>
		  		</table>
			</c:if>
		  </c:forEach>
		</div>
		
	</div>
</body>
</html>