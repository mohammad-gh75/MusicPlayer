package org.maktab36.musicplayer.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.maktab36.musicplayer.controller.fragment.SongPlayFragment;

public class SongPlayActivity extends SingleFragmentActivity {
    public static final String EXTRA_SONG_POSITION = "extraSongPosition";

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, SongPlayActivity.class);
        intent.putExtra(EXTRA_SONG_POSITION, position);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        int position = getIntent().getIntExtra(EXTRA_SONG_POSITION, 0);
        return SongPlayFragment.newInstance(position);
    }
}