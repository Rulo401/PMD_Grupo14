package upm.pmd.grupo14.models.login;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import upm.pmd.grupo14.tasks.LoginThread;

public class LoginToken {

    private String username;
    private String apitoken;


    public LoginToken(String username, String apitoken){
        this.username = username;
        this.apitoken = apitoken;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized boolean isLogged(){
        return username != null && apitoken != null;
    }

    public synchronized String getApitoken(){
        return apitoken;
    }

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
