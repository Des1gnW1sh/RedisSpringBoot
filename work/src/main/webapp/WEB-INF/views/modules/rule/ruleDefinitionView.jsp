<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则定义查看</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {	
			//修改读取树值
			updateZtree();
		});
		//点击标签获取下面div的显示
		function show(showIndex) {
			$('#allTjDiv').children().attr("class","tjDiv_display")
			$("#tjd"+showIndex).attr("class","tjDiv")
			if($("#tjd"+showIndex).length==0){
				var id = $("div.tjDiv_display:last").attr("id");
				if(id==""||id=="undefind"||id==null){
					id = $("div.tjDiv:last").attr("id");
				}
				$("#"+id).attr("class","tjDiv")
			}
			$("#tjbq").children().attr("class","titileDiv-noRead")
			$("#tj"+showIndex).attr("class","titileDiv")
		}
		
		var indd;
		function updateZtree(){
		 	var length = 	$("#length").val();
		 	for(var i =0 ; i<length;i++){
		 		var filtration = $("#filtration"+i).val()
		 		if(filtration==""||filtration==null){
		 			indd =i 
		 			var checkNow = $("#content"+i).val().split(",");
		 			var type = $("#dictName"+i).val()
		 			var setting = {
							check: {
								enable: true,
								chkStyle: "checkbox",
								chkboxType: { "Y" : "ps", "N" : "s" }
							},
							data: {
								simpleData: {
									enable: true
								}
							}
						};
						var dataZtree;
						$.ajax({
					        url: "${ctx}/rule/ruleEnableDetail/treeData?dictName="+type,
					        success: function (data)
					        {
					        	var zNodes =data;
					        	var ztreeId = "zidian"+indd
		      					// 初始化树结构
		      					var tree = $.fn.zTree.init($("#"+ztreeId), setting, zNodes);
		      					// 默认选择节点
		      					for(var i=0; i<checkNow.length; i++) {
		      						var node = tree.getNodeByParam("id", checkNow[i]);
		      						try{tree.checkNode(node, true, false);}catch(e){}
		      					} 
		      					// 默认展开全部节点
		      					tree.expandAll(true);
					        }
					     }); 
		 		}
		 	}
		}
	</script>
	<style type="text/css">
	.form-actions{
		display: none;
	}
	.tjDiv{
		width: 100%;
		border: 1px solid #ccc;
		float: left;
		border-radius:0 5px 5px 5px;
	}
	.tjDiv_display{
		width: 100%;
		height: 300px;
		border: 1px solid #ccc;
		float: left;
		display:none;
	}
	.allDiv{
		width: 60%;
		margin-left: 100px;
	}
	.bqDiv{
		width: 100%;
		float: left;
	}
	.titileDiv{
		width: 10%;
		height: 30px;
		border: 1px solid #ccc;
		font-size: 15px;
		text-align: center;
		padding-top: 5px;
		float: left;
		border-bottom: medium none;
	}
	.addDiv{
		width: 10%;
		height: 30px;
		font-size: 15px;
		text-align: center;
		padding-top: 5px;
		float: left;
	}
	.imgAdd{
		
    width: 25px;
		
	}
	
	.inputCon{
		border: 1px solid #ccc;
		height: 28px;
		border-radius:5px;
	}
	</style>
</head>
<body>
<form  class="form-horizontal">
	<div class="control-group">
		<label class="control-label">规则类型：</label>
		<div class="controls">
			${ruleDefinition.typeName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">规则名称：</label>
		<div class="controls">
			${ruleDefinition.name}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">功能名称：</label>
		<div class="controls">
			${ruleDefinition.functionName}
		</div>
	</div>
	<c:set var="details" value="${ruleDefinition.ruleConditions}" />
	<c:set var="length" value="${fn:length(details)}"/>
	<input id="length" value="${length}" style="display: none"/>
	<c:if test="${!empty details}">
		<div class="allDiv">
			<div class="bqDiv">
				<c:forEach  items ="${details}" var='detail' varStatus="sNub">
					<!-- 标签头 -->
					<div class="titileDiv" id="tj${sNub.index}" onclick="show(${sNub.index})">
						条件${sNub.index+1}
					</div>
				</c:forEach>
			</div>
			<div id="allTjDiv">
				<c:forEach  items ="${details}" var='detail' varStatus="sNub">
					<c:if test="${sNub.index==0}">
					<div class="tjDiv" id="tjd${sNub.index}">
					</c:if>
					<c:if test="${sNub.index!=0}">
					<div class="tjDiv_display" id="tjd${sNub.index}">
					</c:if>
						
						<div class="control-group">
							<label class="control-label">条件规则：</label>
							<div class="controls">
								<c:set var="conds" value="${fns:getDictList('condition_condition')}" />
								<c:if test="${!empty conds}">
									<c:set var="condition" value="${detail.condition}"/>
									<c:forEach  items ="${conds}" var='cond' varStatus="eNub">
										<c:set var="value" value="${cond.value}"/>
										<c:if test="${condition==value}">
											<input name="danxuan${sNub.index}" type="radio" value="${cond.value}" onchange="radioFun(${sNub.index})" disabled checked="checked"/>${cond.label}
										</c:if>
										<c:if test="${condition!=value}">
											<input name="danxuan${sNub.index}" type="radio" value="${cond.value}" onchange="radioFun(${sNub.index})" disabled/>${cond.label} 
										</c:if>
									</c:forEach>
								</c:if>
								<input name="ruleConditions[${sNub.index}].condition" style="display:none;" id="condition${sNub.index}" value="${detail.condition}">
							</div>
						</div>
						
						<!-- 条件表 -->
						<div class="control-group">
							<label class="control-label">条件表：</label>
							<div class="controls">
								${detail.tableName}
							</div>
						</div>
						
						<!-- 条件列 -->
						<div class="control-group">
							<label class="control-label">条件列：</label>
							<div class="controls">
								${detail.tableColumnName}
								<input name="ruleConditions[${sNub.index}].dictName" style="display:none;" id="dictName${sNub.index}" value="${detail.dictName}">
							</div>
						</div>
						
						<c:set var="dictName" value="${detail.dictName}"/>
						<c:if test="${!empty dictName}">
							<!-- 显示树 -->
							<!-- 符号 -->
							<div class="control-group" id="fh${sNub.index}" style="display: none;">
								<label class="control-label">符号：</label>
								<div class="controls">
									${detail.filtrationName}
									<input name="ruleConditions[${sNub.index}].filtration"  style="display:none;" id="filtration${sNub.index}" value="${detail.filtration}">
								</div>
							</div>
							
							<!-- 内容 -->
							<div class="control-group" id="nr${sNub.index}" style="display: none;">
								<label class="control-label">内容：</label>
								<div class="controls">
									<input name="ruleConditions[${sNub.index}].content" id="content${sNub.index}" readonly="readonly" value="${detail.content}" class="inputCon">
								</div>
							</div>
							
							<!-- 字典 -->
							<div class="control-group">
								<div class="controls">
									<div id="zidian${sNub.index}" class="ztree" style="margin-top:3px;float:left;"></div>
								</div>
							</div>
						</c:if>
						<c:if test="${empty dictName}">
							<!-- 显示符号和内容 -->
							<!-- 符号 -->
							<div class="control-group" id="fh${sNub.index}">
								<label class="control-label">符号：</label>
								<div class="controls">
									${detail.filtrationName}
									<input name="ruleConditions[${sNub.index}].filtration"  style="display:none;" id="filtration${sNub.index}" value="${detail.filtration}">
								</div>
							</div>
							
							<!-- 内容 -->
							<div class="control-group" id="nr${sNub.index}">
								<label class="control-label">内容：</label>
								<div class="controls">
									<input name="ruleConditions[${sNub.index}].content" id="content${sNub.index}" readonly="readonly" value="${detail.content}" class="inputCon">
								</div>
							</div>
							
							<div class="control-group" style="display: none;">
								<div class="controls">
									<div id="zidian${sNub.index}" class="ztree" style="margin-top:3px;float:left;"></div>
								</div>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>	
</form>	
</body>
</html>