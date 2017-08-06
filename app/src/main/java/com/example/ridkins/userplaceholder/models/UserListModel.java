package com.example.ridkins.userplaceholder.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;

/**
 * Created by user on 8/6/17.
 */

public class UserListModel {
    @DatabaseField(columnName = "main_users_list", dataType = DataType.SERIALIZABLE)
    private ArrayList<UserModel> mUserModels= new ArrayList<>();

    public ArrayList<UserModel> getUserModels() {
        return mUserModels;
    }

    public void setUserModels(ArrayList<UserModel> userModels) {
        mUserModels = userModels;
    }
}
