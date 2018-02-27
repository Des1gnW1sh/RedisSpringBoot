//多栏内容 显示隐藏代码
function showDiv(obj,num,len)
{
 for(var id = 1;id<=len;id++)
 {
  var ss=obj+id;
  var snav =obj+"nav"+id;
  if(id==num){
	try{document.getElementById(ss).style.display="block"}catch(e){};
	try{document.getElementById(snav).className="active"}catch(e){};
  }else{
	try{document.getElementById(ss).style.display="none"}catch(e){};
	try{document.getElementById(snav).className=""}catch(e){};
  }
 }
}

function showDivv(objv,numv,lenv)
{
 for(var id = 1;id<=lenv;id++)
 {
  var ssv=objv+id;
  var snavv =objv+"nav"+id;
  if(id==numv){
	try{document.getElementById(ssv).style.display="block"}catch(e){};
	try{document.getElementById(snavv).className="active"}catch(e){};
  }else{
	try{document.getElementById(ssv).style.display="none"}catch(e){};
	try{document.getElementById(snavv).className="normal"}catch(e){};
  }
 }  
}
//多栏内容 显示隐藏代码
function showDiv1(obj,num,len)
{
 for(var id = 1;id<=len;id++)
 {
  var ss=obj+id;
  if(id==num){
	try{document.getElementById(ss).style.display="block"}catch(e){};
  }else{
	try{document.getElementById(ss).style.display="none"}catch(e){};
  }
 }
}

//更改更多
function showMore(id,url){
	document.getElementById(id+"More").href=url;
}

//文章页面 文字缩放代码
function fontZoom(size){
var fonts = document.getElementById("fontzoom").getElementsByTagName("font");
var ps = document.getElementById("fontzoom").getElementsByTagName("p");
var divs=document.getElementById("fontzoom").getElementsByTagName("div");
for(var i=0;i<fonts.length;i++)
{
    fonts[i].removeAttribute("size");
}

var spans= document.getElementById("fontzoom").getElementsByTagName("span");
for(var i=0;i<spans.length;i++)
{
    spans[i].removeAttribute("style");
}
for(var i=0;i<ps.length;i++)
{
    ps[i].removeAttribute("style");
	ps[i].style.fontSize=size+'px';
}
for(var i=0;i<divs.length;i++)
{
    divs[i].removeAttribute("style");
	divs[i].style.fontSize=size+'px';
}
document.getElementById('fontzoom').style.fontSize=size+'px';

document.getElementById('fontzoom').style.lineHeight=(size*2)+'px';

}

//分页代码
function createPageHTML(_nPageCount, _nCurrIndex, _sPageName, _sPageExt){

	 if(_nPageCount == null || _nPageCount<=1){
		 return;
	 }
	 var nCurrIndex = _nCurrIndex || 0;
	 document.write("共"+_nPageCount+"页&nbsp;&nbsp;第"+(nCurrIndex+1)+"页&nbsp;");
	 if(nCurrIndex == 0){
		 document.write("首页&nbsp;上一页&nbsp;&nbsp;");
	 }else if(nCurrIndex == 1){
		 document.write("<a href=\""+_sPageName+"."+_sPageExt+"\">首页</a>&nbsp;<a href=\""+_sPageName+ "."+_sPageExt+"\">上一页</a>&nbsp;&nbsp;");
	 }else{
		document.write("<a href=\""+_sPageName+"."+_sPageExt+"\">首页</a>&nbsp;<a href=\""+_sPageName+"_" + (nCurrIndex-1) + "."+_sPageExt+"\">上一页</a>&nbsp;&nbsp;");
	 }
	 var x = 5;
	 var y = 5;
	 if(nCurrIndex<5){
		x = nCurrIndex;
		y = 10-x;
	 }else if(nCurrIndex>(_nPageCount-5)){
		y = _nPageCount-nCurrIndex;
		x = 10-y;
	 }
	 var nBegin = nCurrIndex-x;
	 var nEnd = nCurrIndex+y>_nPageCount?_nPageCount:(nCurrIndex+y);
     //起始页面必须为0
	 if(nBegin<0){
		nBegin=0; 
	}
	 for(var i=nBegin; i<nEnd; i++){
		 if(nCurrIndex == i){
			  document.write("<span style='color:red;font:bold;'>"+(i+1) + "</span>&nbsp;");
		 }else{
			if(i==0){
				document.write("<a href=\""+_sPageName+"."+_sPageExt+"\">"+(i+1)+"</a>&nbsp;");
			}else{
			  document.write("<a href=\""+_sPageName+"_" + i + "."+_sPageExt+"\">"+(i+1)+"</a>&nbsp;");
			}
		 }
	  }
	  if(nCurrIndex >= (_nPageCount-1)){
		document.write("&nbsp;&nbsp;下一页&nbsp;末页&nbsp;");
	  }else {
		document.write("&nbsp;&nbsp;<a href=\""+_sPageName+"_"+(nCurrIndex+1)+"."+_sPageExt+"\">下一页</a>&nbsp;<a href=\""+_sPageName+"_" + (_nPageCount-1) + "."+_sPageExt+"\">末页</a>&nbsp;");
	  }
	  document.write("&nbsp;转到<input type='text' id='gotopage' length='6' style='border:1px solid #888888;width:50px;'/>页&nbsp;<input type='button' onClick='goPage("+_nPageCount+",\""+_sPageName+"\",\""+_sPageExt+"\");'value=' 转 到 '/>");
}

function goPage(_nPageCount,_sPageName,_sPageExt){
	var page = document.getElementById("gotopage").value;
	var patrn = /^\d{1,4}$/;
	if(!patrn.exec(page)){
		page = 1;
	}
	page = page>_nPageCount?_nPageCount:page;
	page = page<1?1:page;
	if(page == 1){
		location.href=_sPageName+"."+_sPageExt;
	}else{
		location.href=_sPageName+"_"+(page-1)+"."+_sPageExt;
	}

}
function createPageHTML1(_nPageCount, _nCurrIndex, _sPageName, _sPageExt){

	 if(_nPageCount == null || _nPageCount<=1){
		 return;
	  }
	 var nCurrIndex = _nCurrIndex || 0;
	 if(nCurrIndex == 0){
		 document.write("1&nbsp;");
	 }else{
		 document.write("<a href=\""+_sPageName+"."+_sPageExt+"\">1</a>&nbsp;");
	 }
	 for(var i=1; i<_nPageCount; i++){
		 if(nCurrIndex == i){
			  document.write((i+1) + "&nbsp;");
		 }else{
			  document.write("<a href=\""+_sPageName+"_" + i + "."+_sPageExt+"\">"+(i+1)+"</a>&nbsp;");
		 }
	  }
}
//左右不间断滚动图片
function scrollpic(){
	var speed3=25//速度数值越大速度越慢
	var demo1 = document.getElementById("demo");
	var demo1 = document.getElementById("demo1");
	var demo2 = document.getElementById("demo2");
	demo2.innerHTML=demo1.innerHTML
	var MyMar=setInterval(Marquee,speed3)
	demo.onmouseover=function() {clearInterval(MyMar)}
	demo.onmouseout=function() {MyMar=setInterval(Marquee,speed3)}
}
function Marquee(){
	var demo1 = document.getElementById("demo");
	var demo1 = document.getElementById("demo1");
	var demo2 = document.getElementById("demo2");
	if(demo2.offsetWidth-demo.scrollLeft<=0)
	demo.scrollLeft-=demo1.offsetWidth
	else{
		demo.scrollLeft++
	}
}

//天气预报

var MiniSite = new Object();

MiniSite.Browser = {
	ie: /msie/.test(window.navigator.userAgent.toLowerCase()),
	moz: /gecko/.test(window.navigator.userAgent.toLowerCase()),
	opera: /opera/.test(window.navigator.userAgent.toLowerCase())
};

MiniSite.$ = function(s)
{
	return (typeof s == 'object') ? s: document.getElementById(s);
};

MiniSite.JsLoader = {
	load: function(sUrl, fCallback)
	{
		var _script = document.createElement('script');
		_script.setAttribute('charset', 'gbk');
		_script.setAttribute('type', 'text/javascript');
		_script.setAttribute('src', sUrl);
		document.getElementsByTagName('head')[0].appendChild(_script);

		if (MiniSite.Browser.ie)
		{
			_script.onreadystatechange = function()
			{
				if (this.readyState=='loaded' || this.readyState=='complete')
				{
					fCallback();
				}
			};
		}
		else if (MiniSite.Browser.moz)
		{
			_script.onload = function()
			{
				fCallback();
			};
		}
		else
		{
			fCallback();
		}
	}
};

MiniSite.Weather = {

	_print: function(province, city, conainter){
		MiniSite.JsLoader.load("http://weather.news.qq.com/inc/minisite_59.js", function()
		{
			try
			{
				MiniSite.$(conainter).innerHTML = __minisite__weather__ ;
			}
			catch (e)
			{
			}
		});
	},

	print: function(conainter){
		var ok = function()
		{
			var province = null;
			var city = null;


			MiniSite.Weather._print(province, city, conainter);
		};
		ok();
	}
};



function changeimg(){
var i=document.getElementById('imgcount').innerHTML
if(i==3){
showDiv('scrollimg',i,3);
i=1;
}else{
showDiv('scrollimg',i,3);
i++;
}
document.getElementById('imgcount').innerHTML=i;
setTimeout('changeimg()', 2500)
}
