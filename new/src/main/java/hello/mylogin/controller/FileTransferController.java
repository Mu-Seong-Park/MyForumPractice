package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
import hello.mylogin.event.FileUploadCompleteEvent;
import hello.mylogin.member.Member;
import hello.mylogin.service.MemberService;
import hello.mylogin.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api")
public class FileTransferController {
    private final VideoService videoService;

    public FileTransferController(VideoService videoService) {
        this.videoService = videoService;
    }

    @EventListener
    public void handleFileUploadComplete(FileUploadCompleteEvent event) {
        // 세션에 접근하기 위해 RequestContextHolder를 사용하여 현재 요청의 ServletRequestAttributes를 가져옴
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession();
        Member loginMember = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER));

        // 파일 전송 로직 수행
        videoService.transferFile(videoService.getVideoData(), loginMember.getId().toString());
    }


}
