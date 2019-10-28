package com.example.homework12.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework12.Model.Music;
import com.example.homework12.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private List<Music> mMusicList;
    private Context mContext;

    public void setMusicList(Context context, List<Music> musicList) {
        mContext = context;
        mMusicList = musicList;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.musics_list_items, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.bindMusic(mMusicList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder{
        private Music mMusic;
        private ImageView musicImage;
        private TextView musicName;
        private TextView musicSinger;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            musicImage = itemView.findViewById(R.id.music_img);
            musicName = itemView.findViewById(R.id.music_name);
            musicSinger = itemView.findViewById(R.id.singer_name);
        }

        public void bindMusic(Music music){
            mMusic = music;

            musicName.setText(mMusic.getTitle());
            musicSinger.setText(mMusic.getSinger());
        }
    }
}
