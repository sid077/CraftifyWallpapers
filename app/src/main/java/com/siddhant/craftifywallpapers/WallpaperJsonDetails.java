package com.siddhant.craftifywallpapers;


import android.app.Application;

import java.io.Serializable;

public class WallpaperJsonDetails extends Application implements Serializable {
    private int id ;
    private int width;
    private int height;

    public Boolean getFav() {
        return isFav;
    }


    public void setFav(Boolean fav) {
        isFav = fav;
    }

    private Boolean isFav=false;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrlOriginal;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageStringOriginal() {
        return imageUrlOriginal;
    }

    public void setImageStringOriginal(String imageStringOriginal) {
        this.imageUrlOriginal = imageStringOriginal;
    }

    public String getImageUrlLarge2X() {
        return imageUrlLarge2X;
    }

    public void setImageUrlLarge2X(String imageUrlLarge2X) {
        this.imageUrlLarge2X = imageUrlLarge2X;
    }

    public String getImageUrlLarge() {
        return imageUrlLarge;
    }

    public void setImageUrlLarge(String imageUrlLarge) {
        this.imageUrlLarge = imageUrlLarge;
    }

    public String getImageStringMedium() {
        return imageUrlMedium;
    }

    public void setImageStringMedium(String imageStringMedium) {
        this.imageUrlMedium = imageStringMedium;
    }

    public String getImageUrlSmall() {
        return imageUrlSmall;
    }

    public void setImageUrlSmall(String imageUrlSmall) {
        this.imageUrlSmall = imageUrlSmall;
    }

    public String getImageStringPortrait() {
        return imageUrlPortrait;
    }

    public void setImageUrlPortrait(String imageUrlPortrait) {
        this.imageUrlPortrait = imageUrlPortrait;
    }

    public String getImageUrlSquare() {
        return imageUrlSquare;
    }

    public void setImageUrlSquare(String imageUrlSquare) {
        this.imageUrlSquare = imageUrlSquare;
    }

    public String getImageUrlTiny() {
        return imageUrlTiny;
    }

    public void setImageUrlTiny(String imageUrlTiny) {
        this.imageUrlTiny = imageUrlTiny;
    }

    public String getImageUrlLandscape() {
        return imageUrlLandscape;
    }

    public void setImageUrlLandscape(String imageUrlLandscape) {
        this.imageUrlLandscape = imageUrlLandscape;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public String getPhotoGrapherUrl() {
        return PhotoGrapherUrl;
    }

    public void setPhotoGrapherString(String photoGrapherString) {
        PhotoGrapherUrl = photoGrapherString;
    }

    private String imageUrlLarge2X;
    private String imageUrlLarge;
    private String imageUrlMedium;
    private String imageUrlSmall;
    private String imageUrlPortrait;
    private String imageUrlSquare;
    private String imageUrlTiny;
    private String imageUrlLandscape;
    private String photographerName;
    private String PhotoGrapherUrl;
}
