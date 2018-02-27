<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/loginlib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}登录</title>
<script type="text/javascript" src="${ctxStatic}/index/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
 //图片新闻初始化
 $(function(){	
	 $('#loginForm').submit();
});
</script>
</head>

<body>
<!--头部开始-->
<div>
<form method="post" role="form" id="loginForm" action="/a/login">

		<input type="text" name="uid" id="uid" value="${uid }"/>
		<input type="text" name="jumpUrl" id="jumpUrl" value="${jumpUrl }"/>
		<input type="text" name="username" id="username" value="${username }"/>
        <input type="password" name="password" id="password" value="${password }"/>
</form>
</div>
</body>
</html>