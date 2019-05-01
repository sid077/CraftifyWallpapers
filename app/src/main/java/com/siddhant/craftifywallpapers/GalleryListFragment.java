package com.siddhant.craftifywallpapers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GalleryListFragment extends Fragment {
    private static final String SEARCH_QUERY_FIRST = "https://api.pexels.com/v1/search?query=";
    private static final String SEARCH_QUERY_LAST = "+query&per_page=40&page=";
    private SetupConnection setupConnection;
    private String keyword = "";
    private String finalSearchQuery = "";
    private ArrayList<String> favourite;
    private ArrayList<WallpaperJsonDetails> wallpaperJsonDetailsArrayList;
    private RecyclerView recyclerView;


    public static GalleryListFragment getInstance(String query) {

        Bundle args = new Bundle();
        args.putString("keyword", query);
        GalleryListFragment galleryListFragment = new GalleryListFragment();
        galleryListFragment.setArguments(args);
        return galleryListFragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_gallery_view, container, false);
        recyclerView = rootview.findViewById(R.id.recyclerViewGallery);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        recyclerView.setLayoutManager(manager);

        recyclerView.setHasFixedSize(true);
        ((GridLayoutManager) manager).setSmoothScrollbarEnabled(true);


        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();






        keyword = "popular";

        Bundle args = getArguments();
        args = getArguments();
        keyword = args.getString("keyword", "popular");


        finalSearchQuery = SEARCH_QUERY_FIRST + keyword + SEARCH_QUERY_LAST;
        setupConnection = new SetupConnection(getActivity().getApplicationContext());

        try {
            wallpaperJsonDetailsArrayList = setupConnection.execute(finalSearchQuery).get();
            writeArrayList();
            recyclerView.setAdapter(new GalleryRecyclerViewViewHolder(getActivity().getApplicationContext(), wallpaperJsonDetailsArrayList, getActivity().getSupportFragmentManager(), favourite));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void writeArrayList() {
        final File path = new File(Environment.getExternalStorageDirectory() + getActivity().getApplicationContext().getPackageResourcePath() +
                File.separator);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                path.mkdirs();
                File file = new File(path + "/" + "slideshow");

                if (!file.exists()) {
                    //file.mkdirs();
                    try {
                        file.createNewFile();
                        FileWriter writer = new FileWriter(file);
                        for (WallpaperJsonDetails x : wallpaperJsonDetailsArrayList) {
                            writer.write(x.getImageStringPortrait() + "\r\n");
                        }
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

}
