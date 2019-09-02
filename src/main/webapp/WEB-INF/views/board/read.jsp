<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">
						READ PAGE
					</h3>
				</div>
				
					<!-- box-body -->
					<div class="box-body">
						<!-- post로 삭제하는법 -->
						<form role="form" method="post" id="f1"><!-- action없음 주소를 안보내기위해서 -->
							<input type="hidden" name="bno" value="${board.bno }">
						</form>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" value="${board.title }" readonly="readonly">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="" name="content" class="form-control" readonly="readonly">${board.content}</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="Writer" class="form-control" value="${board.writer}" readonly="readonly">
						</div>
					</div>
					<!-- box-footer -->
					<div class="box-footer">
						<button class="btn btn-warning" id="modify">Modify</button>
						<button class="btn btn-danger" id="remove">Remove</button>
						<button class="btn btn-primary" id="list">Go List</button>
						
					</div>
				
			</div>
		</div>
	</div>
</section>	
<%@ include file="../include/footer.jsp" %>
<script>
	/* $("#remove").click(function(){
		var a = confirm("삭제하시겠습니까?");
		if(a==true){
			location.href="${pageContext.request.contextPath}/board/delete?bno=${board.bno}";
		}
		return false;
	}) */
	
	$("#remove").click(function(){
		$("#f1").attr("action","remove");
		$("#f1").attr("method","post");
		$("#f1").submit();
	})
	
	
	$("#list").click(function(){
		location.href="${pageContext.request.contextPath}/board/listAll";
	})
	
	$("#modify").click(function(){
		location.href="${pageContext.request.contextPath}/board/modify?bno=${board.bno}";
	})
	
</script>