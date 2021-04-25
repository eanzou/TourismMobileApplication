package com.example.travelpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class c_signup_page extends AppCompatActivity {

    EditText email, username, password, repassword;
    Button signup, login;
    z_DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_signup_main);

        email = findViewById(R.id.c_email);
        username = findViewById(R.id.c_username);
        password = findViewById(R.id.c_password);
        repassword = findViewById(R.id.c_repassword);
        signup = findViewById(R.id.c_btnSignup);
        login = findViewById(R.id.c_toLogin);
        myDB = new z_DatabaseHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                check(mail, user, pass, repass);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (c_signup_page.this, b_login_page.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void check(String mail, String user, String pass, String repass)
    {
        if (email.equals("")||user.equals("")||pass.equals("")||repass.equals(""))
            Toast.makeText(c_signup_page.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        else
        {
            if (pass.equals(repass)) {
                Boolean checkuser = myDB.checkusername(user);
                Boolean checkemail = myDB.checkemail(mail);
                if (checkuser == false && checkemail == false) //user does not exist
                {
                    Boolean insert = myDB.insertData(mail, user, pass);
                    if (insert == true)
                    {
                        Toast.makeText(c_signup_page.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (c_signup_page.this, b_login_page.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(c_signup_page.this, "Registered failed!", Toast.LENGTH_SHORT).show();
                }
                else if (checkuser == true)
                {
                    Toast.makeText(c_signup_page.this, "Username already exist", Toast.LENGTH_SHORT).show();
                }
                    else
                {
                    Toast.makeText(c_signup_page.this, "Email already used", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(c_signup_page.this, "Password and Repassword does not match", Toast.LENGTH_SHORT).show();

        }
    }
}