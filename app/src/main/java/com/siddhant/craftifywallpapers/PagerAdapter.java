package com.siddhant.craftifywallpapers;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.ViewGroup;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private ViewPager viewPager ;
    private FragmentManager fragmentManager;
    private String dynamicQuery;


    public PagerAdapter(FragmentManager fm, ViewPager viewPager, String dQuery) {
        super(fm);
        fragmentManager = fm;
        this.viewPager = viewPager;
        this.dynamicQuery = dQuery;
        int i = 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);

    }



    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return GalleryListFragment.getInstance(dynamicQuery);
            case 1:

                return GalleryListFragment.getInstance("top%20rated");
            case 2:

                return GalleryListFragment.getInstance("abstract");
            case 3:

                return GalleryListFragment.getInstance("Art");
            case 4:

                return GalleryListFragment.getInstance("Architecture");
            case 5:

            return GalleryListFragment.getInstance("Black%20and%20white");
            case 6:

                return GalleryListFragment.getInstance("bikes");
            case 7:

                return GalleryListFragment.getInstance("cars");
            case 8:

                return GalleryListFragment.getInstance("city");
            case 9:

            return GalleryListFragment.getInstance("construction");
            case 10:

                return GalleryListFragment.getInstance("creative");
            case 11:

            return GalleryListFragment.getInstance("dark");
            case 12:

            return GalleryListFragment.getInstance("desert");
            case 13:

            return GalleryListFragment.getInstance("fire");
            case 14:

                return GalleryListFragment.getInstance("forest");
            case 15:

                return GalleryListFragment.getInstance("garden");
            case 16:

                return GalleryListFragment.getInstance("material%20design");
            case 17:

                return GalleryListFragment.getInstance("green");
            case 18:

                return GalleryListFragment.getInstance("god");
            case 19:

                return GalleryListFragment.getInstance("house");
            case 20:

                return GalleryListFragment.getInstance("nature");
            case 21:

                return GalleryListFragment.getInstance("start");
            case 22:

                return GalleryListFragment.getInstance("summer");
            case 23:

                return GalleryListFragment.getInstance("trees");
            case 24:

                return GalleryListFragment.getInstance("wall");
            case 25:

                return GalleryListFragment.getInstance("winter");

            case 26:
                return GalleryListFragment.getInstance("quotes");

            default:
                 return GalleryListFragment.getInstance("fire");

        }



    }

    @Override
    public int getCount() {
        return 27;
    }
}
