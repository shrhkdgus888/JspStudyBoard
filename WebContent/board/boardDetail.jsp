<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" import="board.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bean객체 생성 / BoardDao dao = new BoardDao();와 같은 뜻 -->
<jsp:useBean id="dao" class="board.BoardDao"/>
<% 
	//게시글 번호(num)는 파라미터로 들어오게됌.
	int num = Integer.parseInt(request.getParameter("num"));
	BoardVo vo = dao.selectOne(num);
	//"vo"의 속성값을 vo에 넣어준다.
	pageContext.setAttribute("vo", vo);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 내용</title>
</head>
<body>
<h3>글 정보</h3>
<!--위의 setAttribute "vo"에서 vo로 넣어준 값들을 아래와 같이 p태그로 나타냄 -->
<p>번호:${vo.num}</p>
<p>제목:${vo.title}</p>
<p>작성자:${vo.writer}</p>
<p>내용:${vo.content}</p>
<p>등록일자:${vo.regdate}</p>
<p>조회수:${vo.cnt}</p>
<a href="<c:url value="editForm.jsp?num=${vo.num}"/>"><button>수정</button></a>
<a href="<c:url value="deleteForm.jsp?num=${vo.num}"/>"><button>삭제</button></a>
</body>
</html>