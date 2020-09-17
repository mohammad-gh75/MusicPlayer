package org.maktab36.musicplayer.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.model.Song;

import java.util.List;
import java.util.UUID;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongHolder> {
    private List<Song> mSongList;
    private Fragment mFragment;

    public void setSongList(List<Song> songList) {
        mSongList = songList;
    }

    public SongListAdapter(Fragment fragment, List<Song> songList) {
        mFragment = fragment;
        mSongList = songList;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragment.getActivity());
        View view = inflater.inflate(R.layout.list_row_song, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        Song song = mSongList.get(position);
        holder.bindTask(song);
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        private Song mSong;
        private TextView mSongTitle;
        private TextView mSongArtistAndAlbum;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            mSongTitle=itemView.findViewById(R.id.song_title);
            mSongArtistAndAlbum=itemView.findViewById(R.id.song_artist_and_album);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void bindTask(Song song) {
            mSong = song;
            mSongTitle.setText(song.getTitle());
            String artistAndAlbum=mFragment.getString(R.string.song_artist_and_album,
                    song.getArtist(),song.getAlbum());
            mSongArtistAndAlbum.setText(artistAndAlbum);
        }
    }
}
