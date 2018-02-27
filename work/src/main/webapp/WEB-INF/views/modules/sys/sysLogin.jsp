<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/loginlib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}登录</title>
	<meta name="decorator" content="default"/>
<link href="${ctxStatic}/index/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/index/css/area-style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctxStatic}/index/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript" src="${ctxStatic}/index/js/jquery.SuperSlide.2.1.1.js"></script><!--图片新闻外部js-->
<script type="text/javascript" src="${ctxStatic}/index/js/flexslider.js"></script><!--专题专栏外部js-->
<script type="text/javascript" src="${ctxStatic}/index/js/scroll.js"></script><!--列表滚动外部js-->
<script type="text/javascript" src="${ctxStatic}/index/js/WdatePicker.js"></script>
<script type="text/javascript" src="${ctxStatic}/index/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery.form.js"></script>
<link href="${ctxStatic}/index/css/head.css" rel="stylesheet" type="text/css">
<!--头部单独css-->
<link href="${ctxStatic}/index/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
 //图片新闻初始化
 $(function(){
	//jQuery(".focusBox").slide({ titCell:".num li", mainCell:".pic",effect:"fold", autoPlay:true,trigger:"click",
			//下面startFun代码用于控制文字上下切换
	//		startFun:function(i){
	//			 jQuery(".focusBox .txt li").eq(i).animate({"bottom":0}).siblings().animate({"bottom":-36});
	//		}
});
 //<!--办件列表滚动初始化-->
     $("div.bj-list").myScroll({
                speed:60, //数值越大，速度越慢
                rowHeight:28 //li的高度
            });
 //专题专栏图片滚动初始化       
   //  $('#demo').flexslider({       
   //     slideshow:true,
   //     animation: "slide",
   //     direction:"horizontal",
   //     easing:"swing",
   //     controlNav: false,
   //     directionNav:true
  //  });
   // $(".flexslider").each(function(){
  //      $(this).hover(function(){
   //         $(this).find(".flex-direction-nav").fadeIn("slow")
   //     },function(){
   //         $(this).find(".flex-direction-nav").hide()
  //      })
 //   })
//})

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
 
 
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if (self.frameElement && self.frameElement.tagName == "IFRAME"
			|| $('#left').length > 0 || $('.jbox').length > 0) {
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "/a";
	}
	$(window).resize(function() {
		$("#desktop").height($(window).height());
		/* var top = $('#desktop').height() / 2 - $('.form-signin').height() / 2 - 39;
		var left = $('#desktop').width() / 2 - $('.form-signin').width() / 2 - 14;
		$('.form-signin').css({'top':top,'left':left});
		$('.footer').css({'top':top+250,'left':left+30}); */
	});
</script>
</head>

<body>

<!--头部开始-->
<div>
	<iframe width="100%" scrolling="no" height="216" frameborder="0" allowtransparency="true" src="${ctxStatic}/index/head.html"></iframe>
</div>
<!--头部结束-->
<div class="news-bk"> 
  <!--图片新闻外框-->
  <div class="photo-news"> 
    <!--图片新闻-->
    <div class="focusBox">
      <ul class="pic" style="position: relative; width: 368px; height: 268px;">
        <li style="position: absolute; width: 368px; left: 0px; top: 0px; display: none;"><a href="content.html" target="_blank"><img src="${ctxStatic}/index/images/W020161225542338755446.jpg" alt="1.jpg" style="opacity: 1;"></a></li>
        <li style="position: absolute; width: 368px; left: 0px; top: 0px; display: list-item;"><a href="content.html" target="_blank"><img src="${ctxStatic}/index/images/W020161225368354689512.jpg" alt="5.jpg" style="opacity: 1;"></a></li>
        <li style="position: absolute; width: 368px; left: 0px; top: 0px; display: none;"><a href="content.html" target="_blank"><img src="${ctxStatic}/index/images/W020161225364827509447.jpg" alt="3.jpg" style="opacity: 1;"></a></li>
        <li style="position: absolute; width: 368px; left: 0px; top: 0px; display: none;"><a href="content.html" target="_blank"><img src="${ctxStatic}/index/images/W020161225359418592417.jpg" alt="x_4654823250_m.jpg" style="opacity: 1;"></a></li>
        <li style="position: absolute; width: 368px; left: 0px; top: 0px; display: none;"><a href="content.html" target="_blank"><img src="${ctxStatic}/index/images/W020161224636331257552.jpg" alt="3.jpg" style="opacity: 1;"></a></li>
      </ul>
      <div class="txt-bg"></div>
      <div class="txt">
        <ul>
          <li style="bottom: -36px;"><a href="content.html" target="_blank">内江市财政局组织党员干部开展义务植树活动</a></li>
          <li style="bottom: 0px;"><a href="content.html" target="_blank">2017年全市财政反腐倡廉建设工作会议召开</a></li>
          <li style="bottom: -36px;"><a href="content.html" target="_blank">内江市财政局召开机关党建品牌创建动员大会</a></li>
          <li style="bottom: -36px;"><a href="content.html" target="_blank">市财政局多措并举助推“放管服”改革</a></li>
          <li style="bottom: -36px;"><a href="content.html" target="_blank">我市召开2017年全市财政支农工作会议</a></li>
        </ul>
      </div>
      <ul class="num">
        <li class=""><a>1</a><span></span></li>
        <li class="on"><a>2</a><span></span></li>
        <li class=""><a>3</a><span></span></li>
        <li class=""><a>4</a><span></span></li>
        <li class=""><a>5</a><span></span></li>
      </ul>
    </div>
  </div>
  <!--新闻列表外框-->
  <div class="news-list-k"> 
    <!--新闻列表标题-->
    <div class="news-tit"><a href="list.html" target="_blank">更多 &gt;</a><span>工作动态</span></div>
    <!--新闻列表-->
    <div class="news-list">
      <ul>
        <li><a href="content.html" target="_blank">内江市扎实全面开展会计信息质量检查</a></li>
        <li><a href="content.html" target="_blank">我市召开贫困村产业扶持基金、扶贫小额信贷分险基金管理交流座谈会</a></li>
        <li><a href="content.html" target="_blank">我市召开2017年全市财政支农工作会议</a></li>
        <li><a href="content.html" target="_blank">省财政厅到内江调研政府资产报告编制及资产管理工作</a></li>
        <li><a href="content.html" target="_blank">内江市财政局组织党员干部集中收看四川省第十一次党代会开幕大会</a></li>
        <li><a href="content.html" target="_blank">内江市财政局召开机关党建品牌创建动员大会</a></li>
        <li><a href="content.html" target="_blank">市财政局多措并举助推“放管服”改革</a></li>
        <li><a href="content.html" target="_blank">内江专题学习贯彻全省四项扶贫基金培训会议精神</a></li>
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
		<input type="text" class="form-control" name="username" id="username" placeholder="用户名" autocomplete="off" />
    </div>

    <div class="form-group">
		<i class="fa1 fa-key1"></i>
        <input type="password" class="form-control" name="password" id="password" placeholder="密码" autocomplete="off" />
    </div>
    <div class="form-group" style="height:25px; line-height:25px; text-align:left;">
        <input type="checkbox" class="" name="checkbox" id="checkbox" placeholder="checkbox" autocomplete="off" />
        <span class="checkfont">记住我的帐号</span>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-info btn-block btn-login">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
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
    </div>
    <!--信息公开内容区-->
    
    <div class="zwgk-list">
      <table width="779" border="0" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <td><table bgcolor="#dddddd" width="100%" border="0" align="center" cellPadding="0" cellSpacing="1" class="zwgk-list1">
                <tr >
                  <td width="32" height="26" align="center" valign="middle" bgcolor="#f1f1f1">序号</td>
                  <td align="center" bgcolor="#f1f1f1">信息名称</td>
                  <td width="80" align="center" bgcolor="#f1f1f1">发布日期</td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">1</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">内江市财政局 内江市供销合作社联合社关于印发《内江市市级供销综合改革及发展专项资金管理办</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2017-02-16 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">2</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">内江市财政局 内江市发展和改革委员会关于印发《内江市市级储备粮油轮换风险准备金管理暂行办</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2017-01-26 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">3</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">国务院关于印发矿产资源权益金制度改革方案的通知</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2017-01-09 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">4</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">关于印发《水污染防治专项资金绩效评价办法》的通知</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-12-29 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">5</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">关于进一步加强财政部门和预算单位资金存放管理的指导意见</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-11-25 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">6</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">关于进一步落实高等教育学生资助政策的通知</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-11-10 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">7</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">内江市财政局关于转发《四川省财政厅关于印发<2017年会计管理及行政审批工作要点>的通知》</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-11-10 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">8</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">内江市财政局关于实行政府采购专管员制度的通知</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-11-10 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">9</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">财政部 科技部 工业和信息化部 发展改革委关于调整新能源汽车推广应用财政补贴政策的通知</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-11-02 </td>
                </tr>
                <tr>
                  <td height="25" align="center" valign="middle" bgcolor="#FFFFFF">10</td>
                  <td align="left" bgcolor="#FFFFFF"><a href="content.html" target="_blank">财政部关于印发《财政部政府和社会资本合作（PPP）专家库管理办法》的通知</a></td>
                  <td bgcolor="#FFFFFF" style="text-align: center"> 2016-10-19 </td>
                </tr>
                <tr align="center" valign="middle"  style="padding:5px 5px 5px 5px;">
                  <td height="26" colspan="3" bgcolor="#FFFFFF"><table width="100%" cellSpacing="0"cellPadding="0" border="0" >
                      <tr>
                        <td align="center" valign="middle">&nbsp;&nbsp;共375条</td>
                        <td align="right" valign="middle">共38页&nbsp;&nbsp;&nbsp;&nbsp;当前为第&nbsp;1&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页</a>&nbsp;
                          <input type='text' name='pageno1' id='pageno1' size=2 value='1'>
                          <input type='button' class='cssButton' onclick="goButton('pageno1')"; value='go' name='gopage1'>
                          &nbsp;&nbsp;</td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="zccx-wk">
    <div class="notice-tit"><span>通知公告</span></div>
    <div class="zc-list">
      <ul>
        <li><a href="content.html" target="_blank">内江市财政局关于2017年政府采...</a></li>
        <li><a href="content.html" target="_blank">内江市财政局 内江市发展和改革...</a></li>
        <li><a href="content.html" target="_blank">内江市2017年注册会计师全国统...</a></li>
        <li><a href="content.html" target="_blank">内江市财政局“内控制度及风险评...</a></li>
        <li><a href="content.html" target="_blank">内江市2017年度全国会计专业技...</a></li>
        <li><a href="content.html" target="_blank">内江市财政局关于2017年政府采...</a></li>
        <li><a href="content.html" target="_blank">内江市财政局 内江市发展和改革...</a></li>
        <li><a href="content.html" target="_blank">内江市2017年注册会计师全国统...</a></li>
        <li><a href="content.html" target="_blank">内江市财政局“内控制度及风险评...</a></li>
        <li><a href="content.html" target="_blank">内江市2017年度全国会计专业技...</a></li>
        <li><a href="content.html" target="_blank">内江市财政局关于2017年政府采...</a></li>
      </ul>
    </div>
  </div>
</div>
<div>
  <iframe width="100%" scrolling="no" height="120" frameborder="0" allowtransparency="true" src="${ctxStatic}/index/bottom.html"></iframe>
</div>
</body>
</html>