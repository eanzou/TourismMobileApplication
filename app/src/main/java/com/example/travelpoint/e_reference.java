package com.example.travelpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class e_reference extends AppCompatActivity {

    AppCompatButton btn_back;
    TextView reference_img, reference_icon, reference_vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_reference_main);

        btn_back = findViewById(R.id.btn_back_source);
        reference_img = findViewById(R.id.reference_img);
        reference_icon = findViewById(R.id.reference_icon);
        reference_vid = findViewById(R.id.reference_vid);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reference_img.setText(R.string.reference_image);
        reference_icon.setText(R.string.reference_icon);
        reference_vid.setText(R.string.reference_video);

    }


}