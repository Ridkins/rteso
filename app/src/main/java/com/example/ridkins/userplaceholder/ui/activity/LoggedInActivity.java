package com.example.ridkins.userplaceholder.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ridkins.userplaceholder.R;
import com.example.ridkins.userplaceholder.models.UserModel;
import com.example.ridkins.userplaceholder.retrofit.RetrofitService;
import com.example.ridkins.userplaceholder.ui.adapters.UsersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rud on 8/5/17.
 */

public class LoggedInActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView tvNoResults;
    private ProgressBar mProgressBar;
    private int position;
    private ArrayList<UserModel> usersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loged_in_main_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_users);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_results);
        tvNoResults = (TextView) findViewById(R.id.tv_no_results);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        makeRequest();
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
                if (usersList!= null && usersList.size() != 0)
                    initAdapter();
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


    private void initAdapter() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new UsersAdapter(mClickListener,this, usersList));
        mProgressBar.setVisibility(View.GONE);



    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            position = mRecyclerView.getChildAdapterPosition(v);

            Intent intent = new Intent(LoggedInActivity.this, LoggedInActivity.class);

          //  intent.putExtra("id", String.valueOf(usersList.get(position).getId()));

            startActivityForResult(intent,23);
          //  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            Toast.makeText(getActivity(), getString(R.string.pressed)+position+ getString(R.string.element), Toast.LENGTH_SHORT).show();

        }
    };

}
