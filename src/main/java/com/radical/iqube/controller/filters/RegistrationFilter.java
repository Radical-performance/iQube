package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class RegistrationFilter implements Filter {
    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) {
        StringBuilder sb = new StringBuilder();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String line;
        JSONObject obj;
            try {
                if (request.getSession(false) == null && request.getCookies() == null) {
                    BufferedReader reader = req.getReader();
                    while ((line = reader.readLine()) != null) {sb.append(line);}
                    System.out.println(sb);

                    obj = new JSONObject(sb.toString());
                        if (
                                obj.getString("login").isEmpty() ||
                                        obj.getString("password").isEmpty() ||
                                        obj.getString("email").isEmpty() ||
                                        obj.getString("nickName").isEmpty()
                        ){
                            System.out.println("empty");
                            response.setStatus(200);
                        response.sendRedirect("/home");}
                        else {
                            System.out.println(obj.getString("login"));
                            System.out.println(obj.getString("password"));
                            System.out.println(obj.getString("email"));
                            System.out.println(obj.getString("nickname"));

                            HttpSession ses =request.getSession(true);
                            ses.setAttribute("userData", obj);
                            response.setHeader("Access-Control-Allow-Origin", "*");
                            response.setStatus(200);

                            request.getRequestDispatcher("/registration").forward(request, response);
                        }

                }else{
                    response.setStatus(303);
                    response.addHeader("Connection","Keep-Alive");
                    response.addHeader("Access-Control-Allow-Origin","*");
                    response.sendRedirect("/home");}

            } catch (IOException | ServletException e) {
                logger = Logger.getLogger(RegistrationFilter.class);
                logger.warn(new Date() + " : " + new Date().getTime() + ": " + e.getMessage());
            }
        }
    }


