package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.MemberVO;
import com.yi.persistence.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao dao;

	@Override
	public void create(MemberVO vo) throws Exception {
		dao.insertMember(vo);
	}

	@Override
	public void modify(MemberVO vo) throws Exception {
		dao.updateMember(vo);
	}

	@Override
	public MemberVO delete(String memberid) throws Exception {
		return dao.deleteMember(memberid);
	}

	@Override
	public List<MemberVO> listAll() throws Exception {
		return dao.selectMemberByAll();
	}

	@Override
	public MemberVO selectMemberByIdAndPw(String userid, String userpw) {
		return dao.selectMemberByIdAndPw(userid, userpw);
	}
	
	
}
