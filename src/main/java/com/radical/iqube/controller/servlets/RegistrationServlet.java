package com.radical.iqube.controller.servlets;

import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.model.entity.UserEntityBuilderImpl;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private UserDaoService service = null;

    @Override
    public void init() throws ServletException {
        service = new UserDaoService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject userJson = new JSONObject(req.getParameter("userData"));


        UserEntity user = new UserEntityBuilderImpl()
                .setLogin(userJson.getString("login"))
                .setPassword(userJson.getString("password"))
                .setEmail(userJson.getString("email"))
                .setNickName(userJson.getString("nickname"))
                .build();

//        if(!service.createUser(user).equals(false)){
//
//        }else{
//            resp.getWriter().write("user with choosed login already exist");
//        }


    }
}
