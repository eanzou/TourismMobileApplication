package com.example.travelpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class e_updateUsername extends AppCompatActivity {

    EditText new_user;
    AppCompatButton confirm_user, btn_back;
    private z_DatabaseHelper Mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_update_username);

        new_user = findViewById(R.id.new_user);
        confirm_user = findViewById(R.id.btn_confirm_user);
        btn_back = findViewById(R.id.btn_back_user);
        Mydb = new z_DatabaseHelper(this);


        SharedPreferences preferencesusername = getSharedPreferences(EMAIL, MODE_PRIVATE);
        String username = preferencesusername.getString("username", "");

        confirm_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_username = new_user.getText().toString();

                Boolean checkuser = Mydb.checkusername(new_username); //check to see username exist or not
                 if (checkuser)
                     Toast.makeText(e_updateUsername.this, "Username already exist.", Toast.LENGTH_SHORT).show();
                else {
                     Boolean updateProfile = Mydb.updateUsername(username, new_username);

                     if (updateProfile) {
                         Toast.makeText(e_updateUsername.this, "Username updated.", Toast.LENGTH_SHORT).show();

                         SharedPreferences.Editor editor = getSharedPreferences(EMAIL, MODE_PRIVATE).edit();
                         editor.putString("username", new_username);
                         editor.apply();
                     } else
                         Toast.makeText(e_updateUsername.this, "Failed to update username. ", Toast.LENGTH_SHORT).show();

                 }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}