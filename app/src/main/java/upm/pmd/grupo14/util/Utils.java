package upm.pmd.grupo14.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.common.Constants;

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
