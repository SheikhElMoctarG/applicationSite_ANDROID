package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    ImageView logo;
    TextView name;
    JSONArray posts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    public void getPosts(){
        RequestQueue queue = Volley.newRequestQueue(this);;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new dotEnv().URL_SERVER, response -> {
            try {
                posts = new JSONArray(response);
                for(int i=0; i<= posts.length()-1; i++){
                    Log.i("posts", posts.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(stringRequest);
    }
}