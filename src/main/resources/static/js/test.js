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
}