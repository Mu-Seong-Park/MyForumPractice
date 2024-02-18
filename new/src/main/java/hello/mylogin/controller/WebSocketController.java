package hello.mylogin.controller;

import hello.mylogin.websocket.MyWebSocketHandler;
import hello.mylogin.websocket.RequestDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
public class WebSocketController {

    @Autowired
    private final MyWebSocketHandler myWebSocketHandler;

    public WebSocketController(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }
//
//    @PostMapping("/videoResult")
//    public String receiveData(@RequestBody RequestDataDto requestDataDto) {
//
//        String userToken = requestDataDto.getUserToken();
//        List<String> videoResult = requestDataDto.getVideoDataResult();
//
//        log.info("userToken : {} , videoResult : {}",userToken,videoResult);
//
//        return "redirect:/";
//    }

    //임시 데이터를 읽기 위한 임시 메서드
    @PostMapping("/videoResult")
    public String receiveData(@RequestBody RequestDataDto requestDataDto) {

        String userToken = requestDataDto.getAuth();
        String videoResult = requestDataDto.getResults();

        log.info("userToken : {} , videoResult : {}",userToken,videoResult);

        //카카오톡에 알림을 넣는다던가 그런 식으로 사용할 수 있을 것 같음.
        //혹은 websocket을 통해서 사용자에게 알림이 가도록 설정할 수 있어야 함.
        //redirect는 어차피 flask 서버로 응답하기 때문에 하던 말던 의미가 없음.
        return "redirect:/";
    }


}