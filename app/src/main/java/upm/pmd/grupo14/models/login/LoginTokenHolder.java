package upm.pmd.grupo14.models.login;

public class LoginTokenHolder {

    private LoginToken loginToken;
    public LoginToken getLoginToken(){ return loginToken; }
    public void setLoginToken(LoginToken loginToken) {this.loginToken = loginToken; }

    private LoginTokenHolder(){
        loginToken = null;
    }

    private static final LoginTokenHolder holder = new LoginTokenHolder();
    public static LoginTokenHolder getInstance(){ return holder; }
}
