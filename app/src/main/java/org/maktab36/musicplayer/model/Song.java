package org.maktab36.musicplayer.model;


import android.graphics.Bitmap;

public class Song {
    private String mPath;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    //    private String mCoverPath;
    private Bitmap mCover;

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

    /*public String getCoverPath() {
        return mCoverPath;
    }

    public void setCoverPath(String coverPath) {
        mCoverPath = coverPath;
    }*/

    public Song() {
    }

    /*public Song(String path, String title, String artist, String album, String coverPath) {
        mPath = path;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mCoverPath = coverPath;
    }*/

    public Bitmap getCover() {
        return mCover;
    }

    public void setCover(Bitmap cover) {
        mCover = cover;
    }

    public Song(String path, String title, String artist, String album, Bitmap cover) {
        mPath = path;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mCover = cover;
    }
}
