package com.siddhant.craftifywallpapers;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class BottomSheetWalpaperType extends BottomSheetDialogFragment {

   private FloatingActionButton portraitFAB, landscapeFAB, freeStyleFAB;
   private int pos;
   private ViewPager viewPager;
    private ArrayList<WallpaperJsonDetails> wallpaperJsonDetails;
    private ProgressBar progressBar, roundProgressBar;
    private TextView portraitTextView,landScapeTextView;
   private ProgressDialog progressDialog ;
    private String status;
    private InterstitialAd interstitialAd;
    private static final String INTERSTITIAL_ID ="ca-app-pub-2724635946881674/2370892871";
    public static final String TEST_INT_ID="ca-app-pub-3940256099942544/1033173712";
    private AdView adview;


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallpaperselector,container,false);
        Bundle arg = getArguments();
        pos =  arg.getInt("pos");
        wallpaperJsonDetails = (ArrayList<WallpaperJsonDetails>) arg.getSerializable("wallpaperDetails");
        status = arg.getString("status");
        interstitialAd = new InterstitialAd(getActivity().getApplicationContext());
        interstitialAd.setAdUnitId(INTERSTITIAL_ID);
        adview =view.findViewById(R.id.adViewWallpaperSelector);
        interstitialAd.loadAd(new AdRequest.Builder().build());
        portraitFAB = view.findViewById(R.id.floatingActionButtonPortrait);
        landscapeFAB = view.findViewById(R.id.floatingActionButtonLandscape);
        freeStyleFAB = view.findViewById(R.id.floatingActionButtonFreestyle);
        progressBar = view.findViewById(R.id.horizontalProgressBar);
        portraitTextView = view.findViewById(R.id.textViewPortrait);
        landScapeTextView = view.findViewById(R.id.textViewLandscpe);
        progressDialog = new ProgressDialog(view.getContext());
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();

        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        if(status.equalsIgnoreCase("off")){
            landscapeFAB.setVisibility(View.GONE);
            portraitFAB.setVisibility(View.GONE);
            landScapeTextView.setVisibility(View.GONE);
            portraitTextView.setVisibility(View.GONE);
        }
        Point p = new Point();
        WindowManager windowManager = getActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(p);
       final int height = p.y, width = p.x;
       roundProgressBar = getActivity().findViewById(R.id.progressBar3);

        portraitFAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setAsWallpaper(0,width,height);


            }
        });
        landscapeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsWallpaper(1,0,0);


            }
        });

        freeStyleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsWallpaper(2,0,0);


            }
        });




    }
   private void setAsWallpaper(final int category, final int width, final int height){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        roundProgressBar.setVisibility(View.VISIBLE);

                    }
                });
                if(category == 0){
                    DownloadImage downloadImage = new DownloadImage(progressBar, roundProgressBar,getActivity().getApplicationContext());
                    try {

                        Bitmap wallpaperBitmap;
                        wallpaperBitmap = downloadImage.execute(wallpaperJsonDetails.get(pos).getImageStringPortrait()).get();
                        WallpaperManager manager = WallpaperManager.getInstance(getActivity().getApplicationContext());
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(wallpaperBitmap,width,height,false);
                        manager.setBitmap(scaledBitmap);
                        wallpaperBitmap.recycle();
                        scaledBitmap.recycle();
                        manager.forgetLoadedWallpaper();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if(category == 1){
                    DownloadImage downloadImage = new DownloadImage(progressBar, roundProgressBar,getActivity().getApplicationContext());
                    WallpaperManager manager = WallpaperManager.getInstance(getActivity().getApplicationContext());
                    try {
                        Bitmap wallpaperBitmap = downloadImage.execute(wallpaperJsonDetails.get(pos).getImageStringOriginal()).get();
                        manager.setBitmap(wallpaperBitmap);
                        manager.forgetLoadedWallpaper();
                        wallpaperBitmap.recycle();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    DownloadImage downloadImage = new DownloadImage(progressBar, roundProgressBar,getActivity().getApplicationContext());
                    WallpaperManager manager = WallpaperManager.getInstance(getActivity().getApplicationContext());
                    try {
                        Bitmap wallpaperBitmap = downloadImage.execute(wallpaperJsonDetails.get(pos).getImageStringPortrait()).get();
                        manager.setBitmap(wallpaperBitmap);
                        wallpaperBitmap.recycle();

                        manager.forgetLoadedWallpaper();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getActivity().getApplicationContext(),"Wallpaper Changed Successfully!",Toast.LENGTH_SHORT).show();
                   roundProgressBar.setVisibility(View.GONE);
                    progressBar.setProgress(100);
                    if(interstitialAd.isLoaded())
                    interstitialAd.show();
                    else Log.i("ad","not loaded");
                }
            });
            }

        });
        thread.start();

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (thread.isAlive()){
                        progressBar.incrementProgressBy(2);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    dismiss();
                }
            });
            th.start();


    }

}
