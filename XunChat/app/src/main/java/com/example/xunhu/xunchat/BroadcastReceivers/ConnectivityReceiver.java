package com.example.xunhu.xunchat.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.example.xunhu.xunchat.View.XunApplication;

/**
 * Created by xunhu on 6/13/2017.
 */

public class ConnectivityReceiver extends BroadcastReceiver{
    public static ConnectReceiverListener connectReceiverListener;

    public ConnectivityReceiver(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        if (connectReceiverListener!=null){
            connectReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public interface ConnectReceiverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
