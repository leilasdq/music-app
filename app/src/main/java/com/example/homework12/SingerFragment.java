package com.example.homework12;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingerFragment extends Fragment {


    public SingerFragment() {
        // Required empty public constructor
    }

    public static SingerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SingerFragment fragment = new SingerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

}
