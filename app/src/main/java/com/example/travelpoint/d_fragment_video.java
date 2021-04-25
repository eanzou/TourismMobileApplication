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

public class d_fragment_video extends Fragment{

    YouTubePlayerView videoview, videoview2, videoview3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_fragment_video_main, container, false);

        videoview = view.findViewById(R.id.d_video);
        videoview2 = view.findViewById(R.id.d_video2);
        videoview3 = view.findViewById(R.id.d_video3);

        getLifecycle().addObserver(videoview);
        getLifecycle().addObserver(videoview2);
        getLifecycle().addObserver(videoview3);

        return view;
    }


}
