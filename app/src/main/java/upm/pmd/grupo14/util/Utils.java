package upm.pmd.grupo14.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.tasks.LoginThread;

public class Utils {

    public static String readInputStream(InputStream in){
        String res = null;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String inputLine;
            while ((inputLine = bf.readLine()) != null){
                result.append(inputLine);
            }
            res = result.toString();
            bf.close();
        }
        catch(IOException e){ System.err.println("Exception reading the input stream"); }
        return res;
    }

    public static LoginToken getUserFromPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        if (!pref.contains(Constants.PreferenceNames.USERNAME) || !pref.contains(Constants.PreferenceNames.PASSWORD)){
            return null;
        }
        String username = pref.getString(Constants.PreferenceNames.USERNAME,"");
        String password = pref.getString(Constants.PreferenceNames.PASSWORD,"");
        LoginToken token = new LoginToken(username,password);
        if (token.signIn(act)){
            return token;
        }
        else {
            return null;
        }
    }

    public static void saveUserInPreferences(Activity act, String username, String password){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.PreferenceNames.USERNAME, username);
        editor.putString(Constants.PreferenceNames.PASSWORD, password);
        editor.commit();
    }

    public static void deleteUserInPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.PreferenceNames.USERNAME);
        editor.remove(Constants.PreferenceNames.PASSWORD);
        editor.commit();
    }
}
