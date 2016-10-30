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
	<table border="1">
	<tr>
		<td>방번호</td>
		<td>방만든이</td>
		<td>방제목</td>
	</tr>
	<c:forEach var="room" items="${rooms}">
		<tr>
			<td onclick="test(${room.room_no})">${room.room_no}</td>
			<td>${room.creator}</td>
			<td>${room.room_title}</td>
		</tr>
	</c:forEach>
	</table>
	
	<div id="t">
		
	</div>
	
	
	<button onclick="test(5)">아작스</button>
	
	<script>
	
		function test(id) {
			$.ajax({
				url : "/bnwserver/a",
		        type: "get",
		        dataType : "json",
		        data : { "id" : id },
		        success : function(responseData){
		        	for(var i=0;i<responseData.length;i++)
		        	{
		        		alert(responseData[i].participant);
		        	}
		        	
		        	
		        	$("#t").append(responseData.participant);
		        },
		        error : function(request,status,error) {
		            alert("code:"+request.status+"\n"+"error:"+error);
		        }
		        
		    });
		}
	
	</script>
</body>
</html>