package com.radical.iqube.optional;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.HashMap;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {
    private static final Logger log = Logger.getLogger(Listener.class);
    public static volatile HashMap <String,HttpSession> openedSessions = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("openedSessions",new ArrayList<HttpSession>());
        System.out.println("context создан");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("context удалён");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        openedSessions.put(se.getSession().getId(),se.getSession());
        System.out.println("HttpSession: "+ se.getSession().getId() + " successfully created");
        openedSessions.put(se.getSession().getId(), se.getSession());
        System.out.println(openedSessions.size());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        openedSessions.remove(se.getSession().getId());
        System.out.println("HttpSession: "+se.getSession() + "  closed");
    }
}
