package com.zhat.tung.dailyquote.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;
import com.zhat.tung.dailyquote.R;
import com.zhat.tung.dailyquote.fragments.LoginFragment;
import com.zhat.tung.dailyquote.fragments.QuoteFragment;
import com.zhat.tung.dailyquote.managers.Preferences;
import com.zhat.tung.dailyquote.models.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if(Preferences.getInstance().getUserName() == null)
            changeFragment(new LoginFragment(), false, null);
        else
            changeFragment(new QuoteFragment(), false, null);

        Storage storage = SimpleStorage.getInternalStorage(this);
//        storage.createFile("daily_qoute", "quote.txt", "No Paint, No Gain");
        String content = storage.readTextFile("daily_qoute", "quote.txt");
        Log.d(TAG, String.format("Read done: %s", content ));
    }

    @Subscribe
    public void onEvent(FragmentEvent fragmentEvent){
        changeFragment(fragmentEvent.getFragment(), fragmentEvent.isAddToBackStack(), null);
    }

    public void changeFragment(Fragment fragment, boolean addToBackstack, String tag){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment);
        if(addToBackstack){
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }
}
