package hello.mylogin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@EnableWebSocketMessageBroker// SimpMessagingTemplate은 자동으로 빈 등록이 되도록 함.
//Spring WebSocket의 내부 동작에 대한 설정이 @EnableWebSocketMessageBroker 어노테이션을 통해 자동으로 이루어지므로
//해당 어노테이션을 사용하면 필요한 빈들이 자동으로 생성되어 주입되게 됩니다.
//이로써 간단한 설정만으로 WebSocket을 사용할 수 있게 됩니다.
public class EventService {
    private final SimpMessagingTemplate template;

    @Autowired
    public EventService(SimpMessagingTemplate template) {
        this.template = template;
    }

    private Map<String, String> userSessionMap = new ConcurrentHashMap<>();

    public void notifyUser(String username, String message) {
        // 특정 사용자에게 메시지를 전송하는 개인 큐 주소
        String userQueueDestination = "/user/" + getUserSessionId(username) + "/queue/notification";
        template.convertAndSend(userQueueDestination, message);
        log.info("사용자에게 메세지 전송!!");

    }

    // WebSocket 연결 이벤트 핸들러
    @EventListener
    private void handleWebSocketConnectListener(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Principal user = headers.getUser();
        log.info("WebSocket Connect Handler 호출");
        if (user != null) {
            // 연결된 사용자의 세션 ID를 맵에 저장
            userSessionMap.put(user.getName(), headers.getSessionId());
        }
    }


    // WebSocket 연결 해제 이벤트 핸들러
    @EventListener
    private void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Principal user = headers.getUser();

        if (user != null) {
            // 연결이 해제된 사용자의 세션 ID를 맵에서 제거
            userSessionMap.remove(user.getName());
        }
    }

    private String getUserSessionId(String username) {
        // 사용자 세션 ID를 맵에서 가져옴
        return userSessionMap.get(username);
    }
}
