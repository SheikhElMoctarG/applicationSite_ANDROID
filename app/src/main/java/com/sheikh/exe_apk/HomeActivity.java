package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {
    JSONArray posts = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                posts = new JSONArray(extras.getString("posts"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}