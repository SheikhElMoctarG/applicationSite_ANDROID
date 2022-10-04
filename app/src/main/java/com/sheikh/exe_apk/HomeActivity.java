package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.sheikh.exe_apk.Adapter.MyAdapture;
import com.sheikh.exe_apk.Model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    JSONArray posts = null;
    Boolean isConnected ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        if (isConnected(extras)) {
            try {
                connectRecyclerView();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            createDialog(R.string.description_if_no_internet, HomeActivity.this);
        }
        // share button
        shareApp();
        // change the status bar color
        new MainActivity().changeSBarColor(R.color.color_top_linear, this);
        // load ads
        AdView adView = findViewById(R.id.adView);
        loadAd(adView, this);
    }
    // restart the info after again
    public void restart() throws JSONException {
        if (isConnected){
            connectRecyclerView();
        }
    }
    // create new a dialog window
    public void createDialog(int description, Context OurActivity){
        AlertDialog.Builder dialog = new AlertDialog.Builder(OurActivity);
        Resources res = OurActivity.getResources();
        dialog.setMessage(res.getString(description));
        dialog.setCancelable(false);
        dialog.setPositiveButton(res.getString(R.string.tryagian), (dialogInterface, i) -> {
            dialogInterface.cancel();
            getPosts();
        });
        dialog.setNegativeButton(res.getString(R.string.button_exit), (dialogInterface, i) -> this.finish());
        AlertDialog alert = dialog.create();
        alert.show();
    }

    // error not connected to internet
    public Boolean isConnected(Bundle extras)  {
        try {
            posts = new JSONArray(extras.getString("posts"));
            return true;
        }catch (Exception e){
            return false;
        }

    }
    public void getPosts(){
        RequestQueue queue = Volley.newRequestQueue(this);;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new dotEnv().URL_SERVER, response -> {
            isConnected = true;
            try {
                posts = new JSONArray(response);
                restart();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            createDialog(R.string.description_if_no_internet, this);
            isConnected = false;
        });
        queue.add(stringRequest);
    }

    // method for share app to your friends
    public void shareApp(){
        ImageView share = findViewById(R.id.buttonButton);
        share.setOnClickListener((View view) ->{
                Intent intent = new Intent(Intent.ACTION_SEND);
                String text = getResources().getString(R.string.shareing_text)+ "\n" + new dotEnv().LINK_APP_ON_GOOGLEPLAY;
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(intent);
        });
    }

    // methode to connect to recyclerview
    public void connectRecyclerView() throws JSONException {
        RecyclerView postsList = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.loading_home);
        List<ListItem> listOfPosts = new ArrayList<>();
        postsList.setHasFixedSize(true);
        postsList.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < new dotEnv().LINGTH_OF_POSTS; i++) {
            JSONObject post = posts.getJSONObject(i);
            listOfPosts.add(new ListItem(post.getString("image"),post.getString("title"), post.getString("description"), post.getString("date"), post.getString("link")));
        }
        progressBar.setVisibility(View.INVISIBLE);
        MyAdapture myAdapture = new MyAdapture(this, listOfPosts);
        postsList.setAdapter(myAdapture);
    }

    // admob method
    public void loadAd(AdView adView, Context context){
        MobileAds.initialize(context, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Log.i("admobAreLoaded", adError.toString());
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
    }
}