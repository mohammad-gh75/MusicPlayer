package org.maktab36.musicplayer.controller.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.model.Song;
import org.maktab36.musicplayer.model.SongRepeatStates;
import org.maktab36.musicplayer.repository.SongRepository;

import java.util.List;
import java.util.Random;

public class SongPlayFragment extends Fragment {
    public static final String ARG_SONG_POSITION = "argSongPosition";
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
    private MediaPlayer mPlayer;
    private SongRepeatStates mRepeatState;
    private SongRepository mRepository;
    private int mCurrentSongPosition;

    private List<Song> mPlayList;


    public SongPlayFragment() {
        // Required empty public constructor
    }

    public static SongPlayFragment newInstance(int position) {
        SongPlayFragment fragment = new SongPlayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SONG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = SongRepository.getInstance(getActivity());
        mCurrentSongPosition = getArguments().getInt(ARG_SONG_POSITION);
        mPlayList = mRepository.getCurrentPlayList();
        setCurrentSong(mCurrentSongPosition);
        mPlayer = mRepository.getPlayer();
        mRepeatState = mRepository.getRepeatState();
        mPlayer.reset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_play, container, false);

        findViews(view);
        updateUI();
        setListeners();

        playSong();
        return view;
    }


    private void findViews(View view) {
        mSongIcon = view.findViewById(R.id.song_icon);
        mSongTitle = view.findViewById(R.id.song_title);
        mArtistName = view.findViewById(R.id.song_artist);
        mElapsedTime = view.findViewById(R.id.elapsed_time);
        mSongDuration = view.findViewById(R.id.song_duration);
        mSeekBar = view.findViewById(R.id.seekBar);
        mButtonPlay = view.findViewById(R.id.play_song);
        mButtonPrevious = view.findViewById(R.id.previous_song);
        mButtonNext = view.findViewById(R.id.next_song);
        mButtonRepeat = view.findViewById(R.id.repeat_shuffle);
    }

    private void updateUI() {
        mPlayer.reset();
        mSongTitle.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist());
        if (mSong.getCover() != null) {
            mSongIcon.setImageBitmap(mSong.getCover());
        } else {
            mSongIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.shape_play_page, null));
        }

        switch (mRepeatState) {
            case PLAY_IN_ORDER:
                mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_arrow, null));
                break;
            case REPEAT_ALL:
                mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_repeat, null));
                break;
            case REPEAT_ONE:
                mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_repeat_one, null));
                break;
            case SHUFFLE:
                mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_shuffle, null));
                break;
        }

        try {
            mPlayer.setDataSource(mSong.getPath());
            mPlayer.prepare();
            mSeekBar.setMax(mPlayer.getDuration() / 1000);
            final Handler handler = new Handler();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mPlayer != null) {
                        mSeekBar.setProgress(mPlayer.getCurrentPosition() / 1000);
                    }
                    handler.post(this);
                }
            });
            mSongDuration.setText(getTimeText(mPlayer.getDuration()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListeners() {
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    pauseSong();
                } else {
                    playSong();
                }
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSong();
            }
        });
        mButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mRepeatState) {
                    case PLAY_IN_ORDER:
                        mRepeatState = SongRepeatStates.SHUFFLE;
                        mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_shuffle, null));
                        Toast.makeText(getActivity(),
                                "Shuffle", Toast.LENGTH_SHORT).show();
                        break;
                    case REPEAT_ALL:
                        mRepeatState = SongRepeatStates.REPEAT_ONE;
                        mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_repeat_one, null));
                        Toast.makeText(getActivity(),
                                "Repeat current song", Toast.LENGTH_SHORT).show();
                        break;
                    case REPEAT_ONE:
                        mRepeatState = SongRepeatStates.PLAY_IN_ORDER;
                        mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_arrow, null));
                        Toast.makeText(getActivity(),
                                "Play in order", Toast.LENGTH_SHORT).show();
                        break;
                    case SHUFFLE:
                        mRepeatState = SongRepeatStates.REPEAT_ALL;
                        mButtonRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_repeat, null));
                        Toast.makeText(getActivity(),
                                "Repeat list", Toast.LENGTH_SHORT).show();
                        break;
                }
                mRepository.setRepeatState(mRepeatState);
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                switch (mRepeatState) {
                    case PLAY_IN_ORDER:
                        mCurrentSongPosition++;
                        if (mCurrentSongPosition == mPlayList.size()) {
                            mp.pause();
                            mButtonPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.ic_play, null));
                        } else {
                            setCurrentSong(mCurrentSongPosition);
                            updateUI();
                            playSong();
                        }
                        break;
                    case REPEAT_ALL:
                        mCurrentSongPosition++;
                        if (mCurrentSongPosition == mPlayList.size()) {
                            mCurrentSongPosition = 0;
                        }
                        setCurrentSong(mCurrentSongPosition);
                        updateUI();
                        playSong();
                        break;
                    case REPEAT_ONE:
                        mPlayer.start();
                        break;
                    case SHUFFLE:
                        Random random = new Random();
                        int rand;
                        do {
                            rand = random.nextInt(mPlayList.size());
                        } while (mCurrentSongPosition == rand);
                        mCurrentSongPosition = rand;
                        setCurrentSong(mCurrentSongPosition);
                        updateUI();
                        playSong();
                        break;
                }
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    mPlayer.seekTo(progress * 1000);
                }
                mElapsedTime.setText(getTimeText(progress * 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void nextSong() {
        if (mRepeatState == SongRepeatStates.SHUFFLE) {
            if (mPlayList.size() != 1) {
                Random random = new Random();
                int rand;
                do {
                    rand = random.nextInt(mPlayList.size());
                } while (mCurrentSongPosition == rand);
                mCurrentSongPosition = rand;
            }
        } else {
            mCurrentSongPosition++;
            if (mCurrentSongPosition == mPlayList.size()) {
                mCurrentSongPosition = 0;
            }
        }
        setCurrentSong(mCurrentSongPosition);
        updateUI();
        playSong();
    }

    private void previousSong() {
        if (mRepeatState == SongRepeatStates.SHUFFLE) {
            if (mPlayList.size() != 1) {
                Random random = new Random();
                int rand;
                do {
                    rand = random.nextInt(mPlayList.size());
                } while (mCurrentSongPosition == rand);
                mCurrentSongPosition = rand;
            }
        } else {
            mCurrentSongPosition--;
            if (mCurrentSongPosition == -1) {
                mCurrentSongPosition = mPlayList.size() - 1;
            }
        }
        setCurrentSong(mCurrentSongPosition);
        updateUI();
        playSong();
    }

    private void pauseSong() {
        mPlayer.pause();
        mButtonPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_play, null));
    }

    private void playSong() {
        mPlayer.start();
        mButtonPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_pause, null));

    }

    private String getTimeText(int milis) {
        StringBuilder timeText = new StringBuilder();
        int seconds = milis / 1000;
        int minutes = seconds / 60;
        seconds %= 60;
        if (minutes < 10) {
            timeText.append("0").append(minutes);
        } else {
            timeText.append(minutes);
        }
        timeText.append(":");
        if (seconds < 10) {
            timeText.append("0").append(seconds);
        } else {
            timeText.append(seconds);
        }
        return timeText.toString();
    }

    private void setCurrentSong(int position) {
        mSong = mPlayList.get(position);
    }
}