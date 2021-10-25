let topp = document.getElementById("top");
let bottom = document.getElementById("bottom");
let left = document.getElementById("left");
let right = document.getElementById("right");
let front = document.getElementById("front");
let back = document.getElementById("back");

let cube = document.getElementById("cube");
let sides = document.querySelectorAll(".side");

window.onload = function(){



    for (let v = 0; v < 70; v++) {

      let curcube = document.createElement("div");
      let curcube2 = document.createElement("div");
      let curcube3 = document.createElement("div");
      let curcube4 = document.createElement("div");
      let curcube5 = document.createElement("div");
      let curcube6 = document.createElement("div");



        curcube.className = "curcube";
        curcube2.className = "curcube2";
        curcube3.className = "curcube3";
        curcube4.className = "curcube4";
        curcube5.className = "curcube5";
        curcube6.className = "curcube6";



        front.appendChild(curcube);
        topp.appendChild(curcube2);
        left.appendChild(curcube3);
        bottom.appendChild(curcube4);
        right.appendChild(curcube5);
        back.appendChild(curcube6);
    }
}
function over() {
    front.style.transform = "translateZ(63px)";
    topp.style.transform = "rotateX(90deg) translateZ(63px)";
    bottom.style.transform = "rotateX(-90deg) translateZ(63px)";
    left.style.transform = "rotateY(-270deg) translateZ(63px) rotateZ(0deg)";
    right.style.transform = "rotateY(90deg) translateZ(-63px)";
    back.style.transform = "translateZ(-63px)";

    cube.animate([
        {transform: cube.style.transform},
        {transform: 'rotateY(-1481deg) rotateX(-24deg) rotateZ(3deg)'}],
        {duration: 7000, iteration: Infinity});

    sides.forEach((e) => {
        setTimeout(function() {
            e.style.background = "black";
            e.style.border = "1px solid white";
            e.style.boxShadow = "cyan 0px 0px 1px 1px inset, cyan 0px 0px 1px 1px";}, 1500);});
}
    function leave() {
        cube.animate([
            {transform: cube.style.transform},
            {transform: 'rotateY(-808deg) rotateX(239deg)'},
            {transform: 'rotateY(-1481deg) rotateX(-24deg) rotateZ(3deg)'}],
        {duration: 25000, iterations: Infinity});

    front.style.transform = "translateZ(62px)";
    topp.style.transform = "rotateX(90deg) translateZ(62px)";
    bottom.style.transform = "rotateX(-90deg) translateZ(62px)";
    left.style.transform = "rotateY(-270deg) translateZ(62px)  rotateZ(0deg)";
    right.style.transform = "rotateY(90deg) translateZ(-62px)";
    back.style.transform = "translateZ(-62px)"

    sides.forEach((e) => {
        e.style.boxShadow = "cyan 0px 0px 1px 1px inset, 0px 0px 1px 1px cyan";
        e.style.border = "1px solid black";});
}

cube.addEventListener("mouseover", over);
cube.addEventListener("mouseleave", leave);
