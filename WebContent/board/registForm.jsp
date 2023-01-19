<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글등록</title>
</head>
<body>
<h3>등록하기</h3>
<form action="regist.jsp" method="post">
	<!-- required : 필수조건 -->
	<input type="text" name="title" palceholder="제목" required><br>
	<input type="text" name="writer" palceholder="작성자" required><br>
	<textarea row="4" cols="23.5" name="content" placeholder="내용"></textarea><br>
	<input type="submit" value="등록">
</form>
</body>
</html>