package com.yi.domain;

public class PageMaker {
	private int totalCount;//게시글 전체갯수
	private int startPage; //현재 보이는 페이지 시작번호
	private int endPage;   //현재 보이는 페이지 끝 번호
	private boolean prev;  //이전 10개 존재여부
	private boolean next;  //이후 10개 존재여부
	private int displayPageNum = 10; //보이는 페이지번호 갯수
	private Criteria cri;
	
	private void calcData() {
		//현재선택한 페이지 번호는 cri.getPage
		//현재 페이지의 끝 번호를 구함 -> ex) 15/10 = 1.5 = 2 * 10 = 20 
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		
		//현재 페이지의 시작 번호를 구함 -> ex) 20 - 10 => 10 + 1 = 11
		startPage = (endPage - displayPageNum) + 1;
		
		//ex) totalCount = 151-16
		//현페이지에 보여질 게시물 갯수 
		//전체 게시물이 151이고 현재 페이지가 15일 때, 마지막 end는 16으로 나타나야 한다.
		//Math.ceil(151/10) = 16
		int tempEndPage = (int) (Math.ceil(totalCount / (double)cri.getPerPageNum()));
		
		//만약, 끝 페이지 번호가 실제 끝번호보다 크다면 변경해준다.
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		//이전 페이지 여부
		if(startPage == 1){
			prev = false;
		}else {
			prev = true;
		}
		
		//다음 페이지 여부
		if(endPage * cri.getPerPageNum() >= totalCount) {
			next = false;
		}else {
			next = true;
		}
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData(); //토탈카운트값 넣고 바로 호출되도록.
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public Criteria getCri() {
		return cri;
	}
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}
	
	
	
	
}
