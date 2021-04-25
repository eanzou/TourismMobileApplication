package com.example.travelpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class d_explore_focus extends AppCompatActivity {

    GridView focusview;
    ArrayList<String> current_id;
    String img_name[];
    Bitmap img_img[];
    TextView state_name;
    AppCompatButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_explore_focus_main);

        Intent intent = getIntent();
        String chosenstate = intent.getStringExtra("state");

        state_name = findViewById(R.id.state_name);
        state_name.setText(chosenstate);
        focusview = findViewById(R.id.d_gridview);
        btn_back = findViewById(R.id.btn_back_focus);

        z_AccessDatabaseHelper databaseaccess = z_AccessDatabaseHelper.getInstance(this);
        databaseaccess.open();

        current_id = new ArrayList<>();
        current_id = databaseaccess.get_id(chosenstate); //get id in string form

        int size = current_id.size();
        img_name = new String [size];
        img_img = new Bitmap [size];

        for (int j=0; j<size; j++) {
            img_name[j] = databaseaccess.get_name(Integer.parseInt(current_id.get(j)));
            ByteArrayInputStream current_img = databaseaccess.get_image(Integer.parseInt(current_id.get(j))); //get image
            img_img[j] = BitmapFactory.decodeStream(current_img); //convert image to bitmap
        }

        databaseaccess.close();

        MyAdapter adapter = new MyAdapter(this, img_name, img_img);
        focusview.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String d_title[];
        Bitmap d_img[];

        MyAdapter (Context context, String d_title[], Bitmap d_img[])
        {
            super(context, R.layout.d_list_column, R.id.d_column_name, d_title); //img title des
            this.context = context;
            this.d_title = d_title;
            this.d_img = d_img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.d_list_column, parent, false);
            ImageView images = row.findViewById(R.id.d_column_img);
            TextView mytitle = row.findViewById(R.id.d_column_name);

            images.setBackground(new BitmapDrawable(getResources(), d_img[position]));;
            mytitle.setText(d_title[position]);

            z_AccessDatabaseHelper databaseaccess = z_AccessDatabaseHelper.getInstance(d_explore_focus.this);
            databaseaccess.open();
            String current_id = databaseaccess.get_id_by_name(d_title[position]);
            int current_id_int = Integer.parseInt(current_id);
            databaseaccess.close();

            LinearLayout button_click = row.findViewById(R.id.column_click2);

            button_click.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(d_explore_focus.this, e_details_page.class);
                    intent.putExtra("id", current_id_int);
                    startActivity(intent);
                }
            });

            return row;
        }
    }

}