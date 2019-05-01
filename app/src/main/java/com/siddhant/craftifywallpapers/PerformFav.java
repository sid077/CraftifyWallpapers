package com.siddhant.craftifywallpapers;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;




public class PerformFav {
    private static ArrayList<String> favFile = new ArrayList<>();
    private static ArrayList<String> fav = new ArrayList<>();
    public static void readAllFav(final File path){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    File f = new File(path + "/");
                    if (f.isDirectory()) {
                        File[] files ;
                        files = f.listFiles();

                        for (int j = 0; j < files.length; j++) {
                            favFile.add(files[j].getName());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[j])));
                            fav.add(reader.readLine());
                        }

                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public static void removeOneFromFavList(final ArrayList<WallpaperJsonDetails> wallpaperJsonDetails, final int position, final File path){
        wallpaperJsonDetails.get(position).setFav(false);
        fav.remove(wallpaperJsonDetails.get(position).getImageStringPortrait());
        favFile.remove(String.valueOf(wallpaperJsonDetails.get(position).getId()));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File[] files = path.listFiles();
                    for (int j = 0; j < files.length; j++) {
                        if (files[j].getName().equalsIgnoreCase(String.valueOf(wallpaperJsonDetails.get(position).getId()))) {
                            files[j].delete();
                            }
                    }

                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
                catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }



            }
        });
        thread.start();

    }
    public static void addOneFavToList(final ArrayList<WallpaperJsonDetails> wallpaperJsonDetails, final int position, final File path){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                path.mkdirs();
                File file = new File(path+"/"+wallpaperJsonDetails.get(position).getId());
                wallpaperJsonDetails.get(position).setFav(true);
                fav.add(wallpaperJsonDetails.get(position).getImageStringPortrait());
                favFile.add(String.valueOf(wallpaperJsonDetails.get(position).getId()));
                if (!file.exists()) {
                    //file.mkdirs();
                    try {
                        file.createNewFile();
                        FileWriter writer = new FileWriter(file);
                        writer.write(wallpaperJsonDetails.get(position).getImageStringPortrait());
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        thread.start();


    }
   public static void delFileFromFav(final String id, final File path){
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   File[] files = path.listFiles();
                   for (int j = 0; j < files.length; j++) {
                      if(files[j].getName().equalsIgnoreCase(id)) {
                          files[j].delete();
                          favFile.remove(files[j].getName());
                      }
                   }

               }
               catch (NullPointerException e){
                   e.printStackTrace();
               }
               catch (ArrayIndexOutOfBoundsException e){
                   e.printStackTrace();
               }



           }
       });
       thread.start();


   }




    public static ArrayList<String> getFavFile() {
        return favFile;
    }

    public static void setFavFile(ArrayList<String> favFile) {
        PerformFav.favFile = favFile;
    }

    public static ArrayList<String> getFav() {
        return fav;
    }

    public static void setFav(ArrayList<String> fav) {
        PerformFav.fav = fav;
    }
}
