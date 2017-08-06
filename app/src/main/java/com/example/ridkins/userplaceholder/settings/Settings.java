package com.example.ridkins.userplaceholder.settings;

import android.content.SharedPreferences;

import com.example.ridkins.userplaceholder.utils.Utils;

import java.util.Set;

/**
 * Created by Rud on 8/6/17.
 */

public abstract class Settings {

    public static final String FILE_NAME = "settings";


    public static final String FIRST_NAME = "first_name";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_LOGGED_IN = "user_logged_in";
    public static final String USER_PHOTO_URL = "user_photo_url";
    public static final String IS_ENCRYPTED = "is_encrypted";


    public static boolean isSavedPreference(Set<String> set, String id) {
        return set.contains(id);
    }


    public static String getUserName(SharedPreferences preferences) {
        return getFromPreferences(preferences.getString(FIRST_NAME, ""), preferences);
    }


    public static void setUserName(String name, SharedPreferences preferences) {
        putToPreferences(FIRST_NAME, name, preferences);
    }


    public static String getUserPhotoUrl(SharedPreferences preferences) {
        return getFromPreferences(preferences.getString(USER_PHOTO_URL, ""), preferences);
    }


    public static void setUserPhotoUrl(String photo, SharedPreferences preferences) {
        putToPreferences(USER_PHOTO_URL, photo, preferences);
    }


    public static String getUserID(SharedPreferences preferences) {
        return getFromPreferences(preferences.getString(USER_ID, "-1"), preferences);
    }


    public static void setUserID(String id, SharedPreferences preferences) {
        putToPreferences(USER_ID, id, preferences);

    }

    public static String getUserEMail(SharedPreferences preferences) {
        return getFromPreferences(preferences.getString(USER_EMAIL, ""), preferences);

    }


    public static void setUserEMail(String email, SharedPreferences preferences) {
        putToPreferences(USER_EMAIL, email, preferences);

    }


    public static void setUserLoggedIn(boolean loggedIn, SharedPreferences preferences) {
        putToPreferences(USER_LOGGED_IN, loggedIn, preferences);
    }

    public static boolean getUserLoggedIn(SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(USER_LOGGED_IN, false);
    }


    private static void putToPreferencesNonCrypted(String key, Object data, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor mEditor = sharedPreferences.edit();

        try {
            if (data instanceof String) {
                mEditor.putString(key, data.toString());
            }
            if (data instanceof Boolean) {
                mEditor.putBoolean(key, (Boolean) data);
            }
            if (data instanceof Integer) {
                mEditor.putInt(key, (Integer) data);
            }
            if (data instanceof Float) {
                mEditor.putFloat(key, (Float) data);
            }
        } catch (Exception mE) {
            mE.printStackTrace();
            mEditor.putString(key, data.toString());
        }
        mEditor.apply();
    }


    private static void putToPreferences(String key, Object data, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor mEditor = sharedPreferences.edit();

        try {
            if (data instanceof String) {
                mEditor.putString(key, data.toString());
            }
            if (data instanceof Boolean) {
                mEditor.putBoolean(key, (Boolean) data);
            }
            if (data instanceof Integer) {
                mEditor.putInt(key, (Integer) data);
            }
            if (data instanceof Float) {
                mEditor.putFloat(key, (Float) data);
            }
        } catch (Exception mE) {
            mE.printStackTrace();
            mEditor.putString(key, Utils.encrypt(data.toString()));
        }
        mEditor.apply();
    }


    private static String getFromPreferences(String value, SharedPreferences sharedPreferences) {
        if (value.isEmpty())
            return value;

        return value;
    }

}