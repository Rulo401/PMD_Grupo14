package upm.pmd.grupo14.util;

import android.os.NetworkOnMainThreadException;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.Image;
import upm.pmd.grupo14.models.login.LoginToken;

/**
 * Implements the web services of the app with RESTful methods
 */
public class WebServices {

    /**
     * POST method to set the connection with the servers using certain credentials
     * @param uri Server url
     * @param username Username
     * @param password Password
     * @return Exit status
     */
    public static String login(String uri, String username, String password){
        String result = null;
        String format = "{\"username\":\"%s\",\"passwd\":\"%s\"}";
        String body = String.format(format, username, password);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(body);
            dos.flush();
            dos.close();
            InputStream in = conn.getInputStream();
            result = Utils.readInputStream(in);
        }catch (Exception e){}
        return result;
    }

    /**
     * Receives a list of n articles from the server given the index
     * @param number Number of articles to return
     * @param from Position where start searching
     * @return Articles received from the server
     */
    public static List<Article> getArticles (int number, int from){
        List<Article> articlesList = new LinkedList<>();
        try {
            URL url = new URL(Constants.url+"/articles/"+number+"/"+from);
            URLConnection conn = url.openConnection();
            Gson gson = new Gson();
            InputStream in = conn.getInputStream();
            articlesList = Arrays.asList(gson.fromJson(Utils.readInputStream(in), Article[].class));
            for (Article art : articlesList){
                if (art.getThumbnail_data() != null){
                    art.setThumbnail(new Image(ImageSerializer.base64StringToImg(art.getThumbnail_data()),art.getThumbnail_media_type()));
                }
            }
            in.close();
        }catch (Exception e){}
        return articlesList;
    }

    /**
     * Receives one article given the id of an article
     * @param id Id of the article requested
     * @return Article with id requested
     */
    public static Article getArticle (String id){
        Article art = null;
        try {
            URLConnection conn = new URL(Constants.url+"/article/"+id).openConnection();
            Gson gson = new Gson();
            InputStream in = conn.getInputStream();
            art = gson.fromJson(Utils.readInputStream(in),Article.class);
            if(art.getImage_data()!=null && art.getImage_media_type()!=null){
                art.setImage(new Image(ImageSerializer.base64StringToImg(art.getImage_data()),art.getImage_media_type()));
            }
            in.close();
        }
        catch (Exception e){}
        return art;
    }

    /**
     * POST method to upload an article to the server if the login is correct
     * @param article Article to upload
     * @param login Login credentials
     * @return Response code status
     */
    public static boolean uploadArticle (Article article, LoginToken login){
        boolean result = false;
        if(!login.isLogged()) return false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/article").openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization",login.getApitoken());
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Content-Language", "es-ES");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Gson gson = new Gson();
            String art_json = gson.toJson(article);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(art_json);
            dos.flush();
            dos.close();
            result = conn.getResponseCode()==200;
        }catch (Exception e){e.printStackTrace();}
        return result;
    }

    /**
     * DELETE method to delete an article from the server if authorised
     * @param article Article with id to delete
     * @param login Login credentials
     * @return Response code status
     */
    public static boolean deleteArticle (Article article, LoginToken login){
        boolean result = false;
        if(!login.isLogged()) return false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/article/"+article.getId()).openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization",login.getApitoken());
            result = conn.getResponseCode()==204;
        }catch (Exception e){e.printStackTrace();}
        return result;
    }

    /**
     * Method to obtain updates of the server from a certain date
     * @param date Search for updates after this date
     * @return Articles uploaded since date
     */
    public static List<Article> getUpdates (String date){
        List<Article> articlesList = new LinkedList<>();
        try {
            URL url = new URL(Constants.url+"/articlesFrom/"+date);
            URLConnection conn = url.openConnection();
            Gson gson = new Gson();
            InputStream in = conn.getInputStream();
            articlesList = Arrays.asList(gson.fromJson(Utils.readInputStream(in), Article[].class));
            for (Article art : articlesList){
                if (art.getThumbnail_data() != null){
                    art.setThumbnail(new Image(ImageSerializer.base64StringToImg(art.getThumbnail_data()),art.getThumbnail_media_type()));
                }
            }
            in.close();
        }catch (Exception e){}
        return articlesList;
    }
}
