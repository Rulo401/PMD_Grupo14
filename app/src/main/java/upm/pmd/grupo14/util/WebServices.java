package upm.pmd.grupo14.util;

import java.net.HttpURLConnection;
import java.net.URL;

import upm.pmd.grupo14.models.login.LoginToken;

public class WebServices {
    public static String login(String uri, LoginToken token){
        String result = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("username", token.getUsername());
            conn.setRequestProperty("passwd", token.getPassword());
            result = conn.getResponseMessage();
        }catch (Exception e){}
        return result;
    }
}
