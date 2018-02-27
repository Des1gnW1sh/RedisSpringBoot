<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta name="decorator" content="cms_default_${site.theme}" />
<meta name="description" content="JeeSite ${site.description}" />
<meta name="keywords" content="JeeSite ${site.keywords}" />
<link href="${ctxStatic}/common/index.css" type="text/css" rel="stylesheet" />

<link href="${ctxStatic}/index/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/index/css/area-style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctxStatic}/index/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/index/js/jquery.SuperSlide.2.1.1.js"></script><!--图片新闻外部js-->
<script type="text/javascript" src="${ctxStatic}/index/js/flexslider.js"></script><!--专题专栏外部js-->
<script type="text/javascript" src="${ctxStatic}/index/js/scroll.js"></script><!--列表滚动外部js-->
<script type="text/javascript" src="${ctxStatic}/index/js/WdatePicker.js"></script>
<script type="text/javascript" src="${ctxStatic}/index/js/jquery.easyui.min.js"></script>

<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>

<script type="text/javascript">
 //图片新闻初始化
 $(function(){
	jQuery(".focusBox").slide({ titCell:".num li", mainCell:".pic",effect:"fold", autoPlay:true,trigger:"click",
			//下面startFun代码用于控制文字上下切换
			startFun:function(i){
				 jQuery(".focusBox .txt li").eq(i).animate({"bottom":0}).siblings().animate({"bottom":-36});
			}
	});
	 <!--办件列表滚动初始化-->
	$("div.bj-list").myScroll({
	    speed:60, //数值越大，速度越慢
	    rowHeight:28 //li的高度
	});
	 //专题专栏图片滚动初始化       
	$('#demo').flexslider({       
	   slideshow:true,
	   animation: "slide",
	   direction:"horizontal",
	   easing:"swing",
	   controlNav: false,
	   directionNav:true
	});
    $(".flexslider").each(function(){
        $(this).hover(function(){
            $(this).find(".flex-direction-nav").fadeIn("slow")
        },function(){
            $(this).find(".flex-direction-nav").hide()
        })
    })
})
$(document).ready(function(){
    //政策检索标签切换
    var $li = $("#mac li");
	var $div = $("[id=zcqh]");		
	$li.hover(function(){
	var $t = $("#mac li").index($(this));
	$li.removeClass();
	$(this).addClass("active");
	$div.css("display","none");
	$div.eq($t).css("display","block");
	})
	//站群更多内切换
	var $lia = $(".sw-link a");
	var $diva = $(".xq");		
	$lia.hover(function(){
	var $ta = $(".sw-link a").index($(this));
	$lia.removeClass();
	$(this).addClass("active");
	$diva.css("display","none");
	$diva.eq($ta).css("display","block");
	})
	
	
	//站群更多点击事件
	$(".more").click(function(){
		$(".link-list").slideToggle();
	})
 }); 
 
 
 $(document).ready(function() {
		$("#loginForm").validate({
			rules : {
				validateCode : {
					remote : "/intellectreport/servlet/validateCodeServlet"
				}
			},
			messages : {
				username : {
					required : "请填写用户名."
				},
				password : {
					required : "请填写密码."
				},
				validateCode : {
					remote : "验证码不正确.",
					required : "请填写验证码."
				}
			},
			errorLabelContainer : "#messageBox",
			errorPlacement : function(error,
					element) {
				error.appendTo($("#loginError")
						.parent());
			}
		});
});
// 如果在框架或在对话框中，则弹出提示并跳转到首页
if (self.frameElement && self.frameElement.tagName == "IFRAME"
		|| $('#left').length > 0 || $('.jbox').length > 0) {
	alert('未登录或登录超时。请重新登录，谢谢！');
	top.location = "/intellectreport/a";
}
$(window).resize(function() {
	$("#desktop").height($(window).height());
	/* var top = $('#desktop').height() / 2 - $('.form-signin').height() / 2 - 39;
	var left = $('#desktop').width() / 2 - $('.form-signin').width() / 2 - 14;
	$('.form-signin').css({'top':top,'left':left});
	$('.footer').css({'top':top+250,'left':left+30}); */
});
-->
</script>
</head>
<body onload="re">
<!--头部结束-->
<div class="news-bk"> 
  <!--图片新闻外框-->
  <div class="photo-news"> 
    <!--图片新闻-->
    <div id="focusBox" class="focusBox">
      <ul class="pic" style="position: relative; width: 368px; height: 268px;">
      	<c:forEach items="${fnc:getArticleList(category.site.id,'', not empty pageSize?pageSize:5, 'posid:1,orderBy: \"hits desc\"')}" var="article" varStatus="status" >
			  <c:choose>
	        	<c:when test="${status.index==0}">
	        		<li style="position: absolute; width: 368px; left: 0px; top: 0px; display: list-item;">
						<a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">
							<img src="${ctxStatic }${article.image}" alt="1.jpg" style="opacity: 1;">
						</a>
					</li>
	        	</c:when>
	        	<c:otherwise>
	        		<li style="position: absolute; width: 368px; left: 0px; top: 0px; display: none;">
						<a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">
							<img src="${ctxStatic }${article.image}" alt="1.jpg" style="opacity: 1;">
						</a>
					</li>
	        	</c:otherwise>
	        </c:choose>
			
			  <div class="txt1">
		        <c:choose>
		        	<c:when test="${status.index==0}">
		        		<li style="bottom: 0px;"><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">${fns:abbr(article.title,16)}</a></li>
		        	</c:when>
		        	<c:otherwise>
		        		<li style="bottom:-36px;"><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">${fns:abbr(article.title,16)}</a></li>
		        	</c:otherwise>
		        </c:choose>
		      </div>
		      <div  class="num1">
	      	  	<c:choose>
		        	<c:when test="${status.index==0}">
						<li class="on"><a>${status.index+1 }</a><span></span></li>		        	
					</c:when>
		        	<c:otherwise>
						<li class=""><a>${status.index+1 }</a><span></span></li>
		        	</c:otherwise>
		        </c:choose>
		      </div>
		</c:forEach>
      </ul>
      <div class="txt-bg"></div>
      <div id="txt" class="txt"><ul> </ul></div>
      <ul id="num" class="num"></ul>
       <script type="text/javascript">
      	$("#focusBox").find(".txt1").find("li").each(function(){
      		$(this).appendTo($("#txt").find("ul:first"));
      	});
    	$("#focusBox").find(".txt1").each(function(){
    		$(this).remove();
    	});
      	$("#focusBox").find(".num1").find("li").each(function(){
      		$(this).appendTo($("#num"));
      	});
      	$("#focusBox").find(".num1").each(function(){
    		$(this).remove();
    	});
      </script>
    </div>
  </div>
  <!--新闻列表外框-->
  <div class="news-list-k"> 
    <!--新闻列表标题-->
    <div class="news-tit"><a href="f/list-8d1271b85fb04c6c9f5541fa7a064a8e.html" target="_blank">更多 &gt;</a><span>工作动态</span></div>
    <!--新闻列表-->
    <div class="news-list">
      <ul>
 	    <c:forEach items="${fnc:getArticleList(category.site.id,'8d1271b85fb04c6c9f5541fa7a064a8e', not empty pageSize?pageSize:8, 'orderBy: \"hits desc\"')}" var="article">
      		 <li><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">${fns:abbr(article.title,66)}</a></li>
      	</c:forEach>
      </ul>
    </div>
  </div>
  <!--通知公告外框-->
  <div class="notice-k"> 
    <!--通知公告标题-->
    <div class="notice-tit"><span>系统登陆</span></div>
    <!--通知公告列表-->
    <div class="notice-list">
<form method="post" role="form" id="loginForm" action="/a/login">

    <div class="form-group">
    	<i class="fa1 fa-user1"></i>
		<input type="text" class="form-control required" name="username" id="username" placeholder="用户名" autocomplete="off" />
    </div>

    <div class="form-group">
		<i class="fa1 fa-key1"></i>
        <input type="password" class="form-control required" name="password" id="password" placeholder="密码" autocomplete="off" />
    </div>
    <div class="form-group" style="height:25px; line-height:25px; text-align:left;">
        <input type="checkbox" class="" name="checkbox" id="checkbox" placeholder="checkbox" autocomplete="off" />
        <span class="checkfont">记住我的帐号</span>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary btn-block btn-login">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
    </div>

</form>
    </div>
  </div>
  <!--专题专栏切换--></div>
<!--信息公开-->
<div class="xxgk"> 
  <!--信息公开&办事服务-->
  <div class="qh-wk"> 
  
    <!--信息公开-->
    <div class="sw-tit-s">
      <ul>
        <li class="active">政策法规</li>
      </ul>
      <a href="f/list-3c42daa79f3b4575aae517e1569662a1.html" target="_blank" style="float: right;padding-top: 6px;">更多 &gt;
    </div>
    
    <!--信息公开内容区-->
    
    <div class="zwgk-list" style="min-height: 300px;">
      <table width="779" border="0" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <td><table bgcolor="#dddddd" width="100%" border="0" align="center" cellPadding="0" cellSpacing="1" class="zwgk-list1">
                <tr >
                  <td width="32" height="26" align="center" valign="middle" bgcolor="#f1f1f1">序号</td>
                  <td align="center" bgcolor="#f1f1f1">信息名称</td>
                  <td width="80" align="center" bgcolor="#f1f1f1">发布日期</td>
                </tr>
                 <c:forEach items="${fnc:getArticleList(category.site.id,'3c42daa79f3b4575aae517e1569662a1', not empty pageSize?pageSize:10, 'orderBy: \"hits desc\"')}" var="article" varStatus="status">
                	<tr>
	                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">${status.index+1 }</td>
	                  <td align="left" bgcolor="#FFFFFF"><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">${fns:abbr(article.title,106)}</a></td>
	                  <td bgcolor="#FFFFFF" style="text-align: center"><fmt:formatDate value="${article.createDate }" type="DATE" /></td>
	                </tr>
                </c:forEach>
               <!--  <tr align="center" valign="middle"  style="padding:5px 5px 5px 5px;">
                  <td height="26" colspan="3" bgcolor="#FFFFFF"><table width="100%" cellSpacing="0"cellPadding="0" border="0" >
                      <tr>
                        <td align="center" valign="middle">&nbsp;&nbsp;共375条</td>
                        <td align="right" valign="middle">共38页&nbsp;&nbsp;&nbsp;&nbsp;当前为第&nbsp;1&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页</a>&nbsp;
                          <input type='text' name='pageno1' id='pageno1' size=2 value='1'>
                          <input type='button' class='cssButton' onclick="goButton('pageno1')"; value='go' name='gopage1'>
                          &nbsp;&nbsp;</td>
                      </tr>
                    </table></td>
                </tr> -->
              </table>
             </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="zccx-wk">
    <div class="notice-tit"><a href="f/list-0eea81c7311241a28f5e0bc4aa584ce1.html" target="_blank">更多 &gt;</a><span>通知公告</span></div>
    <div class="zc-list">
      <ul>
      	<c:forEach items="${fnc:getArticleList(category.site.id,'0eea81c7311241a28f5e0bc4aa584ce1', not empty pageSize?pageSize:11, 'orderBy: \"hits desc\"')}" var="article">
      		 <li><a href="${ctx}/view-${article.category.id}-${article.id}${urlSuffix}" target="_blank">${fns:abbr(article.title,56)}</a></li>
      	</c:forEach>
      </ul>
    </div>
  </div>
</div>
</body>
</html>