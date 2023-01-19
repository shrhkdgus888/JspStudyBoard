<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="board.*, java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//한글이 깨지지않도록 인코딩
	request.setCharacterEncoding("utf-8");
%>
<!-- Bean객체 생성 / BoardDao dao = new BoardDao();와 같은 뜻  -->
<jsp:useBean id="vo" class="board.BoardVo"/>
<jsp:useBean id="dao" class="board.BoardDao"/>
<!-- useBean 액션태그로 생성한 자바빈 객체에 대해서 프로퍼티(필드)에 값을 설정 -->
<!-- property 속성에 * 를 사용하면 프로퍼티와 동일한 이름의 파라미터를 이용하여 setter 메서드를 생성한 모든 프로퍼티(필드)에 대해 값을 설정,매칭해줌 -->
<%-- <jsp:setProperty="title" name="vo"/>
<jsp:setProperty="writer" name="vo"/>
<jsp:setProperty="content" name="vo"/> 등..
위의 동일한 내용, 쉽게 말해서 title은 vo클래스에 setTitle을 찾아서 자동으로 매칭시켜준다. --%>
<jsp:setProperty name="vo" property="*"/>

<% 
	dao.update(vo);
	/* vo에 대한 정보가 없으면 num값이 0이 들어온다 */
	pageContext.setAttribute("vo", vo);
%>
<!-- insert한 후에 list.jsp로 redirect함. -->
<!-- 아래와 동일한 코드 → response.sendRedirect(request.getContextPath() + "/board/list.jsp"); -->
<%-- /boardDetail.jsp?num=${vo.num} 수정이 완료된 내용을 볼 수 있도록 경로설정 --%>
<c:redirect url="${pageContext.request.contextPath}/boardDetail.jsp?num=${vo.num}"></c:redirect>