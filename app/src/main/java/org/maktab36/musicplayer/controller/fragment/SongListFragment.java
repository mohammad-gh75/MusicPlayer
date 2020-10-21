package org.maktab36.musicplayer.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.adapters.SongListAdapter;
import org.maktab36.musicplayer.model.Song;
import org.maktab36.musicplayer.repository.SongRepository;

import java.util.List;


public class SongListFragment extends Fragment {
    public static final String ARG_NAME = "argName";
    public static final String ARG_TYPE = "argType";
    private RecyclerView mRecyclerView;
    private SongListAdapter mAdapter;
    private List<Song> mSongs;
    private SongRepository mRepository;

    public SongListFragment() {
        // Required empty public constructor
    }

    public static SongListFragment newInstance(String Name, String type) {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, Name);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = SongRepository.getInstance(getActivity());
        getSongs();
    }

    private void getSongs() {
        String name = getArguments().getString(ARG_NAME);
        String type = getArguments().getString(ARG_TYPE);
        if (name == null || type == null) {
            mSongs = mRepository.getSongList();
        } else if (type.toLowerCase().equals("artist")) {
            mSongs = mRepository.getArtistSongs(name);
        } else if (type.toLowerCase().equals("album")) {
            mSongs = mRepository.getAlbumSongs(name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }


    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_song);
    }

    private void updateUI() {
        getSongs();
        if (mAdapter == null) {
            mAdapter = new SongListAdapter(this, mSongs);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setSongList(mSongs);
            mAdapter.notifyDataSetChanged();
        }
    }
}