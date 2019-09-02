package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDao;

@Service
public class BoardServiceImple implements BoardService { //dao에 있는 함수 호출하쟈

	@Autowired
	BoardDao dao;
	
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return dao.read(bno);
	}

	@Override
	public BoardVO delete(int bno) throws Exception {
		return dao.delete(bno);
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		dao.modify(vo);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	
}
