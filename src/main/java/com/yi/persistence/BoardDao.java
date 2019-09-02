package com.yi.persistence;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;

public interface BoardDao {
	public void create(BoardVO vo) throws Exception;
	public BoardVO read(int bno) throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public BoardVO delete(int bno) throws Exception;
	public void modify(BoardVO vo) throws Exception;
//	public void viewCount() throws Exception;
	
	public List<BoardVO> listPage(int page) throws Exception;
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	
}
