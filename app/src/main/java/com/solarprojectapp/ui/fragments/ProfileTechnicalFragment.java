package com.solarprojectapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solarprojectapp.R;

import butterknife.ButterKnife;


public class ProfileTechnicalFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";

    public ProfileTechnicalFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home_page, container, false);
        ButterKnife.bind(this, view);
        return view;

    }







}