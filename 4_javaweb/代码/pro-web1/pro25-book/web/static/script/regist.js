function $(id) {
    return document.getElementById(id);
}

function checkInput() {

    /**
     * 用户名判断
     * @type {RegExp}
     */
    var unameReg = /[A-Z0-9a-z]{8,16}/;
    var uname = $("uname").value;
    var unameSpan = $("unameSpan");
    if(!unameReg.test(uname)) { // 如果用户名不是8，16位
        unameSpan.style.visibility = "visible";
        return false;
    } else {
        unameSpan.style.visibility = "hidden";
    }

    /**
     * 密码合法性判断
     */
    var pwdReg = /[A-Z0-9a-z]{8,16}/;
    var pwd = $("pwd").value;
    var pwdSpan = $("pwdSpan");
    if(!pwdReg.test(pwd)) {
        pwdSpan.style.visibility = "visible";
        return false;
    } else {
        pwdSpan.style.visibility = "hidden";
    }

    var pwd2 = $("pwd2").value;
    var pwd2Span = $("pwd2Span");
    if(pwd2 != pwd) {
        pwd2Span.style.visibility = "visible";
        return false;
    } else {
        pwd2Span.style.visibility = "hidden";
    }


}

// 1) 创建XMLHttpRequest
// 2) 调用open进行设置："GET" , URL , true
// 3) 绑定状态改变时执行的回调函数 - onreadystatechange
// 4) 发送请求 - send()
// 5) 编写回调函数，在回调函数中，我们只对XMLHttpRequest的readystate为4的时候感兴趣
var xmlHttpRequst;

function ckUname(uanme) {
    /**
     * Ajax异步请求四步骤
     * 1. 创建XMLHttpRequest
     * 2. 调用open进行设置，（请求参数、请求路径、是否进行异步请求）get、url、true
     * 3. 绑定状态改变时执行的回调函数，onreadystatechange
     * 4. 发送请求 send
     * 5. 编写回调函数
     */

    xmlHttpRequst = new XMLHttpRequest();
    xmlHttpRequst.open("GET", "user.do?operation=chUname&uname=" + uanme, true);
    xmlHttpRequst.onreadystatechange = ckUnameCB;
    xmlHttpRequst.send();
}

function ckUnameCB() {
    if(xmlHttpRequst.readyState == 4 && xmlHttpRequst.status == 200) {
        var xmlHttpResponse = xmlHttpRequst.responseText;
        if (xmlHttpResponse == "{'uname': 1}") {
            alert("已经注册");
        } else {
            alert("没有注册");
        }

    }
}