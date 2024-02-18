package hello.mylogin.websocket;

import hello.mylogin.config.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> clients = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //클라이언트가 서버와 연결
        String sessionId = session.getId(); // 원래라면 websocket 세션을 id를 얻는데, HttpSessionHandshakeInterceptor를 사용해서
        //http 세션을 조회하여 id를 얻는다.
        if(sessionId!=null) {	// 로그인 값이 있는 경우만
            log.info("WebSocket session opened: {}",sessionId);
            clients.put(sessionId, session);   // 로그인중 개별유저 저장
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 연결이 닫혔을 때의 동작 정의
        String sessionId = session.getId();
        if(sessionId!=null) {	// 로그인 값이 있는 경우만
            log.info("WebSocket session opened: {}",sessionId);
            clients.remove(sessionId, session);   // 로그인중 개별유저 삭제

        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        //보낸 사람 ID
        String senderId = getMemberId(session);
        //특정 client에게 보내기
        String msg = message.getPayload();

        if(msg != null) {
            String[] strs = msg.split(",");
            log.info("message text : {}",strs.toString());
            if(strs != null && strs.length == 4) {
                String type = strs[0];
                String target = strs[1];
                String content = strs[2];
                String url = strs[3];
                WebSocketSession targetSession = clients.get(target);

                // 실시간 접속 시
                if(targetSession != null) {
                    TextMessage tmpMsg = new TextMessage("<a target='_blank' href='"+ url +"'>[<b>" + type + "</b>] " + content + "</a>" );
                    targetSession.sendMessage(tmpMsg);
                }
            }
        }
    }

    // 웹소켓에 id 가져오기
    // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
    private String getMemberId(WebSocketSession session) {
        Map<String, Object> httpSession = session.getAttributes();
        String loginMember = (String) httpSession.get(SessionConst.LOGIN_MEMBER); // 세션에 저장된 m_id 기준 조회
        return loginMember==null? null: loginMember;
    }

}