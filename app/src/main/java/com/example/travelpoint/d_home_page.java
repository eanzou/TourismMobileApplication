package com.example.travelpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class d_home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_home_main);
        BottomNavigationView d_bottomnav = findViewById(R.id.d_bottomnav);
        d_bottomnav.setItemIconTintList(null); //remove the purple tint on icons
        d_bottomnav.setOnNavigationItemSelectedListener(d_navlistener);

        //when visit this page auto home
        getSupportFragmentManager().beginTransaction().replace
                (R.id.d_frame, new d_fragment_home()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener d_navlistener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.menu_home:
                    selectedFragment = new d_fragment_home();
                    break;

                case R.id.menu_video:
                    selectedFragment = new d_fragment_video();
                    break;

                case R.id.menu_explore:
                    selectedFragment = new d_fragment_explore();
                    break;

                case R.id.menu_settings:
                    selectedFragment = new d_fragment_settings();
                    break;
            }

            //change dframe into new fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.d_frame, selectedFragment).commit();
            return true;
        }
    };

}