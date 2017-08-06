package com.example.ridkins.userplaceholder.database;

import android.content.Context;

import com.example.ridkins.userplaceholder.models.UserListModel;
import com.example.ridkins.userplaceholder.models.UserModel;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by user on 8/6/17.
 */

public class UsersDBUtils {
    private Context mContext;
    Dao<UserListModel, Integer> mUserListModels;

    public UsersDBUtils(Context context) {
        mContext = context;
        try {
            mUserListModels = DBController.create(mContext).getUserListdb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isDBHasInfo() {

        try {
            return mUserListModels.queryForAll().size() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<UserModel> getUsersDBList() {
        ArrayList<UserModel> mNameIdModels = new ArrayList<>();


        try {
            mNameIdModels = mUserListModels.queryForAll().get(0).getUserModels();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mNameIdModels;


    }

}
