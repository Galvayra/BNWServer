<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		var ws = new WebSocket('ws:localhost:8080/bnwserver/echo');
		ws.onopen = function(message){
            alert('오픈');
        };
        //웹 소켓이 닫혔을 때 호출되는 이벤트
        ws.onclose = function(message){
            alert('닫힘');
        };
        //웹 소켓이 에러가 났을 때 호출되는 이벤트
        ws.onerror = function(message){
            alert('에러');
        };
        //웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
        ws.onmessage = function(message){
            alert(message);
        };
	</script>
</body>
</html>