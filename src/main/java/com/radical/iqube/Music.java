package com.radical.iqube;

import com.google.gson.Gson;
import com.radical.iqube.model.hibernate.ObjectMapper;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.Buffer;

public class Music extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException, NullPointerException {
        System.out.println(req.getRequestURL());
        System.out.println(req.getParameterNames().nextElement());
        if (req.getParameter("artist_tracklist") != null) {
            System.out.println("aga contain");
            String uri = req.getRequestURI();
            try {
                System.out.println(uri);
                URL url = new URL("https://api.deezer.com/artist/" + req.getParameter("artist_tracklist") + "/top?limit=50");
                System.out.println(url);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();


                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.connect();
                resp.addHeader("Content-Type", "application/json; charset=utf-8");
                resp.addHeader("Access-Control-Allow-Origin", "*");
                resp.addHeader("Cache-Control", "private, max-age=3600");
                resp.addHeader("Connection", "close");


                String inputLine;
                StringBuilder content = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((inputLine = in.readLine()) != null) {content.append(inputLine);}


//                System.out.println(content);
                connection.disconnect();
//            in.lines().forEach(System.out::println);

                in.close();

                PrintWriter writer = resp.getWriter();
                writer.print(content);
                writer.flush();
                writer.close();
            }catch (IOException e){e.printStackTrace();}
        } else {

            System.out.println(req.getParameter("name"));
            //тут надо проксировать через nginx, но пока тяжеловато идёт эта тема, нужно сменть подход
            //пока это будет прокси сервлет...
            try {
                System.out.println(req.getParameter("name"));
                URL url = new URL("https://api.deezer.com/search?q=" + req.getParameter("name"));
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.connect();

                resp.addHeader("Content-Type", "application/json");
                resp.addHeader("Access-Control-Allow-Origin", "*");
                resp.addHeader("Cache-Control", "private, max-age=3600");


                String inputLine;
                StringBuilder content = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                System.out.println(connection.getResponseCode());
                in.close();
                connection.disconnect();
//            in.lines().forEach(System.out::println);



                PrintWriter writer = resp.getWriter();
                writer.print(content);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("aga contain");
//        String uri = req.getRequestURI();
//try {
//    System.out.println(uri);
//    URL url = new URL("https://api.deezer.com/artist/" + req.getParameter("tracklist") + "/top?limit=25");
//    System.out.println(url);
//    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//
//
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestMethod("GET");
//    connection.setReadTimeout(5000);
//    connection.setConnectTimeout(5000);
//    connection.connect();
//    resp.addHeader("Content-Type", "application/json");
//    resp.addHeader("Access-Control-Allow-Origin", "*");
//    resp.addHeader("Cache-Control", "private, max-age=3600");
//
//
//    String inputLine;
//    StringBuilder content = new StringBuilder();
//    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//    while ((inputLine = in.readLine()) != null) {content.append(inputLine);}
//
//
//    System.out.println(content);
//    connection.disconnect();
////            in.lines().forEach(System.out::println);
//
//    in.close();
//
//    PrintWriter writer = resp.getWriter();
//    writer.print(content);
//    writer.flush();
//    writer.close();
//}catch (IOException e){
//    e.printStackTrace();
//}





    }
    // playPause.onclick = function () {
    //     audio.load();
    //     audio.play();
    //     var context = new AudioContext();
    //     var src = context.createMediaElementSource(audio);
    //     var analyser = context.createAnalyser();
    //
    //     var canvas = can1;
    //     canvas.width = window.innerWidth;
    //     canvas.height = window.innerHeight;
    //     var ctx = canvas.getContext("2d");
    //
    //     src.connect(analyser);
    //     analyser.connect(context.destination);
    //
    //     analyser.fftSize = 2048;
    //
    //     var bufferLength = analyser.frequencyBinCount;
    //     console.log(bufferLength);
    //
    //     var dataArray = new Uint8Array(bufferLength);
    //
    //     var WIDTH = canvas.width;
    //     var HEIGHT = canvas.height;
    //
    //     var barWidth = (WIDTH / bufferLength) / 2;
    //     var barHeight;
    //     var x = 0;
    //
    //     function renderFrame() {
    //         requestAnimationFrame(renderFrame);
    //
    //         x = 0;
    //
    //         analyser.getByteFrequencyData(dataArray);
    //
    //         // ctx.fillStyle = "#000";
    //         ctx.clearRect(0, 0, WIDTH, HEIGHT);
    //
    //         for (var i = 0; i < bufferLength; i++) {
    //             barHeight = dataArray[i+5]*2;
    //
    //
    //             ctx.fillStyle = "#f60288";
    //             ctx.fillRect(x * 2, HEIGHT - barHeight, barWidth*1.5, barHeight - 70);
    //
    //             x += barWidth + 1.3;
    //         }
    //     }
    //
    //     audio.play();
    //     renderFrame();
    // };






//<!-- 	<div id="content">
//        <input type="file" id="thefile">
//        <canvas id="c1"></canvas>
//        <audio id="audio" controls></audio>
//    </div> -->
//
//<!--script type="text/javascript">
//            window.onload = function() {
//
//        var file = document.getElementById("thefile");
//        var audio = document.getElementById("audio");
//
//
//
//        let xwidth;
// audio3.addEventListener("timeupdate", function(){
// var currentMinutes, currentSeconds, totalMinutes, totalSeconds, currentTime, totalTime;

//     currentMinutes = smartTime(Math.floor(audio3.currentTime/60));
//     totalMinutes =smartTime(Math.floor(audio3.duration /60));

//     currentSeconds = smartTime(Math.floor(audio3.currentTime % 60));
//     totalSeconds = smartTime(Math.floor(audio3.duration % 60));

//     currentTime = currentMinutes + ":" + currentSeconds;
//     totalTime = totalMinutes + ":" + totalSeconds;


//     timeBox.innerHTML = currentTime + "/" + totalTime;

//     function smartTime(time){  return time < 10 ? "0" + time.toString().trim() :  time  }

//      xwidth = ((audio3.currentTime / audio3.duration) * 100) + "%";
//     progress.style.transition = '0.3s ease';
//     progress.style.width = xwidth;

// });

// progressBar.addEventListener("click", function(event){
//     let clickedPosition = event.clientX - progressBar.offsetLeft;
//     progress.style.width = xwidth;
//     audio3.currentTime = clickedPosition / progressBar.offsetWidth * audio3.duration;
// });



// progressBar.addEventListener("mousedown",function () {
//     progress.style.transition = '0.1s ease';

//     progress.style.height = '1px';
// progress.style.boxShadow = 'white 0px 0px 2px 1px, white 0px 0px 0px 0px inset';
// });

// progressBar.addEventListener("mouseup", function () {
//     progress.style.transition = '1s ease';

//     setTimeout(function () {progress.style.transition = '0.3s ease'}, 800);

//     progress.style.height = '2px';

//     progress.style.boxShadow = 'rgb(255 255 255) 0px 0px 7px 1px, rgb(255 255 255) 0px 0px 17px 0px inset';

// });


//</script-->



    }
