package com.yi.persistence;

import java.util.List;

import com.yi.domain.MemberVO;

public interface MemberDao {
	public String getTime();
	public void insertMember(MemberVO vo);
	public MemberVO readMember(String userid);
	
	public List<MemberVO> selectMemberByAll();
	public void updateMember(MemberVO vo);
	public MemberVO deleteMember(String userid);
	
	public MemberVO selectMemberByIdAndPw(String userid, String userpw);
	
	
}
