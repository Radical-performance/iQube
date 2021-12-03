const innerPane1 = document.getElementById("innerPane1");
const innerPane2 = document.getElementById("innerPane2");
const innerPane1sub = document.getElementById("innerPane1sub");

const mainPane = document.getElementById("mainPane");

let rotateForce = 19;
let rotateX;
let rotateY;

window.addEventListener("mousemove", function (e){
    rotateY=(e.pageX/innerWidth*rotateForce*2)-rotateForce;
    rotateX=((e.pageY/innerHeight*rotateForce*2)-rotateForce);
    mainPane.style.transform =
        "rotateZ("+rotateX/12+"deg) rotateY("+rotateY/10 +"deg) rotateX("+rotateY/2+"deg)"+
        " translateX(-"+ e.pageX/10+"px) translateY(-"+e.pageY/10+"px) translateZ("+e.pageX/10+"px)";
    innerPane1.style.transform="translateX(-"+e.pageX/18+"px) translateZ("+e.pageX/8+"px)";
    innerPane1sub.style.filter="hue-rotate("+e.pageX/45 +"deg)";});
