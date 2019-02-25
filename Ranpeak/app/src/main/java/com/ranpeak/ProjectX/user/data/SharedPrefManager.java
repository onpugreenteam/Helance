package com.ranpeak.ProjectX.user.data;

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
    private static final String KEY_AGE = "user_age";
    private static final String KEY_GENDER = "user_gender";
    private static final String KEY_AVATAR = null;

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
    public boolean userLogin(String login, String name, String email, String country, int age, String gender, String avatar){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_COUNTRY, country);
        editor.putInt(KEY_AGE, age);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_AVATAR, avatar);

        editor.apply();

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

    public int getUserAge(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(KEY_AGE, 0);
    }

    public String getUserCountry(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_COUNTRY,null);
    }

    public String getUserGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_GENDER,null);
    }

    public String getUserAvatar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_AVATAR, null);
    }
}
