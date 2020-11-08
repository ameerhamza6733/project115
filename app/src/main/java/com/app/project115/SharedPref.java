package com.app.project115;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {


    public static final String KEY_URL_WEBVIEW = "KEY_URL_WEBVIEW";
    public static String IS_FIREST_TIME="is first time";
    public static String KEY_COUNT_POP_X="key count pop x";
    public static String KEY_COUNT_POP_Y="key count pop y";
    public static String KEY_IS_POP_SHOW="is pop showing";

    private static SharedPreferences mSharedPref;


    private SharedPref() {

    }

    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static void clear() {
        mSharedPref.edit().clear().apply();
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }

    public static void write(String key, Float value) {

        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putFloat(key, value).commit();
    }

    public static float read(String key, float defValue) {
        return mSharedPref.getFloat(key, defValue);
    }


}