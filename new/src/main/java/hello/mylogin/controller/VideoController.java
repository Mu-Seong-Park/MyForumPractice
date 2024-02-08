package hello.mylogin.controller;

import hello.mylogin.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller//RESTful 컨트롤러
@RequestMapping("/videos")
@Slf4j
//@CrossOrigin(origins = "http://localhost:8080") // 클라이언트 도메인
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/upload")
    public String uploadVideoForm() {
        return "video/uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestPart("file") MultipartFile file, Model model) {
        videoService.setVideoData(file);
        videoService.handleFileUploadComplete();
        model.addAttribute("message", "File uploaded successfully!");
        log.info("File uploaded successfully!");
        return "redirect:/members/info";
    }
}
