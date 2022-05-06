package com.radical.iqube.controller.servlets;

import com.google.gson.JsonArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AudioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonArray names = new JsonArray();
        names.add("1.mp3");
        names.add("2.mp3");
        names.add("bones-dirt.mp3");
        names.add("Maintain.mp3");

        String namesString = names.toString();
        PrintWriter writer = resp.getWriter();
        writer.println(namesString);
        writer.flush();
    }
}
