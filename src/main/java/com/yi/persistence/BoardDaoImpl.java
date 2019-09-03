package com.yi.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;

@Repository
public class BoardDaoImpl implements BoardDao {
	private static final String namespace = "com.yi.mapper.BoardMapper";
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+".create",vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return sqlSession.selectOne(namespace+".read",bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return sqlSession.selectList(namespace+".listAll");
	}

	@Override
	public BoardVO delete(int bno) throws Exception {
		return sqlSession.selectOne(namespace+".delete",bno);
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		System.out.println(vo);
		sqlSession.update(namespace+".modify",vo);
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		page = (page-1)*10; //0~9, 10~19, 20~29 페이지
		return sqlSession.selectList(namespace+".listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return sqlSession.selectList(namespace+".listCriteria", cri);
	}

	@Override
	public void viewCount(int bno) throws Exception {
		sqlSession.update(namespace+".viewCount", bno);		
	}

	@Override
	public int countPaging() throws Exception {
		return sqlSession.selectOne(namespace+".countPaging");
	}

}
