package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
import hello.mylogin.member.Member;
import hello.mylogin.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@EnableWebSocket
@Slf4j
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // 클라이언트로부터의 메시지를 처리하는 메서드
    @MessageMapping("/videoCheck")
    public void handleMessageFromClient(@Payload String result, HttpServletRequest request) {
        // 비즈니스 로직을 수행하고 결과를 받음
        // 이 부분은 FLASK에서 어떻게 처리할지 좀 더 생각을 해보고 수정을 해야 할듯.
        String resultMessage = "Complete!!";
        log.info("Complete!!");
        // 클라이언트에게 결과를 전송
        sendResultToClient(getCurrentUserId(request), resultMessage);
    }

    // 결과를 클라이언트에게 전송하는 메서드
    private void sendResultToClient(String sessionId, String resultMessage) {
        eventService.notifyUser(sessionId, resultMessage);
    }

    //현재 유저의 ID를 세션을 통해 가져오는 메서드
    public String getCurrentUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 세션에서 현재 사용자의 ID를 가져옵니다.
        Member loginMember = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER));
        Long userId = loginMember.getId();

        // userId가 null이 아니라면 로그인한 사용자의 세션 ID를 반환합니다.
        return userId != null ? session.getId() : null;
    }
}
