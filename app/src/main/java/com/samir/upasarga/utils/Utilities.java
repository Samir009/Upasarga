package com.samir.upasarga.utils;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.GsonBuilder;
import com.samir.upasarga.Bottomsheets.ThemeChangeBottomSheetDialog;
import com.samir.upasarga.constants.AppConstants;
import com.samir.upasarga.models.UserLoginModel;

import static android.content.Context.MODE_PRIVATE;
import static com.samir.upasarga.helpers.MyApplication.getSharedPreference;

public class Utilities {
    /**
     *
     * @param loginResponse
     */
    public static void saveLoginResponse(UserLoginModel loginResponse){
        String json = new GsonBuilder().create().toJson(loginResponse);
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(AppConstants.LOGIN_RESPONSE, json);
        editor.apply();
        setIsFirstLogin(true);
    }

    public static UserLoginModel getLoginResponse()
    {
        String savedUserResponse = getSharedPreference().getString(AppConstants.LOGIN_RESPONSE, null);
        return new GsonBuilder().create().fromJson(savedUserResponse, UserLoginModel.class);
    }
    public static void setIsFirstLogin(boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(AppConstants.IS_FIRST_LOGIN, status);
        editor.apply();
    }

    public static boolean isLogin()
    {
        return getSharedPreference().getBoolean(AppConstants.IS_FIRST_LOGIN, false);
    }

    public static boolean isIntroOpened(){

        return getSharedPreference().getBoolean(AppConstants.IS_FIRST_INTRO_OPENED, false);

    }
    public static void setIntroCompleted(boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(AppConstants.IS_FIRST_INTRO_OPENED, status);
        editor.apply();
    }

    public static void setNightMode(Boolean nightMode){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(AppConstants.APP_THEME,nightMode);
        editor.apply();
    }

//    changing the theme of app
      public static boolean isNightModeOn(){
        return getSharedPreference().getBoolean(AppConstants.APP_THEME, false);
    }
}
