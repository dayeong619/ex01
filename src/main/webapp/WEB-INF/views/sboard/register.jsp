<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">
						REGISTER PAGE
					</h3>
				</div>
				<form action="register" method="post" role="form" enctype="multipart/form-data">
					<!-- box-body -->
					<div class="box-body">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="" name="content" class="form-control" placeholder="Enter Content"></textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="Writer" class="form-control" placeholder="Enter Writer">
						</div>
						<div class="form-group">
							<label>Files</label>
							<input type="file" name="imgFiles" class="form-control" multiple="multiple">
						</div>
					</div>
					<!-- box-footer -->
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>	
<%@ include file="../include/footer.jsp" %>