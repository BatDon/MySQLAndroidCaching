package com.test.table.MySQLAndroidCaching3;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class NetworkConnection extends Application {

    private static Network network;
    private static Context context;
    private static NetworkConnection instance;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            context=GlobalApplication.getAppContext();
            instance = new NetworkConnection(context);
        }
    }

    public static NetworkConnection getInstance() {
        if (instance == null) {
            instance = new NetworkConnection(GlobalApplication.getAppContext());
        }
        return instance;
    }

    private NetworkConnection(Context context) {
        context = context;
    }

    public static boolean internetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkCapabilities capabilities = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            network = connectivity.getActiveNetwork();
            if (network == null) {
                return false;
            }
            capabilities = connectivity
                    .getNetworkCapabilities(network);
            return capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }

    public static boolean isConnected(Context context){

        //GlobalApplication.getAppContext()isNetworkConnected();
        if (instance==null){
            getInstance();
            if(instance==null) {
                Log.i("instance", "NULLLLLLLLL");
            }
        }
        return instance.internetConnection(context);
    }
}
