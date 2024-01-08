package hello.mylogin.controller;

import hello.mylogin.event.FileUploadCompleteEvent;
import hello.mylogin.service.VideoService;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class FileTransferController {
    private final VideoService videoService;

    public FileTransferController(VideoService videoService) {
        this.videoService = videoService;
    }

    @EventListener
    public void handleFileUploadComplete(FileUploadCompleteEvent event) {
        // 파일 전송 로직 수행
            videoService.transferFile(videoService.getVideoData());
    }
}
