package com.siddhant.craftifywallpapers;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
    private   ArrayList<String> favourites, favouriteFileName;
    private TextView textViewEmpty;
    private AdView adview;
    private static final String TEST_AD_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";

    @Override
    protected void onResume() {
        super.onResume();
        favourites = new ArrayList<>();
        favouriteFileName = new ArrayList<>();
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + getPackageResourcePath()+"/";
            File f = new File(path );
            if (f.isDirectory()) {
                File[] files;
                files = f.listFiles();

                for (int j = 0; j < files.length; j++) {
                    favouriteFileName.add(files[j].getName());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[j])));
                    favourites.add(reader.readLine());
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.recyclerViewFavourites);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.setVisibility(View.VISIBLE);

            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WallpaperFavouritesRecyclerAdapter adapter = new WallpaperFavouritesRecyclerAdapter(this,favourites,getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_favourites);
        Toolbar toolbar = findViewById(R.id.my_toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle((Html.fromHtml("Powered By <a title=\"Pexels\" href=\"https://www.pexels.com/\"> Pexels</a>")));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        adview =findViewById(R.id.adViewPreview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        favourites = new ArrayList<>();
        favouriteFileName = new ArrayList<>();
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + getPackageResourcePath()+"/";
            File f = new File(path );
            if (f.isDirectory()) {
                File[] files;
                files = f.listFiles();

                for (int j = 0; j < files.length; j++) {
                    favouriteFileName.add(files[j].getName());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[j])));
                    favourites.add(reader.readLine());
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    public void jumpToSite(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.pexels.com/"));
        startActivity(i);
    }
}
