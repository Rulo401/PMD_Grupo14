package upm.pmd.grupo14.tasks;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.List;
import java.util.Properties;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.util.WebServices;

/**
 * Thread which implements the login for the app
 */
public class LoginThread implements Runnable{

    private static String uri = Constants.url + "/login";

    private Activity act;
    private String username;
    private String password;
    private List<String> result;

    /**
     * Constructor
     * @param act Where is going to take place
     * @param username Username
     * @param password Password
     * @param result Results
     */
    public LoginThread(Activity act, String username, String password, List<String> result) {
        this.act = act;
        this.username = username;
        this.password = password;
        this.result = result;
    }

    /**
     * Executes the login method from web services and if the connection returns is authorized asigns the new api token
     */
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
