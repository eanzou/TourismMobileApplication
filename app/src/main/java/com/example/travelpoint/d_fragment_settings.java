package com.example.travelpoint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class d_fragment_settings extends Fragment{

    TextView setting_username;
    LinearLayout btn_user, btn_pass, btn_delete, btn_privacy, btn_source;
    AppCompatButton btn_logout;
    z_DatabaseHelper myDB;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_fragment_settings_main, container, false);

        myDB = new z_DatabaseHelper(getActivity());

        setting_username = view.findViewById(R.id.setting_username);
        btn_user = view.findViewById(R.id.btn_edit_username);
        btn_pass = view.findViewById(R.id.btn_change_pass);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_privacy = view.findViewById(R.id.btn_privacy);
        btn_source = view.findViewById(R.id.btn_source);
        btn_logout = view.findViewById(R.id.btn_logout);

        SharedPreferences preferencesUsername = getActivity().getSharedPreferences(EMAIL, Context.MODE_PRIVATE);
        String username = preferencesUsername.getString("username", "");
        setting_username.setText(username); //set text

        //edit username
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (getActivity(), e_updateUsername.class);
                startActivity(intent);
            }
        });

        //change password
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (getActivity(), e_updatePassword.class);
                startActivity(intent);
            }
        });

        //delete account
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (getActivity(), e_delete_acc.class);
                startActivity(intent);
            }
        });

        //privacy policy
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (getActivity(), e_privacy_policy.class);
                startActivity(intent);
            }
        });

        //implicit intent to website
        btn_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (getActivity(), e_reference.class);
                startActivity(intent);
            }
        });

        //logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.putString("username", "");
                editor.apply();

                Intent intent = new Intent(getActivity(), b_login_page.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        return view;
    }
}
