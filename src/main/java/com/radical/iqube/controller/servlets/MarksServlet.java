package com.radical.iqube.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MarksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletRequest request = req;

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    protected JSONObject parseObj(InputStream stream) {
        String inputStr;
        StringBuilder parsedContent;
        BufferedReader input;
        JSONObject parsedObject = null;
        parsedContent = new StringBuilder();
        try {
            input = new BufferedReader(new InputStreamReader(stream));
            while ((inputStr = input.readLine()) != null) {parsedContent.append(inputStr);}

            parsedObject = new ObjectMapper().readValue(new Gson().toJson(parsedContent), JSONObject.class);
            stream.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsedObject;
    }
}
