let stompClient = null;

function connect() {
    let socket = new SockJS("/ws/connect");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("connected!");
        stompClient.subscribe("/messages/hreshi", function (data) {
            console.log("Message Received : ");
            console.log(JSON.parse(data.body));
        })

        sendMessage("first message");
        sendMessage("second message");
    });
}
function sendMessage(message) {
    let data = {
        sender:"hreshi",
        recver:"hreshi",
        content:message
    };

    console.log("Sending Message");
    stompClient.send("/ms/send", {}, JSON.stringify(data));
}

connect();
