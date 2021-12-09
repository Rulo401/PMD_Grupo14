package upm.pmd.grupo14.models.login;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import upm.pmd.grupo14.tasks.LoginThread;

public class LoginToken {
    private String username;
    private String password;

    private String apitoken;
    private String expDate;

    public LoginToken(String username, String password){
        this.username = username;
        this.password = password;
        apitoken = null;
        expDate = null;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized void updateToken(String newApitoken, String newExpDate){
        this.apitoken = newApitoken;
        this.expDate = newExpDate;
    }

    public synchronized String getApitoken(){
        return apitoken;
    }

    public synchronized boolean isLoginStillValid(){
        //TODO + solicitar nuevo api en los metodos que llaman al get
        return apitoken == null;
    }

    public synchronized boolean signIn(Activity act){
        List<String> threadResult = new ArrayList<String>(2);
        LoginThread lt = new LoginThread(act, username, password, threadResult);
        Thread th = new Thread(lt);
        th.start();
        try { th.join(); } catch (InterruptedException e) {}
        if(threadResult.size() == 2){
            apitoken = threadResult.get(0);
            expDate = threadResult.get(1);
            return true;
        }
        return false;
    }

}
