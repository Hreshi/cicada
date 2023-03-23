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
    let k = await res.text();
    console.log(k);
    return k;
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
function call(email) {
    let ringEndTime = get("/api/stego/request/"+email, 'get');
}
function reject() {
    get("/api/stego/request/reject", 'post')
}
function accept() {
    get("/api/stego/request/accept", 'post')
}
function endCall() {
    get("/api/stego/call/end", "post")
}
// Generate keys
let keyPair = null
async function generateKeys() {
    keyPair = await window.crypto.subtle.generateKey(
        {
            name: "RSA-OAEP",
            modulusLength: 2048,
            publicExponent: new Uint8Array([0x01, 0x00, 0x01]), // 65537
            hash: { name: "SHA-256" },
        },
        true,
        ["encrypt", "decrypt"]
    )
    return keyPair
}


// export to pem format
function ab2str(buf) {
    return String.fromCharCode.apply(null, new Uint8Array(buf));
}
function formatAsPem(str) {
    var finalString = '-----BEGIN PUBLIC KEY-----\n';

    while(str.length > 0) {
        finalString += str.substring(0, 64) + '\n';
        str = str.substring(64);
    }

    finalString = finalString + "-----END PUBLIC KEY-----";

    return finalString;
}
async function exportPublicKey(key) {
    let pubArrayBuffer = await window.crypto.subtle.exportKey(
        "spki",
        key
    );
    const exportedAsString = ab2str(pubArrayBuffer);
    const exportedAsBase64 = window.btoa(exportedAsString);
    return formatAsPem(exportedAsBase64);
}


// import from pem format to object
function str2ab(str) {
    const buf = new ArrayBuffer(str.length);
    const bufView = new Uint8Array(buf);
    for (let i = 0, strLen = str.length; i < strLen; i++) {
        bufView[i] = str.charCodeAt(i);
    }
    return buf;
}


function importPublicKey(pem) {
    // fetch the part of the PEM string between header and footer
    const pemHeader = "-----BEGIN PUBLIC KEY-----";
    const pemFooter = "-----END PUBLIC KEY-----";
    const pemContents = pem.substring(
        pemHeader.length,
        pem.length - pemFooter.length
    );
    // base64 decode the string to get the binary data
    const binaryDerString = window.atob(pemContents);
    // convert from a binary string to an ArrayBuffer
    const binaryDer = str2ab(binaryDerString);

    return window.crypto.subtle.importKey(
        "spki",
        binaryDer,
        {
            name: "RSA-OAEP",
            hash: "SHA-256",
        },
        true,
        ["encrypt"]
    );
}

// Encrypt text using public key object
async function encryptData(key, data64) {
    let dataArrayBuffer = str2ab(data64)
    let encryptedDataBuffer = await window.crypto.subtle.encrypt(
        {
            name: "RSA-OAEP"
        },
        key,
        dataArrayBuffer
    );
    return btoa(ab2str(encryptedDataBuffer))
}

// Decrypt cipher data into plaintext using private key
async function decryptData(key, cipherBase64) {
    let binData = atob(cipherBase64);
    let dataBuffer = str2ab(binData);

    let decryptedDataBuffer = await window.crypto.subtle.decrypt(
        {
            name:"RSA-OAEP"
        },
        key,
        dataBuffer
    )
    return ab2str(decryptedDataBuffer);
}

async function start(data64) {
    keyPair = await generateKeys();


    let publicPem = await exportPublicKey(keyPair.publicKey);
    console.log(publicPem)


    let publicObject = await importPublicKey(publicPem);

    let encryptedData = await encryptData(publicObject, data64);
    console.log(encryptedData)

    let decryptedData = await decryptData(keyPair.privateKey, encryptedData);
    console.log(decryptedData)
}