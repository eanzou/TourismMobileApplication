package com.example.travelpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class e_updatePassword extends AppCompatActivity {

    EditText current_pass, new_pass, new_repass;
    AppCompatButton confirm_change, btn_back;
    z_DatabaseHelper Mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_update_password);

        current_pass = findViewById(R.id.current_pass);
        new_pass = findViewById(R.id.new_pass);
        new_repass = findViewById(R.id.new_repass);
        confirm_change = findViewById(R.id.btn_confirm_pass);
        btn_back = findViewById(R.id.btn_back_pass);

        Mydb = new z_DatabaseHelper(this);

        confirm_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Read the user's input
                String current_pw = current_pass.getText().toString();
                String new_pw = new_pass.getText().toString();
                String confirmation_pw = new_repass.getText().toString();

                SharedPreferences preferencesUser = getSharedPreferences(EMAIL, MODE_PRIVATE);
                String username = preferencesUser.getString("username", "");

                //Condition check on user's input
                if (current_pw.equals("") || new_pw.equals("") || confirmation_pw.equals("")) {
                    Toast.makeText(e_updatePassword.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (new_pw.equals(current_pw)) {
                    Toast.makeText(e_updatePassword.this, "New password cannot be the same as current password", Toast.LENGTH_SHORT).show();
                }
                else if (!new_pw.equals(confirmation_pw)) {
                    Toast.makeText(e_updatePassword.this, "New password does not match with the Confirmation password", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Use UserDatabaseHelper class to authenticate user current password
                    Boolean authenticateUser = Mydb.authenticateUser(username, current_pw);

                    if (!authenticateUser) {
                        Toast.makeText(e_updatePassword.this, "Invalid current password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean updatePw = Mydb.updatePassword(username, new_pw);

                        if (updatePw) {
                            Toast.makeText(e_updatePassword.this, "Password successfully is updated!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(e_updatePassword.this, "Fail to update password", Toast.LENGTH_SHORT).show();
                        }
                    }
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