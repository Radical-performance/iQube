package com.radical.iqube.controller.servlets;

import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.optional.Listener;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         UserDaoService service = new UserDaoService();

        HttpSession session = req.getSession(false);
        JSONObject credentials = (JSONObject) session.getAttribute("credentials");
        String login = credentials.getString("login");
        String pwd = credentials.getString("password");
        UserEntity user = service.getByLogin(login);
        System.out.println(user);

        System.out.println(req.getAttribute("credentials") + "  ses from auth servlet");
        try {
            if (user != null) {
                if (user.getPassword().equals(pwd)) {
                    Cookie usrLogin = new Cookie("userNickname",user.getNickname());
                    Cookie usrPwd = new Cookie("userPassword",user.getPassword());
                    Cookie jsessionId = new Cookie("JSESSIONID",session.getId());
                    resp.addCookie(jsessionId);
                    resp.addCookie(usrLogin);
                    resp.addCookie(usrPwd);
                    resp.setStatus(200);
                    resp.addHeader("Access-Control-Allow-Origin","*");
//                    resp.addHeader("Connection","Keep-Alive");
                    resp.addHeader("accepted","logged in");
                    resp.addHeader("Device", "xxxx");



                    System.out.println("redir");
                    System.out.println(session);
                    session.setAttribute("credentials",credentials);
                  resp.sendRedirect("https://50a0-81-177-127-253.ngrok.io/userPage");

                } else {
                    resp.getWriter().write("incorrect password");
                }


            } else {
                resp.getWriter().write("user is not exist");
            }

        } catch (Exception e) {/*-----log}*/}
    }
}
