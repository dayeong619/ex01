package com.yi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yi.controller.LoginController;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("****************AuthInterceptor prehandle"); //register컨트롤러 가기전에 호출됨
		HttpSession session = request.getSession();
		
		if(session.getAttribute("Auth") == null) { //로그인이 되어 있지 않은 상태임
			logger.info("current user is not login~ㅠㅠ");
			saveDest(request); //원래의 목적지와 매개변수를 저장함.
			response.sendRedirect(request.getContextPath()+"/auth/login");
			return false; //register컨트롤러 아예 못가게 막음.
		}
		
		return true;
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		
		
		
		
		
	}
	
	private void saveDest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString(); //목적지를 들고있음
		
		if(query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?"+query;
		}
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			logger.info("dest : "+uri+query);
			request.getSession().setAttribute("dest", uri+query);
		}
	}

	
	
	
	
	
}

















