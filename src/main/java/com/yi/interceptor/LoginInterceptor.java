package com.yi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yi.controller.LoginController;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("** preHandle **");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//modelAndView 컨트롤러에서 모델로 보낸거 받아오는 것
		//handler 어떤 컨트롤러 쓰는지
		logger.info("** postHandle **");
		HttpSession session = request.getSession();
		
		Object loginDto = modelAndView.getModel().get("loginDto"); //아이디랑,비번 일치 하는사람있어서 그 사람꺼 온거임
		if(loginDto != null) {
			session.setAttribute("Auth", loginDto); //그 사람의 아이디랑 비번 저장됨.
			
			Object dest = session.getAttribute("dest");
			String path = (dest != null) ? (String) dest : request.getContextPath();
			
			
			response.sendRedirect(path); //home컨트롤러에 return값으로 감.
		}else {
			response.sendRedirect(request.getContextPath()+"/auth/login");
		}
		
		
		
		
	}

}
