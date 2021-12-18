package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
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
        String line = null;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject object = new JSONObject(sb.toString());
            JSONArray array = new JSONArray();
            array.put(object);

            for(int i=0; i< array.length(); i++){
                if (array.get(i) == null || array.get(i).toString().isEmpty()) {
                    response.sendRedirect(req.getServletContext().getContextPath() + "/home");
                } else {
                    request.setAttribute("userData", object);
                    request.getRequestDispatcher("/registration").forward(request,response);
                }
            }
        } catch (IOException | ServletException e) {
            logger = Logger.getLogger(RegistrationFilter.class);
            logger.warn(new Date() + " : "+ new Date().getTime() + ": "+ e.getMessage());
        }
    }
}

