package com.yi.service;

import java.util.List;

import com.yi.domain.MemberVO;

public interface MemberService {
	public void create(MemberVO vo) throws Exception;
	public void modify(MemberVO vo) throws Exception;
	public MemberVO delete(String memberid) throws Exception;
	public List<MemberVO> listAll() throws Exception;

	public MemberVO selectMemberByIdAndPw(String userid, String userpw);
}
