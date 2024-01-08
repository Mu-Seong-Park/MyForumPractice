package hello.mylogin.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 수신한 텍스트 메시지 처리
        String receivedMessage = message.getPayload();
        log.info("Received message: {}",receivedMessage);

        // 받은 메시지를 그대로 클라이언트에게 다시 전송
        session.sendMessage(new TextMessage("Echo: " + receivedMessage));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // WebSocketSession 사용 예제
        String sessionId = session.getId();
        log.info("WebSocket session opened: {}",sessionId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);

        // 연결이 닫혔을 때의 동작 정의
        String username = session.getPrincipal().getName();
        log.info("WebSocket connection closed for user: {}",username);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);

        // 전송 에러가 발생했을 때의 동작 정의
        String username = session.getPrincipal().getName();
        log.info("WebSocket transport error for user: {}" ,username);
        exception.printStackTrace();
    }

}