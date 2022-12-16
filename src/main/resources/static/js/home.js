let chatDiv = document.getElementById('chat-page')
let sendBtn = document.getElementById('send-btn')
let messageList = document.getElementById('message-list')
let contactList = document.getElementById('contact-list')
let messageInput = document.getElementById('message-input')
let currentUser = "hreshi"
let myself = "hreshi"

let userList = ["hreshi", "mspatild7", "aakanksha2812", "meera12kesh"]
let messageStore = []
messageStore["hreshi"] = []
messageStore["mspatild7"] = []
messageStore["aakanksha2812"] = []
messageStore["meera12kesh"] = []

async function fetchResponse() {
    let res = await fetch("/user/data");
    console.log(await res.text())
}

sendBtn.addEventListener('click', function (event) {
    event.preventDefault()
    let message = {
        sender:myself,
        recver:currentUser,
        content:messageInput.value
    }
    messageStore[currentUser.toLocaleLowerCase()].push(message)
    appendMessage(message)
    sendMessage(message.content)
    messageInput.value = ""
    fetchResponse()
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
    if(message.sender === myself) {
        messageDiv.className = "card text-wrap mt-3 bg-green text-secondary "    
    } else {
        messageDiv.className = "card text-wrap left-message bg-blue mt-3 text-secondary "
    }
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
    console.log(messageStore[sender.toLocaleLowerCase()])
    messageStore[sender.toLocaleLowerCase()].forEach(message => {
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
    messageStore[message.sender.toLocaleLowerCase()].push(message);
    if(message.sender.toLowerCase() === currentUser.toLocaleLowerCase()) {
        appendMessage(message)
        console.log("apple"+ message)
    }
}

let stompClient = null;

function connect() {
    let socket = new SockJS("/ws/connect");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("connected!");
        stompClient.subscribe("/messages/"+myself, function (data) {
            console.log("Message Received : ");
            let msg = JSON.parse(data.body)
            console.log(msg)
            let message = {
                sender:msg.sender.toLocaleLowerCase(),
                recver:msg.recver.toLocaleLowerCase(),
                content:msg.content
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

async function setMyself() {
    let res = await fetch("/user/data");
    let name = await JSON.parse(await res.text());

    myself = name.username.toLocaleLowerCase()
    console.log(myself)
    // for(let i = 0;i < userList.length;i++) {
    //     if(userList[i].toLowerCase() === myself.toLocaleLowerCase()) {
    //         userList.splice(i,1)
    //     }
    // }
    currentUser = userList[0]
    addUsersToContactList()
    connect()
}

function logout() {
    fetch("/logout")
}

setMyself()
