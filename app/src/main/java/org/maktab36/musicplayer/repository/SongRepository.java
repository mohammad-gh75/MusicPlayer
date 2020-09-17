package org.maktab36.musicplayer.repository;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import org.maktab36.musicplayer.model.Album;
import org.maktab36.musicplayer.model.Artist;
import org.maktab36.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongRepository {
    private static SongRepository sSongRepository;
    private static Context mContext;
    private MediaPlayer mPlayer;
    private List<Song> mSongList;
    private List<Artist> mArtistList;
    private List<Album> mAlbumList;
//    private TaskDataBase mDatabase;

    public static SongRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sSongRepository == null) {
            sSongRepository = new SongRepository();
        }
        return sSongRepository;
    }

    private SongRepository() {
        /*mDatabase = Room.databaseBuilder(mContext,
                TaskDataBase.class,
                "TaskManagerDB")
                .allowMainThreadQueries()
                .build();*/
        Uri externalContentUriUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Uri internalContentUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        mSongList = new ArrayList<>();
        mArtistList = new ArrayList<>();
        mAlbumList=new ArrayList<>();
        getSongFromUri(externalContentUriUri);
//        getSongFromUri(internalContentUri);
    }

    public void getSongFromUri(Uri uri) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = mContext
                .getContentResolver()
                .query(uri,
                        null,
                        selection,
                        null,
                        sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                    mSongList.add(new Song(path, title, artist, album));
                    addToArtistList(artist);
                    addToAlbumList(album);
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }
    }

    public List<Song> getSongList() {
        return mSongList;
    }

    public void setSongList(List<Song> songList) {
        mSongList = songList;
    }

    public List<Artist> getArtistList() {
        Collections.sort(mArtistList, new Comparator<Artist>() {
            @Override
            public int compare(Artist o1, Artist o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        return mArtistList;
    }

    private void addToArtistList(String name) {
        boolean equalityFlag = false;
        if (mArtistList.size() == 0) {
            Artist artist = new Artist(name);
            mArtistList.add(artist);
        } else {
            for (Artist artist : mArtistList) {
                if (artist.getName().equals(name)) {
                    artist.setNumberOfSongs(artist.getNumberOfSongs() + 1);
                    equalityFlag = true;
                }
            }
            if (!equalityFlag) {
                Artist artist1 = new Artist(name);
                mArtistList.add(artist1);
            }
        }
    }

    public List<Album> getAlbumList(){
        Collections.sort(mAlbumList, new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        return mAlbumList;
    }

    private void addToAlbumList(String name){
        boolean equalityFlag = false;
        if (mAlbumList.size() == 0) {
            Album album=new Album(name);
            mAlbumList.add(album);
        } else {
            for (Album album : mAlbumList) {
                if (album.getName().equals(name)) {
                    album.setNumberOfSong(album.getNumberOfSong()+1);
                    equalityFlag = true;
                }
            }
            if (!equalityFlag) {
                Album album=new Album(name);
                mAlbumList.add(album);
            }
        }
    }

    public List<Song> getArtistSongs(String artistName){
        List<Song> artistSong=new ArrayList<>();
        for (Song song :mSongList) {
            if(song.getArtist().equals(artistName)){
                artistSong.add(song);
            }
        }
        return artistSong;
    }
}
