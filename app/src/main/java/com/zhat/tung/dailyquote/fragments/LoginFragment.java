package com.zhat.tung.dailyquote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zhat.tung.dailyquote.R;
import com.zhat.tung.dailyquote.managers.Preferences;
import com.zhat.tung.dailyquote.models.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    @BindView(R.id.et_username)
    EditText etUsername;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_save)
    public void save(){
        //get username
        String username = etUsername.getText().toString();

        //Save dataa to SharedPreferences
        Preferences.getInstance().putUsername(username);

        //change fragment
        EventBus.getDefault().post(new FragmentEvent(
                new QuoteFragment(),
                false
        ));
    }
}
