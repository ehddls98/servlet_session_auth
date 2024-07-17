package servlet.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDao;
import entity.User;

/**
 * 
 * Servlet 3대 저장소
 * 1. Application(Context)
 * - 전역(모든 쓰레드가 접근할 수 있는 영역)
 * - 서블릿 설정 관련 데이터(객체)들을 보관
 * - 생명주기: 서버가 실행되어지고 종료되어질 때까지
 * 2. Request
 * - 요청이 들어오고 Filter, Servlet, JSP 등 서로 Request 객체를 넘겨줄 때 사용
 * - 생명주기: 서버가 실행되어지고 종료되어질 때까지는 공통, 요청이 들어오고 응답이 되어지면 소멸
 * 3. Session
 * - 동일 클라이언트(동일 JSESSION KEY값)일 때 데이터를 유지
 * - 생명주기: 서버가 실행되어지고 종료되어질 때까지는 공통, 첫 요청때부터 브라우저가 종료되거나, 만료시간이 다되면 소멸
 */

@WebServlet("/api/login")
public class LoginServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = UserDao.findUserByUsername(username);
		if(user == null) {
			responseLoginError(resp);
			return;
		}
		
		if(!BCrypt.checkpw(password, user.getPassword())) {
			responseLoginError(resp);
			return;
		}
		
		//로그인 성공 시
		HttpSession session = req.getSession(); //새로운 세션 객체를 생성하고 현재 요청과 연관된 세션을 가져오거나 새로운 세션을 생성하여 세션 객체에 전달한다.
		//req.getSession()
		//1. 만약 현재 세션이 없다면, 새로운 세션을 생성합니다.
		//2. 만약 이미 존재하는 세션이 있다면, 그 세션을 반환합니다.
		session.setAttribute("authentication", user); //새로 생성된 session 객체에 user(id, password, name, email)등의 값을 넣어준다.
		//세션 객체에 user 객체를 "authentication"이라는 이름으로 저장한다.
		//이 과정은 사용자 정보를 세션에 저장하여 사용자가 인증되었음을 나타내기 위해 사용된다.
		//이후의 요청에서, session.getAttribute("authentication")을 호출하여 사용자 정보를 가져올 수 있다.
		resp.sendRedirect("/ssa/index"); // 로그인 성공 시 사용자를 index 페이지로 이동시킨다.
	}
	
	private void responseLoginError(HttpServletResponse resp) throws IOException {
		StringBuilder responsebody = new StringBuilder();
		responsebody.append("<script>");
		responsebody.append("alert('사용자 정보를 확인해주세요.');");
		responsebody.append("history.back();");
		responsebody.append("</script>");
	
		resp.setContentType("text/html"); //JavaScript 코드 형식으로 응답을 보냄
		resp.getWriter().println(responsebody.toString());
	}
	
	//로그인 성공시 JavaScript 코드를 통해 index 페이지로 이동
//	private void responseLoginSuccess(HttpServletResponse resp) throws IOException {
//		StringBuilder responsebody = new StringBuilder();
//		responsebody.append("<script>");
//		responsebody.append("location.replace(`/ssa/index')");
//		responsebody.append("</script>");
//	
//		resp.setContentType("text/html"); //JavaScript 코드 형식으로 응답을 보냄
//		resp.getWriter().println(responsebody.toString());
//	}

}
