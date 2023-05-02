<%--
  Created by IntelliJ IDEA.
  User: MSI
  Date: 2023-04-11
  Time: 오전 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<jsp:include page="../includes/header.jsp"></jsp:include>

<form method="post" class="mt-5" style="width:300px; margin:auto" >
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

<div style="margin:auto; width: 300px">
    <c:if test="${not empty member }">
        <p>로그인!!!</p>
        <p>${member.name } | ${member.id }</p>
        <a href="logout">로그아웃</a>
        <hr>
        <h5>메시지 보내기</h5>
        <select size="1" id="receiverList" class="p-1 float-left" style="width:100px">
            <option>1</option>
            <option>1</option>
            <option>1</option>
        </select>
        <button id="btnSend" class="p-1 float-right" style="width:20%">전송</button>
        <input type="text" id="textMessage" class="p-1 mt-2" style="width:70%">
    </c:if>
</div>

<%--<h4>메세지들</h4>--%>
<%--<ul class="messages" style="padding: 0">--%>

<%--</ul>--%>

<p id="uncheckedNoteCount">미확인 쪽지의 개수</p>

<h4>${member.name}의 발송 목록</h4>
<ul class="sendList" style="padding: 0">

</ul>
<hr>
<h4>${member.name}의 수신(미확인) 목록</h4>
<ul class="receiveCheckedList" style="padding: 0">

</ul>
<hr>
<h4>${member.name}의 수신 목록</h4>
<ul class="receiveList" style="padding: 0">

</ul>


<script>let cp = '${pageContext.request.contextPath}'</script>
<script src="${pageContext.request.contextPath}/resources/js/note.js"></script>
<script>
    console.log(noteService)

    $(function() {
        let ws = new WebSocket("ws://localhost/note");
        let sender = '${member.id}';

        ws.onopen = function (ev) {
            console.log("연결 완료", ev)
        }

        ws.onclose = function (ev) {
            console.log("연결 종료", ev)
        }

        ws.onmessage = function (ev) {
            let obj = JSON.parse(ev.data)
            console.log(obj)

            if (obj.sender === sender) {
                $(".sendList").html(getStr(obj.sendList))
            } else {
                $("#uncheckedNoteCount").text(obj.receiveUncheckedList.length)
                $(".receiveCheckedList").html(getStr(obj.receiveUncheckedList));
                $(".receiveList").html(getStr(obj.receiveList));
            }
        }

        $.getJSON("getList").done(function(data){
            let str = "";
            for(let i in data) {
                str += `<option>\${data[i].id}</option>`;
            }
            $("#receiverList").html(str);
        })

        $("#btnSend").click(function() {
            event.preventDefault()

            let receiver = $("#receiverList").val();
            let message = $("#textMessage").val();

            let obj = {sender: sender, receiver: receiver, message:message};
            console.log(obj);
            if(!sender || !receiver) {
                alert("오류");
                return false;
            }

            noteService.send(obj, function(data) {
                ws.send(receiver);
            })
        })

        noteService.getReceiveList(sender, function (data) {
            $(".receiveList").html(getStr(data))
        })

        noteService.getSendList(sender, function (data) {
            $(".sendList").html(getStr(data))
        })

        noteService.getReceiveCheckedList(sender, function (data) {
            $("#uncheckedNoteCount").text(data.length)
            $(".receiveCheckedList").html(getStr(data))
        })

        $(".receiveList").on("click", 'li', function () {
            let noteno = $(this).data("noteno")
            noteService.receive(noteno, function (data) {
                noteService.getReceiveList(sender, function (data) {
                    $(".receiveList").html(getStr(data))
                })
            })
        })

        function getStr(data) {
            let str = ""

            for (let i in data) {
                str += `<li style="list-style: none" data-noteno="\${data[i].noteno}"> \${data[i].sender} : \${data[i].message} - \${data[i].rdate} </li>`
            }

            return str
        }
    })
</script>

<jsp:include page="../includes/footer.jsp" />
