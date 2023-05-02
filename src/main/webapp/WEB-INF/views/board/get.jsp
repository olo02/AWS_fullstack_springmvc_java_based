<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<jsp:include page="../includes/header.jsp"></jsp:include>

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 pb-3 text-gray-800">Board Read Page</h1>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-body">
							<form method="post">
								<div class="form-group">
									<label for="bno">bno</label>
									<input type="text" class="form-control" id="bno" name="bno" value="${board.bno}" readonly>
								</div>
								<div class="form-group">
									<label for="title">제목</label>
									<input type="text" class="form-control" id="title" name="title" value="${board.title}"  readonly>
								</div>
								<div class="form-group">
									<label for="comment">내용</label>
									<textarea rows="10" class="form-control" id="comment" name="content"  readonly>${board.content}</textarea>
								</div>
								<div class="form-group">
									<label for="writer">작성자</label>
									<input class="form-control" type="text" id="writer" name="writer" value="${board.writer}" readonly>
								</div>

								<c:if test="${not empty board.attaches[0].uuid}">
									<div class="form-group">
										<label for="file">file <br> <span class="btn btn-primary">파일첨부</span></label>
										<input type="file" class="form-control" id="file" name="file" multiple style="display: none">
										<div class="uploadResult">
											<ul class="list-group filenames m-3">
												<c:forEach items="${board.attaches}" var="attach">
													<li class='list-group-item' data-uuid='${attach.uuid}' data-name='${attach.name}' data-path='${attach.path}' data-image='${attach.image}'>
														<a href='/download${attach.url}&thumb=off'>
															<i class='far fa-file'></i> ${attach.name}
														</a>
	<%--													<i class="far fa-times btn-remove float-right" data-file="${attach.url}&thumbs=off"></i>--%>
													</li>
												</c:forEach>
											</ul>
											<ul class="nav thumbs">
												<c:forEach items="${board.attaches}" var="attach">
													<c:if test="${attach.image}">
														<li class="nav-item m-2" data-uuid="${attach.uuid}">
															<a class="img-thumb" href="">
																<img class="img-thumbnail" src="/display${attach.url}&thumb=on"
																	 data-src="${attach.url}&thumb=off">
															</a>
														</li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
									</div>
								</c:if>

								<a href="modify${cri.fullQueryString}&bno=${board.bno}" class="btn btn-warning">수정</a>
								<a href="list${cri.fullQueryString}" class="btn btn-info">목록으로</a>
							</form>
                        </div>
                    </div>

					<div class="card">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary float-left">Reply</h6>
							<button class="btn btn-primary float-right btn-sm" id="btnReg">New Reply</button>
						</div>
						<div class="card shadow mb-4">
							<div class="card-body">
								<ul class="list-group chat">
								</ul>
								<button class="btn btn-primary btn-block my-4 col-8 mx-auto btn-more">더보기</button>
							</div>
						</div>
					</div>

					<div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
						 aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Modal</h5>
									<button class="close" type="button" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">×</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label for="reply">Reply</label>
										<input type="text" class="form-control" id="reply">
									</div>
									<div class="form-group">
										<label for="replyer">Replyer</label>
										<input type="text" class="form-control" id="replyer">
									</div>
									<div class="form-group">
										<label for="replydate">ReplyDate</label>
										<input type="text" class="form-control" id="replydate">
									</div>
								</div>
								<div class="modal-footer" id="modalFooter">
									<button class="btn btn-warning" type="button" data-dismiss="modal">Modify</button>
									<button class="btn btn-danger" type="button" data-dismiss="modal">Remove</button>
									<button class="btn btn-primary" type="button" data-dismiss="modal">Register</button>
									<button class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>
				</div>
                <!-- /.container-fluid -->
<script>
	let cp = '${pageContext.request.contextPath}'
</script>
<script src="${pageContext.request.contextPath}/resources/js/reply.js"></script>
<script>
	ClassicEditor.create($("#comment").get(0), {
		toolbar : []
	}).then(function (editor) {
		editor.enableReadOnlyMode('lock');
		editor.isReadOnly = true;
	})
</script>

<script>
	$(function () {
		
		let csrfHeader = '${_csrf.headerName}';
		let csrfToken = '${_csrf.token}';
		let bno = '${board.bno}'
		
		let replyer = '';
		<sec:authorize access="isAuthenticated()">
			replyer = '<sec:authentication property="principal.username"/>';
		</sec:authorize>
		
		$(document).ajaxSend(function(e, xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		})
		
		
		
		$("form button").click(function () {
			event.preventDefault();
			let str = ""

			$(".filenames li").each(function (i, obj) {
				console.log(i, obj, this)

				str += `
					<input type="hidden" name="attaches[\${i}].uuid" value="\${$(this).data('uuid')}">
					<input type="hidden" name="attaches[\${i}].name" value="\${$(this).data('name')}">
					<input type="hidden" name="attaches[\${i}].path" value="\${$(this).data('path')}">
					<input type="hidden" name="attaches[\${i}].image" value="\${$(this).data('image')}">
				`
			})
			console.log(str)
			$("form").append(str).submit();
		})

		function checkExtension(files) {
			const MAX_SIZE = 5 * 1024 * 1024;
			const EXCLUDE_EXT = new RegExp("(.*?)\.(js|jsp|asp|php)");

			for (let i in files) {
				if (files[i].size >= MAX_SIZE || EXCLUDE_EXT.test(files[i].name)) {
					return false;
				}
			}
			return true
		}

		function showUploadedFile(uploadResultArr) {
			let str = "";
			let imgStr = "";
			for(let i in uploadResultArr) {
				let obj = uploadResultArr[i];
				str += `<li class='list-group-item' data-uuid='\${obj.uuid}' data-name='\${obj.name}' data-path='\${obj.path}' data-image='\${obj.image}'><a href='/download\${obj.url}'><i class='far fa-file'></i> `
				str += obj.name + "</a> <i class='far fa-times-circle btn-remove' data-file='\${obj.url}' style='cursor:pointer'></i> </li>"
				if (obj.image) {
					obj.thumb = "off";
					imgStr += `<li class="nav-item m-2" data-uuid="' + \${obj.uuid} +'"><a class="img-thumb" href=""><img class="img-thumbnail" src="/display\${obj.url}&thumb=on" data-src="\${obj.url}" ></a></li>`;
				}
			}
			// 내부적으로 스트림 사용
			$(".uploadResult .filenames").append(str);
			$(".uploadResult .thumbs").append(imgStr);
		}

		$(".uploadResult ul").on("click", ".btn-remove", function () {
			let file = $(this).data("file");

			$.ajax({
				url : "/deleteFile" + file,
				success : function (data) {
					$('[data-uuid="' + data +'"]').remove()
				}
			})
		})

		$(".uploadResult ul").on("click" , ".img-thumb", function () {
			event.preventDefault()
			let param = $(this).closest("li").find("img").data("src")
			$("#showImageModal").find("img").attr("src", "/display" + param).end().modal("show")
		})

		$(":file").change(function () {
			event.preventDefault();
			let files = $(":file").get(0).files;
			console.log(files);

			if (!checkExtension(files)) {
				alert("조건 불일치")
				return false;
			}

			let formData = new FormData();

			for (let i in files) {
				formData.append("files", files[i])
			}

			$.ajax({
				url : "/uploadAjax",
				processData: false,
				contentType : false,
				data : formData,
				beforeSend : function(xhr) {
					xhr.setRequestHeader(csrfHeader, csrfToken)
				},
				method : "post",
				success : function (data) {
					console.log(data)
					$("form").get(0).reset();
					showUploadedFile(data)
				}
			})
		})

		moment.locale('ko')
		var str = ""

		replyService.getList({bno:${board.bno}}, function(result) {
			var str = "";
			for(var i in result) {
				console.log(result[i]);
				str += getReplyLiStr(result[i]);
			}
			$(".chat").html(str);
		});

		$("#btnReg").click(function (){
			$("#replyModal").find("input").val("").prop("readonly", false)
			$("#replydate").closest("div").hide()
			$("#modalFooter button").hide()
			$("#modalFooter button").eq(2).show()
			$("#modalFooter button").eq(3).show()
			$("#replyModal").modal("show")
		})

		// 댓글 작성
		$("#modalFooter button").eq(2).click(function () {
			var obj = {
				bno : ${board.bno},
				reply : $("#reply").val(),
				replyer : $("#replyer").val()
			}

			replyService.add(obj, function (result) {
				$("#replyModal").find("input").val("")
				$("#replyModal").modal("hide")
				console.log(result)

				replyService.get(result, function (data) {
					$(".chat").prepend(getReplyLiStr(data))
				})
			});
		})

		$(".chat").on("click", "li", function () {
			replyService.get($(this).data("rno"), function (result) {
				$("#reply").val(result.reply)
				$("#replyer").val(result.replyer)
				$("#replydate").val(moment(result.replyDate).format('llll')).prop("readonly", true).closest("div").show();
				
				$("#modalFooter button").show()
				$("#modalFooter button").eq(2).hide()
				if(replyer != result.replyer){
					$("#modalFooter button").eq(0).hide()					
					$("#modalFooter button").eq(1).hide()					
				}
				
				$("#replyModal").modal("show").data("rno", result.rno)
				console.log(result)
			})
		})

		// 삭제
		$("#modalFooter button").eq(1).click(function () {
			var obj = {rno = $("#replyModal").data("rno"), replyer:$("#replyer").val()}
			replyService.remove(obj, function (result) {
				$("#replyModal").modal("hide")
				console.log(result)
				$(".chat li").each(function () {
					if ($(this).data("rno") == obj.rno) {
						$(this).remove()
					}
				})
			})
		})

		//수정
		$("#modalFooter button").eq(0).click(function () {
			var rno = $("#replyModal").data("rno")
			var obj = {rno : rno, reply : $("#reply").val()}

			replyService.modify(obj, function (result) {
				$("#replyModal").modal("hide")
				console.log(result)
				$(".chat li").each(function () {
					if ($(this).data("rno") == obj.rno) {
						let $this = $(this)
						// $(this).replaceWith("")
						replyService.get($this.data("rno"), function (r) {
							$this.replaceWith(getReplyLiStr(r))
						})
					}
				})
			})
		})

		// 더보기 클릭 이벤트
		$(".btn-more").click(function () {
			replyService.getList({bno : ${board.bno}, rno : $(".chat li:last").data("rno")}, function (result) {
				console.log(result)
				if (!result.length) {
					$(".btn-more").prop("disabled", true);
					return
				}
				str = ""
				for (var i in result) {
					str += getReplyLiStr(result[i])
				}
				$(".chat").append(str)
			});
		})

		function getReplyLiStr(obj) {
			return `<li class="list-group-item" data-rno="\${obj.rno}">
				<div class="header">
					<strong class="primary-font">\${obj.replyer}</strong>
					<small class="float-right text-muted">\${moment(obj.replydate).fromNow()}</small>
				</div>
				<p>\${obj.reply}</p>
			</li>`;
		}


		// function getReplyLiStr(obj) {
		// 	// let result = "";
		// 	return `<li class="list-group-item" data-rno="\${obj.rno}">
		// 		<div class="header">
		// 			<strong class="text-primary">\${obj.replyer}</strong>
		// 			<small class="float-right text-muted">\${moment(obj.replyDate).startOf('day').fromNow()}</small>
		// 		</div>
		// 		<p>\${obj.reply}</p>
		// 	</li>`
		// }
	})
</script>
<jsp:include page="../includes/footer.jsp"></jsp:include>