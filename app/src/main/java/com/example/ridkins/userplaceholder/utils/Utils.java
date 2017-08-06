package com.example.ridkins.userplaceholder.utils;

import android.util.Base64;

/**
 * Created by Rud on 8/6/17.
 */

public class Utils {

    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);
    }

    public static String decrypt(String input) throws Exception{
        return new String(Base64.decode(input, Base64.NO_WRAP));
    }


}
