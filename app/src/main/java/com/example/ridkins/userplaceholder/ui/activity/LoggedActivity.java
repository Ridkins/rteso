package com.example.ridkins.userplaceholder.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ridkins.userplaceholder.R;
import com.example.ridkins.userplaceholder.database.DBController;
import com.example.ridkins.userplaceholder.database.UsersDBUtils;
import com.example.ridkins.userplaceholder.models.UserListModel;
import com.example.ridkins.userplaceholder.models.UserModel;
import com.example.ridkins.userplaceholder.my_interfaces.InterfaceUtils;
import com.example.ridkins.userplaceholder.my_interfaces.OnNetworkChange;
import com.example.ridkins.userplaceholder.retrofit.RetrofitService;
import com.example.ridkins.userplaceholder.settings.Settings;
import com.example.ridkins.userplaceholder.ui.adapters.UsersAdapter;
import com.facebook.login.LoginManager;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rud on 8/6/17.
 */

public class LoggedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , OnNetworkChange{
    private RecyclerView mRecyclerView;
    private TextView tvNoResults;
    private ProgressBar mProgressBar;
    private int position;
    private ArrayList<UserModel> usersList = new ArrayList<>();
    private ArrayList<UserModel> usersListDb = new ArrayList<>();
    private TextView tvProfileName;
    private TextView tvProfileMail;
    private ImageView ivProfilePhoto;
    SharedPreferences mSharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(Settings.FILE_NAME, MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        tvProfileName = ((TextView) headerLayout.findViewById(R.id.profile_name));
        tvProfileMail = ((TextView) headerLayout.findViewById(R.id.profile_mail));
        ivProfilePhoto = ((ImageView) headerLayout.findViewById(R.id.profile_photo));

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_users);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_results);
        tvNoResults = (TextView) findViewById(R.id.tv_no_results);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        InterfaceUtils.getmInterfaceUtils().setOnNetworkChange(this);

        if(!isOnline())
        initDBUsers();
        else
         makeRequest();

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void clearUserInfo(){


        Settings.setUserLoggedIn(false, mSharedPreferences);
        Settings.setUserName("", mSharedPreferences);
        Settings.setUserEMail("", mSharedPreferences);
        Settings.setUserID(String.valueOf(""), mSharedPreferences);
        Settings.setUserPhotoUrl("", mSharedPreferences);
        Intent mIntent = new Intent(LoggedActivity.this, LoginActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.trans_right_out);
        finish();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {

            LoginManager.getInstance().logOut();
            clearUserInfo();
                }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeRequest(){
        mProgressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<UserModel>> mCall = null;


        mCall = RetrofitService.getInstance().getUserList();
        mCall.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                tvNoResults.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                usersList = response.body();
                if (usersList!= null && usersList.size() != 0){
                    initAdapter(usersList);
                    String fullName = Settings.getUserName(mSharedPreferences) ;
                    tvProfileName.setText(fullName);
                    tvProfileMail.setText(Settings.getUserEMail(mSharedPreferences));
                    Glide.with(LoggedActivity.this).load(Settings.getUserPhotoUrl(mSharedPreferences)).into(ivProfilePhoto);

                    Dao<UserListModel, Integer> mUsersList = null;
                    UserListModel mUserListModel = new UserListModel();
                    mUserListModel.setUserModels(response.body());

                    try {
                        mUsersList = DBController.create(LoggedActivity.this).getUserListdb();
                        mUsersList.deleteBuilder().delete();
                        mUsersList.create(mUserListModel);


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    tvNoResults.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                tvNoResults.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                tvNoResults.setText("Проверьте интернет соидинение");

            }
        });

    }


    private void initAdapter(ArrayList<UserModel> arrayList) {
        mRecyclerView.removeAllViews();
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new UsersAdapter(mClickListener,this, arrayList));
        mProgressBar.setVisibility(View.GONE);



    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            position = mRecyclerView.getChildAdapterPosition(v);
            UserModel mUserModel = usersList.get(position);
            Intent intent = new Intent(LoggedActivity.this, UserBigCardActivity.class);
            intent.putExtra("user",(Serializable)mUserModel);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);



        }
    };

    private void initDBUsers() {

        UsersDBUtils mUsersDBUtils = new UsersDBUtils(LoggedActivity.this);
        usersListDb = mUsersDBUtils.getUsersDBList();
        for (int i = 0; i < usersListDb.size() ; i++) {
            Log.e("From DB = ", " " + usersListDb.get(i).getName());

        }

        initAdapter(usersListDb);
        Toast.makeText(LoggedActivity.this, "Data downloaded from database", Toast.LENGTH_LONG).show();

    }

    @Override
    public void OnNetworkActive() {
        makeRequest();
    }

    @Override
    public void OnNetworkOff() {

        if(usersListDb.size() == 0 &&  usersList.size() == 0 ){
            mProgressBar.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            tvNoResults.setText("Проверьте интернет соидинение");

        }else  if( usersList.size() == 0){
            initDBUsers();

        }
    }


    @Override
    protected void onDestroy() {
        InterfaceUtils.getmInterfaceUtils().setOnNetworkChange(null);
        super.onDestroy();
    }
}
