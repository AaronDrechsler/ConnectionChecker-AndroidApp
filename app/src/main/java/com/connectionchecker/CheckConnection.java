package com.connectionchecker;

import android.content.Context;
import android.net.*;

import java.net.NetworkInterface;


public class CheckConnection {
       public static boolean isConnectedOrNot(Context context){
          ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifiConn != null && wifiConn.isConnected()){
                return true;
            }else {
                return false;
            }
    }
}
