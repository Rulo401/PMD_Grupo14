package upm.pmd.grupo14.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        catch(IOException e){}
        return res;
    }

    public static LoginToken getUserFromPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        if (!pref.contains(Constants.PreferenceNames.USERNAME) || !pref.contains(Constants.PreferenceNames.APITOKEN)) return null;
        String username = pref.getString(Constants.PreferenceNames.USERNAME,"");
        String apitoken = pref.getString(Constants.PreferenceNames.APITOKEN,"");
        return new LoginToken(username, apitoken);
    }

    public static void saveUserInPreferences(Activity act, String username, String token){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.PreferenceNames.USERNAME, username);
        editor.putString(Constants.PreferenceNames.APITOKEN, token);
        editor.commit();
    }

    public static void deleteUserInPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.PreferenceNames.USERNAME);
        editor.remove(Constants.PreferenceNames.APITOKEN);
        editor.commit();
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static long stringDateToLong(String date){
        long milliseconds = 0l;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date dateFormat = format.parse(date);
            milliseconds = dateFormat.getTime();
        } catch (ParseException e){}
        return milliseconds;
    }

    public static String longDateToString(long date){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return form.format(new Date(date));
    }

    public static void saveDateInPreferences(Context ctx, String date, boolean last_check){
        SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.NOTIFICATIONS_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(last_check ? Constants.PreferenceNames.LAST_CHECK_DATE : Constants.PreferenceNames.LAST_ARTICLE_UPDATE, date);
        editor.commit();
    }

    public static String[] getDatesFromPreferences(Context ctx){
        SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.NOTIFICATIONS_FILENAME, Context.MODE_PRIVATE);
        return new String[]{pref.getString(Constants.PreferenceNames.LAST_CHECK_DATE, Utils.getCurrentDate()), pref.getString(Constants.PreferenceNames.LAST_ARTICLE_UPDATE, Utils.getCurrentDate())};
    }
}
