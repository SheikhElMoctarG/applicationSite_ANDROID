package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // for desible the auto dork mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // called method for connect to server and get html code from page.
        getData();
        // button back
        ImageView button = findViewById(R.id.buttonBack);
        buttonBack(button);
        // load ads
//        AdView adView = findViewById(R.id.adView); // ACTIVE THIS LINE TO ADD THE ADS
//        new HomeActivity().loadAd(adView, this); // ACTIVE THIS LINE TO ADD THE ADS
    }
    // method to get data of article
    public void getData(){
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        String title = extras.getString("title");
        String password = extras.getString("password");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new dotEnv().URL_SERVER + "/post", response -> {
            try {
                JSONObject res = new JSONObject(response);
                ProgressBar progressBar = findViewById(R.id.loading);
                progressBar.setVisibility(View.INVISIBLE);
                WebView content = findViewById(R.id.webView);
                content.loadData(addHtml(res.getString("html")),  "text/html; charset=utf-8","UTF-8");
                content.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
        }){
            // for add headers
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("url", url);
                headers.put("title", title);
                headers.put("authentication", password);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    // button for the back
    public void buttonBack(ImageView button){
        button.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    public String addHtml(String html){
        return "<div style='font-family: Cairo, sans-serif; direction: rtl;'>"+html+"</div>";
    }
}