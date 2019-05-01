package com.siddhant.craftifywallpapers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;



public class PreviewFragment extends Fragment {
   private ImageView previewImageView;
    private ImageButton imageButton;

    private ArrayList<WallpaperJsonDetails> wallpaperJsonDetails;
    private String status;



    private int position;
    private boolean fullscreen = false;

    public static PreviewFragment getInstance(int position, ArrayList<WallpaperJsonDetails> wallpaperJsonDetails, String status)  {
        PreviewFragment previewFragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        args.putSerializable("wallpaperDetails",wallpaperJsonDetails);
        args.putString("status",status);
        previewFragment.setArguments(args);

        position++;




        return previewFragment;
    }
    @Override
    public void onStart() {
        super.onStart();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_fragment,container,false);
        Bundle args = getArguments();
        position = args.getInt("position");
        status = args.getString("status");

        previewImageView = view.findViewById(R.id.imageView4);

        imageButton = view.findViewById(R.id.imageButton4);
        if(status.equalsIgnoreCase("off")){
        imageButton.setVisibility(View.GONE);
        }
        wallpaperJsonDetails = (ArrayList<WallpaperJsonDetails>) args.getSerializable("wallpaperDetails");

        Picasso.get().load(wallpaperJsonDetails.get(position).getImageStringPortrait()).into(previewImageView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fullscreen = false;
                if(!fullscreen){
                    fullscreen = true;
                    previewImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Picasso.get().load(wallpaperJsonDetails.get(position).getImageUrlLandscape()).into(previewImageView);

                }
                else {
                    fullscreen = false;
                    previewImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Picasso.get().load(wallpaperJsonDetails.get(position).getImageStringPortrait()).into(previewImageView);
                }
            }
        });

        return view;
    }
}

