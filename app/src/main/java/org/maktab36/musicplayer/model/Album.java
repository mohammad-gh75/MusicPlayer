package org.maktab36.musicplayer.model;

import android.graphics.Bitmap;

public class Album {
    private String mName;
    private int mNumberOfSong;
    private Bitmap mCover;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getNumberOfSong() {
        return mNumberOfSong;
    }

    public void setNumberOfSong(int numberOfSong) {
        mNumberOfSong = numberOfSong;
    }

    public Bitmap getCover() {
        return mCover;
    }

    public void setCover(Bitmap cover) {
        mCover = cover;
    }

    public Album(String name, Bitmap cover) {
        this(name, 1, cover);
    }

    public Album(String name, int numberOfSong, Bitmap cover) {
        mName = name;
        mNumberOfSong = numberOfSong;
        mCover = cover;
    }
}
