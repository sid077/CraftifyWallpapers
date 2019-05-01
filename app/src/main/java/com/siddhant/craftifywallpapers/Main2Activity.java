package com.siddhant.craftifywallpapers;

import android.Manifest;
import android.content.ActivityNotFoundException;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private  static int backCount=0;
    private String TAG = "MainActivity";
    private InterstitialAd interstitialAd;
    private  File localFile;
    private  String dQuery;

    static {





    }

    private static File localFile1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
        interstitialAd = new InterstitialAd(this);
        MobileAds.initialize(this,"ca-app-pub-2724635946881674~2482573510");
        interstitialAd.setAdUnitId("ca-app-pub-2724635946881674/7489990632");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        Intent i = getIntent();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("categories/category.txt");
        localFile = null;
        try {
            localFile = File.createTempFile("dynamics", "txt");
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    try {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));
                        dQuery =  reader.readLine();
                        reader.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace(); } }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        isStoragePermissionGranted();
        setContentView(R.layout.activity_main2);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        dQuery="egypt";
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),viewPager,dQuery);
        viewPager.setAdapter(pagerAdapter);
       // viewPager.setCurrentItem(3);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


                }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_final);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle((Html.fromHtml("Powered By <a title=\"Pexels\" href=\"https://www.pexels.com/\"> Pexels</a>")));
        navigationView = (NavigationView) findViewById(R.id.nav_view);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.rgb(255,255,255));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }


                        String token = task.getResult().getToken();


                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                    }
                });


    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(backCount == 0){
            Toast.makeText(getApplicationContext(), "Press again to exit.", Toast.LENGTH_SHORT).show();
            backCount++;
        }


         else  {
                 backCount = 0;
                 super.onBackPressed();

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        Switch switchAB = (Switch)menu.findItem(R.id.app_bar_switch)
                .getActionView().findViewById(R.id.switchId);

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                Intent i = new Intent(getApplicationContext(),WallpaperChangerService.class);

                if (isChecked) {
                    startWallpaperService(i);

                } else {

                    stopService(i);
                }
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog
            );

            View view = View.inflate(getApplicationContext(),R.layout.info_app,null);

           TextView textView = view.findViewById(R.id.info_logo_and_screen);
           textView.setText(Html.fromHtml("" +
                   "<div>Icons made by " +
                   "<a href=\"https://www.flaticon.com/authors/roundicons\" title=" +
                   "\"Roundicons\">Roundicons</a> from <a href=\"https://www.flaticon.com/\" " +
                   "title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creative" +
                   "commons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_bla" +
                   "nk\">CC 3.0 BY</a></div>\n"));
           alertDialog.setIcon(R.drawable.ic_launcher_foreground);
            alertDialog.setView(view);
            alertDialog.setTitle("Info : ");
            alertDialog.setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if(interstitialAd.isLoaded())
                    interstitialAd.show();

                }
            });


            alertDialog.setPositiveButtonIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_done_black_24dp));
            alertDialog.show();

            return true;
        } else if (id == R.id.app_bar_switch){


            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.rate_us) {
            Uri uri = Uri.parse("market://details?id="+getApplicationContext().getPackageName());
            Intent i = new Intent(Intent.ACTION_VIEW,uri);
            try {
                startActivity(i);
            }
            catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName())));
            }
        }
        else if (id == R.id.favourites){


          Intent i = new Intent(getApplicationContext(),FavouritesActivity.class);
          startActivity(i);

            }
            else if (id == R.id.share){
           Intent shareIntent = new Intent();
           shareIntent.setAction(Intent.ACTION_SEND);
           shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           shareIntent.setType("text/plain");
           shareIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.siddhant.craftifywallpapers");
           startActivity(shareIntent);
        }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startWallpaperService(Intent i) {


        BottomSheetSlideShow bottomSheetSlideShow =new BottomSheetSlideShow();
        bottomSheetSlideShow.show(getSupportFragmentManager(),"duration");



    }
    @Override
    protected void onResume() {
        super.onResume();
        backCount=0;

    }

    public void jumpToSite(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.pexels.com/"));
        startActivity(i);
    }
    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);

        }
    }

}
