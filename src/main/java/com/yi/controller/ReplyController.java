package com.yi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.ReplyVO;
import com.yi.service.ReplyService;

/**
 * Handles requests for the application home page.
 */
@RestController //json으로 하기위해서
@RequestMapping("/replies")
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService service;
	
//	/replies ㅡPOST             create
//	/replies/all/{bno} ㅡGET    list 
//	/replies/{rno} ㅡPUT,patch  update
//	/replies/{rno} ㅡDelete     delete
//	/replies/{bno}/{page} ㅡGET listPage
	
	
	
	// /replies/all/8187
	@RequestMapping(value="/all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		logger.info("all/bno에 왔엉 ㅡ>bno는 "+bno);
		
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			List<ReplyVO> list = service.list(bno);
			entity = new ResponseEntity<List<ReplyVO>>(list, HttpStatus.OK); //200
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400 error
		}
		
		return entity;
	}
	
	// /replies
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){ // post로 보낼때 @RequestBody 바디로받으니깐
		ResponseEntity<String> entity = null;
		
		logger.info("create 합니당 ㅡ> vo: "+vo);
		
		try {
			service.create(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	//POST, PUT은 데이터가 body에 실려서 옴. 따라서 @RequestBody를 사용해야지만, 정상적으로 값을 받을 수 있음.
	
	@RequestMapping(value="{rno}", method=RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody ReplyVO vo ){
		ResponseEntity<String> entity = null;
		logger.info("update 야 ㅡ> rno는 "+rno+"vo는 "+vo);
		vo.setRno(rno); //넘어오는 rno를 넘어줌 vo에 
		
		try {
			service.update(vo);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
		}
		return entity;
	}
	
	@RequestMapping(value="{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		logger.info("delete 야 ㅡ> rno는 "+rno);
		
		try {
			service.delete(rno);
			entity = new ResponseEntity<>("success", HttpStatus.OK); //200 성공
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST); //400 에러
		}
		
		return entity;
	}
	
	// /replies/{bno}/{page} @PathVariable 이거 그대로 받겠다!!
	@RequestMapping(value="/{bno}/{page}", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") int bno, @PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		logger.info("listPage 야 ㅡ> rno는 "+bno+" page는 "+page);
		
		Criteria cri = new Criteria(); //변수로 페이지 받았찌만 크리테리아 안에 다시 넣어주긔
		cri.setPage(page);
		
		try {
			List<ReplyVO> list = service.listPage(bno, cri);
			
			PageMaker pagemaker = new PageMaker();
			pagemaker.setCri(cri);
			pagemaker.setTotalCount(bno); //토탈카운트 디비에서 가져오도록
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("pageMaker", pagemaker);
			
			entity = new ResponseEntity<>(map, HttpStatus.OK); //200 성공
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400 에러
		}
		
		return entity;
	}
	
	//List<ReplyVO>, PageMaker 두개 다 돌려줘야 됨. 그래서 map으로 
	
}


















