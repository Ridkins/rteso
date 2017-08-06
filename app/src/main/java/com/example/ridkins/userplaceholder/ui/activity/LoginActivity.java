package com.example.ridkins.userplaceholder.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ridkins.userplaceholder.R;
import com.example.ridkins.userplaceholder.settings.Settings;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Rud on 8/5/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    CallbackManager callbackManager;
    private Button btnFaceBook;
    private Button btnGoogle;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_acivity_layout);
        btnGoogle = findViewById(R.id.btn_google);
        btnFaceBook = findViewById(R.id.btn_facebook);
        btnGoogle.setOnClickListener(this);
        btnFaceBook.setOnClickListener(this);
        mSharedPreferences = getSharedPreferences(Settings.FILE_NAME, MODE_PRIVATE);

    }


    private void initFaceBook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(  "public_profile", "email", "user_birthday", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Profile profile = Profile.getCurrentProfile();
                                        try {
                                            Log.e("Email = ", " " + object.getString("email"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Settings.setUserName(profile.getFirstName() + " " + profile.getLastName(), mSharedPreferences);
                                        Settings.setUserID(profile.getId(), mSharedPreferences);
                                        Settings.setUserPhotoUrl(profile.getProfilePictureUri(300, 300).toString(), mSharedPreferences);
                                        Settings.setUserLoggedIn(true, mSharedPreferences);
                                        Intent mIntent = new Intent(LoginActivity.this, LoggedActivity.class);
                                        startActivity(mIntent);
                                        finish();


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Log.v("LoginActivity", "cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.v("LoginActivity", exception.getCause().toString());

                    }
                });


    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_facebook:
                initFaceBook();
                break;

            case R.id.btn_google:
                Intent mIntent = new Intent(LoginActivity.this, LoggedActivity.class);
                startActivity(mIntent);
                break;


        }
    }
}
