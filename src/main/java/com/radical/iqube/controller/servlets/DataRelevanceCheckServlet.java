package com.radical.iqube.controller.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DataRelevanceCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("relevance");

        System.out.println(req.getParameter("tracks"));
        System.out.println(req.getParameter("playlists"));
        System.out.println(req.getParameter("artists"));
        System.out.println(req.getParameter("albums"));

        JSONArray list = new JSONArray();

        int[]mas = new int[10];

        JSONObject playLists = new JSONObject();

        playLists.put("playlists",list);
        playLists.put("tracklist",list);
        playLists.put("artists",list);

        resp.addHeader("Content-Type","application/json");
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Cache-Control","private, max-age=3600");
        PrintWriter writer = resp.getWriter();
        writer.print(playLists);
        writer.flush();
        writer.close();
    }

}
