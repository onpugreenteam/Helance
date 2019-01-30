package com.ranpeak.ProjectX.user.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
    private static UserData mInstance;
    private static Context mCtx;

    private static final String USER_DATA_NAME="myuserdata12";
    private static final String KEY_USERNAME="username";

    private  UserData(Context context){
        mCtx = context;
    }

    public static synchronized UserData getInstance(Context context){
        if(mInstance == null){
            mInstance = new UserData(context);
        }
        return mInstance;
    }

    public boolean userLogin(String login){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, login);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_DATA_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
