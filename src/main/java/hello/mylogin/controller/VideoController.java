package hello.mylogin.controller;

import hello.mylogin.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller//RESTful 컨트롤러
@RequestMapping("/videos")
@Slf4j
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/upload")
    public String uploadVideoForm() {
        return "/uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        videoService.setVideoData(file);
        model.addAttribute("message", "File uploaded successfully!");
        return "redirect:/";
    }
}
