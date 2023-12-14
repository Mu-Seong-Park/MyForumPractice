# MyForumPractice
Making a forum to get used to Spring Framework(and Thymeleaf etc).
CyberCesco 프로젝트와 연계중

# 목표
1. FLASK 서버로부터 비디오 검사 결과를 전송하면 WebSocket을 통해서 이벤트를 수신하고 사용자에게 팝업하는 기능.
2. 테스트 코드 작성하기.
3. JWT를 사용해서 로그인 update
4. 회원 정보 보기 / 회원 삭제 및 수정 / 게시판에서 다시 홈페이지로 가는 버튼.
5. html 정리해서 깔끔한 페이지로 수정하기 & 수정 삭제 후에 페이지 정렬이 잘 안되는 현상 해결하기.


# 추가 목표(우선 순위 순서가 아님)
1. SpringSecurity를 추가할 수 있도록 하는 것.
2. 추천이나 조회수에 대한 기능.
3. 추천/조회수/작성 날짜 등을 기준으로 정렬.
4. 검색 방법 (제목, 작성자 등) 다양화.
5. 위의 다양화가 이루어지면 심화적인 검색 알고리즘 생각해보기.
6. post,member 등의 객체를 DTO를 사용할 수 있도록 수정.
7. jpql이 사용된 곳을 querydsl으로 대체할 수 있도록 리팩토링 진행하기

# 현재 완료된 것
1. 메모리(MAP)에 회원과 포스트를 저장하고, 읽고 쓰는 것
2. 로그인/로그아웃(세션을 이용함)
3. 로그인 유효성 검사 필터
4. 포스트를 작성한 회원만이 포스트를 수정/삭제할 수 있는 것
5. 포스트가 삭제되면 페이지에서 보이지 않는 것
6. 포스트 검색기능(메모리에서 동작)
7. DB에 회원을 저장하고 관리하기.
8. DB에 포스트를 저장하고 관리하기.
9. 포스트 제목으로 검색할 수 있는 기능.
10. 페이지네이션 구현하기.
11. PostRepository에서 findAll() 메서드를 호출했을 때, sql쿼리를 수정해서 deleted 변수가 true인 게시글은 불러오지 않도록 하기.
12. 포스트 제목 검색한 결과 Paging
13. FLASK 서버로 비디오를 전송할 수 있는 시스템
14. 회원정보 수정 및 확인

