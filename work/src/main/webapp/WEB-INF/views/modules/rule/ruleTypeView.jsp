<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则类型查看</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<form  class="form-horizontal">
	<div class="control-group">
		<label class="control-label">名称：</label>
		<div class="controls">
			${ruleType.name}
		</div>
	</div>
</form>	
</body>
</html>