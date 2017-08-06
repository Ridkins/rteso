package com.example.ridkins.userplaceholder.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ridkins.userplaceholder.my_interfaces.InterfaceUtils;

/**
 * Created by Rud on 8/6/17.
 */
public class ConnectionBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:

                if (InterfaceUtils.getmInterfaceUtils().getOnNetworkChange() == null) {
                    return;
                }

                if (isStatusConnected(context)) {
                    InterfaceUtils.getmInterfaceUtils().getOnNetworkChange().OnNetworkActive();
                }else{
                    InterfaceUtils.getmInterfaceUtils().getOnNetworkChange().OnNetworkOff();
                }

                break;

        }

    }

    private boolean isStatusConnected(Context context) {
        @SuppressLint("WrongConstant") ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return null != activeNetwork;
    }

}