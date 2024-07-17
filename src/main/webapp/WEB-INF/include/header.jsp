<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	// session 객체에서 authentication 속성을 가져옴으로서 현재 사용자가 인증된 상태인지 확인한다.
 	// 1. 만약 세션에 "authentication" 속성이 존재하고, 그것이 User 객체라면, user 변수에 해당 User 객체가 저장한다.
 	// 2. 만약 세션에 "authentication" 속성이 존재하지 않거나, null이라면, user 변수는 null이 된다.
 	User user = (User) session.getAttribute("authentication");
 	
 %> 
<header>
	<a href="/ssa/index"><h1 class="logo">LOGO</h1></a>
	<ul class="top-nav">
	<%
		if(user != null) {
			
	%>
		<li><%=user.getName() %>님 환영합니다</li>
		<a href="/ssa/mypage"><li>마이페이지</li></a>
		<a href="/ssa/logout"><li>로그아웃</li></a>
	<%
		} else {
	%>
		<a href="/ssa/login"><li>로그인</li></a>
		<a href="/ssa/register"><li>회원가입</li></a>
	<%
		}
	%>
	</ul>
</header>