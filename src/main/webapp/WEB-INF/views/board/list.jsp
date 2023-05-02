<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: ksha0
  Date: 2023-03-28
  Time: 오후 1:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp"%>

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <h1 class="h3 mb-2 text-gray-800">게시판</h1>

                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary float-left">목록</h6>
                        <a href="register${page.cri.queryString}" class="btn btn-primary float-right btn-sm">글쓰기</a>
                    </div>
                    <div class="card-body">
                        <%
                            String types[] = {"T", "C", "W", "TC", "TW", "CW", "TCW"};
                            String typesValues = "제목,내용,작성자,제목+내용,제목+작성자,내용+작성자,제목+내용+작성자";
                        %>
                        <c:set var="types" value="T,C,W,TC,TW,CW,TCW"></c:set>
                        <form class="mt-3 justify-content-center d-flex mb-3">
                            <div class="col-md-8 col-lg-6 input-group">
                                <select class="form-control col-4" name="type">
                                    <c:forTokens items="제목,내용,작성자,제목+내용,제목+작성자,내용+작성자,제목+내용+작성자" delims="," varStatus="stat" var="i">
                                        <option value="${types.split(",")[stat.index]}" ${types.split(",")[stat.index] eq page.cri.type ? 'selected' : ''}>${i}</option>
                                    </c:forTokens>
                                </select>
                                <input name="keyword" value="${page.cri.keyword}" type="text" class="form-control bg-light border-0 small" placeholder="Search"
                                       aria-label="Search" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn btn-primary">
                                        <i class="fas fa-search fa-sm"></i>
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="pageNum" value="1">
                            <input type="hidden" name="amount" value="${page.cri.amount}">
                        </form>

                        <div class="table-responsive">
                            <table class="table table-bordered" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성일</th>
                                    <th>수정일</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${list}" var="board">
                                        <tr>
                                            <td><c:out value="${board.bno}"/></td>
                                            <td><a href="get${page.cri.fullQueryString}&bno=${board.bno}"><c:out value="${board.title}"/></a><span class="text-muted">[${board.replycnt}]</span></td>
                                            <td><c:out value="${board.writer}"/></td>
                                            <td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd"/></td>
                                            <td><fmt:formatDate value="${board.updatedate}" pattern="yyyy-MM-dd"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

<%--                    <ul class="pagination mt-3 justify-content-center">--%>
<%--                        <c:if test="${page.doublePrev}">--%>
<%--                            <li class="page-item"><a class="page-link" href="list?pageNum=${page.startPage - 1}&${page.cri.queryString}"><i class="fas fa-angle-double-left"></i></a></li>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${page.prev}">--%>
<%--                            <li class="page-item"><a class="page-link" href="list?pageNum=${page.cri.pageNum - 1}&${page.cri.queryString}"><i class="fas fa-angle-left"></i></a></li>--%>
<%--                        </c:if>--%>
<%--                        <c:forEach begin="${page.startPage}" end="${page.endPage}" var="i">--%>
<%--                            <li class="page-item"><a class="page-link ${page.cri.pageNum == i ? 'active' : ''}" href="list?pageNum=${i}&${page.cri.queryString}">${i}</a></li>--%>
<%--                        </c:forEach>--%>
<%--                        <c:if test="${page.next}">--%>
<%--                            <li class="page-item"><a class="page-link" href="list?pageNum=${page.cri.pageNum + 1}&${page.cri.queryString}"><i class="fas fa-angle-right"></i></a></li>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${page.doubleNext}">--%>
<%--                            <li class="page-item"><a class="page-link" href="list?pageNum=${page.endPage + 1}&${page.cri.queryString}"><i class="fas fa-angle-double-right"></i></a></li>--%>
<%--                        </c:if>--%>
<%--                    </ul>--%>

                    <ul class="pagination mt-3 justify-content-center">
                        <c:if test="${page.doublePrev}">
                            <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.startPage-1}"><i class="fas fa-angle-double-left"></i></a></li>
                        </c:if>
                        <c:if test="${page.prev}">
                            <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.cri.pageNum-1}"><i class="fas fa-angle-left"></i></a></li>
                        </c:if>
                        <c:forEach begin="${page.startPage}" end = "${page.endPage}" var="i">
                            <li class="page-item ${page.cri.pageNum == i ? 'active' : '' }"><a class="page-link" href="list${page.cri.queryString}&pageNum=${i}">${i}</a></li>
                        </c:forEach>
                        <c:if test="${page.next}">
                            <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.cri.pageNum + 1}"><i class="fas fa-angle-right"></i></a></li>
                        </c:if>
                        <c:if test="${page.doubleNext}">
                            <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.endPage + 1}"><i class="fas fa-angle-double-right"></i></a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <!-- /.container-fluid -->

<script>
    let result = '<c:out value="${result}"/>'
    $(function () {
        if(!result || history.state) {
            return;
        }

        if (result !== 'success') {
            $("#myModal .modal-body").html("게시글 " + result + "번이 등록되었습니다")
        }

        $("#myModal").modal("show");
        history.replaceState({}, null, null)
    })
</script>
<%@ include file="../includes/footer.jsp"%>
