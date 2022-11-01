let chatDiv = document.getElementById('chat-page')
let sendBtn = document.getElementById('send-btn')
let messageList = document.getElementById('message-list')
let contactList = document.getElementById('contact-list')
let messageInput = document.getElementById('message-input')
let currentUser = "hreshi"
let myself = "hreshi"

let userList = ["hreshi", "Mspatild7", "Aakanksha", "Meera12kesh"]
let messageStore = []
messageStore["hreshi"] = []
messageStore["Mspatild7"] = []
messageStore["Aakanksha"] = []
messageStore["Meera12kesh"] = []

sendBtn.addEventListener('click', function (event) {
    event.preventDefault()
    let message = {
        sender:myself,
        receiver:currentUser,
        content:messageInput.value
    }
    messageStore[currentUser].push(message)
    appendMessage(message)
    sendMessage(message.content)
    messageInput.value = ""
})

function makeMessage(message) {
    let messageDiv = document.createElement('div')
    let nameP = document.createElement('span')
    nameP.innerText = message.sender 
    nameP.className = " username"
    let messageP = document.createElement('span')
    messageP.innerText = message.content
    messageP.className = " ms-1"
    messageDiv.append(nameP)
    messageDiv.append(messageP)
    messageDiv.className = "card text-wrap left-message mt-3 text-secondary "
    return messageDiv
}

function makeContact(name) {
    let contactDiv = document.createElement('div')
    let nameP = document.createElement('span')
    nameP.innerText = name
    contactDiv.setAttribute("id", name)
    nameP.className = " ms-1 btn"
    contactDiv.append(nameP)
    contactDiv.className = " card text-wrap left-message mt-3 text-secondary "
    return contactDiv
}
function appendMessage(message) {
    messageList.append(makeMessage(message))
    messageList.scrollTop = messageList.scrollHeight
}

function handleContactClickEvent(element) {
    console.log(element.getAttribute("id"))
    messageInput.value = ""
    while(messageList.firstChild) {
        messageList.removeChild(messageList.firstChild)
    }
    let sender = element.getAttribute("id")
    console.log(messageStore[sender])
    messageStore[sender].forEach(message => {
        appendMessage(message)
    })
}

function addUsersToContactList() {
    userList.forEach(name => {
        let ele = makeContact(name);
        ele.addEventListener('click', function (event) {
            currentUser = ele.getAttribute("id")
            handleContactClickEvent(ele)
        })
        contactList.append(ele)
    })
}

function handleIncomingMessage(message) {
    messageStore[message.sender].push(message);
    if(message.sender == currentUser) {
        appendMessage(message)
    }
}

let stompClient = null;

function connect() {
    let socket = new SockJS("/ws/connect");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("connected!");
        stompClient.subscribe("/messages/hreshi", function (data) {
            console.log("Message Received : ");
            let msg = JSON.parse(data.body)
            console.log(msg)
            let message = {
                sender:msg.sender,
                receiver:msg.recver,
                content:msg.connect
            }
            handleIncomingMessage(message)
        })

    });
}
function sendMessage(message) {
    let data = {
        sender:myself,
        recver:currentUser,
        content:message
    };

    console.log("Sending Message");
    stompClient.send("/ms/send", {}, JSON.stringify(data));
}

function setMyself() {
    fetch("/user/data", {method:"get", credentials: 'include'})
    .then(response => {
        response
    })
    .then(text => {
        console.log(text)
        myself = text
    })
    .catch(err => {
        console.log(err)
    })
}


addUsersToContactList()
setMyself()
