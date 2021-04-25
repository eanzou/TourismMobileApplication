package com.example.travelpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.sql.Clob;

public class d_fragment_home extends Fragment implements View.OnClickListener{

    LinearLayout highlight[] = new LinearLayout[3];
    ImageButton btn_states[] = new ImageButton[4]; //KL, PG, KD, SB

    ImageView image[] = new ImageView[3];
    TextView image_name[] = new TextView[3];
    TextView image_des[] = new TextView[3];

    int random_num[] = new int[3];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_fragment_home_main, container, false);

        highlight[0] = view.findViewById(R.id.highlight1);
        highlight[1] = view.findViewById(R.id.highlight2);
        highlight[2] = view.findViewById(R.id.highlight3);

        for (int i=0; i<3; i++)
            highlight[i].setOnClickListener(this);

        btn_states[0] = view.findViewById(R.id.btn_kl);
        btn_states[1] = view.findViewById(R.id.btn_pg);
        btn_states[2] = view.findViewById(R.id.btn_kd);
        btn_states[3] = view.findViewById(R.id.btn_sb);

        for (int i=0; i<4; i++) {
            btn_states[i].setOnClickListener(this);
            btn_states[i].setColorFilter(Color.argb(255, 255, 255, 255));
        }

        //today's highlights details
        image[0] = view.findViewById(R.id.d_image1);
        image[1] = view.findViewById(R.id.d_image2);
        image[2] = view.findViewById(R.id.d_image3);
        image_name[0] = view.findViewById(R.id.d_imgname1);
        image_name[1] = view.findViewById(R.id.d_imgname2);
        image_name[2] = view.findViewById(R.id.d_imgname3);
        image_des[0] = view.findViewById(R.id.d_imgdes1);
        image_des[1] = view.findViewById(R.id.d_imgdes2);
        image_des[2] = view.findViewById(R.id.d_imgdes3);

        int max = 24, min = 1; //amount of data in database
        int previous = -1;

        z_AccessDatabaseHelper databaseaccess = z_AccessDatabaseHelper.getInstance(getActivity());
        databaseaccess.open();

        for (int i=0; i<3; i++)
        {
            //get random numbers
            do {
                random_num[i] = (int) (Math.random() * (max - min + 1) + min);
            } while (previous == random_num[i]); //if this number is same as previous, loop again to get new number

            String current_name = databaseaccess.get_name(random_num[i]); //get title
            String current_des = databaseaccess.get_shortdes(random_num[i]); //get description
            ByteArrayInputStream current_img = databaseaccess.get_image(random_num[i]); //get image
            Bitmap bitmap = BitmapFactory.decodeStream(current_img); //convert image to bitmap

            image_name[i].setText(current_name);
            image_des[i].setText(current_des);
            image[i].setBackground(new BitmapDrawable(getResources(), bitmap)); //set bitmap as background

            previous = random_num[i]; //let previous be this number
        }

        databaseaccess.close();

        return view;

    }

    @Override
    public void onClick(View v) {

        for (int i=0; i<3; i++)
            if (v.equals(highlight[i]))
            {
                Intent intent = new Intent(getActivity(), e_details_page.class);
                intent.putExtra("id", random_num[i]);
                startActivity(intent);
                break;
            }

        for (int i=0; i<4; i++)
            if (v.equals(btn_states[i]))
            {
                Intent intent = new Intent(getActivity(), d_explore_focus.class);

                if (i==0)
                    intent.putExtra("state", "Kuala Lumpur");
                else if (i==1)
                    intent.putExtra("state", "Penang");
                else if (i==2)
                    intent.putExtra("state", "Kedah");
                else
                    intent.putExtra("state", "Sabah");

                startActivity(intent);
                break;
            }
    }
}
