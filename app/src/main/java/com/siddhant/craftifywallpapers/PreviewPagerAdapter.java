package com.siddhant.craftifywallpapers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.IOException;
import java.util.ArrayList;



public class PreviewPagerAdapter extends FragmentStatePagerAdapter  {
    private int position;
    private ArrayList<WallpaperJsonDetails> wallpaperJsonDetails;
    private String status;







    public PreviewPagerAdapter(FragmentManager fm, int position, ArrayList<WallpaperJsonDetails> wallpaperJsonDetails, String status) {
        super(fm);
        this.position = position;
        this.wallpaperJsonDetails = wallpaperJsonDetails;
        this.status = status;

    }


    @Override
    public Fragment getItem(int i) {
        return PreviewFragment.getInstance(i,wallpaperJsonDetails,status);

    }

    @Override
    public int getCount() {
        return wallpaperJsonDetails.size();
    }
}
