package hello.mylogin.forum.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDto {

    private int endPage;
    private int startPage;
    private int realEnd;
    private int total;

    private boolean prev, next;

    private PageParam pageParam;

    public PageDto(int total, PageParam pageParam) {
        this.total = total;
        this.pageParam = pageParam;

        int current = pageParam.getPage();
        int amount = pageParam.getAmount();

        //페이징 끝 번호
        //ceil은 소숫점 자리에서 올림한다. (float형을 int로 만든다고 생각하면 됨)
        this.endPage = (int)(Math.ceil(current*0.1)) * 10;

        //페이징 시작 번호
        this.startPage = endPage - 9;

        this.realEnd = (int)(Math.ceil(total / amount));

        if(realEnd < endPage) {
            this.endPage = realEnd;
        }

        this.prev = current > 1;
        this.next = current < realEnd;
    }
}
