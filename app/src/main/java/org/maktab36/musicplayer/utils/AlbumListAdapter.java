package org.maktab36.musicplayer.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.controller.activity.SongListActivity;
import org.maktab36.musicplayer.model.Album;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumHolder> {
    private List<Album> mAlbumList;
    private Fragment mFragment;

    public void setAlbumList(List<Album> albumList) {
        mAlbumList = albumList;
    }

    public AlbumListAdapter(Fragment fragment, List<Album> albumList) {
        mFragment = fragment;
        mAlbumList = albumList;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragment.getActivity());
        View view = inflater.inflate(R.layout.list_row_album, parent, false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Album album = mAlbumList.get(position);
        holder.bindTask(album);
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        private Album mAlbum;
        private TextView mAlbumName;
        private TextView mNumberOfSong;
        private ImageView mAlbumListIcon;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);

            mAlbumName=itemView.findViewById(R.id.album_name);
            mNumberOfSong=itemView.findViewById(R.id.number_of_song);
            mAlbumListIcon=itemView.findViewById(R.id.album_list_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= SongListActivity.newIntent(mFragment.getActivity(),
                            mAlbum.getName(),
                            "album");
                    mFragment.startActivity(intent);
                }
            });
        }

        public void bindTask(Album album) {
            mAlbum=album;
            mAlbumName.setText(album.getName());
            String numberOfSong=mFragment.getString(R.string.number_of_song,
                    album.getNumberOfSong());
            mNumberOfSong.setText(numberOfSong);
            if(album.getCover()!=null) {
                mAlbumListIcon.setImageBitmap(album.getCover());
            }else{
                mAlbumListIcon.setImageDrawable(ResourcesCompat.getDrawable(mFragment.
                                getResources(),R.drawable.shape_album_list, null));
            }
        }
    }
}
