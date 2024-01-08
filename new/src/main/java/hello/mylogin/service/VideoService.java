package hello.mylogin.service;

import hello.mylogin.event.FileUploadCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class VideoService {
    //controller 사이에 video 데이터를 넘겨주기 위한 service

    private byte[] videoData;
    private final ApplicationEventPublisher eventPublisher;
    private final RestTemplate restTemplate;

    public VideoService(ApplicationEventPublisher eventPublisher, RestTemplate restTemplate) {
        this.eventPublisher = eventPublisher;
        this.restTemplate = restTemplate;
    }

    // 파일 업로드 완료 후 호출되는 메서드
    public void handleFileUploadComplete() {
        // 파일 업로드 완료 이벤트 발행
        eventPublisher.publishEvent(new FileUploadCompleteEvent(this));
    }

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

    public void transferFile(byte[] fileData) {
        // 파일 전송 로직 수행
        transferFileToFlaskServer(fileData);
    }

    private void transferFileToFlaskServer(byte[] fileData) {
        // 파일 전송할 플라스크 서버의 URL
        String flaskServerUrl = "http://127.0.0.1:5000/upload";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 파일 데이터와 헤더를 포함한 HTTP 엔티티 생성
        ByteArrayResource resource = new ByteArrayResource(fileData);
        HttpEntity<ByteArrayResource> requestEntity = new HttpEntity<>(resource, headers);

        // 파일을 플라스크 서버로 전송
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskServerUrl, requestEntity, String.class);

        // 응답 코드 확인
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("Transfer Success!!!");
        } else {
            log.error("Transfer Failed. Status Code: {}", statusCode);
        }
    }

}