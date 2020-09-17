package org.maktab36.musicplayer.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.model.Artist;
import org.maktab36.musicplayer.model.Song;
import org.maktab36.musicplayer.repository.SongRepository;
import org.maktab36.musicplayer.utils.ArtistListAdapter;
import org.maktab36.musicplayer.utils.SongListAdapter;

import java.util.List;


public class ArtistListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArtistListAdapter mAdapter;
    private List<Artist> mArtists;
    private SongRepository mRepository;

    public ArtistListFragment() {
        // Required empty public constructor
    }


    public static ArtistListFragment newInstance() {
        ArtistListFragment fragment = new ArtistListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = SongRepository.getInstance(getActivity());
        mArtists = mRepository.getArtistList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_artist_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_artist);
    }

    private void updateUI() {
        mArtists = mRepository.getArtistList();
        if (mAdapter == null) {
            mAdapter = new ArtistListAdapter(this,mArtists);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setArtistList(mArtists);
            mAdapter.notifyDataSetChanged();
        }
    }
}