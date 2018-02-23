<%@ page language="java" contentType="text/html; charset=utf-8""
    pageEncoding="utf-8""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="resource/jquery-1.7.1.min.js"></script>
</head>
<body>
	<input type="button" value="go" onclick="go()"/>
	<input type="button" value="aaa" onclick="aaa()"/>

	<script>

		var url = "http://localhost:8080/sample/getMap";
		function go(){
			$.ajax({
			  url: url,
		      type: "POST",
		      dataType:'jsonp',
			  jsonp:'callback',
			  jsonpCallback:"success_jsonpCallback",
		      success: function(data) {
				alert("data -> "+data);
			  },
			  error: function(data){
				alert("error -> "+data);
			  }
			});
		}


		function aaa(){
		
			$.ajax({
				url: 'http://localhost:8080/sample/authCode',
				type: 'post',
				dataType:'jsonp',
				jsonp: "callback",
				success:function(data){
					alert(data)
				},
				error:function(data){
					alert("error -> "+data)
				}
			});
		
		}
		
		</script>
</body>