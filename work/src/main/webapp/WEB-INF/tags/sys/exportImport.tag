<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="URL" type="java.lang.String" required="true"%>
<li id="tagBtLi" class="btn-group">
	<input id="tag_btnExport" class="btn btn-primary" type="button" value="导出"/>
	<input id="tag_btnImport" class="btn btn-primary" type="button" value="导入"/></li>	
	<form id="exportForm" hidden="hidden" style="display: none;"  action="${URL}/export" method="post"></form>				 
</li> 
<div id="importBox" class="hide">
	<form id="importForm" action="${URL}/import" method="post" enctype="multipart/form-data" class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
		<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		<a href="${URL}/import/template">下载模板</a>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#tagBtLi").appendTo($("#searchForm").find(".toolbar ul"));
	
	$("#tag_btnExport").click(function(){
		top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
			if(v=="ok"){
				$("#exportForm").submit();
			}
		},{buttonsFocus:1});
		top.$('.jbox-body .jbox-icon').css('top','55px');
	});
	$("#tag_btnImport").click(function(){
		$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
			bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
	});
});
</script>