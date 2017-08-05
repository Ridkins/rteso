package com.example.ridkins.userplaceholder.retrofit;

import com.example.ridkins.userplaceholder.models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 8/5/17.
 */

public interface API {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("users")
    Call<ArrayList<UserModel>> getUserList();
}
