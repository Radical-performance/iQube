let authorize = function () {
    let credentials = {
        'login': $('#loginInput').val(),
        'password': $('#passwordInput').val()
    }
    let div1 = null;
    let div2 = null;
    let div3 = null;

    $.post({
        url: 'http://localhost:8080/iQube/auth',
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
login.addEventListener("contextmenu", function(){authorize()});

function reg(){
    let data = {
        'login':$('#regLogin').val(),
        'password':$('#regPwd').val(),
        'email':$('#regEmail').val(),
        'nickname':$('#regNickname').val()
    }

    $.post({
        url: 'http://localhost:8080/iQube/registration',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'html',
        success:function (){
            location.reload();
        }
    })
}
let registration = document.getElementById("registration");
registration.addEventListener("contextmenu",function(){reg()});
