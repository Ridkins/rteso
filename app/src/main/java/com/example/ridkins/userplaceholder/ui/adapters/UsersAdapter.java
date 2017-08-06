package com.example.ridkins.userplaceholder.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ridkins.userplaceholder.R;
import com.example.ridkins.userplaceholder.models.UserModel;

import java.util.List;

/**
 * Created by Rud on 8/5/17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.TradersViewHolder> {
    private View.OnClickListener mListener;
    private Context mContext;
    private List<UserModel> mMyCompanyList;

    public UsersAdapter(View.OnClickListener onClickListener, Context context, List<UserModel> myCompanies) {
        mListener = onClickListener;
        mContext = context;
        mMyCompanyList = myCompanies;
    }

    @Override
    public TradersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = null;

        mView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.users_card, parent, false);

        if (mListener != null)
            mView.setOnClickListener(mListener);
        return new TradersViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final TradersViewHolder holder, int position) {


        holder.tvUserName.setText(mMyCompanyList.get(position).getName());
        holder.tvCompanyName.setText(mMyCompanyList.get(position).getCompany().getName());
        holder.tvUsersWebSite.setText(mMyCompanyList.get(position).getWebsite());



    }

    @Override
    public int getItemCount() {
        return mMyCompanyList.size();
    }


    public class TradersViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName,  tvUsersWebSite, tvCompanyName;


        public TradersViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvUsersWebSite = (TextView) itemView.findViewById(R.id.tv_web_site);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tv_company_name);


        }
    }
}