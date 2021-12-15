package upm.pmd.grupo14.models.appContext;

import android.app.Application;

import upm.pmd.grupo14.models.login.LoginToken;

/**
 * Class that extends Application for storing the global variable needed in a logged context.
 */
public class LogContext extends Application {

    private LoginToken loginToken;

    /**
     * LoginToken getter.
     * @return LoginToken
     */
    public LoginToken getLoginToken(){ return loginToken; }

    /**
     * LoginToken setter.
     * @param loginToken User´s loginToken
     */
    public void setLoginToken(LoginToken loginToken) {this.loginToken = loginToken; }
}
