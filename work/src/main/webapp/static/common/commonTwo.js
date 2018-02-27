function contentView1(url,text,width,height){
	top.$.jBox.open('iframe:'+url,text,width,height,{
		/* iframeScrolling:'no', */
		dragLimit: true,
		showScrolling: true,			
		dragLimit: true,
		persistent: true,
		opacity: 0.5, 
		buttons: {}, 
		loaded: function (h) {
		    top.$("#jbox-content").css("overflow-y","hidden");
		}
	});	
}//静态使用 可随时删除


function TagBlackOrNone(TagName){
 var obj = document.getElementById(TagName);
 if(obj.style.display==""){
  obj.style.display = "none";
 }else{
  obj.style.display = "";
 }
 var btn = document.getElementById("btnBlack"); 
 if (btn.innerText != null) {
     if (btn.innerText == "隐藏")
         btn.innerText = "显示";
     else
         btn.innerText = "隐藏";
 }
}