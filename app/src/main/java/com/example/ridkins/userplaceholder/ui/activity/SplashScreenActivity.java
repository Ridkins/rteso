package com.example.ridkins.userplaceholder.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ridkins.userplaceholder.R;
import com.example.ridkins.userplaceholder.settings.Settings;

/**
 * Created by Rud on 8/6/17.
 */

public class SplashScreenActivity  extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        mSharedPreferences = getSharedPreferences(Settings.FILE_NAME, MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if( Settings.getUserLoggedIn(mSharedPreferences)){
                        Intent mIntent = new Intent(SplashScreenActivity.this, LoggedActivity.class);
                        startActivity(mIntent);
                        finish();
                    }else{
                        Intent mIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(mIntent);
                        finish();
                    }

                }
            }, 2000);


    }
}
