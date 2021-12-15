package upm.pmd.grupo14.models.login;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import upm.pmd.grupo14.tasks.LoginThread;

/**
 * Models a token composed by the attributes needed for user authentication.
 */
public class LoginToken {

    private String username;
    private String apitoken;

    /**
     * Constructor of a LoginToken.
     * @param username Username
     * @param apitoken User´s apitoken
     */
    public LoginToken(String username, String apitoken){
        this.username = username;
        this.apitoken = apitoken;
    }

    /**
     * Username getter.
     * @return Username
     */
    public synchronized String getUsername() {
        return username;
    }

    /**
     * Method that checks the user´s log status.
     * @return If the user is logged
     */
    public synchronized boolean isLogged(){
        return username != null && apitoken != null;
    }

    /**
     * Apitoken getter.
     * @return Apitoken
     */
    public synchronized String getApitoken(){
        return apitoken;
    }

    /**
     * Method that sign in the user.
     * @param act Activity that calls the method
     * @param password User´s password for login
     * @return If the login intent was successfully
     */
    public synchronized boolean signIn(Activity act, String password){
        List<String> threadResult = new ArrayList<String>(2);
        LoginThread lt = new LoginThread(act, username, password, threadResult);
        Thread th = new Thread(lt);
        th.start();
        try { th.join(); } catch (InterruptedException e) {}
        if(threadResult.size() == 1){
            apitoken = threadResult.get(0);
            return true;
        }
        return false;
    }

}
