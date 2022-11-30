let authorize = function () {
    let credentials = {
        'login': $('#login').val(),
        'password': $('#password').val()
    }
    let div1 = null;
    let div2 = null;
    let div3 = null;

    $.post({
        url: 'http://be5a-217-107-127-44.ngrok.io/auth',
        data: JSON.stringify(credentials),
        contentType: 'application/json',
        dataType: 'html',
        success: function(){
            location.reload();
        }
    });
}
//
// let register = function (){
//     let userData = {
//         'login' : $('#newLogin'),
//         'password' : $('#newPassword'),
//         'email' : $('#newEmail'),
//         'nickname' : $('#newNickname')
//     }
//
//     $.post({
//         url: 'http://localhost:8080/iQube/home',
//         data: JSON.stringify(userData),
//         contentType: 'application/json',
//         success:
//             function(respomse){
//             //redirect to ...
//             }
//
//
//     })
// }
let login = document.getElementById("loginButton");
login.addEventListener("click", function(){authorize()});

function reg(){
    let data = {
        // 'login':$('#regLogin').val(),
        // 'password':$('#regPwd').val(),
        // 'email':$('#regEmail').val(),
        // 'nickname':$('#regNickname').val()
        'login':$('#loginreg').val(),
        'password':$('#passwordreg').val(),
        'email':$('#email').val(),
        'nickname':$('#nickname').val()
    }

    $.post({
        url: 'http://ae43-213-24-133-54.ngrok.io/registration',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'html',
        method: 'post',
        success:function (){
            location.reload();
        }
    })
}
let registration = document.getElementById("regButton");
registration.addEventListener("contextmenu",function(){reg()});
