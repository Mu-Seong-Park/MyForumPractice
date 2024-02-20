package hello.mylogin.websocket;

import hello.mylogin.config.SessionConst;
import hello.mylogin.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession httpSession = servletRequest.getServletRequest().getSession(false);
            if (httpSession != null) {
                Member loginMember = (Member) httpSession.getAttribute(SessionConst.LOGIN_MEMBER);
                if (loginMember != null) {
                    attributes.put("memberId", loginMember.getId()); // 세션에 저장된 회원 ID를 WebSocket 세션의 속성으로 설정
                }
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
