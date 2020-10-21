package org.maktab36.musicplayer.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.adapters.AlbumListAdapter;
import org.maktab36.musicplayer.model.Album;
import org.maktab36.musicplayer.repository.SongRepository;

import java.util.List;

public class AlbumListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AlbumListAdapter mAdapter;
    private List<Album> mAlbums;
    private SongRepository mRepository;


    public AlbumListFragment() {
        // Required empty public constructor
    }


    public static AlbumListFragment newInstance() {
        AlbumListFragment fragment = new AlbumListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = SongRepository.getInstance(getActivity());
        mAlbums = mRepository.getAlbumList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_album);
    }

    private void updateUI() {
        mAlbums = mRepository.getAlbumList();
        if (mAdapter == null) {
            mAdapter = new AlbumListAdapter(this, mAlbums);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAlbumList(mAlbums);
            mAdapter.notifyDataSetChanged();
        }
    }
}