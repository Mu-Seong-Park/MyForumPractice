package hello.mylogin.event;

import org.springframework.context.ApplicationEvent;

public class FileUploadCompleteEvent extends ApplicationEvent {
    //EventPublisher 객체가 VideoService를 빈 등록할 때 생성되면서 이벤트는 자동으로 등록이 된다고 한다. 자동 관리가 되므로 빈 등록을 안해도 된다.
    public FileUploadCompleteEvent(Object source) {
        super(source);
    }
}
