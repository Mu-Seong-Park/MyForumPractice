package hello.mylogin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class VideoService {
    //controller 사이에 video 데이터를 넘겨주기 위한 service

    private byte[] videoData;

    public void setVideoData(MultipartFile file) {
        try {
            this.videoData = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 추가해 주세요.
        }
    }

    public byte[] getVideoData() {
        return this.videoData;
    }
}