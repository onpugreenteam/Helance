package com.ranpeak.ProjectX.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedprefname12";
    private static final String KEY_LOGIN = "user_login";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_COUNTRY = "user_country";
    private static final String KEY_AVATAR = null;
    private static final String KEY_TELEPHONE = "user_telephone";

    private SharedPrefManager(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // Если пользователь ввошел в акаунт(ввел правильно логин и пароль) -->
    // его данные(которые прийдут по логину и паролю с БД) сохраняться в приложении
    public boolean userLogin(String login, String name, String email, String country, String avatar, String telephone){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_COUNTRY, country);
        editor.putString(KEY_AVATAR, avatar);
        editor.putString(KEY_TELEPHONE, telephone);

        editor.apply();
        editor.commit();

        return true;
    }

    public boolean userUpdateImage(String avatar){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(KEY_AVATAR);
        editor.putString(KEY_AVATAR, avatar);
        editor.apply();
        return true;
    }

    // Метод показывает что пользователь уже авторизован -->
    // ему не будет показываться те активити в которых этот медот будет переопределен
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_LOGIN, null) != null){
            return true;
        }
        return false;
    }


    // Выход пользователя с приложения, очистка данных, которые были приняты с БД при входе данного пользователя
    public boolean logout(){
        //facebook
//        LoginManager.getInstance().logOut();
//        AccessToken.setCurrentAccessToken(null);

        //not facebook
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


  /** Ниже показаны методы, которые используются для получения данных, которые сохранились при входе,
   *  а также предназначены для записи данных в профиле пользователя
   */

    public String getUserLogin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOGIN,null);
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);
    }

    public String getUserCountry(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_COUNTRY,null);
    }


    public String getUserTelephone(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TELEPHONE,null);
    }

    public String getUserAvatar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_AVATAR, null);
    }
}
