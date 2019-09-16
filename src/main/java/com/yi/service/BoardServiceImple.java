package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDao;

@Service
public class BoardServiceImple implements BoardService { //dao에 있는 함수 호출하쟈

	@Autowired
	BoardDao dao;
	
	@Override
	@Transactional
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		
		for(String fullName : board.getFiles()) {
			dao.addAttach(fullName);
		}
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	@Transactional
	public BoardVO read(int bno) throws Exception {
		BoardVO vo = dao.read(bno);
		List<String> files = dao.getAttach(bno);
		vo.setFiles(files);
		return vo;
	}

	@Override
	@Transactional
	public void delete(int bno) throws Exception {
		dao.deleteAttach(bno); //파일목록만 삭제됨.
		dao.delete(bno);
		
	}

	@Override
	@Transactional
	public void modify(BoardVO vo, String[] delFiles) throws Exception {
		// 파일 삭제함
		if(delFiles != null) {
			for(String file : delFiles) {
				dao.deleteAttachByFullName(vo.getBno(), file);
			}
		}
		
		// 파일 추가함
		for(String filename : vo.getFiles()) {
			dao.addAttachByBno(filename, vo.getBno()); //새로 만들었음
		}
		
		dao.modify(vo);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public void viewCount(int bno) throws Exception {
		dao.viewCount(bno);
	}

	@Override
	public int listcountCriteria() throws Exception {
		return dao.countPaging();
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		dao.modify(vo);		
	}

	
	
}
