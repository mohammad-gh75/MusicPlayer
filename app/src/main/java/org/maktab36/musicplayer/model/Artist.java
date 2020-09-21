package org.maktab36.musicplayer.model;

import android.graphics.Bitmap;

public class Artist {
    private String mName;
    private int mNumberOfSongs;
    private Bitmap mCover;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getNumberOfSongs() {
        return mNumberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        mNumberOfSongs = numberOfSongs;
    }

    public Bitmap getCover() {
        return mCover;
    }

    public void setCover(Bitmap cover) {
        mCover = cover;
    }
    public Artist(String name, int numberOfSongs,Bitmap cover) {
        mName = name;
        mNumberOfSongs = numberOfSongs;
        mCover=cover;
    }

    public Artist(String name,Bitmap cover) {
        this(name,1,cover);
    }

}
