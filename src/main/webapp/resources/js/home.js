const innerPane2 = document.getElementById("innerPane2");
let rotateForce = 19;
let rotateX;
let rotateY;

window.addEventListener("mousemove", function (e){
    rotateY = (e.pageX / innerWidth * rotateForce * 2) - rotateForce;
    rotateX = ((e.pageY / innerHeight * rotateForce * 2) - rotateForce);
    innerPane2.style.transform =
        'rotateZ(' + rotateX / 2 + 'deg) rotateY(' + rotateX/10 + 'deg) rotateX(' + rotateY/2 + 'deg) ' +
        'translateX(-' + e.pageX / 15 + 'px) translateY(-' + e.pageY / 15 + 'px) translateZ(' + e.pageX/5 + 'px)';



})