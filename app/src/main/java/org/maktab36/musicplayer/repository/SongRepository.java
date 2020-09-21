package org.maktab36.musicplayer.repository;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.model.Album;
import org.maktab36.musicplayer.model.Artist;
import org.maktab36.musicplayer.model.Song;
import org.maktab36.musicplayer.model.SongRepeatStates;

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
    private List<Song> mCurrentPlayList;
    private SongRepeatStates mRepeatState;

    public static SongRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sSongRepository == null) {
            sSongRepository = new SongRepository();
        }
        return sSongRepository;
    }

    private SongRepository() {
        mRepeatState = SongRepeatStates.PLAY_IN_ORDER;
        mPlayer = new MediaPlayer();
        Uri externalContentUriUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        mSongList = new ArrayList<>();
        mArtistList = new ArrayList<>();
        mAlbumList = new ArrayList<>();
        getSongFromUri(externalContentUriUri);
    }

    public MediaPlayer getPlayer() {
        return mPlayer;
    }

    public SongRepeatStates getRepeatState() {
        return mRepeatState;
    }

    public void setRepeatState(SongRepeatStates repeatState) {
        mRepeatState = repeatState;
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
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                    String title = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String album = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    int albumId = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    Bitmap cover = getAlbumCover(path);
                    mSongList.add(new Song(path, title, artist, album, cover));
                    addToArtistList(artist,cover);
                    addToAlbumList(album,cover);
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }
    }

    private Bitmap getAlbumCover(String songPath) {
        Bitmap cover;
        byte[] byteCover;
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(songPath);
        byteCover = metadataRetriever.getEmbeddedPicture();
        if (byteCover != null) {
//            cover= PictureUtils.getScaledBitmap(byteCover,180,180);
            cover = BitmapFactory.decodeByteArray(byteCover, 0, byteCover.length);
        } else {
            /*cover = BitmapFactory
                    .decodeResource(mContext.getResources(), R.drawable.ic_song);*/
            cover=null;
        }

        /*Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(albumId)};
        Cursor cursor = mContext
                .getContentResolver()
                .query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

        if (cursor != null && cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String coverPath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    if (coverPath != null) {
                        cover = BitmapFactory.decodeFile(coverPath);
                    } else {
                        cover = BitmapFactory
                                .decodeResource(mContext.getResources(), R.drawable.ic_song);
                    }
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }*/
        return cover;
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

    private void addToArtistList(String name,Bitmap cover) {
        boolean equalityFlag = false;
        if (mArtistList.size() == 0) {
            Artist artist = new Artist(name,cover);
            mArtistList.add(artist);
        } else {
            for (Artist artist : mArtistList) {
                if (artist.getName().equals(name)) {
                    artist.setNumberOfSongs(artist.getNumberOfSongs() + 1);
                    equalityFlag = true;
                }
            }
            if (!equalityFlag) {
                Artist artist1 = new Artist(name,cover);
                mArtistList.add(artist1);
            }
        }
    }

    public List<Album> getAlbumList() {
        Collections.sort(mAlbumList, new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        return mAlbumList;
    }

    private void addToAlbumList(String name,Bitmap cover) {
        boolean equalityFlag = false;
        if (mAlbumList.size() == 0) {
            Album album = new Album(name,cover);
            mAlbumList.add(album);
        } else {
            for (Album album : mAlbumList) {
                if (album.getName().equals(name)) {
                    album.setNumberOfSong(album.getNumberOfSong() + 1);
                    equalityFlag = true;
                }
            }
            if (!equalityFlag) {
                Album album = new Album(name,cover);
                mAlbumList.add(album);
            }
        }
    }

    public List<Song> getArtistSongs(String artistName) {
        List<Song> artistSong = new ArrayList<>();
        for (Song song : mSongList) {
            if (song.getArtist().equals(artistName)) {
                artistSong.add(song);
            }
        }
        return artistSong;
    }

    public List<Song> getAlbumSongs(String albumName) {
        List<Song> albumSong = new ArrayList<>();
        for (Song song : mSongList) {
            if (song.getAlbum().equals(albumName)) {
                albumSong.add(song);
            }
        }
        return albumSong;
    }

    public List<Song> getCurrentPlayList() {
        return mCurrentPlayList;
    }

    public void setCurrentPlayList(List<Song> currentPlayList) {
        mCurrentPlayList = currentPlayList;
    }
}
