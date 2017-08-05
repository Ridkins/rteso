package com.example.ridkins.userplaceholder.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rud on 8/5/17.
 */

public class RetrofitService {

    private static API sRetrofitApi;
    private static OkHttpClient okHttpClient;


    private static void initClient() {

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder().build();
                    }
                })
                .build();

    }

    private RetrofitService() {

    }

    /**
     * Instance oj {@link API} used with main thread only;
     *
     * @return instance
     */
    public static API getInstance() {

        if (sRetrofitApi == null){
            initClient();

            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sRetrofitApi = mRetrofit.create(API.class);
        }

        return sRetrofitApi;
    }




}