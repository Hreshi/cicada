let sock = null;
let stmp = null;
let path = ''
let authHeader = ''
let myEmail = ''
async function get(url, meth) {

    let res = await fetch(url, {
        method: meth,
        headers:{
            'Authorization': authHeader,
        },
    });
    console.log(await res.text());
}
function setEmail(email) {
    myEmail = email
    document.getElementById("title").innerText = email
}
function setToken(st) {
    authHeader = 'Bearer ' + st;
    path = '/api/ws?token=' + st
}

function connect() {
    sock = new SockJS(path);
    stmp = StompJs.Stomp.over(sock);
    stmp.activate();
}

function send(user, message) {
    stmp.send('/ms/send/'+user,{}, message)
}

function subscribe() {
    stmp.subscribe('/messages/'+myEmail, function (message) {
        if(message.body) {
            console.log(message.body);
        } else {
            console.log(message)
        }
    })
}
async function login(email, pass) {
    let res = await fetch("/api/login", {
        method: 'post',
        body: JSON.stringify(
            {
                email: email,
                password: pass,
            }),

        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
    if(res.ok) {
        setToken(await res.text())
        setEmail(email)
        connect()
    } else {
        console.log("failed")
    }
}
function listConversation() {
    get("/api/conversation/my-conversation", "get")
}

function sendInvite(email) {
    get("/api/invite/send/"+email, "post")
}

function acceptInvite(email) {
    get("/api/invite/accept/"+email, "post")
}
function deleteInvite(email) {
    get("/api/invite/delete/"+email, "post")
}
function sentInvites() {
    get("/api/invite/sent/", "get")
}
function receivedInvites() {
    get("/api/invite/received/", "get")
}