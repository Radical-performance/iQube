package com.radical.iqube.controller.servlets;

import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.model.entity.UserEntityBuilderImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private UserDaoService service = null;

    @Override
    public void init() throws ServletException {service = new UserDaoService();}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject object = (JSONObject) req.getAttribute("userData");
        UserEntity newUser = new UserEntityBuilderImpl()
                .setLogin(object.getString("login"))
                .setPassword(object.getString("password"))
                .setEmail(object.getString("email"))
                .setNickName(object.getString("nickname"))
                .build();

        if(service.createUser(newUser)){
            resp.setStatus(303);
            resp.sendRedirect(req.getServletContext().getContextPath()+"/userPage");
        }else{
            resp.addHeader("isRegistered", "false");
            resp.sendRedirect(req.getServletContext().getContextPath()+"/hom");
        }


//        if(!service.createUser(user).equals(false)){
//
//        }else{
//            resp.getWriter().write("user with choosed login already exist");
//        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/registration.html").forward(req,resp);
    }
}
