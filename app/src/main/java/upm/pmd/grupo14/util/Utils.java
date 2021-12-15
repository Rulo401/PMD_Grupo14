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
import java.util.Date;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.login.LoginToken;

/**
 * Class containing common methods
 */
public class Utils {

    /**
     * Method to read all the content of an input stream.
     * @param in Input stream
     * @return Content inside the input stream
     */
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

    /**
     * Method that retrieves a LoginToken from the data saved in the preferences.
     * @param act Activity that calls the method
     * @return LoginToken built with the data stored in the preferences
     */
    public static LoginToken getUserFromPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        if (!pref.contains(Constants.PreferenceNames.USERNAME) || !pref.contains(Constants.PreferenceNames.APITOKEN)) return null;
        String username = pref.getString(Constants.PreferenceNames.USERNAME,"");
        String apitoken = pref.getString(Constants.PreferenceNames.APITOKEN,"");
        return new LoginToken(username, apitoken);
    }

    /**
     * Method that stores the data from a LoginToken into the preferences.
     * @param act Activity that calls the method
     * @param username LoginToken´s username
     * @param token LoginToken´s apitoken
     */
    public static void saveUserInPreferences(Activity act, String username, String token){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.PreferenceNames.USERNAME, username);
        editor.putString(Constants.PreferenceNames.APITOKEN, token);
        editor.commit();
    }

    /**
     * Method that deletes the preferences data related to the user´s login.
     * @param act Activity that calls the method
     */
    public static void deleteUserInPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.PreferenceNames.USERNAME);
        editor.remove(Constants.PreferenceNames.APITOKEN);
        editor.commit();
    }

    /**
     * Method that retrieves the current date.
     * @return Current date formatted as the standard of the app
     */
    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Convertor of date formatted as the standard of the app to long.
     * @param date Date to convert
     * @return Date in long
     */
    public static long stringDateToLong(String date){
        long milliseconds = 0l;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date dateFormat = format.parse(date);
            milliseconds = dateFormat.getTime();
        } catch (ParseException e){}
        return milliseconds;
    }

    /**
     * Convertor of date in long to a String formatted as the standard of the app.
     * @param date Date to convert
     * @return Date in String
     */
    public static String longDateToString(long date){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return form.format(new Date(date));
    }

    /**
     * Method that stores a date into the preferences.
     * @param ctx Environment where the method is called
     * @param date Date to save in preferences
     * @param last_check If the date is the last time checked updated articles
     */
    public static void saveDateInPreferences(Context ctx, String date, boolean last_check){
        SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.NOTIFICATIONS_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(last_check ? Constants.PreferenceNames.LAST_CHECK_DATE : Constants.PreferenceNames.LAST_ARTICLE_UPDATE, date);
        editor.commit();
    }

    /**
     * Method that retrieves the last_check date and the last_update date from preferences.
     * @param ctx Environment where the method is called
     * @return An array with both dates formatted as the standard of the app
     */
    public static String[] getDatesFromPreferences(Context ctx){
        SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences(Constants.PreferenceNames.NOTIFICATIONS_FILENAME, Context.MODE_PRIVATE);
        return new String[]{pref.getString(Constants.PreferenceNames.LAST_CHECK_DATE, Utils.getCurrentDate()), pref.getString(Constants.PreferenceNames.LAST_ARTICLE_UPDATE, Utils.getCurrentDate())};
    }
}
