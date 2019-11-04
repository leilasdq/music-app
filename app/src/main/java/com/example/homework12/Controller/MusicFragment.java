package com.example.homework12.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework12.Model.Music;
import com.example.homework12.MusicPrepare.LoadMusics;
import com.example.homework12.MusicPrepare.MusicManager;
import com.example.homework12.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    private ImageView bannerImage;
    private RecyclerView mRecyclerView;
    private List<Music> mMusicList = new ArrayList<>();
    private MusicAdapter mAdapter;
    private getBottomSheet mBottomSheet;
    private getBottomSheet size;

    private MusicManager mMusicManager;

    public static MusicFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  getBottomSheet){
            mBottomSheet = (getBottomSheet) context;
            size = (getBottomSheet) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBottomSheet = null;
        size = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mMusicList.clear();
        setupAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        setBanner(view);

        mRecyclerView = view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    private void setBanner(View view) {
        bannerImage = view.findViewById(R.id.banner);
        bannerImage.setImageResource(R.drawable.music_banner);
    }

    private void setupAdapter(){
        if (mAdapter == null){
            mMusicList = LoadMusics.getSortedMusic(getActivity());
            mAdapter = new MusicAdapter();
            mAdapter.setMusicList(mMusicList);
        } else mAdapter.notifyDataSetChanged();
    }

    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

        private List<Music> mMusicList;

        public void setMusicList(List<Music> musicList) {
            mMusicList = musicList;
        }

        @NonNull
        @Override
        public MusicAdapter.MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.musics_list_items, parent, false);
            return new MusicAdapter.MusicViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicAdapter.MusicViewHolder holder, int position) {
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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBottomSheet.onItemSelected(mMusic, mMusicList);
                        mRecyclerView.setPadding(8, 8, 8, size.bottomMarginSize());
                    }
                });
            }

            public void bindMusic(Music music){
                mMusic = music;

                musicName.setText(mMusic.getTitle());
                musicSinger.setText(mMusic.getSinger());
                if (mMusic.getPicPath() != null){
                    Bitmap bitmap = BitmapFactory.decodeFile(mMusic.getPicPath());
                    musicImage.setImageBitmap(bitmap);
                } else {
                    musicImage.setImageResource(R.drawable.no_cover);
                }
            }
        }
    }

    public void onPlaySelected(Music music, Context context, int currentPosition){
        mMusicManager = MusicManager.getInstance(context, mMusicList);
        mMusicManager.play(music.getId(), currentPosition);
    }

    public interface getBottomSheet{
        void onItemSelected(Music music, List<Music> musicList);
        int bottomMarginSize();
    }

}
