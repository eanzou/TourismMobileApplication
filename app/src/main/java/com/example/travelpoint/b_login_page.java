package com.example.travelpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class b_login_page extends AppCompatActivity {

    EditText username, password;
    Button login, signup;
    CheckBox remember;
    z_DatabaseHelper myDB;
    Boolean rememberMe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_login_main);

        username = findViewById(R.id.b_username);
        password = findViewById(R.id.b_password);
        signup = findViewById(R.id.b_toSignup);
        login = findViewById(R.id.b_btnLogin);
        remember = findViewById(R.id.b_remember);
        myDB = new z_DatabaseHelper(this);

        //Share the value of REMEMBER ME upon user log in
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        //If checked, user no need to log in again next time unless is logged out
        if(checkbox.equals("true")) {
            Intent intent = new Intent(b_login_page.this, d_home_page.class);
            startActivity(intent);
            finish();
        }
        else if (checkbox.equals("false")) {
            Toast.makeText(this, "Please log in.", Toast.LENGTH_SHORT).show();

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                check(user, pass);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (b_login_page.this, c_signup_page.class);
                startActivity(intent);
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked()) {
                    rememberMe = true;

                }
                else if (!compoundButton.isChecked()) {
                    rememberMe = false;
                }
            }
        });

    }

    public void check (String user, String pass) {
        if (user.equals("") || pass.equals(""))
            Toast.makeText(b_login_page.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        else {
            Boolean checkuserpass = myDB.checkusernamepassword(user, pass);
            if (checkuserpass == true) {

                sharepreference_remember();
                sharepreference_username(user);

                Toast.makeText(b_login_page.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(b_login_page.this, d_home_page.class);
                startActivity(intent);
                finish();

            } else
                Toast.makeText(b_login_page.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

        }
    }

    private void sharepreference_remember()
    {
        SharedPreferences.Editor preferences_remember = getSharedPreferences("checkbox", MODE_PRIVATE).edit();

        //Setting the shared value -- "remember"
        if (rememberMe)
            preferences_remember.putString("remember", "true");
        else
            preferences_remember.putString("remember", "false");

        preferences_remember.apply();
    }

    private void sharepreference_username(String user)
    {
        SharedPreferences.Editor preferences_user = getSharedPreferences(EMAIL, MODE_PRIVATE).edit();
        preferences_user.putString("username", user);
        preferences_user.apply();
    }
}

