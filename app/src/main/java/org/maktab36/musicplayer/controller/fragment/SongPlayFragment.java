package org.maktab36.musicplayer.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.model.Song;

public class SongPlayFragment extends Fragment {
    public static final String ARG_SONG="argSong";
    private Song mSong;
    private ImageView mSongIcon;
    private TextView mSongTitle;
    private TextView mArtistName;
    private SeekBar mSeekBar;
    private TextView mElapsedTime;
    private TextView mSongDuration;
    private ImageButton mButtonPlay;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonRepeat;


    public SongPlayFragment() {
        // Required empty public constructor
    }

    public static SongPlayFragment newInstance(Song song) {
        SongPlayFragment fragment = new SongPlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SONG,song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSong= (Song) getArguments().getSerializable(ARG_SONG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_song_play, container, false);

        findViews(view);
        initUI();

        return view;
    }

    private void findViews(View view) {
        mSongIcon=view.findViewById(R.id.song_icon);
        mSongTitle=view.findViewById(R.id.song_title);
        mArtistName=view.findViewById(R.id.song_artist);
        mElapsedTime=view.findViewById(R.id.elapsed_time);
        mSongDuration=view.findViewById(R.id.song_duration);
        mSeekBar=view.findViewById(R.id.seekBar);
        mButtonPlay=view.findViewById(R.id.play_song);
        mButtonPrevious=view.findViewById(R.id.previous_song);
        mButtonNext=view.findViewById(R.id.next_song);
        mButtonRepeat=view.findViewById(R.id.repeat_shuffle);
    }

    private void initUI(){
        mSongTitle.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist());
    }
}