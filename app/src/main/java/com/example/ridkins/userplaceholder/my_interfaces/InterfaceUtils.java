package com.example.ridkins.userplaceholder.my_interfaces;

/**
 * Created by skif on 22.05.17.
 */

public class InterfaceUtils {

    private static InterfaceUtils mInterfaceUtils;

    private OnNetworkChange mOnNetworkChange ;


    public static InterfaceUtils getmInterfaceUtils() {
        if (mInterfaceUtils == null)
           mInterfaceUtils = new InterfaceUtils();
        return mInterfaceUtils;
    }


    public OnNetworkChange getOnNetworkChange() {
        return mOnNetworkChange;
    }

    public void setOnNetworkChange(OnNetworkChange onNetworkChange) {
        mOnNetworkChange = onNetworkChange;
    }
}
