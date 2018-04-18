package com.example.admin.healofytest.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.healofytest.R;
import com.example.admin.healofytest.model.Song;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by admin on 18/04/18.
 */

public class SongViewAdapter extends RealmRecyclerViewAdapter<Song,SongViewAdapter.MyViewHolder> {


    public SongViewAdapter(@Nullable OrderedRealmCollection<Song> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song song = getItem(position);
        holder.nameView.setText(song.getName());
        holder.artistView.setText(song.getArtist());
        holder.albumView.setText(song.getAlbum());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView artistView;
        TextView albumView;

        MyViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            artistView= (TextView) view.findViewById(R.id.artist);
            albumView  = (TextView) view.findViewById(R.id.album);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }





}

