let authorize = function () {
    let credentials = {
        'login': $('#loginInput').val(),
        'password': $('#passwordInput').val()
    }
    let div1 = null;
    let div2 = null;
    let div3 = null;

    $.post({
        url: 'http://localhost:8080/iQube/home',
        data: JSON.stringify(credentials),
        contentType: 'application/json',
        success:
            function (response) {
                div1 = document.createElement("div");
                div1.className = "side_refresh_lbl";
                div1.id = "lbl_1";
                div1.appendTo("front");
                left.appendChild(div1)
                div2.className = "side_refresh_lbl";
                front.appendChild(div2);
                div3.className = "side_refresh_lbl";
                div2.id = "lbl_2";
                div3.id = "lbl_3";
                bottom.appendChild(div3);
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
        success:function (response){
            alert('xxx')
        }
    })
}

register.addEventListener("contextmenu",function(){reg()});
