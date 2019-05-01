package com.siddhant.craftifywallpapers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class WelcomeScreen extends AppCompatActivity {
    private ProgressBar progressBar;
   private int progress = 1;
    private String dQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getWindow().addFlags(View.SYSTEM_UI_FLAG_FULLSCREEN | View.STATUS_BAR_HIDDEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(2);



        new Thread(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.putExtra("dynamicQuery",dQuery);

                while (progress < 3) {

                    progressBar.setProgress(progress);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress++;

                }
                startActivity(i);
                finish();

            }
        }).start();


    }
}
