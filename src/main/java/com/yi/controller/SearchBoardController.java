package com.yi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;

@RequestMapping("/sboard/*")
@Controller
public class SearchBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception {
		//@ModelAttribute("cri")SearchCriteria cri -> model.addAttribute("cri", value);
		logger.info("-> list");
		
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list); //상단 list부분임
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri); //criteria 필요함 그래서 위에꺼 다시 넣어줌.
		pm.setTotalCount(service.listcountCriteria());
		model.addAttribute("pageMaker", pm); //하단 페이지 갯수
		//return view/sboard/list.jsp 를 찾아감 ->views폴더 밑에 sboard폴더 만들었음
	}
	
	
}















