package com.radical.iqube.controller.servlets;

import com.google.gson.Gson;
import com.mysql.cj.xdevapi.JsonParser;
import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.model.entity.UserEntityBuilder;
import com.radical.iqube.model.entity.UserEntityBuilderImpl;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private UserDaoService service = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        service = new UserDaoService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject userJson = new JSONObject(req.getParameter("credentials"));

        UserEntity user = new UserEntityBuilderImpl()
                .setLogin(userJson.getString("login"))
                .setPassword(userJson.getString("password"))
                .build();
        //in branch 'model' change method argument on UserEntity
//        service.getByLogin()

    }
}
