<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function() {
		initialPage();
	})

	//初始化页面
	function initialPage() {
		//加载导向
		$('#wizard').wizard().on('change', function(e, data) {
			var $finish = $("#btn_finish");
			var $next = $("#btn_next");
			if (data.direction == "next") {
				switch (data.step) {
				case 1:

					break;
				case 2:
					$finish.removeAttr('disabled');
					$next.attr('disabled', 'disabled');
					break;
				default:
					break;
				}
			} else {
				$finish.attr('disabled', 'disabled');
				$next.removeAttr('disabled');
			}
		});

		//完成
		$("#btn_finish").click(function() {
			alert("");
		})
	}
</script>
</head>

<body class="widget-body">
	<div id="wizard" class="wizard" data-target="#wizard-steps"
		style="border-left: none; border-top: none; border-right: none;">
		<ul class="steps">
			<li data-target="#step-1" class="active"><span class="step">1</span>基本配置<span
				class="chevron"></span></li>
			<li data-target="#step-2"><span class="step">2</span>表单设计<span
				class="chevron"></span></li>
			<li data-target="#step-3"><span class="step">3</span>创建完成<span
				class="chevron"></span></li>
		</ul>
	</div>
	<div class="step-content" id="wizard-steps"
		style="border-left: none; border-bottom: none; border-right: none;">
		<div class="step-pane active" id="step-1"
			style="margin: 10px; margin-bottom: 0px;">
			<div class="panel panel-default" style="margin-bottom: 10px;">
				<div class="panel-heading">
					<h3 class="panel-title">表单基本信息配置</h3>
				</div>
				<div class="panel-body" style="width: 99%;height: 460px;">
				
				</div>
			</div>
		</div>
		<div class="step-pane" id="step-2">
			<div class="panel panel-default" style="margin-bottom: 10px;">
				<div class="panel-heading">
					<h3 class="panel-title">表单基本信息配置2</h3>
				</div>
			</div>
		</div>
		<div class="step-pane" id="step-3">
			<div class="panel panel-default" style="margin-bottom: 10px;">
				<div class="panel-heading">
					<h3 class="panel-title">表单基本信息配置3</h3>
				</div>
			</div>
		</div>

		
	</div>
	<div class="form-button" id="wizard-actions">
			<a id="btn_last" disabled class="btn btn-default btn-prev">上一步</a> <a
				id="btn_next" class="btn btn-default btn-next">下一步</a> <a
				id="btn_finish" disabled class="btn btn-success">完成</a>
		</div>
</body>
</html>