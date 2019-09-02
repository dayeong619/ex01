package com.yi.service;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;

public interface BoardService {
	public void regist(BoardVO board) throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public BoardVO read(int bno) throws Exception;
	public BoardVO delete(int bno) throws Exception;
	public void modify(BoardVO vo) throws Exception;
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
}
