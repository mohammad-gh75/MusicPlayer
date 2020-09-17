package org.maktab36.musicplayer.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.maktab36.musicplayer.controller.fragment.SongListFragment;
import org.maktab36.musicplayer.model.Song;

import java.util.List;

public class SongListActivity extends SingleFragmentActivity {
    public static final String EXTRA_SONGS="extraSongs";
    public static final String EXTRA_TYPE="extraArtistOrAlbum";

    public static Intent newIntent(Context context, String Name,String type){
        Intent intent=new Intent(context,SongListActivity.class);
        intent.putExtra(EXTRA_SONGS,Name);
        intent.putExtra(EXTRA_TYPE,type);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        String Name=getIntent().getStringExtra(EXTRA_SONGS);
        String type=getIntent().getStringExtra(EXTRA_TYPE);
        return SongListFragment.newInstance(Name,type);
    }
}