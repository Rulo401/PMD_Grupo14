package upm.pmd.grupo14.util;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.login.LoginToken;

public class WebServices {

    public static String login(String uri, LoginToken token){
        String result = null;
        String format = "{\"username\":\"%s\",\"passwd\":\"%s\"}";
        String body = String.format(format,token.getUsername(), token.getPassword());
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
            result=conn.getResponseMessage();
        }catch (Exception e){}
        return result;
    }

    public static List<Article> getArticles (int n){
        Article[] articles = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/articles/"+n+"/0").openConnection();
            conn.setRequestMethod("GET");
            Gson gson = new Gson();
            articles = gson.fromJson(conn.getResponseMessage(), Article[].class);
        }catch (Exception e){}
        return Arrays.asList(articles);
    }

    public static Article getArticle (String id){
        Article art = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/articles/"+id).openConnection();
            conn.setRequestMethod("GET");
            Gson gson = new Gson();
            art = gson.fromJson(conn.getResponseMessage(),Article.class);
        }catch (Exception e){}
        return art;
    }

    public static void uploadArticle (Article article, LoginToken login){
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Constants.url+"/articles").openConnection();
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
        }catch (Exception e){}
    }

}
