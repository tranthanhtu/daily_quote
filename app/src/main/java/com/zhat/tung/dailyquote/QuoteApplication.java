package com.zhat.tung.dailyquote;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;
import com.zhat.tung.dailyquote.fragments.FileManager;
import com.zhat.tung.dailyquote.managers.NetworkManager;
import com.zhat.tung.dailyquote.managers.Preferences;

import java.net.InetAddress;

/**
 * Created by tungb on 10/12/2016.
 */

public class QuoteApplication extends Application {

    private static final String TAG = QuoteApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Preferences.init(this);
        NetworkManager.init(this);
        FileManager.init(this);
        initImageLoader();

        if(NetworkManager.getInstance().isConnectedToInternet()){
            Log.d(TAG, "Connected");
        }else {
            Log.d(TAG, "NOT Connected");
        }


    }


    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }
}
