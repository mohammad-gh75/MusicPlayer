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
import org.maktab36.musicplayer.model.Artist;
import org.maktab36.musicplayer.model.Song;
import org.maktab36.musicplayer.repository.SongRepository;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistHolder> {
    private List<Artist> mArtistList;
    private Fragment mFragment;

    public void setArtistList(List<Artist> artistList) {
        mArtistList = artistList;
    }

    public ArtistListAdapter(Fragment fragment, List<Artist> artistList) {
        mFragment = fragment;
        mArtistList = artistList;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragment.getActivity());
        View view = inflater.inflate(R.layout.list_row_artist, parent, false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        Artist artist = mArtistList.get(position);
        holder.bindTask(artist);
    }

    @Override
    public int getItemCount() {
        return mArtistList.size();
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {
        private Artist mArtist;
        private TextView mArtistName;
        private TextView mNumberOfSong;
        private ImageView mArtistListIcon;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);

            mArtistName=itemView.findViewById(R.id.artist_name);
            mNumberOfSong=itemView.findViewById(R.id.number_of_song);
            mArtistListIcon=itemView.findViewById(R.id.artist_list_icon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= SongListActivity.newIntent(mFragment.getActivity(),
                            mArtist.getName(),
                            "artist");
                    mFragment.startActivity(intent);
                }
            });
        }

        public void bindTask(Artist artist) {
            mArtist = artist;
            mArtistName.setText(artist.getName());
            String numberOfSong=mFragment.getString(R.string.number_of_song,
                    artist.getNumberOfSongs());
            mNumberOfSong.setText(numberOfSong);
            if(artist.getCover()!=null) {
                mArtistListIcon.setImageBitmap(artist.getCover());
            }else{
                mArtistListIcon.setImageDrawable(ResourcesCompat.getDrawable(mFragment.
                        getResources(),R.drawable.shape_artist_list, null));
            }
        }
    }
}
