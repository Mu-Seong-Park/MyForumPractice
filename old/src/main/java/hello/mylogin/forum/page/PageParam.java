package hello.mylogin.forum.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageParam {

    // 현재 페이지
    private int page;

    // 한 페이지 당 보여지는 포스트 갯수
    private int amount;

    //스킵할 게시물의 수
    private int skip;

    public PageParam() {
        this(1,10);
        this.skip = 0;
    }

    public PageParam(int page, int amount) {
        this.page = page;
        this.amount = amount;
        this.skip = (page - 1) * amount;
    }
}
