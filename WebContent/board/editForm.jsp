<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="board.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bean객체 생성 / BoardDao dao = new BoardDao();와 같은 뜻 -->
<jsp:useBean id="dao" class="board.BoardDao"/>
<% 
	//게시글 번호(num)는 파라미터로 들어오게됌.
	int num = Integer.parseInt(request.getParameter("num"));
	//몇번의 게시글을 찾아올껀지 select함.
	BoardVo vo = dao.selectOne(num);
	//"vo"의 속성값을 vo에 넣어준다.
	pageContext.setAttribute("vo", vo);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
</head>
<body>
<h3>수정하기</h3>
<form action="edit.jsp" method="post">
	<!-- 사용자에게는 보여주지는 않지만 값${vo.num}이 존재할 수 있도록 해줌, Why? 어떤것을 변경할 것인지에 대한 개념을 알려주기 위해 -->
	<%-- 동일한 방법 <input type="hidden" value="<%=num%> name="num"> --%>
	<input type="hidden" name="num" value="${vo.num}">
							   <%-- value="${vo.title}" → 기존에 들어있던 게시글 --%>
	<input type="text" name="title" value="${vo.title}" required><br>
	<input type="text" name="writer" value="${vo.writer}" required disabled><br>
	<textarea row="4" cols="23.5" name="content" placeholder="내용">${vo.content}</textarea><br>
	<input type="submit" value="수정">
</form>
</body>
</html>