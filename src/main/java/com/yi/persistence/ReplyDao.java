package com.yi.persistence;

import java.util.List;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;

public interface ReplyDao {
	public List<ReplyVO> list(int bno) throws Exception;
	public void create(ReplyVO vo) throws Exception;
	public void update(ReplyVO vo) throws Exception;
	public void delete(int rno) throws Exception;
	
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception; //댓글 페이지 번호
	public void totalCount(int bno) throws Exception;
	public int getBno(int rno) throws Exception;
}
