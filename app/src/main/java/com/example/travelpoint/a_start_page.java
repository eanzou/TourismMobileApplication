package com.example.travelpoint;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

public class a_start_page extends AppCompatActivity {

    int loading_time = 4000;
    //4000 = 4 seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_start_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent = navigation or transition from one screen to another
                Intent home = new Intent(a_start_page.this, b_login_page.class);
                //from this start activity to that login activity
                startActivity(home);
                finish();
            }
        }, loading_time);

    }
}
