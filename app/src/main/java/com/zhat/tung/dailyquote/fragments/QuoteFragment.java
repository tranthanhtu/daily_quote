package com.zhat.tung.dailyquote.fragments;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhat.tung.dailyquote.R;
import com.zhat.tung.dailyquote.constants.Constants;
import com.zhat.tung.dailyquote.jsonmodels.QuoteJSONModel;
import com.zhat.tung.dailyquote.managers.Preferences;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment {
    private static final String TAG = QuoteFragment.class.getSimpleName();
    @BindView(R.id.iv_background)
    ImageView ivBackground;

    @BindView(R.id.tv_content)
    TextView tvContent;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_greeting)
    TextView tvGreeting;

    public QuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quote, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    private void setupUI() {
        //get image with ImageLoader
        ImageLoader.getInstance().displayImage(Constants.UNSPLASH_API, ivBackground);

        //get qoute with OkHttpClient
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.QOUTE_API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bodyString = response.body().string();
                Log.d(TAG, "onResponse: " + bodyString);

                //Use GSON parse bodyString to QuoteJSONModel
                Gson gson = new Gson();
                QuoteJSONModel[] quotes = gson.fromJson(bodyString, QuoteJSONModel[].class);

                if (quotes.length > 0) {
                    QuoteFragment.this.updateQuote(quotes[0]);
                }

            }
        });

        tvGreeting.setText(getString(R.string.hello) + " " + Preferences.getInstance().getUserName());

        File file = FileManager.getInstance().loadImage("unsplash");
        ImageLoader.getInstance().displayImage(
                Uri.fromFile(file).toString(),
                ivBackground
        );
//        ImageLoader.getInstance().loadImage(Constants.UNSPLASH_API, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                Log.d(TAG, "Imaged loaded");
//                FileManager.getInstance().createImage(loadedImage, "unsplash");
//                Log.d(TAG, "Imaged save done");
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        });
    }

    private void updateQuote(final QuoteJSONModel quote) {
        Activity parent = getActivity();
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTitle.setText(quote.getTitle());
                tvContent.setText(Html.fromHtml(quote.getContent()));
            }
        });
    }
}
