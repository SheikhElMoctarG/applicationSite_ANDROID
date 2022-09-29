package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sheikh.exe_apk.Adapter.MyAdapture;
import com.sheikh.exe_apk.Model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ImageView logo;
    TextView name;
    JSONArray posts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for desible the auto dork mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        logo = findViewById(R.id.logo);
        name = findViewById(R.id.text_logo);
        logo.setImageResource(R.drawable.logo_e);
        getPosts();
        new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("posts", (posts == null) ? null : posts.toString());
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
        }, 10000);
        // change the status bar color
        changeSBarColor(R.color.white, this);
    }

    public void getPosts(){
        RequestQueue queue = Volley.newRequestQueue(this);;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new dotEnv().URL_SERVER, response -> {
            try {
                posts = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(stringRequest);
    }
    // method to change the status bar android
    public void changeSBarColor(int color, Activity activity){
        if (Build.VERSION.SDK_INT >= 21){
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }

}