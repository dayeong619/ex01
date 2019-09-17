package com.yi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.LoginDto;
import com.yi.domain.MemberVO;
import com.yi.service.MemberService;

@RequestMapping("/auth")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	MemberService service;
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public void loginGET() { 
		logger.info("----- loginGET");
	}
	
	@RequestMapping(value="loginPost", method=RequestMethod.POST)
	public void loginPOST(MemberVO mvo, Model model) throws Exception { 
		logger.info("----- loginPOST, mvo는 "+mvo);
		MemberVO dbMember = service.selectMemberByIdAndPw(mvo.getUserid(), mvo.getUserpw());
		
		if(dbMember == null) {
			logger.info("loginPost -> login fail, not member");
			return ;
		}
		LoginDto dto = new LoginDto();
		dto.setUserid(dbMember.getUserid());
		dto.setUsername(dbMember.getUsername());
		model.addAttribute("loginDto", dto);
		
		
		
		
	}
	

	
	
	
	
}

















