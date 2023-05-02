<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../includes/header.jsp"></jsp:include>

	<!-- Begin Page Content -->
	<div class="container-fluid">

		<!-- Page Heading -->
		<h1 class="h3 mb-2 pb-3 text-gray-800">글 작성하기</h1>

		<!-- DataTales Example -->
		<div class="card shadow mb-4">
			<div class="card-body">
				<form method="post">
					<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" placeholder="제목을 입력하세요" id="title" name="title">
					</div>
					<div class="form-group">
					<label for="comment">내용</label>
					<textarea rows="10" class="form-control" id="comment" name="content" style="min-height: 300px"></textarea>
					</div>
					<div class="form-group">
					<label for="writer">작성자</label>
					<input class="form-control" type="text" id="writer" name="writer" placeholder="작성자를 입력하세요">
					</div>

					<div class="form-group">
						<label for="file">file <br> <span class="btn btn-primary">파일첨부</span></label>
						<input type="file" class="form-control" id="file" name="file" multiple style="display: none">
						<div class="uploadResult">
							<ul class="list-group filenames m-3"></ul>
							<ul class="nav thumbs"></ul>
						</div>
					</div>

					<button id="save" type="submit" class="btn btn-primary">저장</button>
					<a href="list" class="btn btn-default float-right">목록으로</a>

					<input type="hidden" name="pageNum" value="1">
					<input type="hidden" name="amount" value="10">
<%--					<input type="hidden" name="amount" value="${page.cri.amount -> 얘가 안됨}">--%>
				</form>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->

<style>
	.ck-editor__editable[role='textbox'] {
		min-height: 300px;
	}
</style>
<script src="${pageContext.request.contextPath}/resources/vendor/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
<script>
	ClassicEditor.create($("#comment").get(0), {
		ckfinder : {
			uploadUrl : '/ckImage.json'
		}
	})
</script>
<script>
	$(function () {
		
		let csrfHeader = '${_csrf.headerName}';
		let csrfToken = '${_csrf.token}';
		
		
		$(".filenames").sortable({
			change : function (event) {
				console.log(this)
			}
		}).css({cursor : "move"})


		$("form #save").click(function () {
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

		$("#file").change(function () {
			event.preventDefault();
			let files = $("#file").get(0).files;
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
				beforeSend : function(xhr) {
					xhr.setRequestHeader(csrfHeader, csrfToken)
				},
				data : formData,
				method : "post",
				success : function (data) {
					console.log(data)
					$("form").get(0).reset();
					showUploadedFile(data)
				}
			})
		})
	})
</script>
<jsp:include page="../includes/footer.jsp"></jsp:include>