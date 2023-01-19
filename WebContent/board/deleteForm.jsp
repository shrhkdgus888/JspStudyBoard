<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 삭제를 할껀지 말껀지 다시 묻는것 -->
<title>삭제확인</title>
</head>
<body>
<%
	int num = Integer.parseInt(request.getParameter("num"));
%>
<!-- jsp표현식 -->
<form action="delete.jsp">
<%-- <input type="hidden" name="num" value="${vo.num}"> --%>
<input type="hidden" value="<%=num%>" name="num"> 
	삭제하시겠습니까?
	<input type="submit" value="예">			
</form>

</body>
</html>