<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则条件查看</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<form  class="form-horizontal">
	<div class="control-group">
		<label class="control-label">规则表ID：</label>
		<div class="controls">
			${ruleCondition.dId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">表ID：</label>
		<div class="controls">
			${ruleCondition.tableId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">规则条件（包含/排除）：</label>
		<div class="controls">
			${ruleCondition.condition}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">表字段：</label>
		<div class="controls">
			${ruleCondition.tableColumn}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">等于，包含，排除：</label>
		<div class="controls">
			${ruleCondition.filtration}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">数据过滤内容：</label>
		<div class="controls">
			${ruleCondition.content}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">所属公司：</label>
		<div class="controls">
			${ruleCondition.office.name}
		</div>
	</div>
</form>	
</body>
</html>