package org.maktab36.musicplayer.model;

public class Artist {
    private String mName;
    private int mNumberOfSongs;

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

    public Artist(String name, int numberOfSongs) {
        mName = name;
        mNumberOfSongs = numberOfSongs;
    }

    public Artist(String name) {
        this(name,1);
    }

}
