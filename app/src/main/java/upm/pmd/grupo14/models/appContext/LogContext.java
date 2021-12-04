package upm.pmd.grupo14.models.appContext;

import android.app.Application;

import upm.pmd.grupo14.models.login.LoginToken;

public class LogContext extends Application {
    private LoginToken loginToken;
    public LoginToken getLoginToken(){ return loginToken; }
    public void setLoginToken(LoginToken loginToken) {this.loginToken = loginToken; }
}
