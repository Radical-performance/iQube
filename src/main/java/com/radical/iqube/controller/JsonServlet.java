package com.radical.iqube.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.radical.iqube.model.hibernate.Album;
import com.radical.iqube.model.hibernate.Artist;
import com.radical.iqube.model.hibernate.Song;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String body = req.getReader().lines().reduce()>?????????????????
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Cookie [] cookies = req.getCookies();


        Query query;
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("EntityManagerFactory");
        PrintWriter writer = resp.getWriter();

        resp.setHeader("Content-Type","application/json; charset=utf-8");
        resp.setHeader("Connection", "keep-alive");
        resp.setHeader("Cache-Control", "no-cache");

        if (req.getParameter("method").equals("add_artist")) {
            boolean res = addToArtist(cookies[0].getValue(),req.getParameter("val"));
        }else if(req.getParameter("method").equals("remove_artist")){
            boolean res = removeFromArtists(req.getParameter("val"),cookies[0].getValue());
        }else if(req.getParameter("method").equals("add_album")){
            boolean res = addToAlbums(cookies[0].getValue(),req.getParameter("val"));
        }else if(req.getParameter("method").equals("remove_album")){
            boolean res = removeFromAlbums(cookies[0].getValue(), req.getParameter("val"));
        }
        else if (req.getParameter("method").startsWith("a")) {
            String sub = req.getParameter("method").substring(1);
            System.out.println("ARTIST ALBUMS REQUEST         " + sub);

            query = factory.createEntityManager().createNamedQuery("Artist.findById");
            query.setParameter("id", Integer.parseInt(sub));
            Artist a = (Artist) query.getSingleResult();
            System.out.println("Artist.findById --->  результат ---->  " + ow.withDefaultPrettyPrinter().writeValueAsString(a));

            HashMap<String, Object> m = new HashMap<>();
            m.put("albums", a.getAlbums().toArray());
            String parsedMap = ow.writeValueAsString(m);

            resp.setHeader("Content-Type","application/json; charset=utf-8");
            resp.setHeader("Connection", "keep-alive");
            resp.setHeader("Cache-Control", "public,max-age=3600");

            if (parsedMap != null && parsedMap.length() != 0) {
                writer.print(parsedMap);
                writer.flush();
                writer.close();

            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("EntityManagerFactory");
        Controller controller = new Controller();
        Query query;
        URL url = null;
        JSONObject data = null;
        PrintWriter writer = null;
        Cookie [] cookies = req.getCookies();
///// 1
        data = controller.parse(req.getInputStream());
        System.out.println(data);

        if(req.getParameter("method").equals("add_to_tracklist")){
            try {
                //Parsed Request Body
                boolean isAdded = addTrackToTracklist(data,cookies[0].getValue());

                System.out.println("IS ADDED----------->  "+   isAdded);

                writer = resp.getWriter();
                url = new URL("https://api.deezer.com/artist/" + data.getInt("artist_id") + "/albums");
            } catch (IOException e){e.printStackTrace();}

            int [] identificators = new Controller().fetchAlbumsDataByArtistIdThenGetEachId(url);

            ArrayList<Song> songs;
            Album album;
            ArrayList<Album> albums = new ArrayList<>();

            if(data != null && !data.isEmpty()) {
                query = factory.createEntityManager().createNamedQuery("Artist.findById");
                query.setParameter("id", data.getInt("artist_id"));
                try {
                    Artist artist1 = (Artist) query.getSingleResult();
                    System.out.println("founded");
//                   artist1.getAlbums().forEach(album1 -> {
//                       System.out.println(album1.getSongs().size());
//                   });!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                    boolean isAdded = addTrackToTracklist(data,cookies[0].getValue());//////////////////////////////////////////////
                    System.out.println("IS ADDED----------->  "+   isAdded);
                } catch (NoResultException e) {  /// если getSingleResult == null
                    Artist artist = Artist.builder()
                            .id(data.getInt("artist_id"))
                            .name(data.getString("artist_name"))
                            .image_url(data.getString("artist_picture"))
                            .tracklist_url(data.getString("artist_tracklist"))
                            .albums_count(identificators.length)
                            .build();

                    for (int cur : identificators) {
                        try {url = new URL("https://api.deezer.com/album/" + cur);}
                        catch (MalformedURLException x) {x.printStackTrace();}

                        songs = new ArrayList<>();
                        JSONObject object = controller.fetchData(url);
                        album = Album.builder()
                                .id(object.getInt("id"))
                                .artist(artist)
                                .title(object.getString("title"))
                                .release_date(object.getString("release_date"))
                                .tracklist_url(object.getString("tracklist"))
                                .img_url(object.getString("cover_big")) //поменя, было медиум
                                .build();

                        JSONArray albumEachTrackData = ((JSONObject) object.get("tracks")).getJSONArray("data");
                        for (Object current : albumEachTrackData) {
                            JSONObject x = (JSONObject) current;
                            songs.add(Song.builder()
                                    .id(x.getInt("id"))
                                    .title(x.getString("title"))
                                    .url(x.getString("preview"))
                                    .duration(x.getInt("duration"))
                                    .album(album)
                                    .build());
                        }
                        album.setSongs(songs);
                        albums.add(album);
                    }
                    artist.setAlbums(albums);
                    HashMap<String, Object> map = new HashMap<>();
                    HashMap<String, Object> map2 = new HashMap<>();
                    map2.put("data", map);
                    map.put("albums", albums);
                    map.put("artist", artist);

                    resp.setHeader("Content-Type", "application/json; charset=utf-8");
                    resp.setHeader("Cache-Control","public,max-age=3600");
                    resp.addHeader("Connection","keep-alive");

                    String jsonString = null;
                    try {jsonString = ow.writeValueAsString(map2);}
                    catch (JsonProcessingException ex) {throw new RuntimeException(ex);}
                    finally {
                        if (writer != null) {
                            if (jsonString != null && jsonString.length() != 0) {
                                writer.print(jsonString);
                                writer.flush();
                            }
                            writer.close();
                        }
                    }



                    EntityManager manager = factory.createEntityManager();
                    manager.getTransaction().begin();
                    manager.persist(artist);
                    manager.flush();
                    manager.getTransaction().commit();
                    manager.close();
                }
            }
        }else if(req.getParameter("method").equals("remove_from_tracklist")){
            boolean wasRemoved = removeTrackFromTracklist(data.getInt("track_id"),cookies[0].getValue());
            System.out.println("was removed ---->   " +wasRemoved);
            writer = resp.getWriter();
            HashMap<String,String>result = new HashMap<>();
            result.put("deleting_result", String.valueOf(wasRemoved));
            writer.print(mapper.writeValueAsString(result));
            writer.flush();
            writer.close();
        }else if(req.getParameter("method").equals("create_playlist")){
            boolean wasCreated = controller.createPlaylist(data.getString("title"), Integer.parseInt(cookies[0].getValue()));
            HashMap<String,String>result = new HashMap<>();
            result.put("playlist_creating_result", String.valueOf(wasCreated));
            writer = resp.getWriter();
            writer.print(mapper.writeValueAsString(result));
            writer.flush();
            writer.close();
        }else if(req.getParameter("method").equals("add_to_playlist")){
            System.out.println( data.getInt("track_id") + data.getInt("number"));
            boolean wasAddedToPlaylist = addTrackToPlaylist(
                    data.getInt("track_id"),
                    data.getInt("number"),
                    cookies[0].getValue()
            );

            HashMap<String,String>result = new HashMap<>();
            result.put("result", String.valueOf(wasAddedToPlaylist));
            writer = resp.getWriter();
            writer.print(mapper.writeValueAsString(result));
            writer.flush();
            writer.close();
        }else if(req.getParameter("method").equals("remove_from_playlist")){
            boolean wasDeleted = removeTrackFromPlaylist(data.getInt("track_id"),data.getInt("playlist_number"),cookies[0].getValue());
        }
    }


    protected boolean uploadAudioTrack(){return true;};


    protected boolean addTrackToPlaylist(int track_id,int playlist_number,String user_id) {
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("users_playlists");
        MongoCollection<Document> collection = database.getCollection("playlists");

        Document track = new Document().append("track_id",track_id);
        Bson filter = Filters.eq("user_id",Integer.parseInt(user_id));
        Bson update = Updates.addToSet("playlists."+String.valueOf(playlist_number)+".tracks",track);


        UpdateResult result = collection.updateOne(filter, update);
        System.out.println(result + "      track  add to pl?");

        return result.getModifiedCount() >= 1;
    }
    protected boolean renamePlaylist(){return false;}
    protected boolean sharePlaylist(){return false;}



   protected boolean removeTrackFromPlaylist(int track_id,int playlist_number, String user_id){
        String playlist_numbe = String.valueOf(playlist_number);
       MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");

       MongoDatabase database = client.getDatabase("users_playlists");
       MongoCollection<Document> collection = database.getCollection("playlists");

       Document track = new Document().append("track_id",track_id);
       Bson filter = Filters.eq("user_id",Integer.parseInt(user_id));
       Bson update = Updates.pull("playlists."+playlist_numbe+".tracks",track);


       UpdateResult result = collection.updateOne(filter, update);
       System.out.println(result + "      track  removed from playlist?");

       return result.getModifiedCount() >= 1;
    }

    protected boolean removeTrackFromTracklist(int track_id,String user_id){
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("users_tracklists");
        MongoCollection<Document> collection = database.getCollection("tracklists");
        Document filter = new Document("user_id",user_id);
        Document update = new Document("$pull", new Document("tracks", new Document("track_id", track_id)));

        UpdateResult result=collection.updateOne(filter, update);
        System.out.println(result.getModifiedCount() +" removed?"  );
        return result.getModifiedCount() >= 0;
    }

    protected boolean addTrackToTracklist(JSONObject data, String user_id){
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("users_tracklists");
        MongoCollection<Document> collection = database.getCollection("tracklists");

        Document newTrack = new Document()
                .append("track_id",data.getInt("track_id"))
                .append("time_marks",data.get("timeMarks"))
                .append("uLike?", data.get("isLiked"));

        Bson filter = Filters.eq("user_id",user_id);
        Bson update = Updates.addToSet("tracks",newTrack);

        UpdateResult updateResult = collection.updateOne(filter,update);
        System.out.println(updateResult);
        System.out.println(updateResult.getModifiedCount());

        return updateResult.getModifiedCount() >= 1;
    }

    protected boolean addToAlbums(String user_id, String album_id){
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("added_albums");
        MongoCollection<Document> collection = database.getCollection("albums");

        Document album = new Document().append("id", album_id);

        Bson filter = Filters.eq("user_id",user_id);
        Bson update = Updates.addToSet("albums",album);

        UpdateResult updateResult = collection.updateOne(filter,update);

        return updateResult.getModifiedCount() >= 1;
    }

    protected boolean removeFromAlbums(String user_id, String album_id){
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("added_albums");
        MongoCollection<Document> collection = database.getCollection("albums");
        Document filter = new Document("user_id",user_id);
        Document update = new Document("$pull", new Document("albums", new Document("id", album_id)));

        UpdateResult result=collection.updateOne(filter, update);
        System.out.println(result.getModifiedCount() +"album removed?"  );
        return result.getModifiedCount() >= 0;
    }

    protected boolean addToArtist(String user_id,String artist_id){
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("users_artists");
        MongoCollection<Document> collection = database.getCollection("artists");

        Document artist = new Document().append("id", artist_id);

        Bson filter = Filters.eq("user_id",user_id);
        Bson update = Updates.addToSet("artists",artist);

        UpdateResult updateResult = collection.updateOne(filter,update);

        return updateResult.getModifiedCount() >= 1;
    }

    protected boolean removeFromArtists(String artist_id, String user_id){
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("users_artists");
        MongoCollection<Document> collection = database.getCollection("artists");
        Document filter = new Document("user_id",user_id);
        Document update = new Document("$pull", new Document("artists", new Document("id", artist_id)));

        UpdateResult result=collection.updateOne(filter, update);
        System.out.println(result.getModifiedCount() +"artist removed?"  );
        return result.getModifiedCount() >= 0;
    }



    private class Controller{

        protected boolean createPlaylist(String title,int user_id){
            MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
            MongoDatabase database = client.getDatabase("users_playlists");
            MongoCollection<Document> collection = database.getCollection("playlists");

            Document playlist = new Document().append("title",title).append("tracks",new ArrayList<>());
            Bson filter = Filters.eq("user_id",user_id);
            Bson update = Updates.addToSet("playlists",playlist);

            UpdateResult result = collection.updateOne(filter,update);
            System.out.println((result.getModifiedCount() >=1) + "   pl created?" );
            return result.getModifiedCount() >=1;

        }


        protected int[] fetchAlbumsDataByArtistIdThenGetEachId(URL url)  {
            JSONObject result = parseRespDataThenDisconnect(openHttpConnection(url));
            JSONArray array = result.getJSONArray("data");
            int [] identificators = new int[array.length()];

            for (int i = 0; i < array.length(); i++) {identificators[i] = (int) array.getJSONObject(i).get("id");}
            return identificators;
        }


        protected JSONObject parse(InputStream stream) {
            String inputStr;
            StringBuilder parsedContent;
            BufferedReader input;
            JSONObject parsedObject = null;
            parsedContent = new StringBuilder();
            try {
                input = new BufferedReader(new InputStreamReader(stream));
                while ((inputStr = input.readLine()) != null) {parsedContent.append(inputStr);}
                /*тут ещё подумать по поводу числа */
                parsedObject = new ObjectMapper().readValue(new Gson().toJson(parsedContent), JSONObject.class);
                stream.close();
                input.close();
            } catch (IOException e) {e.printStackTrace();}
            System.out.println(parsedContent);
            return parsedObject;
        }


        protected JSONObject parseRespDataThenDisconnect(HttpURLConnection connection)  {
            JSONObject o = null;
            try {o = parse(connection.getInputStream());}
            catch (IOException e){System.out.println("IOEXce");}
            finally {connection.disconnect();}

            return o;
        }

        protected HttpURLConnection openHttpConnection(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Connection","keep-alive");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                System.out.println(connection.getConnectTimeout());
                connection.connect();
            } catch (IOException e) {
                /*log*/
            }
            return connection;
        }

        protected JSONObject fetchData(URL url) {return  parseRespDataThenDisconnect(openHttpConnection(url));}
    }
}