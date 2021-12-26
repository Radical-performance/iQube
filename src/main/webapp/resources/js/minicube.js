let cube = document.getElementById("cube");
let sides = document.querySelectorAll(".side");
let topp = document.getElementById("top");
let bottom = document.getElementById("bottom");
let left = document.getElementById("left");
let right = document.getElementById("right");
let front = document.getElementById("front");
let back = document.getElementById("back");



let login = document.getElementById("login");
let register = document.getElementById("registration");
let sec = document.getElementById("secondPane");

let audio1 = document.getElementById("aud1");
let audio2= document.getElementById("aud2");


let labelWrapper = document.getElementById("labelWrapper");
let radical = document.getElementById("radical");
let performance = document.getElementById("performance");

let bgPane = document.getElementById("bgPane");
let hovered = 0;
let hovered1 = 0;
let pressed;



   let frontE = function () {
       register.style.opacity = "0";
       document.querySelectorAll(".stars").forEach((e) => {
           setTimeout(function () {
               e.style.transition = "0.7s ease-out";
               e.style.background = "pink";
               e.style.boxShadow = "0px 0px 7px 1px #f60228,0px 0px 8px 0px crimson";
           }, 10);
       });
       if(hovered === 1){ }else if(hovered === 0){audio2.play();hovered = 1;}
       cube.animate(
           [{transform: window.getComputedStyle(cube).getPropertyValue('transform')}, {transform: 'rotateY(-1449deg) rotateX(-17deg) rotateZ(3deg)'}],
           {duration: 2500, easing: "ease-out", fill: 'forwards'}
       );
   };

front.addEventListener("mouseleave", function (){
    register.style.opacity = "1";
    hovered = 0;
});

  let  leftE =  function (e) {
      document.querySelectorAll(".stars").forEach((e) => {
          setTimeout(function () {
                    e.style.transition = "0.8s linear";
                    e.style.background = "cyan";
                    e.style.boxShadow = "0px 0px 5px 0px blue,0px 0px 6px 0px purple"}, 170);
      });
      login.style.opacity = "0";
          if (hovered1 === 0) {audio2.play();hovered1 = 1;}
          if (e.currentTarget.id === "left") {
              cube.animate(
                        [
                            {transform: window.getComputedStyle(cube).getPropertyValue("transform")},
                            {transform: 'rotateY(-1512deg) rotateX(-17deg) rotateZ(3deg)'}
                        ],
                  {duration: 2500, easing: "linear"});
          }
}

left.addEventListener("mouseleave", function () { login.style.opacity = "1"; hovered1 = 0});

let over = function (e) {
    setTimeout(function () {left.addEventListener("mouseover", leftE)}, 2500);
    setTimeout(function () {front.addEventListener("mouseover", frontE)}, 2500);

    if (e.currentTarget.id === "cube") {
        if (audio1.src.endsWith("intro.mp3")) {
            setTimeout(function () {audio1.src = "resources/content/sound/choose.mp3";}, 5500);
            audio1.play();
        }
        front.style.transform = "translateZ(61px)";
        back.style.transform = "translateZ(-61px)";
        topp.style.transform = "rotateX(90deg) translateZ(61px)";
        bottom.style.transform = "rotateX(-90deg) translateZ(61px)";
        left.style.transform = "rotateY(-270deg) translateZ(61px) rotateZ(0deg)";
        right.style.transform = "rotateY(90deg) translateZ(-61px)";

        radical.style.left = "0%";
        performance.style.left = "0%";
        bgPane.style.filter = "brightness(0.5) opacity(0.7)";
        radical.style.textShadow = "0px 0px 12px white, 5px 3px 2px black";

        cube.animate([{transform:window.getComputedStyle(cube).getPropertyValue("transform")}, {transform: 'rotateY(-1481deg) rotateX(-24deg) rotateZ(3deg)'}],{duration:2000,fill:'forwards'});
        sides.forEach((e) => {
            e.style.background = "rgba(0,0,0,0.76)";
            e.style.border = "2px solid #00ffd2";
            e.style.boxShadow = "cyan 0px 0px 2px 1px inset, cyan 0px 0px 1px 1px"; });
    }
}

    login.addEventListener("click", function () {
        sides.forEach((e)=>{e.style.opacity = "90%"})
        pressed = 1;
        audio1.src = "resources/content/sound/choose2.mp3";
        audio1.play();
        setTimeout(function (){labelWrapper.style.height = "0px"; labelWrapper.style.top = "50%"},2450);
        setTimeout(function () {
            labelWrapper.style.background = "white";
            labelWrapper.style.height = "110px";
            labelWrapper.style.width = "1px";
            labelWrapper.style.top = "45%";
            labelWrapper.style.transition = "0.9s linear";
            labelWrapper.style.boxShadow = "0px 0px 3px 0px white,0px 0px 3px 0px white";}, 1500);
        setTimeout(function () {
            labelWrapper.style.width = "1px";
            labelWrapper.style.left = "400px";
            labelWrapper.style.transition = "0.9s ease-out";
            labelWrapper.style.boxShadow = "0px 4px 5px -3px white,0px 4px 5px -3px white";
            labelWrapper.removeChild(radical);
            labelWrapper.removeChild(performance);}, 500);

        labelWrapper.style.overflow = "visible";
        radical.style.transition = "3s linear";
        radical.style.top = "100%";
        labelWrapper.style.top = "50%";
        labelWrapper.style.height = "1%";

        let loginLabel = document.createElement("label");
        let passwordLabel = document.createElement("label");
        let loginInput = document.createElement("input");
        let passwordInput = document.createElement("input");
        let loginInputBox = document.createElement("div");
        let passwordInputBox = document.createElement("div");

        loginInputBox.id = "loginInputBox";
        passwordInputBox.id = "passwordInputBox";

        loginInput.id = "loginInput";
        passwordInput.id = "passwordInput";

        loginLabel.id = "loginLabel";
        passwordLabel.id = "passwordLabel";

        loginLabel.innerText = "Login:";
        passwordLabel.innerText = "Password:";

        setTimeout(function () {
            labelWrapper.appendChild(loginInputBox);
            labelWrapper.appendChild(passwordInputBox);
            loginInputBox.appendChild(loginInput);
            passwordInputBox.appendChild(passwordInput);
            loginInput.insertAdjacentElement('beforebegin',loginLabel);
            passwordInput.insertAdjacentElement('beforebegin', passwordLabel);
            loginInput.maxLength = "15";
            passwordInput.maxLength = "15";
            passwordInput.type = "password";}, 3000);

        setTimeout(function () {
            loginInputBox.style.width = "300px";
            passwordInputBox.style.width = "450px";
            bgPane.style.filter = "brightness(0.5) opacity(0.7)";},3400);

        setTimeout(function(){
            loginInputBox.style.overflow = "visible";
            passwordInputBox.style.overflow = "visible"},6000)
    });

register.onclick = function () {
    let form = document.createElement('div');
    let regLogin = document.createElement('input');
    let regPwd = document.createElement('input');
    let regEmail = document.createElement('input');
    let regNickname = document.createElement('input');
    form.id = 'regForm';
    regLogin.id = 'regLogin';
    regPwd.id = 'regPwd';
    regEmail.id = 'regEmail';
    regNickname.id = 'regNickname';


    mainPane.appendChild(form);
    $('#regForm').append(
        $("<label for='regLogin' class='regLbl'>Login:</label>"),
        $('<input />', {id: 'regLogin', class: 'data', type: 'text'}),
        $("<label for='regPwd' class='regLbl'>Paswd:</label>"),
        $('<input />', {id: 'regPwd', class: 'data', type: 'text'}),
        $("<label for='regEmail' class='regLbl'>E-mail:</label>"),
        $('<input />', {id: 'regEmail',class: 'data', type: 'text'}),
        $("<label for='regNickname' class='regLbl' id='nickLbl'>Nickname:</label>"),
        $('<input />', {id: 'regNickname', class: 'data', type: 'text'}),
    )





}

    let leave = function leave(e) {

        sides.forEach((e) =>
        {
                e.style.background = "rgba(18,18,18,0.29)";
                e.style.border = "2px solid black";
                e.style.boxShadow = "gray 0px 0px 1px 1px inset, white 0px 0px 1px 1px";
        });
        if(pressed !==1){bgPane.style.filter = "";}else{bgPane.style.filter = "brightness(0.5) opacity(0.7)";}
        cube.animate(
                [{transform: window.getComputedStyle(cube).getPropertyValue("transform")},
                 {transform: 'rotateY(1481deg) rotateX(-24deg) rotateZ(3deg)'}],
                 {duration: 2000, iterations: Infinity});

        front.style.transform = "translateZ(60px)";
        topp.style.transform = "rotateX(90deg) translateZ(60px)";
        bottom.style.transform = "rotateX(-90deg) translateZ(60px)";
        left.style.transform = "rotateY(-270deg) translateZ(60px)  rotateZ(0deg)";
        right.style.transform = "rotateY(90deg) translateZ(-60px)";
        back.style.transform = "translateZ(-60px)";
    }
