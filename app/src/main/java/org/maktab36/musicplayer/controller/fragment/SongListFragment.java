package org.maktab36.musicplayer.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.model.Song;
import org.maktab36.musicplayer.repository.SongRepository;
import org.maktab36.musicplayer.utils.SongListAdapter;

import java.util.List;


public class SongListFragment extends Fragment {
    /*public static final int TASK_DETAIL_REQUEST_CODE = 0;
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";*/
    private RecyclerView mRecyclerView;
    private SongListAdapter mAdapter;
    private List<Song> mSongs;
    //    private ConstraintLayout mEmptyListLayout;
    private SongRepository mRepository;

    public SongListFragment() {
        // Required empty public constructor
    }

    public static SongListFragment newInstance() {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = SongRepository.getInstance(getActivity());
        mSongs = mRepository.getSongList();
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
        mSongs = mRepository.getSongList();
        if (mAdapter == null) {
            mAdapter = new SongListAdapter(this,mSongs);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setSongList(mSongs);
            mAdapter.notifyDataSetChanged();
        }
    }
}