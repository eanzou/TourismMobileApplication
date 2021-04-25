package com.example.travelpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class d_fragment_explore extends Fragment implements View.OnClickListener{
    GridView listview[] = new GridView[4];
    LinearLayout focusclick[] = new LinearLayout[4];
    LinearLayout klclick, kdclick, pgclick, sbclick;
    TextView state1, state2, state3, state4;
    int id_all [][] = new int[4][2];

    String current_state[] = new String [4];
    String img_name[];
    String img_des[];
    Bitmap imgs[];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_fragment_explore_main, container, false);

        listview[0] = view.findViewById(R.id.d_list);
        listview[1] = view.findViewById(R.id.d_list2);
        listview[2] = view.findViewById(R.id.d_list3);
        listview[3] = view.findViewById(R.id.d_list4);

        focusclick[0] = view.findViewById(R.id.kl_click);
        focusclick[1] = view.findViewById(R.id.kedah_click);
        focusclick[2] = view.findViewById(R.id.pg_click);
        focusclick[3] = view.findViewById(R.id.sb_click);
        for (int i=0; i<4; i++)
            focusclick[i].setOnClickListener(this);

        state1 = view.findViewById(R.id.state1);
        state2 = view.findViewById(R.id.state2);
        state3 = view.findViewById(R.id.state3);
        state4 = view.findViewById(R.id.state4);

        //obtain state string
        current_state[0] = (String) state1.getText();
        current_state[1] = (String) state2.getText();
        current_state[2] = (String) state3.getText();
        current_state[3] = (String) state4.getText();

        ArrayList<String> current_id;

        for (int i=0; i<4; i++)
        {
            z_AccessDatabaseHelper databaseaccess = z_AccessDatabaseHelper.getInstance(getActivity());
            databaseaccess.open();

            current_id = new ArrayList<>();
            current_id = databaseaccess.get_id(current_state[i]); //get id in string form

            int size = current_id.size();
            img_name = new String [size];
            img_des = new String [size];
            imgs = new Bitmap [size];

            for (int j=0; j<size; j++) {
                id_all [i][j] = Integer.parseInt(current_id.get(j));

                img_name[j] = databaseaccess.get_name(id_all [i][j]);
                img_des[j] = databaseaccess.get_state(id_all [i][j]);
                ByteArrayInputStream current_img = databaseaccess.get_image(id_all [i][j]); //get image
                imgs[j] = BitmapFactory.decodeStream(current_img); //convert image to bitmap
                if (j==1)
                    break; //only return 2 of them is enough
            }

            databaseaccess.close();

            MyAdapter adapter = new MyAdapter(getActivity(), img_name, img_des, imgs);
            listview[i].setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onClick(View v) {

        for (int i=0; i<4; i++)
            if (v.equals(focusclick[i]))
            {
                Intent intent = new Intent(getActivity(), d_explore_focus.class);

                if (i==0)
                    intent.putExtra("state", "Kuala Lumpur");
                else if (i==1)
                    intent.putExtra("state", "Kedah");
                else if (i==2)
                    intent.putExtra("state", "Penang");
                else
                    intent.putExtra("state", "Sabah");

                startActivity(intent);
                break;
            }
    }

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String d_title[];
        String d_des[];
        Bitmap d_img[];

        MyAdapter (Context context, String d_title[], String d_des[], Bitmap d_img[])
        {
            super(context, R.layout.d_list_row, R.id.d_des_name, d_title); //img title des
            this.context = context;
            this.d_title = d_title;
            this.d_des = d_des;
            this.d_img = d_img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.d_list_row, parent, false);
            ImageView images = row.findViewById(R.id.d_list_img);
            TextView mytitle = row.findViewById(R.id.d_des_name);
            TextView myloc = row.findViewById(R.id.d_img_loc);

            images.setBackground(new BitmapDrawable(getResources(), d_img[position]));;
            mytitle.setText(d_title[position]);
            myloc.setText(d_des[position]);

            z_AccessDatabaseHelper databaseaccess = z_AccessDatabaseHelper.getInstance(getActivity());
            databaseaccess.open();
            String current_id = databaseaccess.get_id_by_name(d_title[position]);
            int current_id_int = Integer.parseInt(current_id);
            databaseaccess.close();

            LinearLayout button_click = row.findViewById(R.id.column_click);

            button_click.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), e_details_page.class);
                    intent.putExtra("id", current_id_int);
                    startActivity(intent);
                }
            });

            return row;
        }
    }
}
