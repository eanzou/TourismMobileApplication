package com.example.travelpoint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class e_delete_acc extends AppCompatActivity {

    EditText con_pass;
    AppCompatButton confirm_del, btn_back;
    private z_DatabaseHelper Mydb;
    String username;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_delete_acc);

        con_pass = findViewById(R.id.pass_con);
        confirm_del = findViewById(R.id.btn_confirm_delete);
        btn_back = findViewById(R.id.btn_back_del);
        Mydb = new z_DatabaseHelper(this);


        SharedPreferences preferencesusername = getSharedPreferences(EMAIL, MODE_PRIVATE);
        username = preferencesusername.getString("username", "");


        confirm_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = con_pass.getText().toString();

                if (pass.equals(""))
                    Toast.makeText(e_delete_acc.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = Mydb.checkusernamepassword(username, pass);

                    if (checkuserpass == true) {

                        deleteAccountAlertDialog();

                    } else
                        Toast.makeText(e_delete_acc.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
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

    public void deleteAccountAlertDialog() {

        builder = new AlertDialog.Builder(e_delete_acc.this);

        //Setting message manually and performing action on button click
        builder.setMessage("Are you sure you want to delete your account?").setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Account Deletion Canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Boolean deleteAccount = Mydb.deleteAcc(username);

                        if (deleteAccount) {

                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("remember", "false");
                            editor.apply();

                            Intent intent = new Intent(e_delete_acc.this, b_login_page.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(e_delete_acc.this, "Account deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(e_delete_acc.this, "Failed to delete your account. Please try again.", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(),"Account Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Account Deletion Conformation");
        alert.show();
    }
}