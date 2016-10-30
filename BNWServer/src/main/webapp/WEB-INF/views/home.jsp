<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	
	<button onclick="test(5)">아작스</button>
	
	<script>
	
		function test(id) {
			
			var ws = new WebSocket('ws:localhost:8080/bnwserver/echo');
			ws.onopen = function(evt) {
                alert('연결됨');
            };
            ws.onmessage = function(evt) {
                alert('연결');
            };
            ws.onerror = function(evt) {
                alert('끊김');
            };
		}
	
	</script>
</body>
</html>