package upm.pmd.grupo14.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.Image;
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
        List<Article> articlesList = new LinkedList<>();
        try {
            URLConnection conn =  new URL(Constants.url+"/articles/"+n+"/70").openConnection();
            Gson gson = new Gson();
            articlesList = Arrays.asList(gson.fromJson(Utils.readInputStream(conn.getInputStream()), Article[].class));
            for (Article art : articlesList){
                if (art.getThumbnail_data() != null && art.getThumbnail_media_type() != null){
                    art.setThubnail(new Image(ImageSerializer.base64StringToImg(art.getThumbnail_data()),art.getThumbnail_media_type()));
                    System.out.println();
                }
            }
        }catch (Exception e){}
        return articlesList;
    }

    public static Article getArticle (String id){
        Article art = null;
        try {
            URLConnection conn = new URL(Constants.url+"/article/"+id).openConnection();
            Gson gson = new Gson();
            art = gson.fromJson(Utils.readInputStream(conn.getInputStream()),Article.class);
            art.setImage(new Image(ImageSerializer.base64StringToImg(art.getImage_data()),art.getImage_media_type()));
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
