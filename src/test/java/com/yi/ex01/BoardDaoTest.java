package com.yi.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class) //junit
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) //설정파일은 요거닷
public class BoardDaoTest {
	
	@Autowired
	BoardDao dao;
	
//	@Test
	public void createTest() throws Exception {
		BoardVO vo  = new BoardVO();
		vo.setContent("새로운 글");
		vo.setTitle("새로운 제목");
		vo.setWriter("새로운 사람");
		dao.create(vo);
	} 
	
//	@Test
	public void updateTest() throws Exception {
		BoardVO vo  = new BoardVO();
		vo.setBno(8);
		vo.setContent("새로운 글");
		vo.setTitle("새로운 제목");
		vo.setWriter("새로운 사람");
		dao.modify(vo);
	} 
	
//	@Test
	public void pageTest() throws Exception {
		List<BoardVO> list = dao.listPage(1);
		for(BoardVO vo : list) {
			System.out.println(vo);
		}
	} 
	
	@Test
	public void listCriteriaTest() throws Exception {
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setPerPageNum(10);
		List<BoardVO> list = dao.listCriteria(cri);
		for(BoardVO vo : list) {
			System.out.println(vo);
		}
	} 
	
	
	
	
	
	
	
}
