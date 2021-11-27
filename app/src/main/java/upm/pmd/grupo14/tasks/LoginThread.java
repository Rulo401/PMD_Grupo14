package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.util.WebServices;

public class LoginThread implements Runnable{

    private static final String CONNECTION_FILENAME = "login";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    private static String uri = Constants.url + "/login";

    private Activity act;
    private LoginToken login;
    private boolean remindMe;

    public LoginThread(Activity act, LoginToken login, boolean remindMe) {
        this.act = act;
        this.login = login;
        this.remindMe = remindMe;
    }
    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            Properties prop = gson.fromJson(WebServices.login(uri, login),Properties.class);

            if(prop.getProperty("status","0").equals("401")){
                Toast.makeText(act, "Credentials not valid",Toast.LENGTH_SHORT).show();
            }else{
                String token = String.format("%s apikey=%s",
                        prop.getProperty("Authorization"),prop.getProperty("apikey"));
                login.updateToken(token, prop.getProperty("expires"));
                if(remindMe){
                    SharedPreferences pref = act.getSharedPreferences(CONNECTION_FILENAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(USERNAME, login.getUsername());
                    editor.putString(PASSWORD, login.getPassword());
                    editor.commit();
                }
            }
        }catch (Exception e){}
    }
}
