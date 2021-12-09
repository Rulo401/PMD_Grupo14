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

public class WebServices {

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

    public static List<Article> getArticles (int n){
        List<Article> articlesList = new LinkedList<>();
        try {
            //TODO desde el comienzo
            URL url = new URL(Constants.url+"/articles/"+n+"/0");
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

    //TODO obtener codigo respuesta
    public static boolean uploadArticle (Article article, LoginToken login){
        boolean result = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/article").openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization",login.getApitoken());
            conn.setRequestProperty("Content-type", "application/json");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Gson gson = new Gson();
            String art_json = gson.toJson(article);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(art_json);
            dos.flush();
            dos.close();
            //conn.connect();
            int a = conn.getResponseCode();
            //result = conn.getResponseCode()!=401;
            System.out.println(a);
            result = true;
        }catch (Exception e){e.printStackTrace();}
        return result;
    }

    //TODO obtener codigo respuesta
    public static boolean deleteArticle (Article article, LoginToken login){
        boolean result = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/article/"+article.getId()).openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization",login.getApitoken());
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //conn.connect();
            result = true;//conn.getResponseCode()==200;
        }catch (Exception e){e.printStackTrace();}
        return result;
    }

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
