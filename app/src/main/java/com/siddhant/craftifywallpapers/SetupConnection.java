package com.siddhant.craftifywallpapers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SetupConnection extends AsyncTask<String, Void, ArrayList<WallpaperJsonDetails>> {
    private String query="";
    private JSONObject jsonObject;
    Context context;
  public  ArrayList<WallpaperJsonDetails> wallpaperJsonDetailsArrayList = new ArrayList<>();





    public SetupConnection(Context context) {
        this.context = context;

    }

    private int page = 1;
    private WallpaperJsonDetails wallpaperJsonDetails;


    @Override
    protected ArrayList<WallpaperJsonDetails> doInBackground(String... strings) throws RuntimeException {

        query = strings[0];

        try {
            URL url = new URL(query+page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization","563492ad6f9170000100000114b600ee9f564948886cfa06f720417a ");
            String code = connection.getHeaderField("X-Ratelimit-Remaining");

            if(Integer.parseInt(code)<=0){
                return null;
            }

            Log.i("remaining requests:",code);
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine())!= null){
                buffer.append(line);
                }
            bufferedReader.close();
            jsonObject = new JSONObject(buffer.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("photos");
            connection.disconnect();;
            for (int i=0;i<40;i++){
                JSONObject object = jsonArray.getJSONObject(i);
                wallpaperJsonDetails= new WallpaperJsonDetails();
                wallpaperJsonDetails.setId(object.getInt("id"));
                wallpaperJsonDetails.setWidth(object.getInt("width"));
                wallpaperJsonDetails.setHeight(object.getInt("height"));
                wallpaperJsonDetails.setImageUrl(object.getString("url"));
                wallpaperJsonDetails.setPhotographerName(object.getString("photographer"));
                wallpaperJsonDetails.setPhotoGrapherString(object.getString("photographer_url"));
                JSONObject srcJsonObject = object.getJSONObject("src");
                wallpaperJsonDetails.setImageStringOriginal(srcJsonObject.getString("original"));
                wallpaperJsonDetails.setImageUrlPortrait(srcJsonObject.getString("portrait"));
                wallpaperJsonDetails.setImageUrlLandscape(srcJsonObject.getString("landscape"));
                wallpaperJsonDetails.setImageUrlLarge(srcJsonObject.getString("large"));
                wallpaperJsonDetails.setImageUrlLarge2X(srcJsonObject.getString("large2x"));
                wallpaperJsonDetails.setImageUrlSmall(srcJsonObject.getString("small"));
                wallpaperJsonDetails.setImageUrlTiny(srcJsonObject.getString("tiny"));
                wallpaperJsonDetails.setImageUrlSquare(srcJsonObject.getString("square"));
                wallpaperJsonDetailsArrayList.add(wallpaperJsonDetails);


            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return wallpaperJsonDetailsArrayList;
    }
}
