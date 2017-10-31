package com.example.ramya_5542.mylibrary.Activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramya_5542.mylibrary.R;

public class MyProfileFragment extends Fragment {
    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        setHasOptionsMenu(true);
        SharedPreferences myPref = getActivity().getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        String user=  myPref.getString("username", "notfound");


        return view;
    }


}
