package hello.mylogin.forum.page;

import org.hibernate.Criteria;

public class PageMakerDto {

    private int startPage;

    private int endPage;

    private boolean prev, next;

    private int total;

    //현재 페이지, 페이지 당 게시글 표시 수 정보
    private Criteria cri;

    public PageMakerDto(int total, Criteria cri) {
        this.total = total;
        this.cri = cri;

        this.endPage = (int)(Math.ceil(cri.g))
        this.startPage =
    }
}
