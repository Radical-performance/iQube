package com.radical.iqube.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
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

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("tessssss");
        writer.flush();
        writer.close();

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
                    System.out.println(artist1);
//                    boolean isAdded = addTrackToTracklist(data,cookies[0].getValue());
//                    System.out.println("IS ADDED----------->  "+   isAdded);
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
                                .build();

                        JSONArray albumEachTrackData = ((JSONObject) object.get("tracks")).getJSONArray("data");
                        for (Object current : albumEachTrackData) {
                            JSONObject x = (JSONObject) current;
                            songs.add(Song.builder()
                                    .id(x.getInt("id"))
                                    .title(x.getString("title"))
                                    .url(x.getString("preview"))
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

                    resp.setContentType("application/json; charset=utf-8");
                    resp.setHeader("Content-Type", "application/json");

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
            System.out.println( data.getInt("track_id") +
                    data.getInt("number"));
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
        }
    }


    protected boolean uploadAudioTrack(){return true;};


    protected boolean addTrackToPlaylist(int track_id,int playlist_number,String user_id) {
        MongoClient client = (MongoClient) getServletContext().getAttribute("MongoClient");
        MongoDatabase database = client.getDatabase("users_playlists");
        MongoCollection<Document> collection = database.getCollection("playlists");
//        System.out.println("playlists."+ playlist_number +".tracks");
//        Document filter = new Document("user_id",user_id);
//        Document update = new Document("$push", new Document("playlists."+ playlist_number +".tracks", new Document("track_id", track_id)));
//
//        UpdateResult result = collection.updateOne(filter, update);
//        JSONObject object = new JSONObject().put("track_id",track_id);
        Document track = new Document().append("track_id",track_id);
        Bson filter = Filters.eq("user_id",Integer.parseInt(user_id));
        Bson update = Updates.addToSet("playlists."+playlist_number+".tracks",track);

        UpdateResult result = collection.updateOne(filter, update);
        System.out.println(result + "      track  add to pl?");

        return result.getModifiedCount() >= 1;
    }
    protected boolean removeTrackFromPlaylist(){return false;}
    protected boolean renamePlaylist(){return false;}
    protected boolean sharePlaylist(){return false;}



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