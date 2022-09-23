package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {
    JSONArray posts = null;
    Boolean isConnected ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        se
        Bundle extras = getIntent().getExtras();
        if (isConnected(extras)) {
            TextView textView = findViewById(R.id.textView);
            textView.setText("مرحبا الشيخ، انت لديك اتصالا بالانترنت.");
        } else {
            createDialog(R.string.description_if_no_internet, HomeActivity.this);
        }
    }
    // restart the info after agian
    public void restart(){
        if (isConnected){
            TextView textView = findViewById(R.id.textView);
            textView.setText("مرحبا الشيخ، انت لديك اتصالا بالانترنت.");
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
                for(int i=0; i<= posts.length()-1; i++){
                    Log.i("posts", posts.getString(i));
                }
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
}