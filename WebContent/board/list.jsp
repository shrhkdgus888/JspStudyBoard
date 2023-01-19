<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" import="board.*, java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	BoardDao dao = new BoardDao();
	List<BoardVo> ls = dao.selectAll();
	/* 아래 ${ls} EL태그에 접근할 수 있도록 ls를 담아준다. */
	pageContext.setAttribute("ls", ls);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록</title>
</head>
<body>
<h2>게시글 목록</h2>
<table border="1">
<tr>
	<th>번호</th>
	<th>제목</th>
	<th>작성자</th>
	<th>등록일</th>
	<th>조회수</th>
</tr>
<!-- jstl반복문(=for문과 같음) -->
<c:forEach var="board" items="${ls}">
<tr>
	<td>${board.num}</td>
	<%-- ${pageContext.request.contextPath} (=최상위폴더, board) 경로 설정시 주의! borad 두번 겹치는거 확인!--%>
	<!-- 상대적인 경로, pageContext객체는 PageContext 클래스를 상속함.-->
	<!-- 웹 컨테이너가 JSP 실행시 자동으로 생성해서 제공하는 내장 객체 -->
	<td><a href="${pageContext.request.contextPath}/board/boardDetail.jsp?num=${board.num}">${board.title}</a></td>
	<td>${board.writer}</td>
	<td>${board.regdate}</td>
	<td>${board.cnt}</td>	
</tr>
<%-- <p>${board}</p> --%>
</c:forEach>
</table>
<%-- <c:url value="/board/registForm.jsp"/> → 절대경로 / ContextPath최상위 경로까지 포함
	 <c:url value="board/registForm.jsp"/>  → 상대경로 --%>
<a href="<c:url value="registForm.jsp"/>"><button>글 등록</button></a>
</body>
</html>