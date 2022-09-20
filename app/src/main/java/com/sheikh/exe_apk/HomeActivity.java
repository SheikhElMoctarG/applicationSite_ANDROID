package com.sheikh.exe_apk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {
    JSONArray posts = null;
    MainActivity mainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        if (isConnected(extras)) {
            TextView textView = findViewById(R.id.textView);
            textView.setText("مرحبا الشيخ، انت لديك اتصالا بالانترنت.");
        } else {
            createDialog(getResources().getString(R.string.description_if_no_internet));
        }
    }
    // create new a dialog window
    public void createDialog(String description){
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setMessage(description);
        dialog.setCancelable(false);
        dialog.setPositiveButton(getResources().getString(R.string.tryagian), (dialogInterface, i) -> {
            dialogInterface.cancel();
            mainActivity.getPosts();
        });
        dialog.setNegativeButton(getResources().getString(R.string.button_exit), (dialogInterface, i) -> finish());
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
}