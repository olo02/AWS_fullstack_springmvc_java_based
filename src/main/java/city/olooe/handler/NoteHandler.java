package city.olooe.handler;

import com.google.gson.Gson;
import city.olooe.domain.MemberVO;
import city.olooe.domain.NoteVO;
import city.olooe.service.NoteService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
@EnableWebSocket
public class NoteHandler extends TextWebSocketHandler  {
    // 접속자 관리 객체
    private List<WebSocketSession> sessions = new ArrayList<>();
    @Autowired
    private NoteService noteService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 접속 되었을 때 할 일
        log.warn("입장한 사람 : " + getIdBySession(session));
        sessions.add(session);
        log.warn("현재 접속자 수 :: " + sessions.size());
        log.warn("현재 접속자 정보");
        log.warn(sessions.stream().map(s -> getIdBySession(s)).collect(Collectors.toList()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception { // sesseion은 나!
        log.warn(noteService);
        String receiver = message.getPayload(); // js, ws.send( ), 수신자 정보가 필요
        String sender = getIdBySession(session);
        List<NoteVO> list = noteService.getReceiveUncheckedList(receiver); // list에 더 많은 정보를 보내면 됨\
        List<NoteVO> list2 = noteService.getRecieveList(receiver);
        List<NoteVO> list3 = noteService.getSendList(getIdBySession(session));

        Map<String, Object> map = new HashMap<>();
        map.put("sendList", list3);
        map.put("receiveList", list2);
        map.put("receiveUncheckedList", list);
        map.put("sender", sender);

        Gson gson = new Gson();

        for(WebSocketSession s : sessions) {
            if (receiver.equals(getIdBySession(s)) || s == session) {
                s.sendMessage(new TextMessage(gson.toJson(map)));
            };
        }
    }

    private String getIdBySession(WebSocketSession session) {
        Object obj = session.getAttributes().get("member");
        String id = null;
        if (obj != null && obj instanceof MemberVO) {
            id = ((MemberVO) obj).getUserid();
        }
        return id;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 접속 종료했을 때 할 일
        sessions.remove(session);
    }
}
