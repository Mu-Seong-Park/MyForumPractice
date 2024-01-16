package hello.mylogin.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> clients = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // WebSocketSession 사용 예제
        String sessionId = session.getId();
        log.info("WebSocket session opened: {}",sessionId);
        clients.put(sessionId,session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 연결이 닫혔을 때의 동작 정의
        String username = session.getPrincipal().getName();
        log.info("WebSocket connection closed for user: {}",username);
        clients.remove(session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();  //메시지를 보낸 아이디
        clients.entrySet().forEach( arg->{
            if(!arg.getKey().equals(id)) {  //같은 아이디가 아니면 메시지를 전달합니다.
                try {
                    arg.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}