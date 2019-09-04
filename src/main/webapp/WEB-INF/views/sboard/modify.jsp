<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">
						MODIFY PAGE
					</h3>
				</div>
				
				<form action="modify" method="post" role="form">
					<!-- box-body -->
					<div class="box-body">
						<div class="form-group">
							<label>Title</label>
							<input type="hidden" name="searchType" value="${cri.searchType}">
							<input type="hidden" name="keyword" value="${cri.keyword}">
							<input type="hidden" name="bno" value="${board.bno }">
							<input type="hidden" name="page" value="${cri.page}">
							<input type="text" name="title" class="form-control" value="${board.title }">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="" name="content" class="form-control">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="Writer" class="form-control" value="${board.writer}" readonly="readonly">
						</div>
					</div>
					<!-- box-footer -->
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">Submit</button>
						<button type="button" id="submitCancle" class="btn btn-primary">Cancle</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>	
<%@ include file="../include/footer.jsp" %>
<script>
$("#submitCancle").click(function(){
	location.href="${pageContext.request.contextPath}/sboard/readPage?bno=${board.bno}&page="+${cri.page}+"&searchType="+${cri.searchType}+"&keyword="+${cri.keyword};
})

</script>