package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sheikh.exe_apk.Adapter.MyAdapture;
import com.sheikh.exe_apk.Model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    }
    // restart the info after again
    public void restart(){
        if (isConnected){

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

    // method for share app to your friends
    public void shareApp(){
        ImageView share = findViewById(R.id.buttonShare);
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
        List<ListItem> listOfPosts = new ArrayList<>();
        postsList.setHasFixedSize(true);
        postsList.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 5; i++) {
            JSONObject post = posts.getJSONObject(i);
            listOfPosts.add(new ListItem(post.getString("title")));
        }
        MyAdapture myAdapture = new MyAdapture(this, listOfPosts);
        postsList.setAdapter(myAdapture);
    }
}