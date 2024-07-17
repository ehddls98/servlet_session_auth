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

//@WebFilter("/*") // /*: 어떠한 경로로 들어오든지 이 filter를 무조건 거쳐간다.
@WebFilter(filterName = "encodingFilter")
public class EncodingFilter extends HttpFilter implements Filter {
       
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//전처리
		System.out.println("전처리");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response); //다음 필터 또는 서블릿 호출
		//chain: tomcat과 servlet 사이에서 req와 resp를 필터링한다. filter는 여러개를 엮어서 사용할 수 있다.
		//후처리
		System.out.println("후처리");
	}
}
