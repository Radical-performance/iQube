package com.radical.iqube.controller.servlets;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.radical.iqube.model.hibernate.*;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

//web-xml description will be replaced with annotations on next steps
public class HomePageServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(HomePageServlet.class);
    public HomePageServlet() {}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(getServletContext().getAttributeNames().toString());
        System.out.println("home servlet");
       resp.setStatus(200);
       // resp.setHeader("Connection","close");
     //   resp.addHeader("Cache-Control","private; max-age=14000");//<---
//
//
//        SessionFactory factory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
//
//        Session session = factory.openSession();
////        session.beginTransaction();
//
//Artist artist = session.get(Artist.class,1665113);
//        System.out.println(artist.getId());
//        List<Album>albums =  artist.getAlbums();
//        System.out.println(albums.size());
//        System.out.println(albums.get(0).getSongs());
//
//        MongoClient client = (MongoClient) req.getServletContext().getAttribute("MongoClient");
//        MongoDatabase db = client.getDatabase("iqubemongodb");//read from .properties
//       db.createCollection("te2st32313211114112");
//



//        System.out.println(albums);
//        List<Song> songs =  albums.get(0).getSongs();
//        System.out.println(songs);
//        System.out.println(songs.get(1));

//        Artist artist =  Artist.builder()
//                .id(1665113)
//                .name("name")
//                .genre("genere")
//                .image_url("url")
//                .build();
//
//        Album album = Album.builder()
//                .id(111612565)
//                .title("title")
//                .release_date(new Date())
//                .artist(artist)
//                .build();
//
//        List<Album>albums = new ArrayList<>();
//
//        albums.add(album);
//        artist.setAlbums(albums);
//
//        session.save(artist);
//
//        session.getTransaction().commit();
//
//
//
//        session.clear();
//        session.close();
//

        resp.addIntHeader("user_id",12323123);
        Cookie cookie = new Cookie("user_id","12323123");
        resp.addCookie(cookie);
        req.getRequestDispatcher("/audio2.html").forward(req,resp);

    }
}
