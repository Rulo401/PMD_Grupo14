package upm.pmd.grupo14.models.login;

import java.util.concurrent.Semaphore;

public class LoginToken {
    private String username;
    private String password;

    private String apitoken;
    private String expDate;

    private Semaphore s;

    public LoginToken(String username, String password){
        this.username = username;
        this.password = password;
        apitoken = null;
        expDate = null;
        s = new Semaphore(1);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void updateToken(String newApitoken, String newExpDate){
        s.acquireUninterruptibly();
        this.apitoken = newApitoken;
        this.expDate = newExpDate;
        s.release();
    }

    public String getApitoken(){
        s.acquireUninterruptibly();
        String result = apitoken;
        s.release();
        return result;
    }

    public boolean isLoginStillValid(){
        s.acquireUninterruptibly();
        boolean result = apitoken == null;// falta verificar si ha expirado
        s.release();
        return result;
    }

}
