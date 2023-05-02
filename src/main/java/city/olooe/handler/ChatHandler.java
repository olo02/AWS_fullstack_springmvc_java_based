package city.olooe.handler;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 소켓의 서버소켓
@Log4j
public class ChatHandler extends TextWebSocketHandler {
    // 접속자 관리 객체
    List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 접속 되었을 때 할 일
        sessions.add(session);
        log.warn("현재 접속자 수 :: " + sessions.size());
        log.warn("현재 접속자 정보");
        sessions.forEach(log::warn);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        Gson gson = new Gson();
        Map<?, ?> map = gson.fromJson(msg, Map.class);
        log.warn(map.get("id"));
        log.warn(map.get("msg"));
        log.warn(session.getAttributes().get("member"));
        for (WebSocketSession s : sessions) {
            s.sendMessage( new TextMessage(session.getId() + " :: " + msg));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 접속 종료했을 때 할 일
        sessions.remove(session);
    }
}

