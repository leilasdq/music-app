package com.example.homework12.Controller;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.homework12.Adapter.MusicAdapter;
import com.example.homework12.Model.Music;
import com.example.homework12.MusicPrepare.LoadMusics;
import com.example.homework12.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    private ImageView bannerImage;
    private RecyclerView mRecyclerView;
    private List<Music> mMusicList;
    private MusicAdapter mAdapter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicList = LoadMusics.getSortedMusic(getActivity());
        mAdapter = new MusicAdapter();
        mAdapter.setMusicList(getActivity(), mMusicList);
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

}
