package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.util.WebServices;

public class LoginThread implements Runnable{

    private static String uri = Constants.url + "/login";

    private Activity act;
    private String username;
    private String password;
    private List<String> result;

    public LoginThread(Activity act, String username, String password, List<String> result) {
        this.act = act;
        this.username = username;
        this.password = password;
        this.result = result;
    }
    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            Properties prop = gson.fromJson(WebServices.login(uri, username, password),Properties.class);

            if(!prop.getProperty("status","0").equals("401")){
                String token = String.format("%s apikey=%s",
                        prop.getProperty("Authorization"),prop.getProperty("apikey"));
                result.add(token);
            }
        }catch (Exception e){}
    }
}
