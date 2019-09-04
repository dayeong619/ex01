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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		List<BoardVO> list = service.listSearch(cri);
		model.addAttribute("list", list); //상단 list부분임
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri); //criteria 필요함 그래서 위에꺼 다시 넣어줌.
		pm.setTotalCount(service.listSearchCount(cri));
		model.addAttribute("pageMaker", pm); //하단 페이지 갯수
		//return view/sboard/list.jsp 를 찾아감 ->views폴더 밑에 sboard폴더 만들었음
	}
	
	@RequestMapping(value="readPage", method=RequestMethod.GET)
	public void readPage(int bno, Model model, @ModelAttribute("cri")SearchCriteria cri) throws Exception { //게시물 하나를 읽는것 
		logger.info("----- readPage GET");
		logger.info("---- readPage GET에 bno"+bno);
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo); //컨트롤러에서의 내용을 jsp에 보여줄때 모델이 필요해
	}
	
	@RequestMapping(value="/removePage", method=RequestMethod.POST) // POST로 삭제
	public String removePage(int bno, int page)throws Exception{
		logger.info("------ deletePOST");
		System.out.println(bno);
		service.delete(bno);
		return "redirect:/sboard/list?page="+page;
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public void modify(int bno, Model model, @ModelAttribute("cri")SearchCriteria cri) throws Exception { 
		logger.info("---- GET> modify.jsp 왔음");
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
	}
	
	//리다이렉트 전용 한글안깨지게 함 -> RedirectAttributes
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(BoardVO vo, Model model, @ModelAttribute("cri")SearchCriteria cri, RedirectAttributes rattr) throws Exception { 
		logger.info("---- POST> modify 수정할꺼야"+cri);
		System.out.println(vo);
		service.modify(vo);
		
		rattr.addAttribute("bno", vo.getBno());
		rattr.addAttribute("page", cri.getPage());
		rattr.addAttribute("searchType", cri.getSearchType());
		rattr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sboard/readPage";
	}

	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGET() { //커맨드랑 동일한jsp파일 이름 이면 리턴생략하고 void로 만들면됨. board폴더안에 register.jsp파일
		logger.info("----- registerGET");
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo) throws Exception { 
		logger.info("----- registerPOST");
		logger.info(vo.toString());

		service.regist(vo);
		return "redirect:/sboard/list"; //jsp가 아닌 controller로 바로감!(화면에 그리지 않고 바로 요청함)
	}
	
	
}















