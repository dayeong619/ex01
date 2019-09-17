package com.yi.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.util.UploadFileUtils;

@RequestMapping("/sboard/*")
@Controller
public class SearchBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception {
		//@ModelAttribute("cri")SearchCriteria cri -> model.addAttribute("cri", value);
		logger.info("-> list GET");
		
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
	public String removePage(int bno, int page, SearchCriteria cri,  RedirectAttributes rattr)throws Exception{
		logger.info("------ deletePOST"+bno);
		BoardVO vo = service.read(bno); //지울 파일 목록 가지고 있음.
		service.delete(bno);
		
		//파일도 지워지도록 처리. 
		for(String filename : vo.getFiles()) {
			UploadFileUtils.deleteFile(uploadPath, filename);
		}
		
		rattr.addAttribute("page", cri.getPage());
		rattr.addAttribute("searchType", cri.getSearchType());
		rattr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/list?page="+page;
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public void modify(int bno, Model model, @ModelAttribute("cri")SearchCriteria cri) throws Exception { 
		logger.info("---- GET> modify.jsp 왔음  bno는"+bno);
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
	}
	
	//리다이렉트 전용 한글안깨지게 함 -> RedirectAttributes
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(BoardVO vo, Model model, String[] delFiles, List<MultipartFile> imgfiles, SearchCriteria cri, RedirectAttributes rattr) throws Exception { 
		logger.info("---- POST> modify 수정할꺼야"+cri);
		
		List<String> list = new ArrayList<>();
		for(MultipartFile file : imgfiles) { //파일 업로드
			logger.info("file의 이름은 "+file.getOriginalFilename());
			logger.info("file의 사이즈는 "+file.getSize());
			if(file.getSize() <= 0) { // ****예외처리**** (파일지우기만 하고 submit 클릭했을때)
				continue;
			}
			
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		
			list.add(savedName);
		}
		vo.setFiles(list);

		service.modify(vo, delFiles);
		
		// ****예외처리**** 지울파일이 없으면 스트링 배열이 null로 초기화됨. (파일첨부만 하고 submit 클릭했을때)
		if(delFiles != null) {
			for(String delFile : delFiles) { //파일 삭제
				logger.info(delFile);
				UploadFileUtils.deleteFile(uploadPath, delFile);
			}
		}
		
		rattr.addAttribute("bno", vo.getBno());
		rattr.addAttribute("page", cri.getPage());
		rattr.addAttribute("searchType", cri.getSearchType());
		rattr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sboard/readPage";
	}

	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGET() { //커맨드랑 동일한jsp파일 이름 이면 리턴생략하고 void로 만들면됨. board폴더안에 register.jsp파일
		logger.info("----- registerGET");
		// sboard/register
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo, List<MultipartFile> imgFiles) throws Exception { 
		logger.info("----- registerPOST");
		logger.info(vo.toString());

		ArrayList<String> list = new ArrayList<>(); 
		for(MultipartFile file : imgFiles) {
			logger.info("file name :" + file.getOriginalFilename());
			logger.info("file size :" + file.getSize());	
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			list.add(savedName);
		}
		
		vo.setFiles(list);
		
		service.regist(vo);
		//jsp가 아니라 controller로 감
		//리다이렉트 : 브라우저에 돌아갈때 /board/listAll주소로 바로 이동하라고 처리하는 것임
		//          브라우저가 화면을 그리기 전에 바로 http://localhost:8080/ex01/board/ListAll로 이동하게 됨
		
		return "redirect:/sboard/list"; 
	}
	
	@RequestMapping(value="/displayFile", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> displayFile(String filename){
		logger.info("-------------- displayFile, filename="+filename);
		
		String formatName = filename.substring(filename.lastIndexOf(".")+1);//확장자만 뽑아냄
		MediaType mType = null;
		ResponseEntity<byte[]> entity = null;
		
		if(formatName.equalsIgnoreCase("jpg")) {
			mType = MediaType.IMAGE_JPEG;
		}else if(formatName.equalsIgnoreCase("gif")) {
			mType = MediaType.IMAGE_GIF;
		}else if(formatName.equalsIgnoreCase("png")) {
			mType = MediaType.IMAGE_PNG;
		}
		InputStream in = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath+"/"+filename);
			headers.setContentType(mType);
			
			entity = new ResponseEntity<byte[]>(
												IOUtils.toByteArray(in),
												headers,
												HttpStatus.CREATED
												);		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);			
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return entity;
	}
	
	
}















