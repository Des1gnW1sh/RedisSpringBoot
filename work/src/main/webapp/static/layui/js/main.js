var myChart = echarts.init(document.getElementById('larry-seo-stats'));
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function randomData(now) {
	if(!now){
		now = new Date();
	}
    value = Math.random() * 210 + 5;
    return {
        name: now,
        value: [
            now,
            Math.round(value)
        ]
    }
}

var data = [];
var now = +new Date();
var oneDay = 1;
var value = Math.random() * 1000;
 var a = now.valueOf();
for (var i = 0; i < 1000; i++) {
	 a = a-(100000-i*100);
//    data.push(randomData(new Date(a)));
}

option = {
	
	title: {
	        text: '数据处理实时速度'
	    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
        	console.log(params)
            params = params[0];
            var date = new Date(params.name);
            return date.Format("hh:mm:ss") + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};

setInterval(function () {

    for (var i = 0; i < 5; i++) {
    	if(data.length>1000){
    		data.shift();
    	}
        data.push(randomData(new Date()));
    }

    myChart.setOption({
        series: [{
            data: data
        }]
    });
}, 1000);


myChart.setOption(option);
