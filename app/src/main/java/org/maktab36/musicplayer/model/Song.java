package org.maktab36.musicplayer.model;


import android.media.MediaPlayer;
import android.provider.MediaStore;

public class Song {
    private long id;
    private String mPath;
    private String mTitle;
    private String mArtist;
    private String mAlbum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public Song() {
    }

    public Song(String path, String title, String artist, String album) {
        mPath = path;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
    }
}
