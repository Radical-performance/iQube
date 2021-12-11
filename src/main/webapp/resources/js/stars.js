window.onload = function (){
    cube.addEventListener("mouseover", over);
    setTimeout(function(){cube.addEventListener("mouseleave", leave);
    },3500);

    for(let i=0; i<100; i++){
        let star = document.createElement("div");
        star.className = "stars";

        star.style.left = Math.floor(Math.random() * innerWidth) + "px";
        star.style.top =Math.floor(Math.random() * innerHeight) + "px";
        star.style.transformStyle = "preserve-3d";

        if(i%2 === 0){
            star.style.zIndex = "123232";
            innerPane2.appendChild(star);
            star.animate(
                [{ transform: 'translateX(-' + Math.floor(Math.random() + innerWidth) + 'px) translateY(1000px)' },
                {transform: 'translateX(500px)  translateY(-1500px) translateZ(400px)'}],
                {duration: Math.floor(Math.random() * 92000) + 25200, iterations: Infinity });
        }else{
            star.style.width = "3px";
            star.style.height = "3px";
            star.style.zIndex = "121211";
            innerPane1sub.appendChild(star);
            star.animate(
                [{ transform: 'translateX(' + Math.floor(Math.random() * innerHeight) + 'px) translateZ(500px)'},
                { transform: 'translateX(-2000px) translateY(900px) translateZ(-1605px)' }],
                { duration: Math.floor(Math.random() * 182000) + 55600, iterations: Infinity });
        }
    }

    for (let v = 0; v < 45; v++) {
        let miniCube1 = document.createElement("div");
        let miniCube2 = document.createElement("div");
        let miniCube3 = document.createElement("div");
        let miniCube4 = document.createElement("div");
        let miniCube5 = document.createElement("div");
        let miniCube6 = document.createElement("div");

        miniCube1.className = "minicube1";
        miniCube2.className = "minicube2";
        miniCube3.className = "minicube3";
        miniCube4.className = "minicube4";
        miniCube5.className = "minicube5";
        miniCube6.className = "minicube6";

        front.appendChild(miniCube1);
        topp.appendChild(miniCube2);
        left.appendChild(miniCube3);
        bottom.appendChild(miniCube4);
        right.appendChild(miniCube5);
        back.appendChild(miniCube6);
    }

}
