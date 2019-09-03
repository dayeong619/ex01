<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Search Board</h3>
				</div>
				<div class="box-body">
					<select>
						<option value="n" ${cri.searchType == null?"selected":"" }>ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ</option>
						<option value="t" ${cri.searchType == "t"?"selected":"" }>Title</option>
						<option value="c" ${cri.searchType == "c"?"selected":"" }>Content</option>
						<option value="w" ${cri.searchType == "w"?"selected":"" }>Writer</option>
						<option value="tc" ${cri.searchType == "tc"?"selected":"" }>Title or Content</option>
						<option value="cw" ${cri.searchType == "cw"?"selected":"" }>Content or Writer</option>
						<option value="tcw" ${cri.searchType == "tcw"?"selected":"" }>Title or Content or Writer</option>
					</select>
					<input type="text" id="keywordInput" name="keyword" value="${cri.keyword }">
					<button id="btnSearch">Search</button>
					<button id="btnAdd">New Board</button>
				</div>
			</div>
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">
						LIST SEARCH PAGE
					</h3>
				</div>
				<!-- box-body -->
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th style="width:10px;">Bno</th>
							<th>Title</th>
							<th>Writer</th>
							<th>RegDate</th>
							<th style="width:40px;">ViewCnt</th>
						</tr>
						<c:forEach var="board" items="${list }">
							<tr>
								<td>${board.bno }</td>
								<td><a href="readPage?bno=${board.bno}&page=${pageMaker.cri.page}">${board.title }</a></td>
								<td>${board.writer }</td>
								<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><span class="badge bg-red">${board.viewcnt }</span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- box-footer -->
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev }"><!-- 이전페이지 -->
								<li><a href="listPage?page=${pageMaker.startPage-1 }"> &laquo; </a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li ${pageMaker.cri.page == idx?"class='active'":"" }><a href="listPage?page=${idx }">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next }"><!-- 다음페이지 -->
								<li><a href="listPage?page=${pageMaker.endPage+1 }"> &raquo; </a></li>
							</c:if>
							 <!-- a태그: 누를때마다 그 페이지의 번호가 인덱스로 전달됨 -->
							 <!-- 누를때마다 css변하게. pageMaker에 cri가 있어서 그 크리랑 인덱스랑 비교해서 있으면 클래스active적용되게 없으면 클래스없음 -->
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>	
<%@ include file="../include/footer.jsp" %>






