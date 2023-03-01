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