package com.siddhant.craftifywallpapers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;


import java.io.File;

import java.util.ArrayList;



public class GalleryRecyclerViewViewHolder extends RecyclerView.Adapter<GalleryRecyclerViewViewHolder.ViewHolder> {
   private Context context;
    private FragmentManager fragmentManager;
    public  ArrayList<WallpaperJsonDetails> wallpaperDetails;
    private  int width;
    private int height;



    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);



    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
            }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {

        super.onViewRecycled(holder);
        Log.i("ooo", String.valueOf(holder.getAdapterPosition()));

            if (PerformFav.getFavFile().contains(String.valueOf(wallpaperDetails.get(holder.getAdapterPosition()).getId()))) {
                holder.favouriteButton.setChecked(true);
                wallpaperDetails.get(holder.getAdapterPosition()).setFav(true);
            }
            else {
                holder.favouriteButton.setChecked(false);
                wallpaperDetails.get(holder.getAdapterPosition()).setFav(false);

            }


    }


    public GalleryRecyclerViewViewHolder(Context context, ArrayList<WallpaperJsonDetails> wallpaperDetails, FragmentManager supportFragmentManager, ArrayList<String> favourite) {
        this.context = context;

       this.wallpaperDetails = wallpaperDetails;
        this.fragmentManager = supportFragmentManager;
        try {
            PerformFav.getFavFile().clear();
            PerformFav.getFav().clear();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        String path = Environment.getExternalStorageDirectory()+context.getPackageResourcePath()+File.separator;
        PerformFav.readAllFav(new File(path));


    }

    @NonNull

    @Override
    public GalleryRecyclerViewViewHolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            viewHolder.favouriteButton.setChecked(false);
            width = 240;
            height = 200;
            Picasso.get().invalidate(wallpaperDetails.get(i).getImageStringPortrait());
            Picasso.get().load(wallpaperDetails.get(i).getImageStringPortrait()).resize(width,height).into(viewHolder.imageView);
                    try {

                        if(PerformFav.getFavFile().contains(String.valueOf(wallpaperDetails.get(i).getId()))){
                            viewHolder.favouriteButton.setChecked(true);
                            wallpaperDetails.get(i).setFav(true);
                        }
                    }
                    catch (NullPointerException ex){

                    }





        viewHolder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()+context.getPackageResourcePath()+File.separator;
                if(viewHolder.favouriteButton.isChecked()) {
                    PerformFav.addOneFavToList(wallpaperDetails,i, new File(path));
                }
                else {

                    PerformFav.removeOneFromFavList(wallpaperDetails,i, new File(path));
                }



            }
        });


        viewHolder.infoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"info",Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                BottomSheetDetails bottomSheetDetails = new BottomSheetDetails();
                bundle.putInt("position",i);
                bundle.putSerializable("wallpaperDetails",wallpaperDetails);
                bottomSheetDetails.setArguments(bundle);
                bottomSheetDetails.show(fragmentManager,"bottom_sheet");


            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PreviewActivity.class);
               intent.putExtra("wallpaperDetails",wallpaperDetails);
              intent.putExtra("position",i);
                intent.putExtra("status","on");
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return wallpaperDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private ImageView imageView;
        private CheckBox favouriteButton;
        private ImageButton infoImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewWallpaper);
            favouriteButton = itemView.findViewById(R.id.favouriteImageButton);
            infoImageButton = itemView.findViewById(R.id.infoImageButton);
        }

    }


}
