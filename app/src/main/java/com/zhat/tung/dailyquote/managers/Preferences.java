package com.zhat.tung.dailyquote.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tungb on 10/12/2016.
 */

public class Preferences {
    private static final String KEY = "Quote";
    private static final String USER_NAME_KEY = "Username";
    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public String getUserName(){
        return sharedPreferences.getString(USER_NAME_KEY, null);
    }

    public void putUsername(String username){
        sharedPreferences
                .edit()
                .putString(USER_NAME_KEY, username)
                .commit();
    }

    private static Preferences instance;
    public static Preferences getInstance(){
        return instance;
    }

    public static void init(Context context){
        instance = new Preferences(context);
    }
}
