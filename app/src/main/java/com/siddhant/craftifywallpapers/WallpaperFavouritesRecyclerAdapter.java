package com.siddhant.craftifywallpapers;

import android.content.Context;
import android.content.Intent;
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



public class WallpaperFavouritesRecyclerAdapter extends RecyclerView.Adapter<WallpaperFavouritesRecyclerAdapter.ViewHolder> {
    private final ArrayList<WallpaperJsonDetails> wallpaperJsonDetails = new ArrayList<>();
    private int width,height;
    Context context ;
    private ArrayList<String> favourites;
    FragmentManager fragmentManager;


    public WallpaperFavouritesRecyclerAdapter(Context context,ArrayList<String>favourites,FragmentManager fragmentManager) {
        this.context = context;
        this.favourites = favourites;
        this.fragmentManager = fragmentManager;
        for (String x :favourites ){
            WallpaperJsonDetails jsonDetails = new WallpaperJsonDetails();
            jsonDetails.setImageUrlPortrait(x);
            wallpaperJsonDetails.add(jsonDetails);
        }
    int c = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_layout,viewGroup,false);

        return new WallpaperFavouritesRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.favouriteButton.setChecked(true);
        width = 240;
        height = 200;

        try {
            Picasso.get().load(favourites.get(i)).resize(width, height).into(viewHolder.imageView);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        viewHolder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            String path = Environment.getExternalStorageDirectory()+context.getPackageResourcePath()+File.separator;
            @Override
            public void onClick(View v) {
                if(viewHolder.favouriteButton.isChecked()){


                }
                else {
                    PerformFav.delFileFromFav(PerformFav.getFavFile().get(i), new File(path));
                    favourites.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i,favourites.size());


                }
            }
        });

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PreviewActivity.class);
                intent.putExtra("wallpaperDetails",wallpaperJsonDetails);
                intent.putExtra("position",i);
                intent.putExtra("status","off");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private CheckBox favouriteButton;
        private ImageButton infoImageButton;
        // private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // cardView = itemView.findViewById(R.id.cardViewInfoAndFav);
            imageView = itemView.findViewById(R.id.imageViewWallpaper);
            favouriteButton = itemView.findViewById(R.id.favouriteImageButton);
            infoImageButton = itemView.findViewById(R.id.infoImageButton);
            //  cardView.setAlpha(0.5f);
            // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.infoImageButton){


            }
            else if (v.getId() == R.id.favouriteImageButton);{
                Toast.makeText(context,"fav",Toast.LENGTH_SHORT).show();
                Log.i("id",String.valueOf(v.getId()));

            }
        }
    }
}
