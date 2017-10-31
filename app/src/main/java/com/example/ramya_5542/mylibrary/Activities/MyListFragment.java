package com.example.ramya_5542.mylibrary.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramya_5542.mylibrary.R;
public class MyListFragment extends Fragment {
    public static MyListFragment newInstance() {
        MyListFragment fragment = new MyListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mylist, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("My List");
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("FAVORITES"));
        tabLayout.addTab(tabLayout.newTab().setText("READING"));
        tabLayout.addTab(tabLayout.newTab().setText("WISHLIST"));

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PagerAdapterMyList adapter = new PagerAdapterMyList(
                getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }
}