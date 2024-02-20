let socket = null;
$(document).ready(function (){
    connectWs();
});

function connectWs() {
    // 절대 경로로 작성해서 어느 html에서 호출해도 websocket이 연결될 수 있도록 해야 함.
    let sock = new SockJS("http://localhost:8080/alarmEvent");
    socket = sock;

    sock.onopen = function () {
        console.log('info: connection opened.');
        // 연결이 성공한 후에 onmessage 속성을 설정합니다.
        sock.onmessage = function(event) {
            const receivedMessage = event.data;
            console.log("서버로부터 메시지를 받았습니다: " + receivedMessage);
            // 여기에 서버로부터 받은 메시지를 원하는 대로 처리하는 코드를 추가할 수 있습니다.
        };
    };

};


// 연결이 닫힐 때 실행될 함수를 정의합니다.
socket.onclose = function() {
    console.log("WebSocket 연결이 닫혔습니다.");
};

// 에러가 발생했을 때 실행될 함수를 정의합니다.
socket.onerror = function(error) {
    console.error("WebSocket 에러 발생: ", error);
};

// 서버로 메시지를 보내는 함수를 정의합니다.
function sendMessageToServer(message) {
    if (socket.readyState === WebSocket.OPEN) {
        // 연결이 완료된 후에 메시지를 보냅니다.
        socket.send(message);
        console.log("서버로 메시지를 보냈습니다: " + message);
    } else {
        console.error("WebSocket 연결이 완료되지 않았습니다.");
    }
}

// 예시: 서버로 "Hello, Server!" 메시지를 보냅니다.
sendMessageToServer("Hello, Server!");
