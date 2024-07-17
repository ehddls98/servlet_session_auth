package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.User;

//@WebFilter({"/mypage", "/mypage/password"})
@WebFilter(filterName = "securityFilter")
public class SecurityFilter extends HttpFilter implements Filter {
       
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
		//ServletRequest는 HttpServletRequest의 상위 클래스이다. HttpServletRequest의 .getSession() 메서드를 사용하기 위해서 다운캐스팅을 해주었다.
		HttpSession session = httpRequest.getSession();
		
		System.out.println("시큐리티 필터");
		
		User user = (User) session.getAttribute("authentication");
		if(user == null) {
			StringBuilder responseBody = new StringBuilder();
			responseBody.append("<script>");
			responseBody.append("alert('잘못된 접근입니다.\\n로그인 후에 이용바랍니다.');"); 
			responseBody.append("location.href='/ssa/login';");
			responseBody.append("</script>");
			response.setContentType("text/html");
			response.getWriter().println(responseBody.toString());
			return;
		} 
		chain.doFilter(request, response);
	}
}
