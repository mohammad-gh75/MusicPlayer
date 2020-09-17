package org.maktab36.musicplayer.model;

public class Album {
    private String mName;
    private int mNumberOfSong;

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

    public Album(String name) {
        this(name,1);
    }

    public Album(String name, int numberOfSong) {
        mName = name;
        mNumberOfSong = numberOfSong;
    }
}
