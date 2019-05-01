package com.siddhant.craftifywallpapers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BottomSheetDetails extends BottomSheetDialogFragment {
   private TextView photoUrl,photographerName,photographerUrl;
    private ArrayList <WallpaperJsonDetails>wallpaperJsonDetails;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet,container,false);
        photoUrl = rootView.findViewById(R.id.photo_url);
        photographerName = rootView.findViewById(R.id.photographer_name);
        photographerUrl = rootView.findViewById(R.id.photographer_url);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        int i = bundle.getInt("position");
        wallpaperJsonDetails = (ArrayList<WallpaperJsonDetails>) bundle.getSerializable("wallpaperDetails");
        photoUrl.setText(wallpaperJsonDetails.get(i).getImageUrl());
        photographerName.setText(wallpaperJsonDetails.get(i).getPhotographerName());
        photographerUrl.setText(wallpaperJsonDetails.get(i).getPhotoGrapherUrl());


    }
}
