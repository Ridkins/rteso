package com.example.ridkins.userplaceholder.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * @author Stas
 * @since 30.08.16.
 */

public abstract class DBController {

    private static OrmDBHelper sDBHelper;

    public static OrmDBHelper create(Context context) {
        if (sDBHelper == null) {
            sDBHelper = OpenHelperManager.getHelper(context, OrmDBHelper.class);
        }
        return sDBHelper;
    }

    public static void releaseDB() {
        if (sDBHelper == null) {
            OpenHelperManager.releaseHelper();
        }
    }

}
