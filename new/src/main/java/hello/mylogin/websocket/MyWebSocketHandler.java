package hello.mylogin.websocket;

import hello.mylogin.config.SessionConst;
import hello.mylogin.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, WebSocketSession> clients = new ConcurrentHashMap<Long, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //클라이언트가 서버와 연결
        String sessionId = session.getId();
        // 정확히는, session.getId()를 하면 websocket의 세션을 return하고, session.getAttribute를 통해서 Map을
        // 불러오고 거기서 회원을 찾는 방식으로 진행한다.

        if(sessionId!=null) {	// 로그인 값이 있는 경우만
            log.info("WebSocket session opened: {}",sessionId);
            Long memberId = (Long) session.getAttributes().get("memberId");
            log.info("login Member : {}",memberId);
            clients.put(memberId, session);   // 로그인중 개별유저 저장
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 연결이 닫혔을 때의 동작 정의
        String sessionId = session.getId();
        Long memberId = (Long) session.getAttributes().get("memberId");
        if(sessionId!=null) {	// 로그인 값이 있는 경우만
            log.info("WebSocket session opened: {}",sessionId);
            clients.remove(memberId, session);   // 로그인중 개별유저 삭제
        }
    }



    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        //message의 내용을 String으로 추출.
        String msg = message.getPayload();

        //특정 유저에게 msg 전송.
        if(msg != null) {
            log.info("message text : {}",msg);
            String temp = msg.substring(msg.indexOf("target : ") + "target : ".length());
            String target = temp.substring(0, temp.indexOf(" "));
            String result = msg.substring(msg.indexOf("videoResult : ") + "videoResult : ".length());

            WebSocketSession targetSession = clients.get(Long.parseLong(target));
            // 실시간 접속 시
            if(targetSession != null) {
                TextMessage tmpMsg = new TextMessage("videoResult : "+result);
                targetSession.sendMessage(tmpMsg);
            }
        }
    }

    public void sendMessageToClient(Long memberId, String message) {
        WebSocketSession session = clients.get(memberId);
        log.info("client : {} \n message : {}",memberId,message);
        if(session != null) {
            try{
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // HttpHandshakeInterceptor를 상속받는 custom interceptor를 구현해서, websocket session에서 데이터를 모두 얻을 수 있도록 수정함.
    // 웹소켓에 id 가져오기
    // 접속한 유저의 http 세션을 조회하여 id를 얻는 함수
//    private Long getMemberId(WebSocketSession session) {
//        Map<String, Object> httpSession = session.getAttributes();
//        Member loginMember = (Member) httpSession.get(SessionConst.LOGIN_MEMBER); // 세션에 저장된 m_id 기준 조회
//        return loginMember==null? null: loginMember.getId();
//    }

    //controller에서 호출하면 이 메세지를 통해서 특정 사용자에게 msg 전달.


}