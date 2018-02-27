<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${article.title} - ${category.name}</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="${article.description} ${category.description}" />
	<meta name="keywords" content="${article.keywords} ${category.keywords}" />
	<script type="text/javascript">
		$(document).ready(function() {
			if ("${category.allowComment}"=="1" && "${article.articleData.allowComment}"=="1"){
				$("#comment").show();
				page(1);
			}
		});
		function page(n,s){
			$.get("${ctx}/comment",{theme: '${site.theme}', 'category.id': '${category.id}',
				contentId: '${article.id}', title: '${article.title}', pageNo: n, pageSize: s, date: new Date().getTime()
			},function(data){
				$("#comment").html(data);
			});
		}
	</script>
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
	 		  <div class="span10" style="min-height: 500px;">
			     <div class="row">
			       <div class="span10">
					<h3 style="color:#555555;font-size:20px;text-align:center;margin:10px">${article.title}</h3>
						<div style="border-bottom:1px solid #ddd;padding:10px;text-align:center;color:#908c8c;">发布者：${article.user.name} &nbsp; 点击数：${article.hits} &nbsp; 发布时间：<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> &nbsp; 更新时间：<fmt:formatDate value="${article.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		 
					
			<%-- 	此处不现实摘要，只需要显示内容	<c:if test="${not empty article.description}">
					<div>${article.description}</div>
					</c:if> --%>
					<div style="padding:10px 20px;" class="divContent">${article.articleData.content}</div>
				 	       </div>
		  	     </div>
			    <!--  <div class="row">
					<div id="comment" class="span10" style="display: none;">
						正在加载评论...
					</div>
			     </div> -->
		  	  </div>
		  	  <div class="row">
			       <div class="span10">
					<h4>相关文章</h4>
					<ol><c:forEach items="${relationList}" var="relation">
						<li style="float:left;width:230px;"><a href="${ctx}/view-${relation[0]}-${relation[1]}${urlSuffix}">${fns:abbr(relation[2],30)}</a></li>
					</c:forEach></ol>
			  	  </div>
		  	    </div>
		</div>
	</div>	
</body>
</html>