package com.example.ridkins.userplaceholder.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ridkins.userplaceholder.R;
import com.example.ridkins.userplaceholder.databinding.MyUserActivityBinding;
import com.example.ridkins.userplaceholder.models.UserModel;
import com.example.ridkins.userplaceholder.settings.Settings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by user on 8/6/17.
 */

public class UserBigCardActivity  extends AppCompatActivity implements OnMapReadyCallback {
    private SharedPreferences mSharedPreferences;
    private GoogleMap mMap;
     private UserModel mUserModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        Intent mIntent = getIntent();
        mUserModel = (UserModel) mIntent.getSerializableExtra("user");
        MyUserActivityBinding mBinding = DataBindingUtil.setContentView(UserBigCardActivity.this, R.layout.my_user_activity);
        mBinding.setUser(mUserModel);
        initToolbar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mSharedPreferences = getSharedPreferences(Settings.FILE_NAME, MODE_PRIVATE);

    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /*TickerTextView mTextView = (TickerTextView) findViewById(R.id.title);
        mTextView.setText(getString(R.string.my_company));*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.trans_right_out);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(mUserModel.getAddress().getGeo().getLat(), mUserModel.getAddress().getGeo().getLng());
        mMap.addMarker(new MarkerOptions().position(sydney).title(mUserModel.getCompany().getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
