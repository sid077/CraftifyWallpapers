package com.siddhant.craftifywallpapers;


import android.content.Intent;

import android.net.Uri;
import android.os.Environment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

import java.util.ArrayList;



public class PreviewActivity extends AppCompatActivity {


    private int position;
    private InterstitialAd interstitialAd;
    private ViewPager previewViewPager;
    private boolean FULLSCREEN;
    private FloatingActionButton setWallpaper;
    private ArrayList<WallpaperJsonDetails> wallpaperJsonDetails;
    private ProgressBar progressBar;
    private String status;
    private AdView adview;
    private static final String TEST_AD_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview_activity_menu,menu);
        if(wallpaperJsonDetails.get(previewViewPager.getCurrentItem()).getFav()){
           MenuItem item = menu.findItem(R.id.fav_menu);
           item.setIcon(R.drawable.ic_favorite_black_selected_24dp);
        }
        Intent i = getIntent();
        status = "on";
        status = i.getStringExtra("status");
        if (status.equalsIgnoreCase("off")){
            menu.setGroupVisible(0,false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fav_menu:
                if(wallpaperJsonDetails.get(previewViewPager.getCurrentItem()).getFav()){
                    item.setIcon(R.drawable.fav_selector);
                    wallpaperJsonDetails.get(previewViewPager.getCurrentItem()).setFav(false);
                    String path = Environment.getExternalStorageDirectory()+getPackageResourcePath()+File.separator;
                    PerformFav.removeOneFromFavList(wallpaperJsonDetails,previewViewPager.getCurrentItem(), new File(path));
                }
                else {
                    item.setIcon(R.drawable.ic_favorite_black_selected_24dp);
                    wallpaperJsonDetails.get(previewViewPager.getCurrentItem()).setFav(true);
                    String path = Environment.getExternalStorageDirectory()+getPackageResourcePath()+File.separator;
                    PerformFav.addOneFavToList(wallpaperJsonDetails,previewViewPager.getCurrentItem(), new File(path));

                }
                break;
            case R.id.info_menu:
                BottomSheetDetails bottomSheetDetails = new BottomSheetDetails();
                Bundle args = new Bundle();
                args.putInt("position",previewViewPager.getCurrentItem());
                args.putSerializable("wallpaperDetails",wallpaperJsonDetails);
                bottomSheetDetails.setArguments(args);
                bottomSheetDetails.show(getSupportFragmentManager(),"details");
                break;
            default:
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Intent i = getIntent();
        status = i.getStringExtra("status");

        adview =findViewById(R.id.adViewPreview);
//        adview.setAdUnitId(TEST_AD_BANNER_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle((Html.fromHtml("Powered By <a title=\"Pexels\" href=\"https://www.pexels.com/\"> Pexels</a>")));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setTitle(R.string.nav_header_title);
        position = i.getIntExtra("position",0);
        wallpaperJsonDetails = (ArrayList<WallpaperJsonDetails>) i.getSerializableExtra("wallpaperDetails");
        progressBar = findViewById(R.id.progressBar3);
        previewViewPager = findViewById(R.id.preview_frame_viewpager);
        final PreviewPagerAdapter previewPagerAdapter = new PreviewPagerAdapter(getSupportFragmentManager(),position,wallpaperJsonDetails,status);
        previewViewPager.setAdapter(previewPagerAdapter);
        previewViewPager.setCurrentItem(position);


        FULLSCREEN= false;

        setWallpaper = findViewById(R.id.setWallpaper);
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                int i = previewViewPager.getCurrentItem();
                args.putInt("pos",i);
                args.putSerializable("wallpaperDetails",wallpaperJsonDetails);
                args.putString("status",status);
                BottomSheetWalpaperType bottomSheetWalpaperType = new BottomSheetWalpaperType();
                bottomSheetWalpaperType.setArguments(args);
                bottomSheetWalpaperType.show(getSupportFragmentManager(),"hola");


            }
        });





    }
    public void jumpToSite(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.pexels.com/"));
        startActivity(i);
    }
}
