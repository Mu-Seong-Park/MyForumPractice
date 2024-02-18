// websocket.js
//전역변수 선언-모든 홈페이지에서 사용 할 수 있게 index에 저장
var socket = null;

$(document).ready(function (){
    connectWs();
});

function connectWs(){
    var sock = new SockJS("http://localhost:8080/alarmEvent");
    //sock = new SockJS('/replyEcho');
    socket = sock;

    sock.onopen = function() {
        console.log('info: connection opened.');
    };

    sock.onmessage = function(evt) {
        var data = evt.data;
        console.log("ReceivMessage : " + data + "\n");

        $.ajax({
            url : '/mentor/member/countAlarm',
            type : 'POST',
            dataType: 'text',
            success : function(data) {
                if(data == '0'){
                }else{
                    $('#alarmCountSpan').addClass('bell-badge-danger bell-badge')
                    $('#alarmCountSpan').text(data);
                }
            },
            error : function(err){
                alert('err');
            }
        });

        // 모달 알림
        var toastTop = app.toast.create({
            text: "알림 : " + data + "\n",
            position: 'top',
            closeButton: true,
        });
        toastTop.open();
    };

    sock.onclose = function() {
        console.log('connect close');
        /* setTimeout(function(){conntectWs();} , 1000); */
    };

    sock.onerror = function (err) {console.log('Errors : ' , err);};

}
//
// // 전역변수 설정
// var socket = null;
//
// $(document).ready(function(){
//     // 웹소켓 연결
//     sock = new SockJS("http://localhost:8080/alarmEvent");
//     socket = sock;
//
//     // 데이터를 전달 받았을때
//     sock.onmessage = onMessage; // toast 생성
//     // ...
// });
//
// // toast 생성 및 추가
// function onMessage(evt) {
//     var data = evt.data;
//     // toast
//     let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
//     toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
//     toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
//     toast += "<span aria-hidden='true'>&times;</span></button>";
//     toast += "</div> <div class='toast-body'>" + data + "</div></div>";
//     $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
//     $(".toast").toast({"animation": true, "autohide": false});
//     $('.toast').toast('show');
// }
