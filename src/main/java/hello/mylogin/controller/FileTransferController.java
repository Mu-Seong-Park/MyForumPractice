package hello.mylogin.controller;

import hello.mylogin.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FileTransferController {
    private final VideoService videoService;

    public FileTransferController(VideoService videoService) {
        this.videoService = videoService;
    }
}
