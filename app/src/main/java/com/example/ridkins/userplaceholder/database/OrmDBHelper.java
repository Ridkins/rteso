package com.example.ridkins.userplaceholder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ridkins.userplaceholder.models.UserListModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @author Skif
 * @since 30.08.16.
 */

public class OrmDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 4;


    private Dao<UserListModel, Integer> mUserListdb;


    public OrmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserListModel.class);

            Log.i(getClass().getName(), "TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, UserListModel.class, true);


            Log.i(getClass().getName(), "TABLE DROPPED");
        } catch (SQLException e) {
            Log.e("###", e.getMessage(), e);
        }finally {
            onCreate(database, connectionSource);
        }

    }

    public Dao<UserListModel, Integer> getUserListdb() throws SQLException {
        if (mUserListdb == null) {
            mUserListdb = getDao(UserListModel.class);
        }
        return mUserListdb;
    }



}

