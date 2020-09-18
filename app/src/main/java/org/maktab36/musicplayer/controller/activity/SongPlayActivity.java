package org.maktab36.musicplayer.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.maktab36.musicplayer.controller.fragment.SongPlayFragment;
import org.maktab36.musicplayer.model.Song;

public class SongPlayActivity extends SingleFragmentActivity {
    public static final String EXTRA_SONG="extraSong";

    public static Intent newIntent(Context context, Song song) {
        Intent intent=new Intent(context,SongPlayActivity.class);
        intent.putExtra(EXTRA_SONG,song);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        Song song= (Song) getIntent().getSerializableExtra(EXTRA_SONG);
        return SongPlayFragment.newInstance(song);
    }
}