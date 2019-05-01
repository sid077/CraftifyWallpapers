package com.siddhant.craftifywallpapers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class WallpaperChangerService extends Service {
    public Timer timer = null;
    Handler handler = new Handler();
    int id = 101;
    public static  int i = 0,interval;
    private ArrayList<String> url = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();




    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Toast.makeText(this, "Craftify wallpapers : stopping service...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        interval = intent.getIntExtra("interval",1);
        int intervalInMillis;
        intervalInMillis = interval*3600*1000;
        String name = "SID" ;
        String CHANNEL_ID = getPackageName();
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription("hello");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent notificationIntent = new Intent(this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setContentText("Wallpaper will change after an interval of "+String.valueOf(interval)+" hour(s).")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setColor(Color.BLACK)
                .setVisibility(1)

                .build();
        startForeground(1, notification);
        if (timer != null)
            timer.cancel();
        else
            timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimer(),0,intervalInMillis);





        return START_REDELIVER_INTENT;
    }
    class MyTimer extends TimerTask{
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        readUrlList();
                        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                        if(i!=40){
                            Bitmap wallpaper = new DownloadImage(null,null,null).execute(url.get(i)).get();
                            manager.setBitmap(wallpaper);
                            wallpaper.recycle();
                            manager.forgetLoadedWallpaper();
                            i++;
                        }
                        else {
                            i=0;
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error in finding the file,Plea" +
                                "se check the permissions!.\ncannot start the service.",Toast.LENGTH_SHORT).show();
                        stopSelf();

                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"Unable to start service!",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        stopSelf();

                    } catch (InterruptedException e) {
                        Toast.makeText(getApplicationContext(),"Unable to start service!",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        stopSelf();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Unable to start service!",Toast.LENGTH_SHORT).show();

                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Unable to start service!",Toast.LENGTH_SHORT).show();
                        stopSelf();

                    }
                }
            });
        }
    }
    private void readUrlList(){
        final File path = new File(Environment.getExternalStorageDirectory()+getApplicationContext().getPackageResourcePath()+
                File.separator);


                try {

                    File f = new File(path + "/slideshow");
                    if (f.exists()) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                        while (reader.ready()){
                            url.add(reader.readLine());
                        }
                        reader.close();


                    }

                }catch (NullPointerException e){
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

    }

}
