<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义启动明细查看</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<form  class="form-horizontal">
	<div class="control-group">
		<label class="control-label">规则定义启动主键：</label>
		<div class="controls">
			${ruleEnableDetail.eId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">启用列：</label>
		<div class="controls">
			${ruleEnableDetail.column}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">启动列名称：</label>
		<div class="controls">
			${ruleEnableDetail.columnName}
		</div>
	</div>
</form>	
</body>
</html>