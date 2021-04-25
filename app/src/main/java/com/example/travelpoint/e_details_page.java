package com.example.travelpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class e_details_page extends AppCompatActivity {

    ImageView e_image;
    TextView e_picname, e_descrip;
    AppCompatButton learnmore, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_details_main);

        btn_back = findViewById(R.id.btn_back_details);
        e_image = findViewById(R.id.e_image);
        e_picname = findViewById(R.id.e_picname);
        e_descrip = findViewById(R.id.e_descrip);
        learnmore = findViewById(R.id.learn_more);

        z_AccessDatabaseHelper databaseaccess = z_AccessDatabaseHelper.getInstance(this);
        databaseaccess.open();

        Intent intent = getIntent();
        int chosenInt = intent.getIntExtra("id", 0);

        ByteArrayInputStream current_img = databaseaccess.get_image(chosenInt); //get image
        Bitmap bitmap = BitmapFactory.decodeStream(current_img); //convert image to bitmap

        String current_name = databaseaccess.get_name(chosenInt); //get title
        String current_des = databaseaccess.get_longdes(chosenInt); //get description
        String current_url = databaseaccess.get_link(chosenInt);

        e_image.setBackground(new BitmapDrawable(getResources(), bitmap)); //set bitmap as background
        e_picname.setText(current_name);
        e_descrip.setText(current_des);

        databaseaccess.close();

        //here got problrm
        learnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(current_url));
                startActivity(intent);
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