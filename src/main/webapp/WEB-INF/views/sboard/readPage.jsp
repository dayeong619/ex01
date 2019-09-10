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
							<input type="hidden" name="page" value="${cri.page}">
							<input type="hidden" name="searchType" value="${cri.searchType}">
							<input type="hidden" name="keyword" value="${cri.keyword}">
							
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
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label>Writer</label>
					<input type="text" placeholder="user id" class="form-control" id="newReplyWriter">
					<label>Reply Text</label>
					<input type="text" placeholder="text" class="form-control" id="newReplyText">
				</div>
				<div class="box-footer">
					<button type="button" class="btn btn-primary" id="btnReplyAdd">ADD REPLY</button>
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="btnReplyList">
					<span class="bg-green">Replies List <span id="cncncn">[${board.replycnt}]</span></span>
				</li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
			</div>
		</div>
	</div>
	<div id="modifyModal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"></h4>
					<input type="hidden" data-rno="${rno}"><!-- rno번호 넣음 -->
				</div>
				<div class="modal-body" data-rno="${rno}">
					<p><input type="text" id="replytext" class="form-control"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" id="btnReplySaveMod">Modify</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<script id="temp" type="text/x-handlebars-template">
	{{#each.}}
	<li class="replyLi" data-rno="{{rno}}"><!-- 댓글 반복되어서 들어감 -->
		<i class="fa fa-comments bg-blue"></i>
		<div class="timeline-item">
			<span class="time">
				<i class="fa fa-clock-o"></i>{{tempdate regdate}}
			</span>
			<h3 class="timeline-header">
				<strong>{{rno}}</strong> -{{replyer}}
			</h3>
			<div class="timeline-body">{{replytext}}</div>
			<div class="timeline-footer">
				<a class="btn btn-warning btn-xs btnReplyModify" data-toggle="modal" data-target="#modifyModal">Modify</a>
				<a class="btn btn-danger btn-xs btnReplyDelete" data-rno="{{rno}}">Delete</a>
			</div>
		</div>
	</li>
	{{/each}}
	</script>
	
	<script>
	
	Handlebars.registerHelper("tempdate", function(time){ //replydate값이 time에 하나씩 들어옴
		var date = new Date(time);
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		
		return year + "-"+month+"-"+day;
	})
	
			var bno = ${board.bno };
			var currentPage = 1;
			
			function getListAll(page){
				$.ajax({
					url:"${pageContext.request.contextPath}/replies/"+bno+"/"+page, /* e */
					type:"get",
					dataType:"json",
					success:function(res){
						console.log(res);
						
						$(".replyLi").remove();
						
						var source = $("#temp").html();
						var fn = Handlebars.compile(source);
						var str = fn(res.list);
						
						
						$(".timeline").append(str);
						
						printPaging(res);	
					}   
				
				})
			}
			
			function printPaging(res){ //ajax로 받은 res가 넘어옴
				$(".pagination").empty();
				for(var i = res.pageMaker.startPage; i<=res.pageMaker.endPage; i++){
					var $li = $("<li>");
					var $a = $("<a>").text(i).attr("href", "#");
					if(res.pageMaker.cri.page == i){
						$li.addClass("active");	
					}
					$li.append($a);
					$(".pagination").append($li);
				}
			}
			
			
			
			$("#btnReplyList").click(function(){
				getListAll(1);
				
			})
			
			$(document).on("click", ".pagination a", function(e){ //페이지번호 누르면 이동되도록
				e.preventDefault(); //이벤트를 막음
				var page = $(this).text(); //선택한 a태그가 들고있는 페이지
				currentPage = page; //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ수정 후 그 페이지에 그대로 있어야함
				getListAll(page);
			
			})
			
			$(document).on("click", "a.btn.btn-danger.btn-xs.btnReplyDelete", function(){ //댓글 삭제 
				var rno = $(this).attr("data-rno");
				$.ajax({
					url:"${pageContext.request.contextPath}/replies/"+rno,
					type:"delete",
					dataType:"text",
					success:function(res){
						console.log(res);
						if(res == "success"){
							getListAll(1);
						}
					}
				})
			})
			
			$("button#btnReplyAdd").click(function(){ //댓글 등록
			var replyer = $("#newReplyWriter").val();
			var replytext = $("#newReplyText").val();
			// @RequestBody post,put
			// @RequestBody	-서버에 오는 json string을 parsing하여 key와 맞는 변수에 값을 넣음
			//@RequestBody를 사용하면 서버로 보내는 값은 json string이여야 함
			//@RequestBody를 사용하는 서버에 값을 보낼때는 반드시 headers에 Content-Type(application/json)을 넣어줘야 함
			
			//var str = "{replyer:'글쓴이', replytext:'내용', bno:111}";
			var json = {bno:bno, replyer:replyer, replytext:replytext};
			var data = JSON.stringify(json);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/replies/",
				type: "post",
				headers:{
					"Content-Type":"application/json"
				},
				data:data,
				dataType:"text",
				success:function(res){
					console.log(res);
					if(res == "success"){
						$("#newReplyWriter").val("");
						$("#newReplyText").val("");
						getListAll(1);
						var vv = $("#cncncn").text(); //약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약약
						var kk = vv+1
						$("#cncncn").text(kk);
					}
				}
			})
		})
		
		
		
		$("#modifyModal").click(function(){ //댓글 수정
			var rno = $(this).parents(".timeline-item").find(".timeline-header strong").text();
			var replytext = $(this).parents(".timeline-item").find("timeline-body").text();
			
			$("div.modal-header").val(rno);
			$("input#replytext.form-control").val(replytext);
			
		})
		
		$(document).on("click", "button#btnReplySaveMod.btn.btn-info", function(){
			var rno = $(this).parents(".modal-content").find(".modal-body").text($(this).attr("data-rno"));
			var replytext = $(this).parents(".modal-content").find("modal-body").text();
			var json = {replytext:replytext};
			var data = JSON.stringify(json);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/replies/"+rno,
				type: "put",
				headers:{
					"Content-Type":"application/json"
				},
				data:data,
				dataType:"text",
				success:function(res){
					console.log(res);
					if(res == "success"){
						getListAll(currentPage);//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ수정 후 그 페이지에 그대로 있어야함
						$("#myModal").modal("hide");
					}
				}
			})
			
			
		} )
		
		
			
			
			
			
	</script>
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
		$("#f1").attr("action","removePage");
		$("#f1").attr("method","post");
		$("#f1").submit();
	})
	
	
	$("#list").click(function(){
		location.href="${pageContext.request.contextPath}/sboard/list?page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
		
	})
	
	
	$("#modify").click(function(){
		location.href="${pageContext.request.contextPath}/sboard/modify?bno=${board.bno}&page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
	})
	
</script>