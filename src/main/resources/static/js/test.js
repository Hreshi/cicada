let sock = null;
let stmp = null;
let path = ''
let authHeader = ''
async function get(url, meth) {

    let res = await fetch(url, {
        method: meth,
        headers:{
            'Authorization': authHeader,
        },
    });
    console.log(await res.text());
}

function setToken(st) {
    authHeader = 'Bearer ' + st;
    path = '/ws?token=' + st
}

function connect() {
    sock = new SockJS(path);
    stmp = StompJs.Stomp.over(sock);
    stmp.activate();
}

function send(user, message) {
    stmp.send('/ms/send/'+user,{}, message)
}