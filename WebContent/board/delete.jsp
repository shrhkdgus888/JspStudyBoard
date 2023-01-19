<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="board.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bean객체 생성 / BoardDao dao = new BoardDao();와 같은 뜻  -->
<jsp:useBean id="dao" class="board.BoardDao"/>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	dao.delete(num);
%>
<!-- insert한 후에 list.jsp로 redirect함. -->
<!-- 아래와 동일한 코드 → response.sendRedirect(request.getContextPath() + "/board/list.jsp"); -->
<%-- /boardDetail.jsp?num=${vo.num} 수정이 완료된 내용을 볼 수 있도록 경로설정 --%>
<c:redirect url="${pageContext.request.contextPath}/list.jsp"/>