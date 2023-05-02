<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: MSI
  Date: 2023-04-12
  Time: 오후 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
</head>
<body>
<h1>Chat Client</h1>


<form action="login" method="post" class="mt-5" style="width:300px; margin:auto">
    <c:if test="${empty member }">
    <div class="mb-3 mt-3">
        <label for="id" class="form-label hide">아이디</label>
        <input type="text" class="form-control" id="id" placeholder="아이디 입력" name="id">
    </div>
    <div class="mb-3">
        <label for="pwd" class="form-label hide">비밀번호</label>
        <input type="password" class="form-control" id="pwd" placeholder="비밀번호 입력" name="pw">
    </div>

    <button type="submit" class="btn btn-primary">로그인</button>
    <p class="mt-3">${msg}</p>
</form>
</c:if>

<c:if test="${not empty member }">
    <div style="margin:auto; width: 300px">
        <p>로그인!!!</p>
        <p>${member.name } | ${member.id }</p>
        <a href="logout">로그아웃</a>

</div>

<form name="frm">
    <textarea name="area" rows="10" readonly style="resize: none;"></textarea><br>
    <input name="message" type="text" autofocus>
    <button>전송</button>
</form>

<script>
    let ws = new WebSocket("ws://localhost/chat");

    ws.onopen = function (ev) {
        console.log("연결 완료", ev)
    }

    ws.onclose = function (ev) {
        console.log("연결 종료", ev)
    }

    ws.onmessage = function (ev) {
        console.log(ev.data)
        frm.area.value = frm.area.value + ev.data + "\n"
    }

    $(function () {
        $(document.frm).submit(function () {
            event.preventDefault();
            let msg = this.message.value;
            this.message.value = "";
            let obj = {id: 'id1', msg : msg}
            ws.send(JSON.stringify(obj))
            console.log(msg)
        })
    })
</script>
</c:if>
</body>
</html>
