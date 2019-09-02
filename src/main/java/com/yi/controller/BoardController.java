package com.yi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService service;
	
	
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGET() { //커맨드랑 동일한jsp파일 이름 이면 리턴생략하고 void로 만들면됨. board폴더안에 register.jsp파일
		logger.info("----- registerGET");
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo) throws Exception { 
		logger.info("----- registerPOST");
		logger.info(vo.toString());

		service.regist(vo);
		return "redirect:/board/listAll"; //jsp가 아닌 controller로 바로감!(화면에 그리지 않고 바로 요청함)
	}
	
	@RequestMapping(value="listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception { 
		logger.info("----- listAll");
		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);
	}
	
//	@RequestMapping(value="read", method=RequestMethod.GET)
//	public void read(int bno, Model model) throws Exception { //게시물 하나를 읽는것 
//		logger.info("----- read");
//		logger.info("---- read에 bno"+bno);
//		BoardVO vo = service.read(bno);
//		
//		model.addAttribute("board", vo); //컨트롤러에서의 내용을 jsp에 보여줄때 모델이 필요해
//	}
//	
//	@RequestMapping(value="delete", method=RequestMethod.GET) //GET으로 삭제
//	public String deletePOST(int bno)throws Exception{
//		logger.info("------ deletePOST");
//		service.delete(bno);
//		return "redirect:/board/listAll";
//	}
	
	@RequestMapping(value="remove", method=RequestMethod.POST) // POST로 삭제
	public String deleteGET(int bno)throws Exception{
		logger.info("------ deletePOST");
		System.out.println(bno);
		service.delete(bno);
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public void modify(int bno, Model model, Criteria cri) throws Exception { 
		logger.info("---- GET> modify.jsp 왔음");
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
		model.addAttribute("cri",cri);
	
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(BoardVO vo, int page, Model model) throws Exception { 
		logger.info("---- POST> modify 수정할꺼야");
		System.out.println(vo);
		service.modify(vo);
		
		return "redirect:/board/readPage?page="+page+"&bno="+vo.getBno();
	}
	
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model, Criteria cri) throws Exception { 
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list); //상단 list부분임
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri); //criteria 필요함 그래서 위에꺼 다시 넣어줌.
		pm.setTotalCount(4096);
		model.addAttribute("pageMaker", pm); //하단 페이지 갯수
	}
	
	@RequestMapping(value="readPage", method=RequestMethod.GET)
	public void readPage(int bno, Model model, Criteria cri) throws Exception { //게시물 하나를 읽는것 
		logger.info("----- readPage GET");
		logger.info("---- readPage GET에 bno"+bno);
		BoardVO vo = service.read(bno);
		
		model.addAttribute("board", vo); //컨트롤러에서의 내용을 jsp에 보여줄때 모델이 필요해
		model.addAttribute("cri", cri); // go list눌러도 그 페이지 되도록
	}
	
	@RequestMapping(value="/removePage", method=RequestMethod.POST) // POST로 삭제
	public String removePage(int bno, int page)throws Exception{
		logger.info("------ deletePOST");
		System.out.println(bno);
		service.delete(bno);
		return "redirect:/board/listPage?page="+page;
	}
	
	
	
	
	
	
}

















