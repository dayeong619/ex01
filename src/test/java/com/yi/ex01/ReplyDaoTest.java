package com.yi.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
import com.yi.persistence.ReplyDao;

@RunWith(SpringJUnit4ClassRunner.class) //junit
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) //설정파일은 요거닷
public class ReplyDaoTest {
	
	@Autowired
	ReplyDao dao;
	
//	@Test
	public void createTest() throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setRno(1);
		vo.setBno(8187);
		vo.setReplytext("첫댓");
		vo.setReplyer("야옹이");
		dao.create(vo);
	} 
	
//	@Test
	public void updateTest() throws Exception{
		ReplyVO vo = new ReplyVO();
		vo.setRno(1);
		vo.setReplytext("첫수정수정");
		dao.update(vo);
	}
	
//	@Test
	public void listTest() throws Exception{
		List<ReplyVO> list = dao.list(1);
		for(ReplyVO vo : list) {
			System.out.println(vo);
		}
	}
	
//	@Test
	public void deleteTest() throws Exception{
		dao.delete(1);
	}
	
	@Test
	public void listPageTest() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(1);
		List<ReplyVO> list = dao.listPage(8187, cri);
		for(ReplyVO vo : list) {
			System.out.println(vo);
		}
	}
	
	
	
}
