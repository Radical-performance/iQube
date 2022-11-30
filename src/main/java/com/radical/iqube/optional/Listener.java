package com.radical.iqube.optional;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.event.*;
import com.radical.iqube.model.hibernate.*;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {
//
    private static final Logger log = Logger.getLogger(Listener.class);
    public static volatile HashMap<String, HttpSession> openedSessions = new HashMap<>();

    public MongoClient client;
    public EntityManagerFactory factory;


    @Resource(name = "jdbc/persistence")
    DataSource source;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
//        ctx.setAttribute("openedSessions",new ArrayList<HttpSession>());

        System.out.println("context создан");
        log.info(" context initialized");

//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(Song.class);
//        configuration.addAnnotatedClass(Album.class);
//        configuration.addAnnotatedClass(Artist.class);
//        configuration.configure("hibernate.cfg.xml");
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        log.info("service registry initialized");

//        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
 factory = Persistence.createEntityManagerFactory("Songs");
        log.info("entityManager has been initialized");
        System.out.println("sessionFactory initialized");

            client = MongoClients.create("mongodb://admin:root@localhost:27017/users_tracklists?authSource=admin");/*authMechanism=SCRAM-SHA-1&*/
        sce.getServletContext().setAttribute("MongoClient", client);
        sce.getServletContext().setAttribute("EntityManagerFactory",factory);

//        sce.getServletContext().setAttribute("SessionFactory", sessionFactory);

        log.info("entitymanagerfactory successfully added to servletContext as atribute");
        System.out.println("sessionFactory successfully added to servletContext as atribute");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        //== connection pool (wraper над нимс для большего функционала)ьный
//        SessionFactory sessionFactory = (SessionFactory) sce.getServletContext().getAttribute("SessionFactory");
//        if (sessionFactory != null && !sessionFactory.isClosed()) {
//            log.info("Closing sessionFactory");
//            sessionFactory.close();
//            log.info("Closing sessionFactory");
//
//        }
        if(factory != null && !factory.isOpen()){
            factory.close();
            System.out.println( "EntittyManagerFactory has been successfully closed");
        }

        log.info("Closing mongo client....");
        client.close();
        log.info("Mongo client  closed successfully....");
        System.out.println("context удалён");
        log.info("context удалён");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        openedSessions.put(se.getSession().getId(), se.getSession());
        System.out.println("HttpSession: " + se.getSession().getId() + " successfully created");
        System.out.println(openedSessions.size());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        openedSessions.remove(se.getSession().getId());
        System.out.println("HttpSession: " + se.getSession() + "  closed");
        System.out.println(openedSessions.size());

    }
}
