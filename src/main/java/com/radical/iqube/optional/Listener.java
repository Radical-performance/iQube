package com.radical.iqube.optional;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {
    private static final Logger log = Logger.getLogger(Listener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("context создан");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("context удалён");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("HttpSession: "+ se.getSession().getId() + " successfully created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("HttpSession: "+se.getSession() + "  closed");
    }
}
