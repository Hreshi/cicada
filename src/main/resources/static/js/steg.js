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
let keyPem = null;
let key64 = null;
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
    keyPem = finalString;
    return finalString;
}
async function exportPublicKey(key) {
    let pubArrayBuffer = await window.crypto.subtle.exportKey(
        "spki",
        key
    );
    const exportedAsString = ab2str(pubArrayBuffer);
    const exportedAsBase64 = window.btoa(exportedAsString);
    key64 = exportedAsBase64
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



// keyPem is public key NOT in pem format just base64
// key is not in pem format becuase it contains newline characters

const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
const HOST_SIZE = 10000;
async function cipher(data64, secret) {
    const lengthString = getLengthString(data64.length);
    let current_number = operation(secret);

    const result = new Array(HOST_SIZE);
    let index = 0;
    data64 = lengthString + data64;
    
    for (let i = 0; i < HOST_SIZE; i++) {
        result[i] = generateRandomBase64Char();
        if(index < data64.length && i == current_number) {
            result[i] = data64.charAt(index++);
            current_number += operation(current_number);
        }
    }
    return result.join('');
}

function getLengthString(len) {
    let lengthString = len+"";
    while(lengthString.length < 4) {
        lengthString = "0"+lengthString;
    }
    return lengthString;
}
function generateRandomBase64Char() {
  const randomIndex = Math.floor(Math.random() * characters.length);
  return characters.charAt(randomIndex);
}


// decipher data

async function decipher(host_data, secret) {
    let current_number = operation(secret);
    let len = "";
    for(let i = 0;i < 4;i++) {
        len += host_data.charAt(current_number);
        current_number += operation(current_number);
    }
    let dataLength = parseInt(len);
    let result = new Array()

    for(let i = 0;i < dataLength;i++) {
        result.push(host_data.charAt(current_number));
        current_number += operation(current_number);
    }
    return result.join('');
}

function operation(num) {
    num += (num && ((num<<1)||num));
    num %= 17;
    num++;
    return num;
}



async function start(data64, secret) {
    keyPair = await generateKeys();


    let publicPem = await exportPublicKey(keyPair.publicKey);

    let publicObject = await importPublicKey(publicPem);

    let encryptedData = await encryptData(publicObject, data64);

    let decryptedData = await decryptData(keyPair.privateKey, encryptedData);

    let hostData = await cipher(key64, secret);

    let decipherData = await decipher(hostData, secret);

    if(key64 == decipherData) {
        console.log("TRUE")
    } else {
        console.log("FALSE")
    }
}