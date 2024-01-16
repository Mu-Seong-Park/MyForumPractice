package hello.mylogin.config;

import hello.mylogin.websocket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(),"/event")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Bean
    public MyWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }

//    @Bean
//    public DefaultHandshakeHandler handshakeHandler() {
//        return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/event").setAllowedOrigins("*")
//                .setHandshakeHandler(handshakeHandler())
//                .addInterceptors(new HandshakeInterceptor() {
//
//                    @Override
//                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//                        return request.getHeaders().getOrigin() != null;
//                    }
//
//                    @Override
//                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                               WebSocketHandler wsHandler, Exception exception) {
//
//                    }
//
//                }).withSockJS();
//    }


}
